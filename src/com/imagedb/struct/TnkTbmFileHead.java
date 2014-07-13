package com.imagedb.struct;

/** TBM文件头信息 */
public class TnkTbmFileHead {
	public int nTileSize;			// 切片大小（256）
    public int nNumColors;			// 影像的颜色数
    public int nWidth;			    // 影像的宽度
    public int nHeight;			  	// 影像的高度
    public int nLayerCount;			// 金字塔的层数
    
    public TnkTbmFileHead() {
    	nTileSize = -1;
    	nNumColors = -1;
    	nWidth = -1;
    	nHeight = -1;
    	nLayerCount = -1;
    }
}
