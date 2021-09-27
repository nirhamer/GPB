package ru.iitdgroup.gpb.data;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IgnoreSystemFileFilter implements FileFilter {

    Set<String> systemFileNames = new HashSet<String>(Arrays.asList("sys", "etc"));


    public boolean accept(File aFile) {

        // in my scenario: each hidden file starting with a dot is a "system file"
        if (aFile.getName().startsWith(".") && aFile.isHidden()) {
            return false;
        }

        // exclude known system files
        if (systemFileNames.contains(aFile.getName())) {
            return false;
        }

        // more rules / other rules

        // no rule matched, so this is not a system file
        return true;
    }}
