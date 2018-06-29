import java.io.RandomAccessFile;
import java.io.IOException;
import java.lang.Math;

public class EQUI extends Atom {

    private RandomAccessFile mRandomAccess = null;
    private double projectionBoundsTop;
    private double projectionBoundsBottom;
    private double projectionBoundsLeft;
    private double projectionBoundsRight;


    public EQUI(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {
        super(Constants.EQUI, length, startOffset, parent, level);

        mRandomAccess = randomAccess;
        projectionBoundsTop = projectionBoundsRight = projectionBoundsLeft = projectionBoundsBottom = 0;
    }

    @Override
    public void parse() {
        try {
            long fptr = mRandomAccess.getFilePointer();

            projectionBoundsTop = (double) mRandomAccess.readInt() / Math.pow(2, 32);
            projectionBoundsBottom = (double) mRandomAccess.readInt() / Math.pow(2, 32);
            projectionBoundsLeft = (double) mRandomAccess.readInt() / Math.pow(2, 32);
            projectionBoundsRight  = (double) mRandomAccess.readInt() / Math.pow(2, 32);

            mRandomAccess.seek(startOffset + length);

            AtomList atomList = AtomList.getInstance();
            if (atomList != null)
                atomList.insert((Atom)this);
            else {
                System.out.println("empty");
            }
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }
}