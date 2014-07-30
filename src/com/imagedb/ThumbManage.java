package com.imagedb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;

import com.imagedb.struct.TnkTbmFileHead;

/**
 * 缩略图管理类
 * 
 * @author 杨冲
 * @version 1.00 1 July 2014
 */
public class ThumbManage extends ImageDatabase {
	private int nThumbHeight = 512; // 产生的缩略图的高度（以像素为单位）

	public ThumbManage(Connection ConnTemp) {
		hConnection = ConnTemp;
	}

	public void setThumbHeight(int nHeight) {
		nThumbHeight = nHeight;
	}

	public int getThumbHeight() {
		return nThumbHeight;
	}

	/**
	 * 创建一张普通图像的缩略图
	 * 
	 * @param inputStream
	 *            源图像数据流
	 * @return 成功：返回存放缩略图的 byte 型数组<br>
	 *         失败：返回 null 值
	 */
	public byte[] createNormImgThumb(InputStream inputStream) {
		try {

			// 读入源图像
			BufferedImage inputImage = ImageIO.read(inputStream);
			int nSrcHeight = inputImage.getHeight();
			int nSrcWidth = inputImage.getWidth();

			// 计算缩略图的大小
			int nDstHeight = nThumbHeight;
			int nDstWidth = nDstHeight * nSrcWidth / nSrcHeight;

			// 对源图像进行重采样
			BufferedImage dstBufferedImage = new BufferedImage(nDstWidth,
					nDstHeight, BufferedImage.TYPE_3BYTE_BGR);
			dstBufferedImage.getGraphics().drawImage(inputImage, 0, 0,
					nDstWidth, nDstHeight, null);

			// 输出缩略图
			ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
			ImageIO.write(dstBufferedImage, "jpg", outputImage);

			strMessage = new StringBuffer("Succeed to create the thumbnail.");
			return outputImage.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			strMessage = new StringBuffer(e.getMessage());
			return null;
		}
	}

	/**
	 * 创建一张 tbm 图像的缩略图
	 * 
	 * @param inputStream
	 *            源图像在数据库中的ID
	 * @return 成功：返回存放缩略图的 byte 型数组<br>
	 *         失败：返回 null 值
	 */
	public byte[] createTbmThumb(long nImageID) {
		try {

			// 获取tbm文件头信息
			BigImgManage bigImgManage = new BigImgManage(hConnection);
			byte[] buffer = bigImgManage.getBlockData(nImageID,
					BigImgManage.getTbmHeadId(), 0);
			TnkTbmFileHead tbmFileHead = BigImgManage.parseTbmFileHead(buffer,
					true);

			// 计算从金字塔的 nNumLayer 层产生缩略图
			int nBlockHeight = (int) (nThumbHeight * tbmFileHead.nTileSize
					* Math.pow(2, tbmFileHead.nLayerCount - 1) / tbmFileHead.nHeight);
			int nNumLayer = (int) (Math.log(nBlockHeight * 1.0
					/ tbmFileHead.nTileSize)
					/ Math.log(2.0) + 1);

			// 输出缩略图
			byte[] thumbnail = bigImgManage
					.getImage(nImageID, nNumLayer, "jpg");
			ByteArrayInputStream inputImage = new ByteArrayInputStream(
					thumbnail);
			return createNormImgThumb(inputImage);
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = new StringBuffer(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取一个图像的缩略图数据
	 * 
	 * @param nImageID
	 *            图像在数据库中的ID
	 * @return 成功：返回存放缩略图的 byte 数组<br>
	 *         失败：返回 null 值
	 */
	public byte[] getThumbData(long nImageID) {
		try {
			String strTableName = Integer.toString(ImageManage
					.getTableID(nImageID));
			StringBuffer strQuery = new StringBuffer(
					"select \"Thumbnail\" from \"");
			strQuery.append(strTableName);
			strQuery.append("\" where \"ID\" = ");
			strQuery.append(nImageID);

			Statement command = hConnection.createStatement();
			ResultSet drResult = command.executeQuery(strQuery.toString());
			byte[] dataBuffer = null;

			if (!drResult.next()) {
				strMessage = new StringBuffer(
						"The image is not exist in the database.");
			} else {
				strMessage = new StringBuffer(
						"Succeed to get the thumbnail of the image.");
				dataBuffer = drResult.getBytes("Thumbnail");
			}

			drResult.close();
			command.close();
			return dataBuffer;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return null;
		}
	}

	/**
	 * 修改一个图像的缩略图数据
	 * 
	 * @param nImageID
	 *            图像在数据库中的ID
	 * @param cThumbData
	 *            修改后的缩略图数据
	 * @return true：缩略图数据修改成功<br>
	 *         false：缩略图数据修改失败
	 */
	public boolean setThumbData(long nImageID, byte[] cThumbData) {
		try {
			String strTableName = Integer.toString(ImageManage
					.getTableID(nImageID));
			StringBuffer strQuery = new StringBuffer("update \"");
			strQuery.append(strTableName);
			strQuery.append("\" set \"Thumbnail\" = ? where \"ID\" = ");
			strQuery.append(nImageID);

			PreparedStatement command = hConnection.prepareStatement(strQuery
					.toString());
			command.setBytes(1, cThumbData);
			command.executeUpdate();
			command.close();

			strMessage = new StringBuffer(
					"Succeed to set the thumbnail of the image.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}

	/**
	 * 导出数据库中的一张图像的缩略图
	 * 
	 * @param nImageID
	 *            要导出缩略图的图像ID
	 * @param strImagePath
	 *            导出缩略图在本地磁盘的存放路径（完整路径）
	 * @return true：缩略图导出成功<br>
	 *         false：缩略图导出失败
	 */
	public boolean exportThumb(long nImageID, String strImagePath) {
		try {
			byte[] dataBuffer = getThumbData(nImageID);
			if (null == dataBuffer) {
				return false;
			}

			FileOutputStream imageFile = new FileOutputStream(strImagePath);
			imageFile.write(dataBuffer);
			imageFile.close();

			strMessage = new StringBuffer("Succeed to export the thumbnail.");
			return true;
		} catch (Exception ex) {
			strMessage = new StringBuffer(ex.getMessage());
			return false;
		}
	}
}
