package ReusingClasses;

import java.util.Random;

class Ex20_Finalizer {
	final int i;
	final Integer INT;

	Ex20_Finalizer() {
		// Commenting either one of the lines below will result in an error.
		// error : variable INT / i might not be initialized.
		INT = new Integer(10);
		i = 15;
	}

	public void display() {
		System.out.println(INT);
		System.out.println(i);
	}
}


class Ex21 extends Ex20_Finalizer {

	public final void display() {
		System.out.println("Override");
	}

	public static void main(String[] args) {
		Ex21 ex = new Ex21();
		ex.display();
	}
}