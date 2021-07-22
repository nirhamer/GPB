# use case 1 Start

1 class scan will scan the AS_root file system recursively.
   * Input: AS root path as String, exclusions file path as String.
   * Output: a List of scanned files as ArrayList<String>
       -OR- the List<ScannedFile>, where ScannedFile contains fileNameAndPath:String and hash:String. Hash will be null at thus step.

2 method `getExcludedFiles` that responsible for dealing with the exclusion file will remain a part of the `ScanningASRoot` class.
   * Input: noting.
   * Output: an internal container with a `Set<String>` of excluded files and folders. 
   
3 method `isExcluded( Path path)` and `exclusionsSet` field will be part of `ScanningASRoot` class.

4 class `SnapshotFileCreator` creating the snapshot file calling methods from `readFile`.
   * Input: nothing.
   * Output: a .txt based file example name (snapshot-2021-07-14_15-20-34.txt)

5 class `HashCalculator` into its own service class responsible for reading the arraylist data and calculates its hashes.
 @param filename is a String that represents the name of the file in this case being snapshot.
 @param snapshotFileWriter is a PrintWriter that allows us to write formatted data to an underlying Writer For instance writing the data of filename and the hexhash.
   * Input: reads the arraylist file/data `List<ScannedFile>`.
   * Output: calculates the file hashes. The same `List<ScannedFile>` but with calculated hashes for each file.
# use case 1 End.
 
# use case 2 Start.

6 class `SnapshotFileVerifier`.
addressing step 2 will compare snapshot calculated value/(last line) vs read/(total hash of all read lines)
   * Input: snapshot as file.
   * Output:move to step 7. If there is a difference it prints a standard error message ("Snapshot is corrupted") and exit `-2`.
   
7 call upon scan class to once again scan the AS_root file system.
**note** how to solve this step implement
   * Input: nothing.
   * Output A: ignore the file and move on.
   * Output B: stores its full path in a separate list of changed files and mark the row from the snapshot file as Processed.
   * Output C: the app will store its full path in a separate list of new files.
   * Output D: adds non processed rows/files  to a separate list of deleted files.
   
method `compareTwoSnapshots`
    * Input1: files from snapshot file
    * Input2: files from the filesystem
    * output: 3 lists `List<String>` - for new, for modified and for deleted files
       
Note regarding Output D (plz remember that output D unlike the previous outputs doesn't need the scans results the filesystem and works only with files from the snapshot file except last line)

8 class `ListVerifier` the class `ListVerifier` all three lists
   * Input: 3 lists A (list of changed files) B (list of new files) C (list of deleted files). `List<String>` for each one.
   * Output: class prints Status Ok no differences found it will terminate with exit code 0
   * Output if: any of the lists has records the class will create a system diff file tp sort each list by the full path
than writes appropriate action, and the full file path for each of file in each list than writes the standard error message `Differences found` and finishes with exit code `-3`
# use case 2 End.