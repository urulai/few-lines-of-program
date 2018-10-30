
/**
 * Write a description of class Ex10 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (29 Aug 2018)
 */

class Move {
	public void walk(boolean faster) {
		if (faster)
			run();
	}

	public void run() {
		System.out.println("run -- base");
	}
}

class Ex11 extends Move {
	@Override
	public void run() {
		System.out.println("run -- overridden");
	}

	public static void main(String[] args) {
		Ex10 ex10 = new Ex10();
		Move mv = ex10;

		mv.walk(true);
	}
}