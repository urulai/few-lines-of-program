interface Inhabitant {
	void interact(Inhabitant fellow);
	String getName();
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
}

class Dwarf extends Character {
	Dwarf(String s) {
		name = s + ", the Dwarf";
	}
}

class Elf extends Character {
	Elf(String s) {
		name = s + ", the Elf";
	}
}

class Troll extends Character {
	Troll(String s) {
		name = s + ", the Manager";
	}
}

class Project {
	public static void main(String[] args) {
		Inhabitant d1 = new Dwarf("Thorin");
		Inhabitant e1 = new Elf("Thranduil");
		Inhabitant t1 = new Troll("Poppy");

		d1.interact(e1);
		e1.interact(d1);
		t1.interact(e1);
		d1.interact(t1);

	}
}

