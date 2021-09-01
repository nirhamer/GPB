package ru.iitdgroup.gpb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SnapshotFileCreator {
    private static final int BUFFER_SIZE = 4096; // 4KB
    private static MessageDigest fileDigest;

    public static void Creator(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest fileDigest = MessageDigest.getInstance("SHA-256");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String date = LocalDateTime.now().format(formatter);
        File snapshotFile = new File("./snapshot-" + date + ".txt");
        PrintWriter snapshotFileWriter = new PrintWriter(snapshotFile);
        snapshotFileWriter.println("\n" + hash2string(fileDigest.digest()));
        snapshotFileWriter.close();

    }


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