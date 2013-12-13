package com.danilov.bool4j.test;

import com.danilov.bool4j.util.Util;

public class MainTest {

	public static void main(String[] args) {
		ITest tests[] = new ITest[6];
		tests[0] = new Test1();
		tests[1] = new Test2();
		tests[2] = new Test3();
		tests[3] = new Test4();
		tests[4] = new Test5();
		tests[5] = new Test6();
		for (int i = 0; i < tests.length; i++) {
			Util.Log("**************************************************");
			Util.Log("Test #" + (i + 1) + ": ");
			Util.Log("**************************************************");
			tests[i].test();
		}
	}

}