package com.lvg.bean;

import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class GetImage {

	public  static int getImageWidth(String src){
		File file=new File(src);
		  FileInputStream is=null;
		  try{
			  is=new FileInputStream(file);
		  }catch(FileNotFoundException e){
			  e.printStackTrace();
		  }
		  BufferedImage sourceImg=null;
		  
		  try{
			  sourceImg=javax.imageio.ImageIO.read(is);
		  } catch(IOException e){
			  e.printStackTrace();
		  }
		 return sourceImg.getWidth();
	}
	
	/*public static void main(String[] args) {
		String src="WebContent/img/images/";
		File directory=new File(src);
		ArrayList filePath=new ArrayList();
		File[] fList=null;
		File tempFile=null;
		
		fList=directory.listFiles();
		
		for(int i=0;i<fList.length;i++){
			tempFile=fList[i];
			if(tempFile.isFile()){
				filePath.add(tempFile);
			}
		}
		System.out.println(fList.length);
		for(int i=0;i<filePath.size();i++){
			System.out.print(i+" : ");
			System.out.print(((File)filePath.get(i)).getPath());
			System.out.println(GetImage.getImageWidth(((File)filePath.get(i)).getPath()));
		}
	}*/
}