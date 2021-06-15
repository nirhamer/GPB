package ru.iitdgroup.gpb;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileReader {
    private static final int BUFFER_SIZE = 4096; // 4KB
    private static final String AS_ROOT = ".";

    /**
     * starts the application and reads the exclusion file
     * @param args
     * @throws IOException an Exception that is thrown when there has been an Input/Output (usually when working with files) error.
     */
    static Set<String> exclusionsSet = new HashSet<>();
    public static void main(String[] args) throws IOException {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String date = LocalDateTime.now().format(formatter);
        File snapshotFile = new File(".\\snapshot-" + date + ".txt");
        PrintWriter snapshotFileWriter = new PrintWriter(snapshotFile);
        Files.walk(Path.of(AS_ROOT))
                .filter(s -> Files.isRegularFile(s, LinkOption.NOFOLLOW_LINKS))
                .forEach(path -> {
                    try {
                        readFile(path.toString(), snapshotFileWriter);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });
        snapshotFileWriter.close();
    }

    /**
     * reads the file and calculates its hash then writes all its details to provided PrintWriter
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
                snapshotFileWriter.println(filename + "\t" + hexhash);
                return;
            }
        }
    }

}