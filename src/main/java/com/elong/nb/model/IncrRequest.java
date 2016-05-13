package com.elong.nb.model;

import java.math.BigInteger;

public class IncrRequest {
	private BigInteger LastId;
	private int Count = 1000;
	public BigInteger getLastId() {
		return LastId;
	}
	public void setLastId(BigInteger lastId) {
		LastId = lastId;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}

}
