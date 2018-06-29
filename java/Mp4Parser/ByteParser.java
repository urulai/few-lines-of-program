import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.Integer;
import java.util.Set;
import java.util.HashSet;

class ByteParser {

    public static void parse(RandomAccessFile randomAccess, String atomName, long startOffset, long length) {

        long count = 0;

        try {
            do {
                long fptr = randomAccess.getFilePointer();
                int atomSize = randomAccess.readInt();
                byte[] bytes = new byte[Constants.ATOM_LABEL_LEN];
                randomAccess.readFully(bytes);

                String strAtom = Util.getString(bytes);

                int level = Util.getAtomLevel(atomName) + 1;
                String printMsg = "";
                for (int indent = 0; indent < level; indent++)
                    printMsg += " -> ";

                printMsg += "(" + strAtom + ") => " + atomSize + " bytes,  Offset: " + fptr + ", Parent: " + atomName;

                System.out.println(printMsg);

                if (strAtom.equals(Constants.TRAK) || strAtom.equals(Constants.MDIA) ||
                        strAtom.equals(Constants.MINF) || strAtom.equals(Constants.STBL)) {
                    parse(randomAccess, strAtom, fptr, atomSize);
                } else if (strAtom.equals(Constants.UUID)) {
                    UUID uuid = new UUID(randomAccess, fptr, atomSize, atomName, level);
                    uuid.parse();
                } else if (strAtom.equals(Constants.STSD)) {
                    STSD stsd = new STSD(randomAccess, fptr, atomSize, atomName, level);
                    stsd.parse();
                }

                count += atomSize;
                randomAccess.seek(fptr + atomSize);

            } while (count < (length - Constants.SIZE_LEN - Constants.ATOM_LABEL_LEN));
            // At the end of parsing, set randomAccessFile to end of the section.
            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }

    public static String getParent(String strAtomName) {
        String id = null;

        if (Constants.childAtomsMOOV != null && Constants.childAtomsMOOV.contains(strAtomName))
            id = Constants.MOOV;
        else if (Constants.childAtomsTRAK != null && Constants.childAtomsTRAK.contains(strAtomName))
            id = Constants.TRAK;
        else if (Constants.childAtomsMDIA != null && Constants.childAtomsMDIA.contains(strAtomName))
            id = Constants.MDIA;
        else if (Constants.childAtomsMINF != null && Constants.childAtomsMINF.contains(strAtomName))
            id = Constants.MINF;
        else if (Constants.childAtomsSTBL != null && Constants.childAtomsSTBL.contains(strAtomName))
            id = Constants.STBL;

        return id;
    }

    public static void main(String[] args) {

        ByteParser objByteParser = new ByteParser();
        String currentPath = System.getProperty("user.dir");
        String filepath = null;

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();

        MP4FileList mp4Files = new MP4FileList(args.length > 0 ? args[0] : null);
        ArrayList<File> fileList = mp4Files.getList();

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

                                Atom atom = null;
                                if (skip && atomSizeLong != -1)
                                    atom = new Atom(strAtom, atomSizeLong, filePointer);
                                else
                                    atom = new Atom(strAtom, atomSize, filePointer);

                                if (matchLevel == 0) {
                                    atom.setParent(Constants.FILE_LEVEL);

                                    //System.out.println("(" + strAtom + ") => " + atomSize + " bytes " + ", Offset: " + filePointer + " , Parent: " + Constants.FILE_LEVEL);
                                    if (strAtom.equals(Constants.MOOV)) {
                                        parse(randomAccess, strAtom, filePointer, atomSize);
                                    }
                                } else
                                    atom.setParent(getParent(strAtom));

                                // if (objByteParser.AtomList != null) {
                                //     objByteParser.AtomList.add(atom);
                                // } else {
                                //     objByteParser.AtomList = new ArrayList<Atom>();
                                //     objByteParser.AtomList.add(atom);
                                // }
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

                // for (int idx = 0; idx < objByteParser.AtomList.size(); idx++) {
                //     Atom atom = objByteParser.AtomList.get(idx);
                //     if (atom != null)
                //         //System.out.println("Atom name: " + atom.getName() + " starts at offset -> " + atom.getAtomOffset() + " , parent -> " + atom.getParentAtom());
                // }

                // objByteParser.AtomList = null;

                //System.out.println();
            }
        }

        cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();

        System.out.println("Millis took to parse ("  + fileList.size() +  ") files --> " + (endTime - startTime) + " msecs");

        AtomList atomList = AtomList.getInstance();
        if (atomList != null)
            atomList.display();

    }
}