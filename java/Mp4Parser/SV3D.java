import java.io.RandomAccessFile;
import java.io.IOException;

public class SV3D extends Atom {

	private RandomAccessFile mRandomAccess = null;

	public SV3D(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {

		super(Constants.SV3D, length, startOffset, parent, level);

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

				printMsg += "(" + strChildAtom + ") => " + childAtomSize + " bytes,  Offset: " + fptr + ", Parent: " + Constants.SV3D;

				//System.out.println(printMsg);

				if (strChildAtom.equals(Constants.SVHD)) {

				} else if (strChildAtom.equals(Constants.PROJ)) {
					PROJ proj = new PROJ(mRandomAccess, fptr, childAtomSize, Constants.SV3D, level + 1);
					proj.parse();
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