class Dog {

	Dog() {
		System.out.println(Dog.class.getSimpleName() + " is quiet");
	}

	Dog(char b) {
		System.out.println(Dog.class.getSimpleName() + " barks");
	}

	Dog(int i) {
		System.out.println(Dog.class.getSimpleName() + " howls");
	}

	Dog(long i) {
		System.out.println(Dog.class.getSimpleName() + " howls loudly");
	}

	Dog(float f) {
		System.out.println(Dog.class.getSimpleName() + " swims");
	}

	Dog(double d) {
		System.out.println(Dog.class.getSimpleName() + " can swim faster");
	}

	Dog(boolean b) {
		System.out.println(Dog.class.getSimpleName() + " is an animal");
	}

	Dog(char c, int i) {
		System.out.println(Dog.class.getSimpleName() + " barks and also howls");
	}

	Dog(int i, char c) {
		System.out.println(Dog.class.getSimpleName() + " howls as well as bark");
	}
}

public class Ex06 {
	public static void main(String[] args) {
		Dog dog = new Dog();
		Dog dog_c = new Dog('b');
		Dog dog_i = new Dog(0);
		Dog dog_l = new Dog(1L);
		Dog dog_f = new Dog(1.0f);
		Dog dog_d = new Dog(1.0d);
		Dog dog_b = new Dog(true);
		Dog dog_ci = new Dog('b', 1);
		Dog dog_ic = new Dog(1, 'b');
	}
}

