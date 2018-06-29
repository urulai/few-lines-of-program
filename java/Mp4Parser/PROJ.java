import java.io.RandomAccessFile;
import java.io.IOException;

public class PROJ extends Atom {

	private RandomAccessFile mRandomAccess = null;

	public PROJ(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {

		super(Constants.PROJ, length, startOffset, parent, level);

		mRandomAccess = randomAccess;
	}

	@Override
	public void parse() {

		try {
			long fptr = mRandomAccess.getFilePointer();
			long endOffset = this.length + this.startOffset;

			do {

				int childAtomSize = mRandomAccess.readInt();

				byte[] bytes = new byte[Constants.ATOM_LABEL_LEN];
				mRandomAccess.readFully(bytes);

				String strChildAtom = Util.getString(bytes);
				String printMsg = "";
				for (int indent = 0; indent < this.level + 1; indent++)
					printMsg += " -> ";

				printMsg += "(" + strChildAtom + ") => " + childAtomSize + " bytes,  Offset: " + fptr + ", Parent: " + Constants.PROJ;

				System.out.println(printMsg);

				if (strChildAtom.equals(Constants.PRHD)) {
					PRHD prhd = new PRHD(mRandomAccess, fptr, childAtomSize, Constants.PRHD, level + 1);
					prhd.parse();
				} else if (strChildAtom.equals(Constants.EQUI)) {
					EQUI equi = new EQUI(mRandomAccess, fptr, childAtomSize, Constants.EQUI, level + 1);
					equi.parse();
				} else if (strChildAtom.equals(Constants.CBMP)) {

				}

				mRandomAccess.seek(fptr + childAtomSize);
				fptr = mRandomAccess.getFilePointer();

			} while (fptr < endOffset);

			mRandomAccess.seek(this.startOffset + this.length);
		} catch (IOException ex) {
			//System.out.println(ex.getMessage());
		}

	}
}