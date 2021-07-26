package ru.iitdgroup.gpb;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ScanFileSystem {

    static Set<String> exclusionsSet = new HashSet<>();
    private static final String AS_ROOT = ".";

    public static void main(String[] args) {

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


    public void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }

     static {
        ScanFileSystem fw = new ScanFileSystem();
        fw.walk(AS_ROOT );

    }

}
