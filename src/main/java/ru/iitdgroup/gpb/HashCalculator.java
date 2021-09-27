package ru.iitdgroup.gpb;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCalculator {
    private static final int BUFFER_SIZE = 4096; // 4KB
    private static MessageDigest fileDigest;

    /**
     * reads file and calculates its hash and writes all its details to provided PrintWriter
     * @param filename is a String that represents the name of the file in this case being snapshot
     * @param snapshotFileWriter is a class that allows us to write formatted data to an underlying Writer For instance writing the data of filename and the hexhash
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
}
