class Cup {

	private int mId;
	Cup() {
	}

	Cup(int id) {
		mId = id;
	}

	void f() {
		System.out.println("Cup(" + mId + ")");
	}
}

class Cups {
	static Cup cup1;
	static Cup cup2;

	static {
		cup1 = new Cup(1);
		cup2 = new Cup(2);
	}

	Cups() {
		System.out.println("Cups");
	}
}

public class Ex13 {
	public static void main(String[] args) {
		Cups.cup1.f();
		Cups.cup2.f();
	}
}

