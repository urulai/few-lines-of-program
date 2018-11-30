final class SingletonPattern {

	// Singleton with lazy initialization
	static SingletonPattern instance = null;
	private String name = "global";

	private SingletonPattern(String n) { name = n; }

	public static SingletonPattern getInstance() {
		if(instance == null)
			instance = new SingletonPattern("One for all");

		return instance;
	}

	public String getName() {
		return name;
	}
}

public class Ex01 {
	public static void main(String[] args) {
		SingletonPattern in = SingletonPattern.getInstance();
		System.out.println(in.getName());
	}
}