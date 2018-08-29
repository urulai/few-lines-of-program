/**
 * Write a description of Ex02 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (29 Aug 2018)
 */

import java.util.Random;

class Instrument {
	void play(Note n) {
		Ex08.print("Instrument.play() " + n);
	}

	public String toString() {
		return "Instrument";
	}

	void adjust() {
		Ex08.print("Adjusting Instrument");
	}

}

class Wind extends Instrument {
	void play(Note n) {
		Ex08.print("Wind.play() " + n);
	}

	public String toString() {
		return "Wind";
	}

	void adjust() {
		Ex08.print("Adjusting Wind");
	}

}

class Percussion extends Instrument {
	void play(Note n) {
		Ex08.print("Percussion.play() " + n);
	}

	public String toString() {
		return "Percussion";
	}

	void adjust() {
		Ex08.print("Adjusting Percussion");
	}
}

class Stringed extends Instrument {
	void play(Note n) {
		Ex08.print("Stringed.play() " + n);
	}

	public String toString() {
		return "Stringed";
	}

	void adjust() {
		Ex08.print("Adjusting Stringed");
	}

}

class Brass extends Wind {
	void play(Note n) {
		Ex08.print("Brass.play() " + n);
	}

	void adjust() {
		Ex08.print("Adjusting Brass");
	}
}

class Woodwind extends Wind {
	void play(Note n) {
		Ex08.print("Woodwind.play() " + n);
	}

	public String toString() {
		return "Woodwind";
	}

	void adjust() {
		Ex08.print("Adjusting Woodwind");
	}
}

class Flute extends Woodwind {
	void play(Note n) {
		Ex08.print("Flute.play() " + n);
	}

	public String toString() {
		return "Flute";
	}

	void adjust() {
		Ex08.print("Adjusting Flute");
	}
}

class RandomInstrumentGenerator {
	Random rand = null;

	public RandomInstrumentGenerator() {
		rand = new Random();
	}

	public Instrument generate() {
		Instrument instance = null;

		int val = rand.nextInt(5);

		switch (val) {
		default:
		case 0:
			instance = new Wind();
			break;
		case 1:
			instance = new Percussion();
			break;
		case 2:
			instance = new Brass();
			break;
		case 3:
			instance = new Stringed();
			break;
		}

		return instance;
	}
}

public class Ex08 {
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

		RandomInstrumentGenerator generator = new RandomInstrumentGenerator();
		Instrument[] orchestra = new Instrument[4];

		for(int idx = 0; idx < orchestra.length; idx++)
			orchestra[idx] = generator.generate();

		// tuneAll(orchestra);
		printAll(orchestra);
	}
}
