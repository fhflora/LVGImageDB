package com.imagedb;

/** Envelope坐标范围信息 */
public class Enve2D implements Cloneable {
	public double XMin = 0;
	public double YMin = 0;
	public double XMax = 0;
	public double YMax = 0;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/** 扩大坐标范围 */
	public void extend(double dX, double dY) {
		XMax += dX;
		XMin -= dX;
		YMax += dY;
		YMin -= dY;

		XMin = Math.max(0, XMin);
		YMin = Math.max(0, YMin);
		XMax = Math.min(1, XMax);
		YMax = Math.min(1, YMax);
	}

	/** 判断other是否包含在该坐标范围之内 */
	public boolean isIntersect(Enve2D other) {
		if (other.XMin >= XMin && other.XMax <= XMax && other.YMin >= YMin
				&& other.YMax <= YMax) {
			return true;
		} else {
			return false;
		}
	}

	public double getWidth() {
		return XMax - XMin;
	}

	public double getHeight() {
		return YMax - YMin;
	}
}