
public class TypesDemo {
	public static void main(String[] args) {
		/*
		Типы по значению: boolean, char, byte, short, int, long, float, double
			Хранятся на стеке
			При передаче как параметры значение копируется
			Для хранения в куче производится «упаковка» (boxing)

		Про boolean:
			Как таковой, не поддерживается в JVM.
			Представляется как int. 0 — false, 1 — true.
			Массив boolean — это массив byte.
		*/
		int dec = 11;		//В 10-й системе счисления
		
		int oct = 013;		//В 8-й системе счисления

		System.out.println("11 == 013? " + ((dec == oct)? "True":"False")); //Будет "True"

		int hex = 0xB;		//В 16-й системе счисления
		System.out.println("11 == 0xB? " + ((dec == hex)? "True":"False")); //Будет "True"

		int bin = 0b1011;	//В 2-й системе счисления
		System.out.println("11 == 0b1011? " + ((dec == bin)? "True":"False")); //Будет "True"

		/*
		NaN:
			-специальное значение, которое ессть у float и double
			-сравнение с NaN всегда false
		*/
		Float nan = Float.NaN;
		System.out.println("NaN != NaN? " + ((nan != nan)? "True":"False")); //Будет "True"

		/*
		Boxing/Unboxing
		*/
		int primInt = 5;
		Integer objInt = primInt;
		++objInt;

		/*
		В итоге это преобразуется компилятором в это:
			int primInt = 5;
			Integer objInt = Integer.valueOf(primInt);
			objInt = Integer.valueOf(objInt.intValue() + 1);
		*/

		/*
		Полезные методы:
			Integer.parseInt, Integer.toString, Long.parseLong, Long.toString
			Статические методы классов Float/Double: isNaN, isInfinite, parse*, compare
			Статические методы класса java.lang.Math
		*/
		int intValDec = Integer.parseInt("12");
		int intValOct = Integer.parseInt("14", 8);
		System.out.println("Integer.parseInt(\"12\") == Integer.parseInt(\"14\", 8)? " + ((intValDec == intValOct)? "True":"False")); //Будет "True"

		/*
		Сравнение объектов:
			==, != для объектов это проверка на одинаковость ссылки (т. е. ссылка ведёт в одно и то же место).
			equals по контракту проверяет на одинаковость содержания (по умолчанию проверка на одинаковость ссылки).
			compareTo(a, b) (интерфейс Comparable) сравнивает с учётом порядка: -1, когда a < b; 0, когда a == b; +1, когда a > b.

		*/
		Integer intVal1 = Integer.valueOf("127");
		Integer intVal2 = Integer.valueOf("127");
		System.out.println("Integer.valueOf(\"127\") == Integer.valueOf(\"127\")? " + ((intVal1 == intVal2)? "True":"False")); //Будет "True"

		intVal1 = Integer.valueOf("128");
		intVal2 = Integer.valueOf("128");
		System.out.println("Integer.valueOf(\"128\") == Integer.valueOf(\"128\")? " + ((intVal1 == intVal2)? "True":"False")); //Будет "False"
		System.out.println("Integer.valueOf(\"128\").equals(Integer.valueOf(\"128\"))? " + ((intVal1.equals(intVal2))? "True":"False")); //Будет "True"
	}
}