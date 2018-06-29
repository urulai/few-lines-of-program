import java.io.RandomAccessFile;
import java.io.IOException;

public class STSD extends Atom {

	private RandomAccessFile mRandomAccess = null;

	public STSD(RandomAccessFile randomAccess, long startOffset, long length, String parent, int level) {

		super(Constants.STSD, length, startOffset, parent, level);

		mRandomAccess = randomAccess;
	}

	@Override
	public void parse() {
		int count = 0;

		try {
			long fptr = mRandomAccess.getFilePointer();
			byte version = mRandomAccess.readByte();

			// System.out.println("Version: " + version);

			//skip flags which is usually set to zero.
			byte[] flags = new byte[Constants.STSD_FLAGS_LEN];
			mRandomAccess.readFully(flags);

			int numEntries = mRandomAccess.readInt();
			System.out.println("Entries: " + numEntries);

			// Start reading child elements, if there are any
			if (numEntries > 0) {

				do {
					// Parse sample description
					fptr = mRandomAccess.getFilePointer();
					int childAtomSize = mRandomAccess.readInt();


					byte[] bytes = new byte[Constants.ATOM_LABEL_LEN];
					mRandomAccess.readFully(bytes);

					String strChildAtom = Util.getString(bytes);
					String printMsg = "";
					for (int indent = 0; indent < this.level + 1; indent++)
						printMsg += " -> ";


					printMsg += "(" + strChildAtom + ") => " + childAtomSize + " bytes,  Offset: " + fptr + ", Parent: " + Constants.STSD;

					System.out.println(printMsg);

					if (strChildAtom.equals(Constants.AVC1)) {
						AVC1 avc1 = new AVC1(mRandomAccess, fptr, childAtomSize, Constants.STSD, level);
						avc1.parse();
					}

				} while (--numEntries > 0);
			}
			mRandomAccess.seek(startOffset + length);
		} catch (IOException ex) {
			//System.out.println(ex.getMessage());
		}

	}
}