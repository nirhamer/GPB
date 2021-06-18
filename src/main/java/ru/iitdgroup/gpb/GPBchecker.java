package ru.iitdgroup.gpb;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GPBchecker {

    static Set<String> exclusionsSet = new HashSet<>();
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    private static final int BUFFER_SIZE = 4096;
    public static void main(String[] args) {
        //region reading the exclusion file
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
        GPBchecker fw = new GPBchecker();
        fw.walk(".");
        if (args.length < 2) {
            System.out.println("f");
            System.exit(0);
        }

        String f = args[0];

        try (
                InputStream inputStream = new FileInputStream(f)
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];

            while (inputStream.read(buffer) != -1);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void walk(String path) {
        File root = new File(path);
        File[] list = root.listFiles();


        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
            } else {
                try {
                    Scanner myReader = new Scanner(f);
                    toHexString(getSHA(""));
                    myReader.close();
                } catch (FileNotFoundException | NoSuchAlgorithmException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println("File:" + f.getAbsoluteFile());
            }
        }
    }
}