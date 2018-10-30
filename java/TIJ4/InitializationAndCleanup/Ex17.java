
class Inner {
	Inner(String s) {
		System.out.println(s);
	}
}

class Ex17 {
	public static void main(String[] args) {
		Inner[] inner = new Inner[5];
	}
}
