package ReusingClasses;

class Game {
	Game() {
		System.out.println("Default constructor -> Game");
	}

	Game(int arg) {
		System.out.println(Game.class.getSimpleName() + " -> " + arg);
	}
}

class BoardGame extends Game {
	BoardGame() {
		System.out.println(BoardGame.class.getSimpleName());
	}
	BoardGame(int arg) {
		super(arg);
		System.out.println(BoardGame.class.getSimpleName() + " -> " + arg);
	}
}

class Chess extends BoardGame {
	Chess() {
		System.out.println(Chess.class.getSimpleName());
	}
	Chess(int arg) {
		super(arg);
		System.out.println(Chess.class.getSimpleName() + " -> " + arg);
	}
}

class Ex06 {
	public static void main(String[] args) {
		Chess c_def;
	}
}