class Inner {
	String str = "Hello";

	Inner(String s) {
		System.out.println(s);
	}
}

class Ex15 {

	Inner inner;

	{
		inner = new Inner("Helix");
		System.out.println("Initializing inner class");
	}

	public static void main(String[] args) {
		new Ex15();
	}
}
