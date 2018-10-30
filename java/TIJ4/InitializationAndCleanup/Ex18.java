
class Inner {
	Inner(String s) {
		System.out.println(s);
	}
}

class Ex18 {
	public static void main(String[] args) {
		Inner[] inner = new Inner[5];
		for(int i = 0; i < inner.length; i++)
			inner[i] = new Inner("hello " + i);
	}
}
