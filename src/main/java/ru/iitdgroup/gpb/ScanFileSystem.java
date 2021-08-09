package ru.iitdgroup.gpb;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.iitdgroup.gpb.App.toHexString;

public class ScanFileSystem {

    static Set<String> exclusionsSet = new HashSet<>();
    private static final String AS_ROOT = ".";

    public static void getExcludedFiles(String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("exclusions.txt"));
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

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
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
        App fw = new App();
        fw.walk("." );
    }
}