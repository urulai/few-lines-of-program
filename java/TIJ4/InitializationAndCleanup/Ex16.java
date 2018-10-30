
class Ex16 {
	void printStr(String[] args) {
		for(String s: args)
			System.out.println(s);
	}

	public static void main(String[] args) {
		String[] str = new String[args.length];

		for(int i = 0; i < args.length; i++)
			str[i] = new String(args[i]);

		for(String s : str)
			System.out.println(s);

		Ex16 ex = new Ex16();
		ex.printStr(new String[] { "Ghana", "Accra" });
		ex.printStr(new String[] { new String("Ghana"), new String("Accra")});
	}
}
