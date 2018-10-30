class Dog {

	void makeSound() {
		bark();
		this.bark();
	}

	void bark() {
		System.out.println("woof");
	}

}

public class Ex08 {
	public static void main(String[] args) {
		Dog dog = new Dog();
		dog.makeSound();
	}
}

