package com.imagedb.struct;

import java.util.Date;


/** 图像信息 */
public class ImageInfo {
	public long nImageID;				// 图像的ID
	public int nTableID;           		// 图像将要插入的表格ID
	public String strFilePath;        	// 图像文件路径	
	
	public String strImageName;			// 图像的名称
	public String strDescInfo;			// 图像的描述信息
	public Date dtAcquireTime;			// 图像的采集时间
    public Date dtInputTime;			// 图像的入库时间
    public boolean bFlagInfo;			// 标注信息
    
	public int  nImageSize;				// 图像的大小   
    public String strImageFormat;		// 图像的格式
    
    public Double dResolution;			// 影像分辨率
    public Double dLeftBottomX;			// 影像左下角X坐标
    public Double dLeftBottomY;			// 影像左下角Y坐标
    
    public ImageInfo() {  
    	nImageID = -1;
    	nTableID = -1;
    	nImageSize = -1;
    	
    	strFilePath = null;
    	strImageName = null;
    	strImageFormat = null;
    	strDescInfo = null;    	

    	dtAcquireTime = null;
    	dtInputTime = null;
    	bFlagInfo = false;
    	
    	dResolution = -1.0;
    	dLeftBottomX = -1.0;
    	dLeftBottomY = -1.0;
    }

	public long getnImageID() {
		return nImageID;
	}

	public void setnImageID(long nImageID) {
		this.nImageID = nImageID;
	}

	public int getnTableID() {
		return nTableID;
	}

	public void setnTableID(int nTableID) {
		this.nTableID = nTableID;
	}

	public String getStrFilePath() {
		return strFilePath;
	}

	public void setStrFilePath(String strFilePath) {
		this.strFilePath = strFilePath;
	}

	public String getStrImageName() {
		return strImageName;
	}

	public void setStrImageName(String strImageName) {
		this.strImageName = strImageName;
	}

	public String getStrDescInfo() {
		return strDescInfo;
	}

	public void setStrDescInfo(String strDescInfo) {
		this.strDescInfo = strDescInfo;
	}

	public Date getDtAcquireTime() {
		return dtAcquireTime;
	}

	public void setDtAcquireTime(Date dtAcquireTime) {
		this.dtAcquireTime = dtAcquireTime;
	}

	public Date getDtInputTime() {
		return dtInputTime;
	}

	public void setDtInputTime(Date dtInputTime) {
		this.dtInputTime = dtInputTime;
	}

	public boolean isbFlagInfo() {
		return bFlagInfo;
	}

	public void setbFlagInfo(boolean bFlagInfo) {
		this.bFlagInfo = bFlagInfo;
	}

	public int getnImageSize() {
		return nImageSize;
	}

	public void setnImageSize(int nImageSize) {
		this.nImageSize = nImageSize;
	}

	public String getStrImageFormat() {
		return strImageFormat;
	}

	public void setStrImageFormat(String strImageFormat) {
		this.strImageFormat = strImageFormat;
	}

	public Double getdResolution() {
		return dResolution;
	}

	public void setdResolution(Double dResolution) {
		this.dResolution = dResolution;
	}

	public Double getdLeftBottomX() {
		return dLeftBottomX;
	}

	public void setdLeftBottomX(Double dLeftBottomX) {
		this.dLeftBottomX = dLeftBottomX;
	}

	public Double getdLeftBottomY() {
		return dLeftBottomY;
	}

	public void setdLeftBottomY(Double dLeftBottomY) {
		this.dLeftBottomY = dLeftBottomY;
	}
    
    
    
}
