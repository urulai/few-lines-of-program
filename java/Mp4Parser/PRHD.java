import java.io.RandomAccessFile;
import java.io.IOException;
import java.lang.Math;

public class PRHD extends Atom {

    private RandomAccessFile mRandomAccess = null;
    private float poseYawDegrees;
    private float posePitchDegrees;
    private float poseRollDegrees;

    public PRHD(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {
        super(Constants.PRHD, length, startOffset, parent, level);

        mRandomAccess = randomAccess;
        poseYawDegrees = poseRollDegrees = posePitchDegrees = 0;
    }

    @Override
    public void parse() {
        try {
            long fptr = mRandomAccess.getFilePointer();

            poseYawDegrees = (float) mRandomAccess.readInt() / (int) Math.pow(2, 16);
            posePitchDegrees = (float) mRandomAccess.readInt() / (int) Math.pow(2, 16);
            poseRollDegrees = (float) mRandomAccess.readInt() / (int) Math.pow(2, 16);

            mRandomAccess.seek(startOffset + length);
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }
}