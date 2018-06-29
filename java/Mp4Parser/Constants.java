import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import java.lang.Integer;
import java.lang.Short;

public class Constants {

    // Size in bytes
    public static final int SIZE_LEN = 4;
    public static final int SIZE_64BITS_LEN = 8;
    public static final int ATOM_LABEL_LEN = 4;
    public static final int STSD_FLAGS_LEN = 3;
    public static final int AVC1_FLAGS_LEN = 3;
    public static final int SHORT_LEN_BYTES = (Short.SIZE/Byte.SIZE);
    public static final int INTEGER_LEN_BYTES = (Integer.SIZE/Byte.SIZE);

    public static final String FILE_LEVEL = "root";

    // Spherical video V1 atom identifiers
    public static final String FTYP = "ftyp";   // File type container
    public static final String MOOV = "moov";   // Movie atom
    public static final String MDAT = "mdat";   // Media Data box

    // moov children
    public static final String TRAK = "trak";   // Trak atom
    public static final String MVHD = "mvhd";   // Movie Header atom
    public static final String IODS = "iods";   // Information Object Definition atom??

    public static final String UUID = "uuid";   //Universal Unique Idenfier

    // UUID value ffcc8263-f855-4a93-8814-587a02521fdd
    public static final int UUID_LEN = 16; // 16 bytes
    public static final String UUID_SPHERICAL = "FFCC8263F8554A938814587A02521FDD";

    // Spherical video v2 atom identifiers
    // trak children
    public static final String TKHD = "tkhd";   // Track Header atom
    public static final String EDTS = "edts";   // Edit atom
    public static final String MDIA = "mdia";   // Media atom

    // edts children
    public static final String ELST = "elst";   // Edit List atom

    // mdia children
    public static final String MDHD = "mdhd";   // Media Header atom
    public static final String HDLR = "hdlr";   // Handler reference atom
    public static final String MINF = "minf";   // Media Information atom
    public static final String UDTA = "udta";   // User Data atom

    // minf children
    public static final String DINF = "dinf";   // Data Information atom
    public static final String STBL = "stbl";   // Sample Table
    public static final String SMHD = "smhd";   // Sound media information header atom
    public static final String VMHD = "vmhd";   // Video media information atom

    //stbl children
    public static final String STSD = "stsd";   // Sample description atom
    public static final String CTTS = "ctts";   // Composition Offset atom
    public static final String CSLG = "cslg";   // Composition Shift Least Greatest atom
    public static final String SBGP = "sbgp";   // Sample-to-group atom
    public static final String SDTP = "sdtp";   // Sample Dependency Flags atom
    public static final String SGPD = "sgpd";   // Sample Group Description atom
    public static final String STPS = "stps";   // Partial Sync Sample atom
    public static final String STSH = "stsh";   // Shadow sync atom
    public static final String STSS = "stss";   // Sync Sample atom
    public static final String STTS = "stts";   // Time-to-sample atom
    public static final String STSZ = "stsz";   // Sample size atom
    public static final String STSC = "stsc";   // Sample-to-chunk atom
    public static final String STCO = "stco";   // Chunk Offset atom

    //stsd children
    public static final String AVC1 = "avc1";   // Advance Video Coding Box
    public static final String MP4V = "mp4v";   // MPEG-4 video
    public static final String H263 = "h263";   // H.263 video
    public static final String MP4A = "mp4a";   // MPEG-4, Advanced Audio Coding (AAC)
    public static final String MP3 = ".mp3";    // MPEG-1 layer 3, CBR & VBR (QT4.1 and later)

    // acv1 children
    public static final String AVCC = "avcC";   // AVC Configuration Box
    public static final String ST3D = "st3d";   // Stereoscopic 3D Video Box
    public static final String SV3D = "sv3d";   // Spherical Video Box
    public static final String PASP = "pasp";   // Pixel Aspect Ratio Box

    // sv3d children
    public static final String SVHD = "svhd";   // Spherical Video Header box
    public static final String PROJ = "proj";   // Projection Box

    // proj children
    public static final String PRHD = "prhd";   // Projection Header Box
    public static final String EQUI = "equi";   // Equirectangular Header Box
    public static final String CBMP = "cbmp";   // Cubemap Projection Box

    public static final Set<String> childAtomsMOOV = new HashSet<String>(Arrays.asList(TRAK, MVHD, IODS));
    public static final Set<String> childAtomsTRAK = new HashSet<String>(Arrays.asList(TKHD, EDTS, MDIA, UUID));
    // TODO: Need to differentiate HDLR contained in MDIA and MINF
    public static final Set<String> childAtomsMDIA = new HashSet<String>(Arrays.asList(MDHD, MINF, HDLR, UDTA));
    public static final Set<String> childAtomsMINF = new HashSet<String>(Arrays.asList(VMHD, HDLR, SMHD, STBL));
    public static final Set<String> childAtomsSTBL = new HashSet<String>(
        Arrays.asList(CTTS, SBGP, SDTP, SGPD, STCO, STSC, STSD, STSS, STSZ, STTS, STSH)
    );
    public static final Set<String> childAtomsSTSD = new HashSet<String>(Arrays.asList(AVC1));
    public static final Set<String> childAtomsAVC1 = new HashSet<String>(Arrays.asList(AVCC, ST3D, SV3D, PASP));
    public static final Set<String> childAtomsPROJ = new HashSet<String>(Arrays.asList(PRHD, EQUI, CBMP));
}