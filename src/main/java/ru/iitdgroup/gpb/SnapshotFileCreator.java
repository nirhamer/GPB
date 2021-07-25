package ru.iitdgroup.gpb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SnapshotFileCreator {


    public static void Creator(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest fileDigest = MessageDigest.getInstance("SHA-256");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String date = LocalDateTime.now().format(formatter);
        File snapshotFile = new File("./snapshot-" + date + ".txt");
        PrintWriter snapshotFileWriter = new PrintWriter(snapshotFile);
        snapshotFileWriter.println("\n" + hash2string(fileDigest.digest()));
        snapshotFileWriter.close();

    }

    public static String hash2string( byte[] hash){
        StringBuilder hexhash = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            hexhash.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return hexhash.toString();
    }
}
