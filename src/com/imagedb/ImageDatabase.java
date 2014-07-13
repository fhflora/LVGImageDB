package com.imagedb;

import java.sql.Connection;


/** 影像数据库系统基类
 * @author		杨冲
 * @version		1.00 25 June 2014 */
public class ImageDatabase {
	protected Connection hConnection;		// 数据库连接句柄
	protected StringBuffer strMessage;		// 命令执行后的反馈信息
    
    public ImageDatabase() {
    	strMessage = null;
    	hConnection = null;
    }
    
    /** 获取命令执行后的反馈信息 */
    public String getExceMessage() {
        return strMessage.toString();
    }
    
    /** 通过影像的类型获取ImageManage对象
	 * @param nType 影像的类型
	 * @return 成功：返回 ImageManage 对象<br>失败：返回 null 值 */
	public static ImageManage getImageManage(int nType, Connection connection) {
		ImageManage imageManage = null;

		if (1 == nType) {
			imageManage = new NormImgManage(connection);
		} else if (2 == nType) {
			imageManage = new BigImgManage(connection);
		} else if (3 == nType) {
			imageManage = new PanoManage(connection);
		} else if (4 == nType) {
			imageManage = new AerialImgManage(connection);
		} else {
			imageManage = null;
		}

		return imageManage;
	}
}
