package com.elong.nb.model;

public enum EnumIncrType
{
	Inventory(0),
	Rate(1),
	Order(2),
	State(3),
	Data(4);

	private int intValue;
	private static java.util.HashMap<Integer, EnumIncrType> mappings;
	private synchronized static java.util.HashMap<Integer, EnumIncrType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, EnumIncrType>();
		}
		return mappings;
	}

	private EnumIncrType(int value)
	{
		intValue = value;
		EnumIncrType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EnumIncrType forValue(int value)
	{
		return getMappings().get(value);
	}
}