class Inner {
	String str;

	Inner() {
		if (str == null)
			System.out.println("Uninitialized String reference is null");
	}
}

public class Ex01 {
	public static void main(String[] args) {
		Inner inner = new Inner();
	}
}

