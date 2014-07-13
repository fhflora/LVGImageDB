package com.imagedb.struct;

/** 热点信息 */
public class HotPntInfo {
	public int nID;				// 热点的编号
	public int nType;			// 热点的类型
	public int nLayerNum;		// 热点所在图层号
	public long nImageID;		// 热点所在图像的ID
	public double dPosX;		// 热点在图像上的X坐标
	public double dPosY;		// 热点在图像上的Y坐标
	public String strName;		// 热点的名称
	public String strRemark;	// 热点的备注信息
	
	public HotPntInfo() {
		nID = -1;
		nType = -1;
		nLayerNum = -1;
		nImageID = -1;
		dPosX = -1.0;
		dPosY = -1.0;
		strName = null;
		strRemark = null;
	}
}
