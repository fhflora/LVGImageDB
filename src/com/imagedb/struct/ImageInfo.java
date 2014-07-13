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
}
