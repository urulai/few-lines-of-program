import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class BadShapeCreation extends Exception {
	BadShapeCreation(String type) {
		super(type);
	}
}

abstract class Shape {
	public abstract void draw();
	public abstract void erase();
	public static Shape factory(String type) throws BadShapeCreation {
		if (type.equals("Circle"))
			return new Circle();
		if (type.equals("Square"))
			return new Square();
		if (type.equals("Triangle"))
			return new Triangle();

		throw new BadShapeCreation(type);
	}
}

class Circle extends Shape {
	Circle() {} // Friendly constructor
	public void draw() {
		System.out.println("Circle.draw");
	}
	public void erase() {
		System.out.println("Circle.erase");
	}
}

class Square extends Shape {
	Square() {} // Friendly constructor
	public void draw() {
		System.out.println("Square.draw");
	}
	public void erase() {
		System.out.println("Square.erase");
	}
}

class Triangle extends Shape {
	Triangle() {}
	public void draw() {
		System.out.println("Triangle.draw");
	}
	public void erase() {
		System.out.println("Triangle.erase");
	}
}

public class Ex01 {
	String shlist[] = { "Circle",
	                    "Square",
	                    "Triangle",
	                  };
	List<Shape> shapes = new ArrayList<Shape>();

	public void test() {
		try {
			for (int i = 0; i < shlist.length; i++)
				shapes.add(Shape.factory(shlist[i]));
		} catch (BadShapeCreation e) {
			e.printStackTrace(System.err);
			assert(false); // Fail the unit test
		}

		Iterator i = shapes.iterator();
		while (i.hasNext()) {
			Shape s = (Shape)i.next();
			s.draw();
			s.erase();
		}
	}

	public static void main(String args[]) {
		new Ex01().test();
	}
}