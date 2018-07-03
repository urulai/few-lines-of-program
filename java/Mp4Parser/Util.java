import java.io.UnsupportedEncodingException;

public class Util {

    public static int getInteger(byte[] bytes) {
        int temp = -1;

        temp = (bytes[0] & 0xFF) << 24 |
               (bytes[1] & 0xFF) << 16 |
               (bytes[2] & 0xFF) << 8 |
               (bytes[3] & 0xFF);

        return temp;
    }

    public static long getLong(byte[] bytes) {

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

    public static String getString(byte[] bytes) {
        String atom_label = null;

        try {
            atom_label = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            //System.out.println(ex.getMessage());
        } finally {
            return atom_label;
        }
    }

    public static int getAtomLevel(String name) {
        switch (name) {
        case Constants.FTYP:
        case Constants.MOOV:
        case Constants.MDAT:
            return 0;

        case Constants.IODS:
        case Constants.TRAK:
        case Constants.MVHD:
            return 1;

        case Constants.TKHD:
        case Constants.EDTS:
        case Constants.MDIA:
        case Constants.UDTA:
            return 2;

        case Constants.MDHD:
        case Constants.MINF:
        case Constants.HDLR:
            return 3;

        case Constants.DINF:
        case Constants.SMHD:
        case Constants.STBL:
        case Constants.VMHD:
            return 4;

        case Constants.CTTS:
        case Constants.SBGP:
        case Constants.SDTP:
        case Constants.STSD:
        case Constants.STTS:
        case Constants.STSS:
        case Constants.STSZ:
        case Constants.STSC:
        case Constants.STCO:
        case Constants.SGPD:
            return 5;

        case Constants.AVC1:
        case Constants.MP4V:
        case Constants.H263:
        case Constants.MP3:
        case Constants.MP4A:
            return 6;

        case Constants.AVCC:
        case Constants.ST3D:
        case Constants.SV3D:
        case Constants.PASP:
            return 7;

        case Constants.UUID:   // can be in any level
            return 99;

        default:
            return -1;
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
}