package ReusingClasses;

import java.util.Random;

class Ex18_Finalizer {
	static final int INT_1 = 10;
	final int INT_2 = new Random().nextInt(1000);

	void display() {
		System.out.println(Ex18_Finalizer.INT_1);
		System.out.println(INT_2);
	}
}

class Ex18 {
	public static void main(Ex18_Finalizer[] args) {
		Ex18_Finalizer f1 = new Ex18_Finalizer();
		Ex18_Finalizer f2 = new Ex18_Finalizer();

		f1.display(); f2.display();
	}
}