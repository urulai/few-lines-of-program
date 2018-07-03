import java.util.ArrayList;

public class AtomList {

	private static AtomList instance = null;

	private ArrayList<Atom> atoms = null;

	public static AtomList getInstance() {
		if (instance == null)
			instance = new AtomList();

		return instance;
	}

	private AtomList() {}

	public void insert(Atom atom) {
		if (atoms != null) {
			atoms.add(atom);
		} else {
			atoms = new ArrayList<Atom>();
			atoms.add(atom);
		}
	}

	public ArrayList<Atom> getList() {
		return atoms;
	}
}