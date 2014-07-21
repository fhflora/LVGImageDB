package com.lvg.action;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import com.lvg.model.ShowImage;
import com.imagedb.*;
import com.opensymphony.xwork2.ActionSupport;
import com.lvg.database.*;

public class ImageListAction extends ActionSupport {

	private int index;
	private int count;
	private int type;
	private String database;
	private BufferedImage img;
	private String tableName;
	private byte[] bytes = null;
	private static  Hashtable<Integer, byte[]> imageBytesTable = new Hashtable<Integer, byte[]>();
	private static long[] imgIDList;
	private List<List<ShowImage>> srcImgRowList;
	private String contentType = "image/jpg";
	private int srcImageID;
	
	public String imageListAjax() {

		Enumeration enumImgList;
		Hashtable<Long, String> imgList = null;
		ImageManage imageManage;
		srcImgRowList = new ArrayList<List<ShowImage>>();

		if (index == 0) {
			System.out.println(tableName);
			imageManage = ImageDatabase.getImageManage(type,
					GetConnection.getConn(database));

			Hashtable<Integer, String> nChildTabID = new Hashtable<Integer, String>();
			imageManage.getChildTablesID(
					imageManage.getTableID(this.tableName), nChildTabID);

			Enumeration enumChildTables = nChildTabID.keys();
			imgList = new Hashtable<Long, String>();
			while (enumChildTables.hasMoreElements()) {
				imageManage.getImagesID(
						(Integer) enumChildTables.nextElement(), imgList);
			}
			enumImgList = imgList.keys();
			imgIDList = new long[imgList.size()];
			for (int i = 0; i < imgList.size(); i++) {
				imgIDList[i] = (Long) enumImgList.nextElement();
			}
			GetConnection.closeAll();
		}

		List<Integer> srcImgIndexList = new ArrayList<Integer>();
		int endIndex = index + count;
		if (endIndex > imgIDList.length)
			endIndex = imgIDList.length;

		for (int i = index; i < endIndex; i++) {
			srcImgIndexList.add(i);
		}

		ArrayList[] img = new ArrayList[13];

		for (int i = 0; i < 13; i++) {
			img[i] = new ArrayList();
		}

		for (int i = 0; i < srcImgIndexList.size(); i++) {
			double width = Math
					.rint(this.getImageBytes(imgIDList[srcImgIndexList.get(i)],
							srcImgIndexList.get(i)) / 1170.0 * 12);
			if (width > 12)
				width = 12;
			img[(int) width].add(srcImgIndexList.get(i));
		}

		for (int i = 12; i > 0; i--) {
			if (i > 9 && i <= 12) {
				if (img[i].size() > 0) {
					for (int j = 0; j < img[i].size(); j++) {
						String srcImg = "ImageAction.action?srcImageID="
								+ img[i].get(j).toString();
						ShowImage showImage = new ShowImage(srcImg, i);
						List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
						System.out.println("row里面添加 ： "+showImage.srcImagePath);
						srcImgRow.add(showImage);
						srcImgRowList.add(srcImgRow);
					}
				}
			}

			int total = 0;
			List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
			ShowImage showImage;
			String srcImg;

			if (i <= 9) {

				while (img[i].size() > 0) {
					total = i;

					srcImg = "ImageAction.action?srcImageID="
							+ img[i].get(0).toString();
					showImage = new ShowImage(srcImg, i);
					System.out.println("row里面添加 ： "+showImage.srcImagePath);
					srcImgRow.add(showImage);
					
					img[i].remove(0);
					System.out.println("从img[i]中remove");
					for (int tail = 12; tail > 0; tail--) {
						if (img[tail].size() > 0) {
							int temp = total + tail;
							if (temp <= 9) {
								srcImg = "ImageAction.action?srcImageID="
										+ img[tail].get(0).toString();

								showImage = new ShowImage(srcImg, tail);
								System.out.println("row里面添加 ： "+showImage.srcImagePath);
								srcImgRow.add(showImage);

								img[tail].remove(0);
								total = temp;
								if (img[tail].size() > 0) {
									tail++;
								}
							}
							if (temp > 9 && temp <= 12) {
								srcImg = "ImageAction.action?srcImageID="
										+ img[tail].get(0).toString();
								showImage = new ShowImage(srcImg, tail);
								System.out.println("row里面添加 ： "+showImage.srcImagePath);
								srcImgRow.add(showImage);

								img[tail].remove(0);

								srcImgRowList.add(srcImgRow);

								srcImgRow = new ArrayList<ShowImage>();
								total = i;
								break;
							}
						}
					}
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 从缓存中获取图片，图片数据存到bytes中
	 * 
	 * @param imageID
	 *            获取的图片ID
	 */
	public int getImageBytes(long imageID, int imageIndex) {

		/*ImageCache imageCache = new ImageCache(CacheManager.getSqliteConn(),
				database);

		if (!imageCache.isImageExist(imageID)) {
			imageCache.insertRow(imageID);
		}
		InputStream buffin = new ByteArrayInputStream(
				imageCache.getImage(imageID));*/
		
		ThumbManage thumbManage=new ThumbManage(GetConnection.getConn(database));
		
		InputStream buffin = new ByteArrayInputStream(thumbManage.getThumbData(imageID));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			img = ImageIO.read(buffin);
			ImageIO.write(img, "jpg", bos);
			this.bytes = bos.toByteArray();
			imageBytesTable.put(imageIndex, this.bytes);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				CacheManager.closeAll();
				bos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return img.getWidth() / 2;

	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		switch (type) {
		case 1:
			this.tableName = "普通图像";
			break;
		case 2:
			this.tableName = "超大图像";
			break;
		case 3:
			this.tableName = "全景图像";
			break;
		case 4:
			this.tableName = "航片图像";
			break;
		default:
			this.tableName = "";
			break;
		}
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public byte[] showImageData() {
		return imageBytesTable.get(srcImageID);
	}

	public String showImage() {
		this.showImageData();
		return "image";
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getContentType() {
		return this.contentType;
	}

	public List<List<ShowImage>> getSrcImgRowList() {
		return srcImgRowList;
	}

	public void setSrcImgRowList(List<List<ShowImage>> srcImgRowList) {
		this.srcImgRowList = srcImgRowList;
	}

	public int getSrcImageID() {
		return srcImageID;
	}

	public void setSrcImageID(int srcImageID) {
		this.srcImageID = srcImageID;
	}


}
