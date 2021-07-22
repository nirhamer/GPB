package ru.iitdgroup.gpb;

public class Instructions {
    //use case 1 start
    // 1 class scan will scan the AS_root file system recursively.
    //    * Input: AS root path as String, exclusions file path as String.
    //    * Output: a List of scanned files as ArrayList<String>
    //        -OR- the List<ScannedFile>, where ScannedFile contains fileNameAndPath:String and hash:String. Hash will be null at thus step.
    // 2 method excluded files that responsible for dealing with the exclusion file will remain a part of the scanning AS_root class.
    //    * Input: noting.
    //    * Output: internal container with a list of excluded files and folders.
    // 3 isExcluded method and exclusionsSet function will be part of scanning AS_root class.
    // 4 method snapshot file creating the snapshot file.
    //    * Input: nothing.
    //    * Output: a .txt based file named example (snapshot-2021-07-14_15-20-34.txt)
    // 5 method readFile into its own service class responsible for reading the arraylist data and calculates its hashes.
    // @param filename is a String that represents the name of the file in this case being snapshot.
    // @param snapshotFileWriter is a PrintWriter that allows us to to write formatted data to an underlying Writer For instance writing the data of filename and the hexhash.
    //    * Input: reads the arraylist file/data.
    //    * Output: calculates the file hashes.
    //use case 1 end.

    // use case 2 start.
    // 6 class compares.
    // addressing step 2 will compare snapshot calculated value/(last line) vs read/(total hash of all read lines)
    //    * Input: snapshot as file.
    //    * Output:move to step 7. If there is a difference it prints a standard error message ("Snapshot is corrupted")
    // 7 call upon scan class to once again scan the AS_root file system.
    //    * Input: scanner class.
    //    * Output A: ignore the file and move on.
    //    * Output B: stores its full path in a separate list of changed files and mark the row from the snapshot file as Processed.
    //    * Output C: the app will store its full path in a separate list of new files.
    //    * Output D: adds non processed rows/files  to a separate list of deleted files.
    // Note regarding Output D (plz remember that output D unlike the previous outputs doesn't need the scans results the filesystem and works only with files from the snapshot file except last line)
    // 8 class verification the class verifies all three lists
    //    * Input: 3 lists A (list of changed files) B (list of new files) C (list of deleted files)
    //    * Output: class prints Status Ok no differences found it will terminates with exit code 0
    //    * Output if: any of the lists has records the class will create a system diff file tp sort each list by the full path
    // than writes appropriate action and the full file path for each of file in each list than writes the standard error message `Differences found` and finishes with exit code -3
}
