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

	public int getnID() {
		return nID;
	}

	public void setnID(int nID) {
		this.nID = nID;
	}

	public int getnType() {
		return nType;
	}

	public void setnType(int nType) {
		this.nType = nType;
	}

	public int getnLayerNum() {
		return nLayerNum;
	}

	public void setnLayerNum(int nLayerNum) {
		this.nLayerNum = nLayerNum;
	}

	public long getnImageID() {
		return nImageID;
	}

	public void setnImageID(long nImageID) {
		this.nImageID = nImageID;
	}

	public double getdPosX() {
		return dPosX;
	}

	public void setdPosX(double dPosX) {
		this.dPosX = dPosX;
	}

	public double getdPosY() {
		return dPosY;
	}

	public void setdPosY(double dPosY) {
		this.dPosY = dPosY;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}
	
}
