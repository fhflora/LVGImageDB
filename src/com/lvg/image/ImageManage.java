package com.lvg.image;

import java.util.ArrayList;

public  abstract class  ImageManage {
	
	/**
	 * 
	 * @param newImage :新的图片，用结构体保存图片信息
	 * @return userID : 返回创建成功后的用户ID
	 */
	public int createImage(AbstractImage newImage){
		return 0;	
	}
	
	/**
	 * 
	 * @param imageType 图片类型：航片图，全景图，普通土，超大图
	 * @return
	 */
	public ArrayList getImageList(String imageType){
		return null;
	}
	
	/**
	 * 
	 * @param keywords
	 * @return
	 */
	public ArrayList searchImages(String keywords){
		return null;
	}
	

}
