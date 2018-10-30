class Inner {
	String str;

	Inner() {
		System.out.println("Uninitialized String reference is null");
	}
}

public class Ex03 {
	public static void main(String[] args) {
		Inner inner = new Inner();
	}
}
