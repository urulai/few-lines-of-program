package ReusingClasses;

class Frog extends Amphibian {
	@Override
	public void jump() {
		System.out.println(Frog.class.getSimpleName() + " likes to jump");
	}
}