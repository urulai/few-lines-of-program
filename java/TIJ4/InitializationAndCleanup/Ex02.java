class Inner {
	String str = "Hello";

	Inner() {
		System.out.println(str);
		str = "Inside constructor";
		System.out.println(str);
	}
}

public class Ex02 {
	public static void main(String[] args) {
		Inner inner = new Inner();
	}
}
