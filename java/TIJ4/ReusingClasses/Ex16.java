package ReusingClasses;

class Ex16 {
	public static void main(String[] args) {
		Frog f = new Frog();

		Amphibian a = (Amphibian) f;
		a.swim();
		a.walk();
		a.jump();
	}
}
