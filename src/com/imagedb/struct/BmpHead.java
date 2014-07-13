package com.imagedb.struct;

/** bmp头文件信息 */
public class BmpHead {
	public int nWidth;
	public int nHeight;
	public int nColorNum;
	
	public BmpHead() {
		nWidth = -1;
		nHeight = -1;
		nColorNum = 24;
	}
}
