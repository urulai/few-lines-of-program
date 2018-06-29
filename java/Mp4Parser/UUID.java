import java.io.RandomAccessFile;
import java.io.IOException;

public class UUID extends Atom {

    private RandomAccessFile mRandomAccess = null;

    public UUID(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {
        super(Constants.UUID, length, startOffset, parent, level);

        mRandomAccess = randomAccess;
    }

    @Override
    public void parse() {
        try {
            long fptr = mRandomAccess.getFilePointer();
            byte[] bytes = new byte[Constants.UUID_LEN];
            mRandomAccess.readFully(bytes);

            String uuid = javax.xml.bind.DatatypeConverter.printHexBinary(bytes);

            String printMsg = "";
            for (int indent = 0; indent < level + 1; indent++)
                printMsg += " -> ";

            printMsg += "Data: " + uuid;

            if (uuid.equals(Constants.UUID_SPHERICAL)) {
                //System.out.println(printMsg);

                // read spherical data
                // Data length = total length - (UUID_LEN) - strlen(UUID) - sizeof(SIZE)
                byte[] data = new byte[(int)length - Constants.UUID_LEN - Constants.UUID.length() - Constants.SIZE_LEN];
                mRandomAccess.readFully(data);

                String str = Util.getString(data);
                System.out.println(str);
            }

            mRandomAccess.seek(startOffset + length);

            AtomList atomList = AtomList.getInstance();
            if(atomList != null)
                atomList.insert((Atom)this);
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }
}