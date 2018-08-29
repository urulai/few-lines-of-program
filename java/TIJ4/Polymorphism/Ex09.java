
/**
 * Write a description of Ex09 here.
 *
 * @author (Subramanian Narasimhan)
 * @version (28 Aug 2018)
 */

import java.util.Random;

class Rodent {
    public void eat() {
        System.out.print("eats");
    }

    public void run() {
        System.out.print("runs");
    }
}

class Mouse extends Rodent {
    @Override
    public void eat() {
        System.out.print("Mouse ");
        super.eat();
    }

    @Override
    public void run() {
        System.out.print("Mouse ");
        super.run();
    }

}

class Gebril extends Rodent {
    @Override
    public void eat() {
        System.out.print("Gebril ");
        super.eat();
    }

    @Override
    public void run() {
        System.out.print("Gebril ");
        super.run();
    }
}

class Hamster extends Rodent {
    @Override
    public void eat() {
        System.out.print("Hamster ");
        super.eat();
    }

    @Override
    public void run() {
        System.out.print("Hamster ");
        super.run();
    }
}

class RandomRodentGenerator {
    private Random rand = null;

    public RandomRodentGenerator() {
        rand = new Random();
    }

    public Rodent generate() {
        Rodent rodent = null;

        int val = rand.nextInt(3);

        switch (val) {
        default:
        case 0:
            rodent = new Mouse();
            break;
        case 1:
            rodent = new Gebril();
            break;
        case 2:
            rodent = new Hamster();
            break;
        }

        return rodent;
    }
}

public class Ex09 {
    public static void main(String[] args) {
        // entry point

        Rodent[] rodents = new Rodent[3];
        RandomRodentGenerator generator = new RandomRodentGenerator();
        for (int idx = 0; idx < rodents.length; idx++) {
            rodents[idx] = generator.generate();

            if (rodents[idx] != null) {
                rodents[idx].run();
                System.out.println();
            }
        }
    }
}
