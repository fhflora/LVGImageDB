package com.lvg.model;

public class ShowImage {
	public String srcImagePath;
	public int srcImageWidth;
	
	public ShowImage(String _srcImagePath,int _srcImageWidth){
		this.srcImagePath = _srcImagePath;
		this.srcImageWidth = _srcImageWidth;
	}
	public String getSrcImagePath() {
		return srcImagePath;
	}
	public void setSrcImagePath(String srcImagePath) {
		this.srcImagePath = srcImagePath;
	}
	public int getSrcImageWidth() {
		return srcImageWidth;
	}
	public void setSrcImageWidth(int srcImageWidth) {
		this.srcImageWidth = srcImageWidth;
	}
	
	
}
