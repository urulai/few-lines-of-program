import java.util.Map;
import java.util.HashMap;

interface Shape {
	void draw();
	void erase();
}

abstract class ShapeFactory {
	static Map<String, ShapeFactory> factories = new HashMap<String, ShapeFactory>();
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
		System.out.print("Circle.draw");
	}
	public void erase() {
		System.out.print("Circle.erase");
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
		System.out.print("Triangle.draw");
	}
	public void erase() {
		System.out.print("Triangle.erase");
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
		System.out.print("Square.draw");
	}
	public void erase() {
		System.out.print("Square.erase");
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


class ShapeWithAttr {
	private Shape shape;
	private String variant;

	ShapeWithAttr(String v, Shape s) {
		variant = v;
		shape = s;
	}

	public void draw() {
		System.out.print(variant + " ");
		shape.draw();
		System.out.println();
	}

	public void erase() {
		System.out.print(variant + " ");
		shape.erase();
		System.out.println();
	}
}

class FactoryOfFactory extends ShapeFactory {

	private Shape shape;
	private static String variant, type;

	public ShapeWithAttr getShapeWithAttrFromFactory(String v, String t) {
		type = t;
		variant = v;

		return new ShapeWithAttr(variant, this.create());
	}

	Shape create() {
		switch (variant) {
		default:
		case "thick":
		case "thin":
			shape = ShapeFactory.getShapeFromFactory(type);
			break;
		}

		return shape;
	}
}

class Ex04 {
	public static void main(String[] args) {
		String[] variant = new String[] {
		    "thick", "thin"
		};

		String[] types = new String[] {
		    "Circle",
		    "Triangle",
		    "Square",
		};

		FactoryOfFactory ff = new FactoryOfFactory();

		for (String v : variant) {
			for (String t : types) {
				ShapeWithAttr swa = ff.getShapeWithAttrFromFactory(v, t);
				swa.draw();
				swa.erase();
			}
		}
	}
}