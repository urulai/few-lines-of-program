
/**
 * Write a description of class Ex01 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (29 Aug 2018)
 */

class Cycle {

	private int numberOfWheels = 0;

	Cycle(int wheels) {
		numberOfWheels = wheels;
	}

	public void ride() {
		System.out.println("A cycle with " + (numberOfWheels > 1 ? (numberOfWheels + " wheels.") : "a wheel."));
	}
}

class Unicycle extends Cycle {
	Unicycle() {
		super(1);
	}
}

class Bicycle extends Cycle {
	Bicycle() {
		super(2);
	}
}

class Tricycle extends Cycle {
	Tricycle() {
		super(3);
	}
}

class Ex01 {
	public static void main(String[] args) {
		Cycle[] cycles = new Cycle [] {
		    new Unicycle(),
		    new Bicycle(),
		    new Tricycle()
		};

		for (Cycle cycle : cycles) {
			cycle.ride();
		}
	}
}