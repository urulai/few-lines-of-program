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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;
import java.util.HashSet;

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

class MP4FileList {
    private ArrayList<File> listFiles = null;

    MP4FileList() {

        String currentPath = System.getProperty("user.dir") + File.separator;
        File f = new File(currentPath);

        if (f != null && f.exists()) {
            File[] paths = null;

            paths = f.listFiles();

            for (File path : paths) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+.mp4");
                Matcher matcher = pattern.matcher(path.toString());

                if (matcher != null && matcher.find()) {
                    if (listFiles == null)
                        listFiles =  new ArrayList<File>();

                    listFiles.add(path);
                }
            }
        }
    }

    public ArrayList<File> getList() {
        return listFiles;
    }
}


class ByteParser {
    private ArrayList<Atom> AtomList = null;

    private static final int SIZE_LEN = 4;
    private static final int SIZE_64BITS_LEN = 8;
    private static final int ATOM_LABEL_LEN = 4;

    private static final String FILE_LEVEL = "root";

    // Spherical video V1 atom identifiers
    private static final String FTYP = "ftyp";
    private static final String MOOV = "moov";

    // moov childs
    private static final String TRAK = "trak";
    private static final String MVHD = "mvhd";
    private static final String IODS = "iods";

    private static final String UUID = "uuid";


    // UUID value ffcc8263-f855-4a93-8814-587a02521fdd
    private static final int UUID_LEN = 16; // 16 bytes
    private static final String UUID_SPHERICAL = "ffcc8263f8554a938814587a02521fdd";

    // Spherical video v2 atom identifiers
    // trak childs
    private static final String TKHD = "tkhd";
    private static final String EDTS = "edts";
    private static final String MDIA = "mdia";


    // edts child
    private static final String ELST = "elst";

    // mdia child
    private static final String MDHD = "mdhd";
    private static final String HDLR = "hdlr";
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

    private static final Set<String> childAtomsMOOV = new HashSet<String>(Arrays.asList(TRAK, MVHD, IODS));
    private static final Set<String> childAtomsTRAK = new HashSet<String>(Arrays.asList(TKHD, EDTS, MDIA));
    private static final Set<String> childAtomsMDIA = new HashSet<String>(Arrays.asList(MDHD, MINF, HDLR));

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
        case MOOV:
        case MDAT:
            return 0;

        case TRAK:
        case MVHD:
            return 1;

        case TKHD:
        case EDTS:
        case MDIA:
            return 2;

        case MDHD:
        case MINF:
        case HDLR:
            return 3;

        case UUID:   // can be in any level
            return 99;

        default:
            return -1;
        }
    }


    public static void parseUUID(RandomAccessFile randomAccess, long startOffset, long length) {

        long count = 0;

        System.out.println("length: " + length);

        try {
            long fptr = randomAccess.getFilePointer();
            byte[] bytes = new byte[UUID_LEN];
            randomAccess.readFully(bytes);

            String uuid = getString(bytes);

            if (uuid.equals(UUID_SPHERICAL)) {
                System.out.println("Atom: " + uuid + " at offset : " + fptr + " , Size : " + length);
            }

            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void parseMdia(RandomAccessFile randomAccess, long startOffset, long length) {
        // look for trak

        long count = 0;

        System.out.println("length: " + length);

        try {
            do {
                long fptr = randomAccess.getFilePointer();
                int atomSize = randomAccess.readInt();
                byte[] bytes = new byte[ATOM_LABEL_LEN];
                randomAccess.readFully(bytes);

                String strAtom = getString(bytes);
                System.out.println("Atom: " + strAtom + " at offset : " + fptr + " , Size : " + atomSize);

                count += atomSize;

                if (strAtom.equals(MINF) || strAtom.equals(STBL)) {
                    parseMdia(randomAccess, fptr, atomSize);
                }

                randomAccess.seek(fptr + atomSize);
            } while (count < (length - SIZE_LEN - ATOM_LABEL_LEN));
            // At the end of parsing, set randomAccessFile to end of the section.
            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void parseTrak(RandomAccessFile randomAccess, long startOffset, long length) {
        // look for trak

        long count = 0;

        System.out.println("trak length: " + length);

        try {
            do {
                long fptr = randomAccess.getFilePointer();
                int atomSize = randomAccess.readInt();
                byte[] bytes = new byte[ATOM_LABEL_LEN];
                randomAccess.readFully(bytes);

                String strAtom = getString(bytes);
                System.out.println("Atom: " + strAtom + " at offset : " + fptr + " , Size : " + atomSize);

                count += atomSize;

                if (strAtom.equals(MDIA)) {
                    parseMdia(randomAccess, fptr, atomSize);
                } else if (strAtom.equals(UUID)) {
                    parseUUID(randomAccess, fptr, atomSize);
                }

                randomAccess.seek(fptr + atomSize);
            } while (count < (length - SIZE_LEN - ATOM_LABEL_LEN));
            // At the end of parsing, set randomAccessFile to end of the section.
            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
    }

    public static void parse(RandomAccessFile randomAccess, long startOffset, long length) {
        // look for trak

        long count = 0;

        System.out.println("moov length: " + length);

        try {
            do {
                long fptr = randomAccess.getFilePointer();
                int atomSize = randomAccess.readInt();
                byte[] bytes = new byte[ATOM_LABEL_LEN];
                randomAccess.readFully(bytes);

                String strAtom = getString(bytes);
                System.out.println("Atom: " + strAtom + " at offset : " + fptr + " , Size : " + atomSize);

                if (strAtom.equals(TRAK) || strAtom.equals(MDIA) ||
                    strAtom.equals(MINF) || strAtom.equals(STBL)) {
                    parse(randomAccess, fptr, atomSize);
                } else if (strAtom.equals(UUID)) {
                    parseUUID(randomAccess, fptr, atomSize);
                }

                count += atomSize;
                randomAccess.seek(fptr + atomSize);

            } while (count < (length - SIZE_LEN - ATOM_LABEL_LEN));
            // At the end of parsing, set randomAccessFile to end of the section.
            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
    }

    public static String getCurrentParent(String strAtomName) {
        String id = null;

        if (childAtomsMOOV != null && childAtomsMOOV.contains(strAtomName)) {
            System.out.println("child: " + strAtomName + " parent: " + MOOV);
            id = MOOV;
        } else if (childAtomsTRAK != null && childAtomsTRAK.contains(strAtomName)) {
            System.out.println("child: " + strAtomName + " parent: " + TRAK);
            id = TRAK;
        } else if (childAtomsMDIA != null && childAtomsMDIA.contains(strAtomName)) {
            System.out.println("child: " + strAtomName + " parent: " + MDIA);
            id = MDIA;
        }

        return id;
    }

    public static void main(String[] args) {

        ByteParser objByteParser = new ByteParser();
        String currentPath = System.getProperty("user.dir");
        String filepath = null;

        // Scanner input = new Scanner(System.in);
        // String fileName = input.nextLine();

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();

        MP4FileList mp4Files = new MP4FileList();
        ArrayList<File> fileList = mp4Files.getList();

        for (int file_idx = 0; file_idx < fileList.size(); file_idx++) {

            File f = fileList.get(file_idx);

            if (f.exists()) {

                long fileSize = f.length();
                System.out.println("File path: " + f.getPath());

                try(RandomAccessFile randomAccess = new RandomAccessFile(f, "r")) {

                    try {
                        long filePointer = randomAccess.getFilePointer();

                        byte[] bytes = new byte[ATOM_LABEL_LEN];

                        do {
                            int count = 0;
                            boolean skip = false;

                            int atomSize = randomAccess.readInt();
                            long atomSizeLong = -1;

                            // System.out.println("Size in dec: " + atomSize);
                            // System.out.println("Size in hex: " + Integer.toHexString(atomSize));

                            randomAccess.readFully(bytes);

                            String strAtom = getString(bytes);

                            int matchLevel;
                            if ((matchLevel = matchAtomName(strAtom)) != -1) {

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

                                if (matchLevel == 0) {
                                    atom.setParent(FILE_LEVEL);

                                    if (strAtom.equals(MOOV)) {
                                        System.out.println("start parsing moov");

                                        parse(randomAccess, filePointer, atomSize);
                                    }
                                } else
                                    atom.setParent(getCurrentParent(strAtom));

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
                                newOffset = filePointer + atomSize;

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
                        System.out.println("Atom name: " + atom.getName() + " starts at offset -> " + atom.getAtomOffset() + " , parent -> " + atom.getParentAtom());
                }

                objByteParser.AtomList = null;

                System.out.println();
            }
        }

        cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();

        System.out.println("Millis took to parse --> " + (endTime - startTime) + " msecs");
    }
}