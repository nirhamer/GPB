package ru.iitdgroup.gpb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    private static final int BUFFER_SIZE = 4096; // 4KB
    private static final String AS_ROOT = ".";

    /**
     * starts the application/reading the exclusion file
     * @param args
     * @throws IOException an Exception that is thrown when there has been an Input/Output (usually when working with files) error.
     */
    static Set<String> exclusionsSet = new HashSet<>();
    private static MessageDigest fileDigest;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

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

        if (args.length == 0) TestUseCase_1();
        else throw new IllegalArgumentException("Wrong arguments");

    }

    public static void TestUseCase_1() throws NoSuchAlgorithmException, IOException {
        fileDigest = MessageDigest.getInstance("SHA-256");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String date = LocalDateTime.now().format(formatter);
        File AS_File = new File("./AS_File-" + date + ".txt");
        PrintWriter DataHolder = new PrintWriter(AS_File);
        Files.walk(Path.of(AS_ROOT))
                .filter(s -> Files.isRegularFile(s, LinkOption.NOFOLLOW_LINKS))
                .filter(UseCase1::checkPath)
                .forEach(path -> {
                    try {
                        readFile(path.toString(), DataHolder);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });

        DataHolder.println("\n" + hash2string(fileDigest.digest()));
        DataHolder.close();
    }

    static boolean checkPath(Path path) {
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
}