class Dog {

	Dog(boolean makeSound) {
		this();
	}

	Dog() {
		System.out.println("woof");
	}

	protected void finalize() {
		System.out.println("Trigger garbage collection!!!");
		// super.finalize();
	}
}

public class Ex11 {
	public static void main(String[] args) {
		Dog dog = new Dog(true);

		// Going out of scope.
		dog.finalize();
	}
}
