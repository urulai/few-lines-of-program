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
		try {
			final byte RESERVED_LEN = 6;
			byte[] reserved = new byte[RESERVED_LEN];
			mRandomAccess.readFully(reserved);

			short datarefIndex = mRandomAccess.readShort();

			short version  = mRandomAccess.readShort();
			short revision = mRandomAccess.readShort();
			int vendor = mRandomAccess.readInt();
			int temporalQuality = mRandomAccess.readInt();
			int spatialQuality = mRandomAccess.readInt();
			short width = mRandomAccess.readShort();
			short height = mRandomAccess.readShort();

			int horizontalResolution = mRandomAccess.readInt();
			int verticalResolution = mRandomAccess.readInt();
			int dataSize = mRandomAccess.readInt();
			short frameCount = mRandomAccess.readShort();

			final byte COMPRESSOR_NAME_LEN = 32;
			byte[] bytes = new byte[COMPRESSOR_NAME_LEN];
			mRandomAccess.readFully(bytes);

			String compressorName = Util.getString(bytes);
			bytes = null;

			short depth = mRandomAccess.readShort();
			short colorTableId = mRandomAccess.readShort();

			// AVC1 ends at endoffset
			long endOffset = this.length + this.startOffset;

			long fptr = mRandomAccess.getFilePointer();
			long count = fptr - this.startOffset;  // Bytes that has been read

			//System.out.println("Bytes read: " + count + " startoffset: " + this.startOffset +  " end : " + endOffset + ", fptr: " + fptr);

			do {

				int childAtomSize = mRandomAccess.readInt();

				bytes = new byte[Constants.ATOM_LABEL_LEN];
				mRandomAccess.readFully(bytes);

				String strChildAtom = Util.getString(bytes);
				String printMsg = "";
				for (int indent = 0; indent < this.level + 1; indent++)
					printMsg += " -> ";

				printMsg += "(" + strChildAtom + ") => " + childAtomSize + " bytes,  Offset: " + fptr + ", Parent: " + Constants.AVC1;

				//System.out.println(printMsg);

				if (strChildAtom.equals(Constants.AVCC) || strChildAtom.equals(Constants.ST3D)
				    || strChildAtom.equals(Constants.PASP)) {
					//System.out.println("will parse --> " + strChildAtom);
				} else if (strChildAtom.equals(Constants.SV3D)) {
						SV3D sv3d = new SV3D(mRandomAccess, fptr, childAtomSize, Constants.AVC1, level + 1);
						sv3d.parse();
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