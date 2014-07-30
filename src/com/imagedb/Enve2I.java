package com.imagedb;

public class Enve2I implements Cloneable {
	public int XMin = 0;
	public int YMin = 0;
	public int XMax = 0;
	public int YMax = 0;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}