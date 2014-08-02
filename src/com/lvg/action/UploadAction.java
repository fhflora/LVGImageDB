package com.lvg.action;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;




import org.apache.struts2.interceptor.SessionAware;

import com.lvg.model.UploadFile;
import com.opensymphony.xwork2.ActionSupport;


public class UploadAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadFile uploadfile ;
	private String userCurrentDir=null;
	
	
	public String getUserCurrentDir() {
		return userCurrentDir;
	}
	public void setUserCurrentDir(String userCurrentDir) {
		this.userCurrentDir = userCurrentDir;
	}


	@Override
	public String execute() throws Exception {
		String savePath;
		if(userCurrentDir!=null && !userCurrentDir.equals(""))
			savePath = userCurrentDir.trim();
		else
			savePath = "D://test";
		File tmp = new File(savePath);
		if(!tmp.exists() || tmp.isFile())
			tmp.mkdirs();
		String fileName = savePath + "/" + uploadfile.getFileFileName();
		System.out.println("文件名" + fileName);
		
		//  获取一个上传文件的输入流
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		// 获取一个保存文件的输出流
		BufferedOutputStream bos = null;
		try {
			// 文件输入流
			bis = new BufferedInputStream(new FileInputStream(uploadfile.getFile()));
			// 文件输出流
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			// 建立一个跟文件大小相同的数组，存放文件数据
			byte[] buf = new byte[(int)uploadfile.getFile().length()];
			int len = 0;
			while((len = bis.read(buf)) != -1){
				bos.write(buf , 0 , len);
			}
			
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(bis!=null)
					bis.close();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(bos!=null)
					bos.close();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return SUCCESS;
	}
	public UploadFile getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(UploadFile uploadfile) {
		this.uploadfile = uploadfile;
	}


	
}
