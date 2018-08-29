
/**
 * Write a description of Ex03 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (28 Aug 2018)
 */
import java.util.Random;

class Shape {
    public void draw() {
        System.out.println("base - draw");
    }

    public void fill() {
        System.out.println("base - fill");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Circle - draw");
    }

    @Override
    public void fill() {
     System.out.println("Circle - fill");
    }
}

class Triangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Triangle - draw");
    }

    @Override
    public void fill() {
        System.out.println("Triangle - fill");
    }
}

class Square extends Shape {
    @Override
    public void draw() {
        System.out.println("Square - draw");
    }

    @Override
    public void fill() {
        System.out.println("Square - fill");
    }
}

class RandomShapeGenerator {
    Random rand = null;
    public RandomShapeGenerator() {
        rand = new Random();
    }

    public Shape generate() {
        Shape s = null;

        int val = rand.nextInt(3);
        System.out.print(val + " ");

        switch (val) {
        default:
        case 0:
            s = new Circle();
            break;
        case 1:
            s = new Triangle();
            break;
        case 2:
            s = new Square();
            break;
        }

        return s;
    }
}
public Ex02 {
    public static void main(String[] args) {
        // entry point

        Shape[] shapes = new Shape[3];
        RandomShapeGenerator generator = new RandomShapeGenerator();
        for (int idx = 0; idx < shapes.length; idx++) {
            shapes[idx] = generator.generate();

            if (shapes[idx] != null)
                shapes[idx].fill();
        }
    }
}
