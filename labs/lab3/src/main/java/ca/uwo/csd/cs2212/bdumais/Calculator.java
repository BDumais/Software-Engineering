package ca.uwo.csd.cs2212.bdumais;

public class Calculator{

	public int add(int a, int b) throws ArithmeticException {
		int result = a + b;

		if (((a ^ result) & (b ^ result)) < 0)
			throw new ArithmeticException("Overflow / Underflow");
		else
			return result;
	}

}
