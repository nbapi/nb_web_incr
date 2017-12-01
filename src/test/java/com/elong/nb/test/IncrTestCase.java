package com.elong.nb.test;

import org.junit.Test;

import com.elong.nb.submeter.util.SubmeterTableCalculate;

public class IncrTestCase {
	
	@Test
	public void test() throws Exception {
		long id = 121l;
		System.out.println(SubmeterTableCalculate.getSegmentBeginId(id));
		System.out.println(SubmeterTableCalculate.getSegmentEndId(id));
	}
	
}
