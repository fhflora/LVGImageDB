package com.imagedb.struct;

/** TBM文件中辅助块信息 */
public class TnkInfoBlockAssit {
	public int nBlockNum;            	// 数据块的总个数
	public int nVlidBlkNum;             // 有效数据块的个数
	public int nInfoBlockSize;          // 该辅助数据块的大小
	
	public TnkInfoBlockAssit() {
		nBlockNum = -1;
		nVlidBlkNum = -1;
		nInfoBlockSize = -1;
	}
}
