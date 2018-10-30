import java.util.*;

class Ex14 {

	static String field1 = "field1";
	static String field2;
	static {
		field2 = "field2";
	}

	static void print() {
		System.out.println(Ex14.field1);
		System.out.println(Ex14.field2);
	}

	public static void main(String[] args) {
		print();
	}
}
