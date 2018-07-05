import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

import java.util.ArrayList;

// import java.lang.Integer;

class VideoFinder {

	public static ArrayList<String> parse(ArrayList<File> fileList) {

		ArrayList<String> lst360Videos = null;

		for (int file_idx = 0; file_idx < fileList.size(); file_idx++) {

			File f = fileList.get(file_idx);

			if (f.exists() && !f.isDirectory()) {

				long fileSize = f.length();
				//System.out.println("File path: " + f.getPath());

				try(RandomAccessFile randomAccess = new RandomAccessFile(f, "r")) {

					try {
						long filePointer = randomAccess.getFilePointer();

						byte[] bytes = new byte[Constants.ATOM_LABEL_LEN];

						while (filePointer < fileSize) {
							int count = 0;
							boolean skip = false;

							int atomSize = randomAccess.readInt();
							long atomSizeLong = -1;

							// //System.out.println("Size in dec: " + atomSize);
							// //System.out.println("Size in hex: " + Integer.toHexString(atomSize));

							randomAccess.readFully(bytes);

							String strAtom = Util.getString(bytes);

							int matchLevel;
							if ((matchLevel = Util.getAtomLevel(strAtom)) != -1) {

								if (strAtom.equalsIgnoreCase(Constants.MDAT) && atomSize == 1) {
									// size will be after mdat
									atomSizeLong = randomAccess.readLong();

									skip = true;
								}

								if (matchLevel == 0) {

									if (strAtom.equals(Constants.MOOV)) {
										MOOV moov = new MOOV(randomAccess, filePointer, atomSize, Constants.FILE_LEVEL, matchLevel);
										moov.parse();
									}
								}
							}

							long newOffset;
							if (skip && atomSizeLong != -1)
								newOffset = filePointer + atomSizeLong;
							else
								newOffset = filePointer + atomSize;

							randomAccess.seek(newOffset);

							filePointer = randomAccess.getFilePointer();
						}

						bytes = null;

						AtomList objAtomList = AtomList.getInstance();
						if (objAtomList != null) {

							ArrayList<Atom> lst = objAtomList.getList();
							if (lst != null && lst.size() > 0) {

								if (lst360Videos == null)
									lst360Videos = new ArrayList<String>();

								lst360Videos.add(f.getPath());
								lst.clear();
							}
						}
					} catch (EOFException ex) {
						//System.out.println(ex + " File : " +  f.getPath());
						ex.printStackTrace();
					} catch (IOException ex) {
						//System.out.println(ex);
					}
				} catch (FileNotFoundException ex) {
					//System.out.println(ex);
				} catch (IOException ex) {
					//System.out.println(ex);
				}
			}
		}

		return lst360Videos;
	}
}