package com.lvg.image;

import java.sql.Blob;
import java.sql.Date;

public abstract class AbstractImage {
	private int ID;
	private String imageName;
	private Date acquireTime;
	private Date inputTime;
	private String imageFormat;
	private int imageSize;
	private Blob imageData;
	private Blob thumbnail;
	private String descInfo;
	private int mapLevel;

	public int getID() {
		return ID;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(Date acquireTime) {
		this.acquireTime = acquireTime;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public int getImageSize() {
		return imageSize;
	}

	public Blob getImageData() {
		return imageData;
	}

	public Blob getThumbnail() {
		return thumbnail;
	}

	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public int getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(int mapLevel) {
		this.mapLevel = mapLevel;
	}

	/**
	 * 
	 * @param xPos
	 * @param yPos
	 * @return 返回上一层图片数据
	 */
	public Blob highLevelBufferImage(float xPos, float yPos) {

		return null;
	}

	/**
	 * 
	 * @param xPos
	 * @param yPos
	 * @return 返回下一层图片数据
	 */
	public Blob lowLevelBufferImage(float xPos, float yPos) {

		return null;
	}
}
