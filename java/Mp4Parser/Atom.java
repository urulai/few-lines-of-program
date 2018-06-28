// MP4
public class Atom {

    long atomSize;
    long atomOffset; // form file start
    String parentAtom;
    String atomLevel;
    String atomName;

    Atom(String name) {
        atomName = name;
    }

    Atom(String name, long size, long offset) {
        this(name);
        atomSize = size;
        atomOffset = offset;
    }

    Atom(String name, long size, long offset, String parent, String level) {
        this(name, size, offset);
        parentAtom = parent;
        atomLevel = level;
    }

    public String getName() {
        return atomName;
    }

    public String getParentAtom() {
        return parentAtom;
    }

    public String getAtomLevel() {
        return atomLevel;
    }

    public long getAtomSize() {
        return atomSize;
    }

    public long getAtomOffset() {
        return atomOffset;
    }

    public void setName(String name) {
        atomName = name;
    }

    public void setAtomLevel(String level) {
        atomLevel = level;
    }

    public void setAtomSize(int sz) {
        atomSize = sz;
    }

    public void setParent(String parent) {
        parentAtom = parent;
    }

    public void setAtomOffset(Long offset) {
        atomOffset = offset;
    }
}