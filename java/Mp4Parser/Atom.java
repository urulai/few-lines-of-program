// MP4
public class Atom {

    long length;
    long startOffset; // form file start
    String parent;
    int level;
    String name;
    byte[] data;

    Atom(String name) {
        this.name = name;
    }

    Atom(String name, long size, long offset) {
        this(name);
        this.length = size;
        this.startOffset = offset;
    }

    Atom(String name, long size, long offset, String parent, int level) {
        this(name, size, offset);
        this.parent = parent;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public long getLength() {
        return length;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setName(String name) {
        name = name;
    }

    public void setlevel(int level) {
        level = level;
    }

    public void setlength(int sz) {
        length = sz;
    }

    public void setParent(String parent) {
        parent = parent;
    }

    public void setStartOffset(Long offset) {
        startOffset = offset;
    }

    public void parse() {}
}