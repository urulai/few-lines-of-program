package ReusingClasses;

class Component1 {
	Component1() {
		System.out.println("Component1");
	}
}
class Component2 {
	Component2() {
		System.out.println("Component2");
	}
}
class Component3 {
	Component3() {
		System.out.println("Component3");
	}
}

class Root {
	Component1 com1 = new Component1();
	Component2 com2 = new Component2();
	Component3 com3 = new Component3();

	Root() {
		System.out.println("Root");
	}
}

class Stem extends Root {
	Component1 com1 = new Component1();
	Component2 com2 = new Component2();
	Component3 com3 = new Component3();

	Stem() {
		System.out.println("Stem");
	}
}

class Ex09 {
		public static void main(String[] args) {
		Stem stem = new Stem();
	}
}