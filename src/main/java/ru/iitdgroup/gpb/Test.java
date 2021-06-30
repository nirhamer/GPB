package ru.iitdgroup.gpb;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    private static final int BUFFER_SIZE = 4096;
    private static final String PATH = "C:\\java projects";

    public static void main(String[] args) throws IOException {
        Files.walk(Path.of(PATH))
                .filter(s->Files.isRegularFile(s, LinkOption.NOFOLLOW_LINKS ))
                .forEach(path -> {
                    try {
                        readFile(path.toString());
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void readFile(String filename) throws IOException, NoSuchAlgorithmException {
        System.out.println("\n\n\n>>>>>>>>>>>>>>>> "+filename);
        BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(Path.of(filename)));
        while (true) {
            byte[] chunk = stream.readNBytes(BUFFER_SIZE);
            byte[] hash = MessageDigest.getInstance("SHA-1").digest(chunk);

            printBuffer(chunk);


            if (chunk.length != BUFFER_SIZE) {
                break;
            }
        }
    }

    private static void printBuffer(byte[] buffer) throws IOException {
        System.out.write(buffer);
    }

}