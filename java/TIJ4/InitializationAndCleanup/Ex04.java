class Inner {
	String str;

	Inner() {
		System.out.println("Uninitialized String reference is null");
	}

	Inner(String str) {
		System.out.print("Uninitialized String reference is null");
		if(str != null)
			System.out.println(", but not this one '" + str + "'.");
	}
}

public class Ex04 {
	public static void main(String[] args) {
		Inner inner = new Inner("Hello World");
	}
}
