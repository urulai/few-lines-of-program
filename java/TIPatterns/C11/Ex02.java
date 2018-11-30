import java.util.Random;

interface Inhabitant {
	void interact(Inhabitant fellow);
	String getName();
	String getWeapon();

}

abstract class Character implements Inhabitant {
	protected String name;

	public String getName() {
		return name;
	}

	public void interact(Inhabitant fellow) {
		String fellowname = fellow.getName();
		System.out.println(name + " meets " + fellowname);
	}

	public int getWeaponOfChoice() {
		Random r = new Random();
		return r.nextInt(3);
	}

	public abstract String getWeapon();
}

class Dwarf extends Character {
	Dwarf(String s) {
		name = s + ", the Dwarf";
	}

	public String getWeapon() {
		int val = getWeaponOfChoice();

		switch (val) {
		default:
		case 0:
			return null;
		case 1:
			return "Jargon";
		case 2:
			return "Play";
		}
	}
}

class Elf extends Character {
	Elf(String s) {
		name = s + ", the Elf";
	}

	public String getWeapon() {
		int val = getWeaponOfChoice();

		switch (val) {
		default:
		case 0:
			return null;
		case 1:
			return "InventFeature";
		case 2:
			return "SellImaginaryProduct";
		}
	}
}

class Troll extends Character {
	Troll(String s) {
		name = s + ", the Manager";
	}

	public String getWeapon() {
		int val = getWeaponOfChoice();

		switch (val) {
		default:
		case 0:
			return null;
		case 1:
			return "Edith";
		case 2:
			return "Schedule";
		}
	}
}

class Ex2 {

	Inhabitant battle(Inhabitant i1, Inhabitant i2) {
		String weapon1 = i1.getWeapon();
		String weapon2 = i2.getWeapon();

		if (weapon1 == null && weapon2 != null)
			return i2;
		else if (weapon1 != null && weapon2 == null)
			return i1;
		else if (weapon1 == weapon2)
			return i1;

		return (weapon1.length() > weapon2.length()) ? i1 : i2;
	}


	void meeting(Inhabitant i1, Inhabitant i2, Inhabitant i3) {
		System.out.println("Match between group");
		System.out.println(i1.getName() + " <-> " + i2.getName() + " <-> " + i3.getName());

		Inhabitant winner1 = battle(i1, i2);
		Inhabitant winner2 = null;

		if (winner1 != i1)
			winner2 = battle(winner1, i2);
		else
			winner2 = battle(winner1, i1);

		System.out.println(winner2.getName() + " wins!!!");
	}

	public static void main(String[] args) {
		Inhabitant d1 = new Dwarf("Thorin");
		Inhabitant e1 = new Elf("Thranduil");
		Inhabitant t1 = new Troll("Poppy");

		Ex2 e = new Ex2();
		// Inhabitant winner = e.battle(d1, e1);
		// System.out.println(winner.getName() + " wins against " + ((winner == d1) ? e1.getName(): d1.getName()));
		e.meeting(d1, e1, t1);
	}
}

