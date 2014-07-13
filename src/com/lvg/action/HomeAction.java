package com.lvg.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.lvg.bean.GetImage;
import com.lvg.model.ShowImage;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {

	public List<List<ShowImage>> srcImgRowList;
	public int index;
	public int count;

	public String ShowImageAction() {

		srcImgRowList = new ArrayList<List<ShowImage>>();

		ServletContext context = ServletActionContext.getServletContext();
		String srcSearch = context.getRealPath("/") + "img/images/";
		String srcShow = "img/images/";

		File directory = new File(srcSearch);
		File tempFile = null;
		File[] fList = null;
		try {
			if (directory.isDirectory()) {
				fList = directory.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> srcImgNameList = new ArrayList<String>();
		for (int i = 0; i < fList.length; i++) {
			tempFile = fList[i];
			if (tempFile.isFile()) {
				srcImgNameList.add(tempFile.getName());
			}
		}

		ArrayList[] img = new ArrayList[13];
		for (int i = 0; i < 13; i++) {
			img[i] = new ArrayList();
		}

		for (int i = 0; i < srcImgNameList.size(); i++) {
			double tempWidth = Math.rint(GetImage.getImageWidth(srcSearch
					+ srcImgNameList.get(i).toString()) / 1170.0 * 12);
			img[(int) tempWidth].add(srcImgNameList.get(i));
		}

		for (int i = 12; i > 0; i--) {
			if (i > 9 && i <= 12) {
				if (img[i].size() > 0) {
					for (int j = 0; j < img[i].size(); j++) {
						String srcImg = srcShow + img[i].get(j).toString();
						ShowImage showimage = new ShowImage(srcImg, i);
						List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
						srcImgRow.add(showimage);
						srcImgRowList.add(srcImgRow);
					}
				}
			}
			int total = 0;
			List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
			ShowImage showimage;
			String srcImg;
			if (i <= 9) {

				while (img[i].size() > 0) {

					total = i;

					srcImg = srcShow + img[i].get(0).toString();
					showimage = new ShowImage(srcImg, i);
					srcImgRow.add(showimage);

					img[i].remove(0);
					for (int tail = 12; tail > 0; tail--) {
						if (img[tail].size() > 0) {
							int temp = total + tail;
							if (temp <= 9) {
								srcImg = srcShow
										+ img[tail].get(0).toString()
												.toString();
								showimage = new ShowImage(srcImg, tail);
								srcImgRow.add(showimage);

								img[tail].remove(0);
								total = temp;
								if (img[tail].size() > 0) {
									tail++;
								}
							}
							if (temp > 9 && temp <= 12) {
								srcImg = srcShow
										+ img[tail].get(0).toString()
												.toString();
								showimage = new ShowImage(srcImg, tail);
								srcImgRow.add(showimage);

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

	public String ShowImageActionAjax() {

		srcImgRowList = new ArrayList<List<ShowImage>>();

		ServletContext context = ServletActionContext.getServletContext();
		String srcSearch = context.getRealPath("/") + "img/images/";
		// String srcSearch="C:/software/LVGImageDB/WebContent/img/images/";
		String srcShow = "img/images/";

		File directory = new File(srcSearch);
		File tempFile = null;
		File[] fList = null;
		try {
			if (directory.isDirectory()) {
				fList = directory.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> srcImgNameList = new ArrayList<String>();
		int endIndex = index + count;
		if (endIndex > fList.length)
			endIndex = fList.length;
		for (int i = index; i < endIndex; i++) {
			tempFile = fList[i];
			if (tempFile.isFile()) {
				srcImgNameList.add(tempFile.getName());
			}
		}

		ArrayList[] img = new ArrayList[13];
		for (int i = 0; i < 13; i++) {
			img[i] = new ArrayList();
		}

		for (int i = 0; i < srcImgNameList.size(); i++) {
			double tempWidth = Math.rint(GetImage.getImageWidth(srcSearch
					+ srcImgNameList.get(i).toString()) / 1170.0 * 12);
			img[(int) tempWidth].add(srcImgNameList.get(i));
		}

		for (int i = 12; i > 0; i--) {
			if (i > 9 && i <= 12) {
				if (img[i].size() > 0) {
					for (int j = 0; j < img[i].size(); j++) {
						String srcImg = srcShow + img[i].get(j).toString();
						ShowImage showimage = new ShowImage(srcImg, i);
						List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
						srcImgRow.add(showimage);
						srcImgRowList.add(srcImgRow);
					}
				}
			}
			int total = 0;
			List<ShowImage> srcImgRow = new ArrayList<ShowImage>();
			ShowImage showimage;
			String srcImg;
			if (i <= 9) {

				while (img[i].size() > 0) {

					total = i;

					srcImg = srcShow + img[i].get(0).toString();
					showimage = new ShowImage(srcImg, i);
					srcImgRow.add(showimage);

					img[i].remove(0);
					for (int tail = 12; tail > 0; tail--) {
						if (img[tail].size() > 0) {
							int temp = total + tail;
							if (temp <= 9) {
								srcImg = srcShow
										+ img[tail].get(0).toString()
												.toString();
								showimage = new ShowImage(srcImg, tail);
								srcImgRow.add(showimage);

								img[tail].remove(0);
								total = temp;
								if (img[tail].size() > 0) {
									tail++;
								}
							}
							if (temp > 9 && temp <= 12) {
								srcImg = srcShow
										+ img[tail].get(0).toString()
												.toString();
								showimage = new ShowImage(srcImg, tail);
								srcImgRow.add(showimage);

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

	public List<List<ShowImage>> getSrcImgRowList() {
		return srcImgRowList;
	}

	public void setSrcImgRowList(List<List<ShowImage>> srcImgRowList) {
		this.srcImgRowList = srcImgRowList;
	}

}
