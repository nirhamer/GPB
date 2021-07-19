package ru.iitdgroup.gpb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UseCase2 {

    private static final int BUFFER_SIZE = 4096; // 4KB
    private static final String AS_ROOT = ".";

    public static MessageDigest fileDigest;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length == 1) step_2(new File(args[0]));
        else throw new IllegalArgumentException("Wrong arguments");}

    static List<String> step_2(File snapshotFile) throws NoSuchAlgorithmException, IOException {

        final List<String> allLines = Files.readAllLines(Paths.get(String.valueOf(snapshotFile)));

        MessageDigest md = MessageDigest.getInstance("SHA-256");


        final int SKIP_FILE_HASH_AND_PREV_EMPTY_LINE = 2;
        for (int i = 0; i < allLines.size() - SKIP_FILE_HASH_AND_PREV_EMPTY_LINE; i++) {
            String line = allLines.get(i);
            md.update(line.getBytes(StandardCharsets.UTF_8));
        }

        String newHash = hash2string(md.digest());
        String oldHash = allLines.get(allLines.size()-1);


        if (oldHash.equals(newHash)) {
            System.out.println("all is good");

        } else throw new IllegalArgumentException("Snapshot is corrupted");


        // Create an empty hash map
        HashMap<File, String> map = new HashMap<>();

        // Add elements to the map
        map.put(snapshotFile, " this is the snapshotFile file");


        // Print size and content
        System.out.println("Size of map is: "
                + map.size());
        System.out.println(map);



        fileDigest = MessageDigest.getInstance("SHA-256");
        PrintWriter DataHolder = new PrintWriter(snapshotFile);
        Files.walk(Path.of(AS_ROOT))
                .filter(s -> Files.isRegularFile(s, LinkOption.NOFOLLOW_LINKS))
                .filter(UseCase2::checkPath)
                .forEach(path -> {
                    try {
                        readFile(path.toString(), DataHolder);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });


        DataHolder.println("\n" + hash2string(fileDigest.digest()));
        DataHolder.close();



        List<String> result;
        try (Stream<String> lines = Files.lines(Paths.get(String.valueOf(snapshotFile)))) {
            result = lines.collect(Collectors.toList());
        }
        System.out.println();
        return result;





    }




    static Set<String> exclusionsSet = new HashSet<>();
     {
        //region reading the exclusions file
        File myObj = new File("exclusions.txt");
        Scanner exclusionsReader;
        try {
            exclusionsReader = new Scanner(myObj);

            while (exclusionsReader.hasNextLine()) {
                String data = exclusionsReader.nextLine();
                exclusionsSet.add(data);
            }
            exclusionsReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("no exclusions.txt file found continuing with the scan");
        }
        //endregion
    }


    static boolean checkPath(Path path) {
        Set<String> exclusionsSet = new HashSet<>();
        for (String exclusion : exclusionsSet) {
            if (path.startsWith(exclusion)) {
                return false;
            }
        }
        return true;

    }

    /**
     * reads file and calculates its hash and writes all its details to provided PrintWriter
     * @param filename is a String that represents the name of the file in this case being snapshot
     * @param snapshotFileWriter is a class that allows us to to write formatted data to an underlying Writer For instance writing the data of filename and the hexhash
     * @throws IOException an Exception that is thrown when there has been an Input/Output (usually when working with files) error.
     * @throws NoSuchAlgorithmException an exception that is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
     */
    public static void readFile(String filename, PrintWriter snapshotFileWriter) throws IOException, NoSuchAlgorithmException {
        BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(Path.of(filename)));
        MessageDigest SHA256 = MessageDigest.getInstance("SHA-256");
        while (true) {
            byte[] chunk = stream.readNBytes(BUFFER_SIZE);
            SHA256.update(chunk);


            if (chunk.length != BUFFER_SIZE) {
                byte[] hash = SHA256.digest();
                StringBuilder hexhash = new StringBuilder();
                for (int i = 0; i < hash.length; i++) {
                    hexhash.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
                final String fileNameAndHash = filename + "\t" + hexhash;
                snapshotFileWriter.println(fileNameAndHash); // Note "\t is tab, \n is new line, \r is for cartridge return";
                fileDigest.update(fileNameAndHash.getBytes(StandardCharsets.UTF_8));
                return;
            }
        }
    }

    public static String hash2string( byte[] hash){
        StringBuilder hexhash = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            hexhash.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return hexhash.toString();
    }

    public static void setFileDigest(MessageDigest fileDigest) {
        UseCase2.fileDigest = fileDigest;
    }
}