class Tank {

	private boolean isTankEmpty = false;

	Tank() { }

	Tank(boolean status) {
		isTankEmpty	= status;
	}

	void setTankStatus(boolean status) {
		isTankEmpty = status;
	}

	protected void finalize() {
		if (!isTankEmpty)
			System.out.println("Error: tank is not empty");
		else
			System.out.println("Success: tank is empty");
	}
}

public class Ex12 {
	public static void main(String[] args) {
		Tank t = new Tank(true);
		t.setTankStatus(false);
		t = null;

		System.gc();
	}
}
