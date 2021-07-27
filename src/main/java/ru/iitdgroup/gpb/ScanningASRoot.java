package ru.iitdgroup.gpb;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScanningASRoot {

    static Set<String> exclusionsSet = new HashSet<>();
    private static final String AS_ROOT = ".";

    public static void getExcludedFiles(String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("exclusion.txt"));
        //region reading the exclusions file
        File myObj = new File("exclusion.txt");
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


    public static void main(String[] args) throws IOException {


        Stream<Path> st = Files.walk(Path.of(AS_ROOT))
                .filter(s -> Files.isRegularFile(s, LinkOption.NOFOLLOW_LINKS))
                .filter(ScanningASRoot::isExcluded);

        List<Path> myList = st.collect(Collectors.toList());

    }
    static boolean isExcluded(Path path) {
        for (String exclusion : exclusionsSet) {
            if (path.startsWith(exclusion)) {
                return false;
            }
        }
        return true;
    }

}