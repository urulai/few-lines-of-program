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

class MOOV extends Atom {

    private RandomAccessFile mRandomAccess = null;

    public MOOV(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {
        super(Constants.MOOV, length, startOffset, parent, level);

        mRandomAccess = randomAccess;
    }

    @Override
    public void parse() {
        this.parse(mRandomAccess, Constants.MOOV, this.startOffset, this.length);
    }

    public void parse(RandomAccessFile randomAccess, String atomName, long startOffset, long length) {

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

                //System.out.println(printMsg);

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

}