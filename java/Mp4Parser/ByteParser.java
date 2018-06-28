import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.Integer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;
import java.util.HashSet;

class ByteParser {
    private ArrayList<Atom> AtomList = null;

    private static final int SIZE_LEN = 4;
    private static final int SIZE_64BITS_LEN = 8;
    private static final int ATOM_LABEL_LEN = 4;

    private static final String FILE_LEVEL = "root";

    // Spherical video V1 atom identifiers
    private static final String FTYP = "ftyp";  // File type container
    private static final String MOOV = "moov";  // Movie atom

    // moov childs
    private static final String TRAK = "trak";  // Trak atom
    private static final String MVHD = "mvhd";  // Movie Header atom
    private static final String IODS = "iods";  // Information Object Definition atom??

    private static final String UUID = "uuid";  //Universal Unique Idenfier

    // UUID value ffcc8263-f855-4a93-8814-587a02521fdd
    private static final int UUID_LEN = 16; // 16 bytes
    private static final String UUID_SPHERICAL = "FFCC8263F8554A938814587A02521FDD";

    // Spherical video v2 atom identifiers
    // trak childs
    private static final String TKHD = "tkhd";  // Track Header atom
    private static final String EDTS = "edts";  // Edit atom
    private static final String MDIA = "mdia";  // Media atom

    // edts child
    private static final String ELST = "elst";  // Edit List atom

    // mdia child
    private static final String MDHD = "mdhd";  // Media Header atom
    private static final String HDLR = "hdlr";  // Handler reference atom
    private static final String MINF = "minf";  // Media Information atom
    private static final String UDTA = "udta";  // User Data atom

    // minf child
    private static final String DINF = "dinf";  // Data Information atom
    private static final String STBL = "stbl";  // Sample Table
    private static final String SMHD = "smhd";  // Sound media information header atom
    private static final String VMHD = "vmhd";  // Video media information atom

    //stbl child
    private static final String STSD = "stsd";  // Sample description atom
    private static final String CTTS = "ctts";  // Composition Offset atom
    private static final String CSLG = "cslg";  // Composition Shift Least Greatest atom
    private static final String SBGP = "sbgp";  // Sample-to-group atom
    private static final String SDTP = "sdtp";  // Sample Dependency Flags atom
    private static final String SGPD = "sgpd";  // Sample Group Description atom
    private static final String STPS = "stps";  // Partial Sync Sample atom
    private static final String STSH = "stsh";  // Shadow sync atom
    private static final String STSS = "stss";  // Sync Sample atom
    private static final String STTS = "stts";  // Time-to-sample atom
    private static final String STSZ = "stsz";  // Sample size atom
    private static final String STSC = "stsc";  // Sample-to-chunk atom
    private static final String STCO = "stco";  // Chunk Offset atom

    //stsd child
    private static final String AVC1 = "avc1"; // Advance Video Coding Box

    // acv1 child
    private static final String AVCC = "avcC";  // AVC Configuration Box
    private static final String ST3D = "st3d";  // Stereoscopic 3D Video Box
    private static final String SV3D = "sv3d";  // Spherical Video Box
    private static final String PROJ = "proj";  // Projection Box
    private static final String PRHD = "prhd";  // Projection Header Box
    private static final String EQUI = "equi";  // Equirectangular Header Box

    private static final String MDAT = "mdat";  // Media Data box

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

    private static final Set<String> childAtomsMOOV = new HashSet<String>(Arrays.asList(TRAK, MVHD, IODS));
    private static final Set<String> childAtomsTRAK = new HashSet<String>(Arrays.asList(TKHD, EDTS, MDIA, UUID));
    // TODO: Need to differentiate HDLR contained in MDIA and MINF
    private static final Set<String> childAtomsMDIA = new HashSet<String>(Arrays.asList(MDHD, MINF, HDLR, UDTA));
    private static final Set<String> childAtomsMINF = new HashSet<String>(Arrays.asList(VMHD, HDLR, SMHD, STBL));
    private static final Set<String> childAtomsSTBL = new HashSet<String>(
        Arrays.asList(CTTS, SBGP, SDTP, SGPD, STCO, STSC, STSD, STSS, STSZ, STTS, STSH)
    );

    public static int getAtomLevel(String name) {
        switch (name) {
        case FTYP:
        case MOOV:
        case MDAT:
            return 0;

        case IODS:
        case TRAK:
        case MVHD:
            return 1;

        case TKHD:
        case EDTS:
        case MDIA:
        case UDTA:
            return 2;

        case MDHD:
        case MINF:
        case HDLR:
            return 3;

        case DINF:
        case SMHD:
        case STBL:
        case VMHD:
            return 4;

        case CTTS:
        case SBGP:
        case SDTP:
        case STSD:
        case STTS:
        case STSS:
        case STSZ:
        case STSC:
        case STCO:
        case SGPD:
            return 5;

        case UUID:   // can be in any level
            return 99;

        default:
            return -1;
        }
    }

    public static void parseUUID(RandomAccessFile randomAccess, long startOffset, long length, int level) {

        int count = 0;

        try {
            long fptr = randomAccess.getFilePointer();
            byte[] bytes = new byte[UUID_LEN];
            randomAccess.readFully(bytes);

            String uuid = javax.xml.bind.DatatypeConverter.printHexBinary(bytes);

            String printMsg = "";
            for (int indent = 0; indent < level + 1; indent++)
                printMsg += " -> ";

            printMsg += "Data: " + uuid;

            if (uuid.equals(UUID_SPHERICAL)) {
                System.out.println(printMsg);

                // read spherical data
                // Data length = total length - (UUID_LEN) - strlen(UUID) - sizeof(SIZE)
                byte[] data = new byte[(int)length - UUID_LEN - UUID.length() - SIZE_LEN];
                randomAccess.readFully(data);

                String str = getString(data);
                System.out.println(str);
            }

            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void parse(RandomAccessFile randomAccess, String atomName, long startOffset, long length) {

        long count = 0;

        try {
            do {
                long fptr = randomAccess.getFilePointer();
                int atomSize = randomAccess.readInt();
                byte[] bytes = new byte[ATOM_LABEL_LEN];
                randomAccess.readFully(bytes);

                String strAtom = getString(bytes);

                int level = getAtomLevel(atomName) + 1;
                String printMsg = "";
                for (int indent = 0; indent < level; indent++)
                    printMsg += " -> ";

                printMsg += "(" + strAtom + ") => " + atomSize + " bytes,  Offset: " + fptr + ", Parent: " + atomName;

                System.out.println(printMsg);

                if (strAtom.equals(TRAK) || strAtom.equals(MDIA) ||
                        strAtom.equals(MINF) || strAtom.equals(STBL)) {
                    parse(randomAccess, strAtom, fptr, atomSize);
                } else if (strAtom.equals(UUID)) {
                    parseUUID(randomAccess, fptr, atomSize, level);
                }

                count += atomSize;
                randomAccess.seek(fptr + atomSize);

            } while (count < (length - SIZE_LEN - ATOM_LABEL_LEN));
            // At the end of parsing, set randomAccessFile to end of the section.
            randomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getParent(String strAtomName) {
        String id = null;

        if (childAtomsMOOV != null && childAtomsMOOV.contains(strAtomName))
            id = MOOV;
        else if (childAtomsTRAK != null && childAtomsTRAK.contains(strAtomName))
            id = TRAK;
        else if (childAtomsMDIA != null && childAtomsMDIA.contains(strAtomName))
            id = MDIA;
        else if (childAtomsMINF != null && childAtomsMINF.contains(strAtomName))
            id = MINF;
        else if (childAtomsSTBL != null && childAtomsSTBL.contains(strAtomName))
            id = STBL;

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
                            if ((matchLevel = getAtomLevel(strAtom)) != -1) {

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

                                    System.out.println("(" + strAtom + ") => " + atomSize + " bytes " + ", Offset: " + filePointer + " , Parent: " + FILE_LEVEL);
                                    if (strAtom.equals(MOOV)) {
                                        parse(randomAccess, strAtom, filePointer, atomSize);
                                    }
                                } else
                                    atom.setParent(getParent(strAtom));

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

                // for (int idx = 0; idx < objByteParser.AtomList.size(); idx++) {
                //     Atom atom = objByteParser.AtomList.get(idx);
                //     if (atom != null)
                //         System.out.println("Atom name: " + atom.getName() + " starts at offset -> " + atom.getAtomOffset() + " , parent -> " + atom.getParentAtom());
                // }

                // objByteParser.AtomList = null;

                System.out.println();
            }
        }

        cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();

        System.out.println("Millis took to parse ("  + fileList.size() +  ") files --> " + (endTime - startTime) + " msecs");
    }
}