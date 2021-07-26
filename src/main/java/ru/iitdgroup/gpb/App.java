package ru.iitdgroup.gpb;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class App {

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    {
        try
        {
            String s1 = "hi friend";
            System.out.println("\n" + s1 + " : " + toHexString(getSHA(s1)));
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }

    public void walk( String path ) {
        File root = new File( path );
        File[] list = root.listFiles();


        if (list == null) return;

        for ( File f : list ) {
            System.out.println("\n" + f.getName() + " : "  );
            if (exclusionsSet.contains(f.getName())) { continue; }
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                new StringBuilder();
                try {
                    Scanner myReader = new Scanner(f);
                    System.out.println("\n" + f.getName() + " : " + toHexString(getSHA("")));
                    myReader.close();
                } catch (FileNotFoundException | NoSuchAlgorithmException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }
    static Set<String> exclusionsSet = new HashSet<>();
    public static void main(String[] args) {
        File myObj = new File("exclusions.txt");
        Scanner exclusionsReader;
        try {
            exclusionsReader = new Scanner(myObj);
            while (exclusionsReader.hasNextLine()) {
                String data = exclusionsReader.nextLine();
                exclusionsSet.add(data);
            }
            exclusionsReader.close();
        } catch (FileNotFoundException e)  {
            System.out.println("no exclusions.txt file found continuing with the scan");
        }
        App fw = new App();
        fw.walk("." );
    }
}