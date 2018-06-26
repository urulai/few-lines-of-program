import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;

// MP4
class Atom {

    long atomSize;
    long atomOffset; // form file start
    String parentAtom;
    String atomLevel;
    String atomName;

    Atom(String name) {
        atomName = name;
    }

    Atom(String name, long size, long offset) {
        this(name);
        atomSize = size;
        atomOffset = offset;
    }

    Atom(String name, long size, long offset, String parent, String level) {
        this(name, size, offset);
        parentAtom = parent;
        atomLevel = level;
    }

    public String getName() {
        return atomName;
    }

    public String getParentAtom() {
        return parentAtom;
    }

    public String getAtomLevel() {
        return atomLevel;
    }

    public long getAtomSize() {
        return atomSize;
    }

    public long getAtomOffset() {
        return atomOffset;
    }

    public void setName(String name) {
        atomName = name;
    }

    public void setAtomLevel(String level) {
        atomLevel = level;
    }

    public void setAtomSize(int sz) {
        atomSize = sz;
    }

    public void setParent(String parent) {
        parentAtom = parent;
    }

    public void setAtomOffset(Long offset) {
        atomOffset = offset;
    }
}


class ByteParser {
    private ArrayList<Atom> AtomList = null;

    private static final int SIZE_LEN = 4;
    private static final int SIZE_64BITS_LEN = 8;
    private static final int ATOM_LABEL_LEN = 4;

    // Spherical video V1 atom identifiers
    private static final String FTYP = "ftyp";
    private static final String MOOV = "moov";
    private static final String TRAK = "trak";
    private static final String UUID = "uuid";

    // UUID value ffcc8263-f855-4a93-8814-587a02521fdd
    private static final String UUID_SPHERICAL = "ffcc8263f8554a938814587a02521fdd";


    // Spherical video v2 atom identifiers
    private static final String MDIA = "mdia";
    private static final String MINF = "minf";
    private static final String STBL = "stbl";
    private static final String STSD = "stsd";
    private static final String AVC1 = "avc1";
    private static final String AVCC = "avcC";
    private static final String ST3D = "st3d";
    private static final String SV3D = "sv3d";
    private static final String PROJ = "proj";
    private static final String PRHD = "prhd";
    private static final String EQUI = "equi";

    // Media data box
    private static final String MDAT = "mdat";

    private static int getInteger(byte[] bytes) {
        int temp = -1;

        temp = (bytes[0] & 0xFF) << 24 |
               (bytes[1] & 0xFF) << 16 |
               (bytes[2] & 0xFF) << 8 |
               (bytes[3] & 0xFF);

        return temp;
    }

    private static long getLong(byte[] bytes) {

        long temp = -1;

        temp = (bytes[0] & 0xFF) << 56 |
               (bytes[1] & 0xFF) << 48 |
               (bytes[2] & 0xFF) << 40 |
               (bytes[3] & 0xFF) << 32 |
               (bytes[4] & 0xFF) << 24 |
               (bytes[5] & 0xFF) << 16 |
               (bytes[6] & 0xFF) << 8 |
               (bytes[7] & 0xFF);

        return temp;
    }

    private static String getString(byte[] bytes) {
        String atom_label = null;
        try {
            atom_label = new String(bytes, "UTF-8");

        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());

        } finally {
            return atom_label;
        }
    }

    public static int matchAtomName(String name) {
        switch (name) {
        case FTYP:
            return 0;

        case MOOV:
            return 1;

        case TRAK:
            return 2;

        case UUID:
            return 3;

        case MDAT:
            return 4;

        default:
            return -1;
        }
    }

    public static void main(String[] args) {

        ByteParser objByteParser = new ByteParser();
        String currentPath = System.getProperty("user.dir");
        String filepath = null;

        // Scanner input = new Scanner(System.in);
        // String fileName = input.nextLine();

        String fileName = "emergence.mp4";
        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();

        filepath = currentPath + "\\" + fileName;

        File f = new File(filepath);

        System.out.println(f.getPath());

        if (f.exists()) {

            long fileSize = f.length();

            try(RandomAccessFile randomAccess = new RandomAccessFile(f, "r")) {

                try {
                    long filePointer = randomAccess.getFilePointer();

                    byte[] bytes = new byte[SIZE_LEN];

                    do {

                        int count = 0;
                        boolean skip = false;

                        int atomSize = randomAccess.readInt();
                        long atomSizeLong = -1;

                        // System.out.println("Size in dec: " + atomSize);
                        // System.out.println("Size in hex: " + Integer.toHexString(atomSize));

                        randomAccess.readFully(bytes);

                        String strAtom = getString(bytes);

                        if (matchAtomName(strAtom) != -1) {

                            if (strAtom.equalsIgnoreCase(MDAT) && atomSize == 1) {
                                // size will be after mdat

                                atomSizeLong = randomAccess.readLong();
                                // System.out.println("AtomSizeLong in dec: " + atomSizeLong);
                                // System.out.println("AtomSizeLong in hex: " + Long.toHexString(atomSizeLong));
                                skip = true;
                            }

                            Atom atom = null;
                            if (skip && atomSizeLong != -1)
                                atom = new Atom(strAtom, atomSizeLong, filePointer);
                            else
                                atom = new Atom(strAtom, atomSize, filePointer);

                            if (objByteParser.AtomList != null) {
                                objByteParser.AtomList.add(atom);
                            } else {
                                objByteParser.AtomList = new ArrayList<Atom>();
                                objByteParser.AtomList.add(atom);
                            }
                        }

                        long newOffset;

                        if (skip && atomSizeLong != -1)
                            newOffset = filePointer + atomSizeLong;
                        else
                            newOffset =  filePointer + atomSize;

                        // System.out.println("set filepointer to start reading at offset " + newOffset);
                        randomAccess.seek(newOffset);

                        filePointer = randomAccess.getFilePointer();
                        // System.out.println("pos : " + filePointer);

                    } while (filePointer != fileSize);

                    bytes = null;
                } catch (EOFException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            for (int idx = 0; idx < objByteParser.AtomList.size(); idx++) {
                Atom atom = objByteParser.AtomList.get(idx);
                if (atom != null)
                    System.out.println("Atom name: " + atom.getName() + " starts at offset -> " + atom.getAtomOffset());
            }
            cal = Calendar.getInstance();
            long endTime = cal.getTimeInMillis();

            System.out.println("Millis took to parse --> " + (endTime - startTime) + " msecs");

        }
    }
}