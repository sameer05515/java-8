package example;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Multiplication
{
	private IntStream getTable(int i)
	{
		return IntStream.rangeClosed(1, 10).map(j -> i * j);
	}

	public void test()
	{
		int[] a = IntStream.rangeClosed(1, 10).flatMap
                                               (this::getTable)
					       .toArray();

		Arrays.stream(a).forEach(System.out::println);
	}

	public static void main(String[] args)
	{
		new Multiplication().test();
	}
}