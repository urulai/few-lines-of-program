import java.io.RandomAccessFile;
import java.io.IOException;

public class AVC1 extends Atom {

	private RandomAccessFile mRandomAccess = null;

	public AVC1(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {
		super(Constants.STSD, length, startOffset, parent, level);

		mRandomAccess = randomAccess;
	}

	@Override
	public void parse() {
		int count = 0;

		System.out.println("Parse AVC1");

	}
}