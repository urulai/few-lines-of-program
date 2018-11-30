import java.util.Map;
import java.util.HashMap;

interface Shape {
	void draw();
	void erase();
}

abstract class ShapeFactory {
	static Map<String, ShapeFactory> factories = new HashMap<String, ShapeFactory>();;
	abstract Shape create();

	public static void addFactory(String type, ShapeFactory factory) {
		if (factories != null)
			factories.put(type, factory);
	}

	public static Shape getShapeFromFactory(String type) {
		if (!factories.containsKey(type)) {
			try {
				Class.forName(type);
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}

			if (!factories.containsKey(type)) {
				System.out.println("Still cannot find the shape");
				return null;
			}
		}

		Shape s = factories.get(type).create();
		return s;
	}
}

class Circle implements Shape {

	private static String type = "Circle";
	private Circle() {}
	public void draw() {
		System.out.println("Circle.draw");
	}
	public void erase() {
		System.out.println("Circle.erase");
	}

	static class Factory extends ShapeFactory {
		Shape create() {
			return new Circle();
		}
	}

	static {
		ShapeFactory.addFactory(type, new Circle.Factory());
	}
}

class Triangle implements Shape {
	private static String type = "Triangle";
	private Triangle() {}
	public void draw() {
		System.out.println("Triangle.draw");
	}
	public void erase() {
		System.out.println("Triangle.erase");
	}

	static class Factory extends ShapeFactory {
		Shape create() {
			return new Triangle();
		}
	}

	static {
		ShapeFactory.addFactory(type, new Triangle.Factory());
	}
}

class Square implements Shape {
	private static String type = "Square";
	private Square() {}
	public void draw() {
		System.out.println("Square.draw");
	}
	public void erase() {
		System.out.println("Square.erase");
	}

	static class Factory extends ShapeFactory {
		Shape create() {
			return new Square();
		}
	}

	static {
		ShapeFactory.addFactory(type, new Square.Factory());
	}
}

class Ex02 {
	public static void main(String[] args) {
		String[] types = new String[] {
		    "Circle",
		    "Triangle",
		    "Square",
		};

		for (String type :  types) {
			Shape s = ShapeFactory.getShapeFromFactory(type);
			if (s != null) {
				s.draw();
				s.erase();
			}
		}
	}
}