
public class StringDemo {
	public static void main(String[] args) {
		/*
		String:
			Внутри хранит массив char
			Неизменяемый объект (любые операции возвращают новый объект)
		*/
		int a = 1;
		String result = "a=" + a;
		/*
		Компилятор преобразовывает это в:
			int i = 1;
			String result = new StringBuilder().append("a=").append(i).toString();
		*/

		String hello = "Hello", lo = "lo";
        System.out.println(hello == "Hello"); 				// true
        System.out.println(hello == ("Hel"+"lo")); 			// true
        System.out.println(hello == ("Hel"+lo)); 			// false
        System.out.println(hello == ("Hel"+lo).intern()); 	// true

	}
}