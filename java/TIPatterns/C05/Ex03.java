interface Obstacle {
	void action();
}

interface Player {
	void interactWith(Obstacle ob);
}

class Gnomes implements Player {
	private String name;

	Gnomes(String n) {
		name = n;
	}

	public void interactWith(Obstacle ob) {
		System.out.print(this.name + " deals with ");
		ob.action();
	}
}

class Fairies implements Obstacle {
	private String name;

	Fairies(String n) {
		name = n;
	}

	public void action() {
		System.out.println(this.name + "\'s magical spells.");
	}
}

interface GameFactory {
	Player getPlayer();
	Obstacle makeObstacle();
}

class GnomesandFairies implements GameFactory {
	public Player getPlayer() {
		return new Gnomes("Paris");
	}

	public Fairies makeObstacle() {
		return new Fairies("Goblin");
	}
}

class GameEnvironment {
	private GameFactory gf = null;

	void setGameFactory(GameFactory g) {
		gf = g;
	}

	void play() {
		Player p = gf.getPlayer();
		Obstacle ob =  gf.makeObstacle();

		p.interactWith(ob);
	}
}

class Ex03 {
	public static void main(String[] args) {
		GameFactory f = new GnomesandFairies();
		GameEnvironment g = new GameEnvironment();

		g.setGameFactory(f);
		g.play();
	}
}
