/**
 * Write a description of Ex02 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (29 Aug 2018)
 */

class Instrument {
	void play(Note n) {
		Ex06.print("Instrument.play() " + n);
	}

	public String toString() {
		return "Instrument";
	}

	void adjust() {
		Ex06.print("Adjusting Instrument");
	}

}

class Wind extends Instrument {
	void play(Note n) {
		Ex06.print("Wind.play() " + n);
	}

	public String toString() {
		return "Wind";
	}

	void adjust() {
		Ex06.print("Adjusting Wind");
	}

}

class Percussion extends Instrument {
	void play(Note n) {
		Ex06.print("Percussion.play() " + n);
	}

	public String toString() {
		return "Percussion";
	}

	void adjust() {
		Ex06.print("Adjusting Percussion");
	}

}

class Stringed extends Instrument {
	void play(Note n) {
		Ex06.print("Stringed.play() " + n);
	}

	public String toString() {
		return "Stringed";
	}

	void adjust() {
		Ex06.print("Adjusting Stringed");
	}

}

class Brass extends Wind {
	void play(Note n) {
		Ex06.print("Brass.play() " + n);
	}

	void adjust() {
		Ex06.print("Adjusting Brass");
	}

}

class Woodwind extends Wind {
	void play(Note n) {
		Ex06.print("Woodwind.play() " + n);
	}

	public String toString() {
		return "Woodwind";
	}

	void adjust() {
		Ex06.print("Adjusting Woodwind");
	}
}


public class Ex06 {
	public static void print(String str) {
		System.out.println(str);
	}

	public static void tune(Instrument i) {
		i.play(Note.MIDDLE_C);
	}

	public static void tuneAll(Instrument[] e) {
		for (Instrument i : e)
			tune(i);
	}

	public static void printAll(Instrument[] in) {
		for (Instrument i : in)
			System.out.println(i);
	}

	public static void main(String[] args) {
		// Upcasting during addition to the array:
		Instrument[] orchestra = {
			new Wind(),
			new Percussion(),
			new Stringed(),
			new Brass(),
			new Woodwind()
		};

		tuneAll(orchestra);
		printAll(orchestra);
	}
}
