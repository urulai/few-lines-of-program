package ReusingClasses;

import java.util.Random;

class Ex19_Finalizer {
	final int i;
	final Integer INT;

	Ex19_Finalizer() {
		// Commenting either one of the lines below will result in an error.
		// error : variable INT / i might not be initialized.
		INT = new Integer(10);
		i = 15;
	}

	void changeRef() {
		// error: cannot assign a value to final variable INT;
		// INT = new Integer(20);
	}

	void display() {
		System.out.println(INT);
		System.out.println(i);
	}
}

class Ex19 {
	public static void main(String[] args) {
		Ex19_Finalizer f1 = new Ex19_Finalizer();
		f1.display();
	}
}