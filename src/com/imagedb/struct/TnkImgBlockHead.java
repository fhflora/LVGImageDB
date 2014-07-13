package com.imagedb.struct;

/** TBM文件中数据块头文件信息 */
public class TnkImgBlockHead {
	public int nImgBlockSize;    	// 该数据块的大小
	public int nImgBlockID;      	// 该数据块的四叉树编码
	public int nImgLocalID;			// 该数据块在文件中的索引号
	
	public TnkImgBlockHead() {
		nImgBlockSize = -1;
		nImgBlockID = -1;
		nImgLocalID = -1;
	}
}
