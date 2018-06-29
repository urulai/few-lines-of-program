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

    public String getparent() {
        return parent;
    }

    public int getlevel() {
        return level;
    }

    public long getlength() {
        return length;
    }

    public long getstartOffset() {
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

    public void setstartOffset(Long offset) {
        startOffset = offset;
    }

    public void parse() {}
}