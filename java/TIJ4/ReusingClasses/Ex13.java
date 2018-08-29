package ReusingClasses;

class Sum {

	void add(String s1, String s2) {
		System.out.print("String : ");
		System.out.println(s1 + s2);
	}

	void add(int i1, int i2) {
		System.out.print("Int : ");
		System.out.println(i1 + i2);
	}

	void add(float f1, float f2) {
		System.out.print("Float : ");
		System.out.println(f1 + f2);
	}
}

class Adder extends Sum {
	void add(double d1, double d2) {
		System.out.print("Double : ");
		System.out.println(d1 + d2);
	}
}

class Ex13 {
	public static void main(String[] args) {
		Adder a = new Adder();

		a.add(0.1f, 0.3f);
		a.add(2.2d, 0.3d);
		a.add(1, 2);
		a.add("awe", "some");
	}
}