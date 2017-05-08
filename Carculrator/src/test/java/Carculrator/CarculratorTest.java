package Carculrator;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarculratorTest {

	@Test
	public void createCalculator() {
		Calculator calc = new Calculator();
		int r=calc.getResult();
		assertEquals(0, r);
	}

	@Test
	
	public void createCalculatorWithValue(){
		Calculator calc = new Calculator(20);
		int r=calc.getResult();
		assertEquals(20, r);
	}


	@Test

	public void createCalculatoradd(){
	Calculator calc = new Calculator();
	calc.add(20);
	assertEquals(20, calc.getResult());
	}



	@Test

	public void createCalculatoradd2(){
	Calculator calc = new Calculator(20);
	calc.add(30);
	assertEquals(50, calc.getResult());
	}

	@Test

	public void testClear1(){
	Calculator calc = new Calculator();
	calc.clear();
	assertEquals(0, calc.getResult());
}
	@Test
	public void testClear2(){
		Calculator calc = new Calculator(30);
		calc.clear();
	assertEquals(0, calc.getResult());
		
	}
}