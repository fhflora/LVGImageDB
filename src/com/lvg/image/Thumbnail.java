package com.lvg.image;

import java.sql.Blob;

/**
 * 
 * 为图像生成缩略图
 * @author 傅智
 *
 */
public class Thumbnail {
	
	/**
	 * 生成缩略图
	 * @param imageData 图像数据
	 * @return 缩略图
	 */
	public Blob createThumbnail(Blob imageData){
		return imageData;
		
	}
}
