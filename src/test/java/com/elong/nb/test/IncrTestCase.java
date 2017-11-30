package com.elong.nb.test;

import org.junit.Test;

import com.elong.nb.submeter.service.impl.SubmeterTableCalculate;

public class IncrTestCase {
	
	private SubmeterTableCalculate calculate = new SubmeterTableCalculate();

	@Test
	public void test() throws Exception {
		long id = 121l;
		System.out.println(calculate.getSegmentBeginId(id));
		System.out.println(calculate.getSegmentEndId(id));
	}
	
}
