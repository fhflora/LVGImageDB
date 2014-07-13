package com.imagedb;

import java.sql.Connection;

import com.imagedb.struct.ImageInfo;

/**
 * @version		1.00 2 July 2014
 * @author		YangChong
 */

public class PanoManage extends ImageManage {

	public PanoManage(Connection ConnTemp) {
		super(ConnTemp);
	}
	
	@Override
	public boolean createImageTable(int nTableID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long addImage(ImageInfo imageInfoTemp) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean replaceImage(long nImageID, String strImagePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteImage(long nImageID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportImage(long nImageID, String strImagePath) {
		// TODO Auto-generated method stub
		return false;
	}

}
