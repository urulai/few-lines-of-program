final class SingletonPattern {

	// Singleton with hard limit on number of objects.

	private static final int MAX = 10;
	private static int count = 0;
	private static SingletonPattern[] pools = null;
	private int id = -1;

	private SingletonPattern(int n) { id = n; }

	public static SingletonPattern getInstance() {
		if (pools == null) {
			pools = new SingletonPattern[MAX];
		}

		if (count < MAX) {
			pools[count] = new SingletonPattern(count + 1);
		} else {
			// reset count to zero.
			count = 0;
		}

		return pools[count++];
	}

	public int getId() {
		return id;
	}
}

public class Ex02 {
	public static void main(String[] args) {
		final int TEST_TOTAL_NUMBER_OBJS = 20;
		SingletonPattern in[] = new SingletonPattern[TEST_TOTAL_NUMBER_OBJS];

		for (int i = 0; i < in.length; i++) {
			in[i] = SingletonPattern.getInstance();
			if(in[i] != null) {
				// After hitting MAX number of objects,
				// getInstance() will return the first instance that was created.
				System.out.println(in[i].getId());
			}
		}
	}
}