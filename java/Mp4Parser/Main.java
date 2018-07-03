import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

class Main {

    public static void main(String[] args) {

        String currentPath = System.getProperty("user.dir");
        String filepath = null;

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();

        MP4FileList mp4Files = new MP4FileList(args.length > 0 ? args[0] : null);
        ArrayList<File> fileList = mp4Files.getList();
        ArrayList<String> lst360Videos = new ArrayList<String>();

        for (int file_idx = 0; file_idx < fileList.size(); file_idx++) {

            File f = fileList.get(file_idx);

            if (f.exists()) {

                long fileSize = f.length();
                //System.out.println("File path: " + f.getPath());

                try(RandomAccessFile randomAccess = new RandomAccessFile(f, "r")) {

                    try {
                        long filePointer = randomAccess.getFilePointer();

                        byte[] bytes = new byte[Constants.ATOM_LABEL_LEN];

                        do {
                            int count = 0;
                            boolean skip = false;

                            int atomSize = randomAccess.readInt();
                            long atomSizeLong = -1;

                            // //System.out.println("Size in dec: " + atomSize);
                            // //System.out.println("Size in hex: " + Integer.toHexString(atomSize));

                            randomAccess.readFully(bytes);

                            String strAtom = Util.getString(bytes);

                            int matchLevel;
                            if ((matchLevel = Util.getAtomLevel(strAtom)) != -1) {

                                if (strAtom.equalsIgnoreCase(Constants.MDAT) && atomSize == 1) {
                                    // size will be after mdat

                                    atomSizeLong = randomAccess.readLong();
                                    // //System.out.println("AtomSizeLong in dec: " + atomSizeLong);
                                    // //System.out.println("AtomSizeLong in hex: " + Long.toHexString(atomSizeLong));
                                    skip = true;
                                }

                                if (matchLevel == 0) {

                                    if (strAtom.equals(Constants.MOOV)) {
                                        MOOV moov = new MOOV(randomAccess, filePointer, atomSize, Constants.FILE_LEVEL, matchLevel);
                                        moov.parse();
                                    }
                                }
                            }

                            long newOffset;
                            if (skip && atomSizeLong != -1)
                                newOffset = filePointer + atomSizeLong;
                            else
                                newOffset = filePointer + atomSize;

                            // //System.out.println("set filepointer to start reading at offset " + newOffset);
                            randomAccess.seek(newOffset);

                            filePointer = randomAccess.getFilePointer();
                            // //System.out.println("pos : " + filePointer);
                        } while (filePointer != fileSize);

                        bytes = null;

                        AtomList objAtomList = AtomList.getInstance();
                        if (objAtomList != null) {

                            ArrayList<Atom> lst = objAtomList.getList();
                            if (lst != null && lst.size() > 0) {
                                lst360Videos.add(f.getPath());
                                lst.clear();
                            } else {}
                        }
                    } catch (EOFException ex) {
                        //System.out.println(ex.getMessage());
                    } catch (IOException ex) {
                        //System.out.println(ex.getMessage());
                    }
                } catch (FileNotFoundException ex) {
                    //System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    //System.out.println(ex.getMessage());
                }
            }
        }

        cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();

        System.out.println("Millis took to parse ("  + fileList.size() +  ") files --> " + (endTime - startTime) + " msecs");

        for (int idx = 0; idx < lst360Videos.size(); idx++) {
            if (idx == 0)
                System.out.println("List of 360 videos");

            System.out.println(lst360Videos.get(idx));
        }


    }
}