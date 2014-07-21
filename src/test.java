import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import com.imagedb.DatabaseManage;
import com.imagedb.ImageDatabase;
import com.imagedb.ImageManage;
import com.imagedb.UserManage;
import com.imagedb.struct.ConnInfo;
import com.imagedb.struct.ImageInfo;
import com.imagedb.struct.UserInfo;


public class test {

	public static void main(String[] args) throws IOException {
    	ConnInfo connInfoTemp = new ConnInfo();
    	connInfoTemp.strDbName = "Test1";
    	connInfoTemp.strPassword = "968132";
    	DatabaseManage imageDBTemp = new DatabaseManage();
    	
    	if (imageDBTemp.connectDB(connInfoTemp)) {
    		
    		/** 数据库测试 */
    		ConnInfo connInfo = imageDBTemp.getConnInfo();
    		Connection connection = imageDBTemp.getConnHandle();   		
 //   		boolean result=imageDBTemp.createDB("Test1");
//    		imageDBTemp.createDB("Test2");
 //   		LinkedList<String> listDbName = new LinkedList<>();
//    		imageDBTemp.listDB(listDbName);
//    		imageDBTemp.deleteDB("Test2");
//    		imageDBTemp.addUser("yangchong609", "123qqq===", 0);
//    		imageDBTemp.addUser("yangchong", "968132", 0);
//    		imageDBTemp.listUser(listDbName);
//    		imageDBTemp.deleteUser("yangchong"); 

    		
    		/** 数据库表测试 */
//   		 ImageManage imageManage=ImageDatabase.getImageManage(1, imageDBTemp.getConnHandle());
//    		imageManage.createTable("百度照片", "存储的是从百度图片上下载的小图", 1);
//    		System.out.println("创建结果"+imageManage.getExceMessage());
//    		imageDBTemp.createTable("69号洞窟东壁", "存储的是69号洞窟东面的壁画小图", 1);
 //   		imageDBTemp.createTable("69号洞窟西壁", "存储的是69号洞窟西面的壁画小图", 1);
//    		imageDBTemp.createTable("69号洞窟北壁", "存储的是69号洞窟北面的壁画小图", 1);
    		
//    		imageDBTemp.createTable("999洞窟大图", "存储的是999号洞窟的壁画小", 2);
//    		imageDBTemp.createTable("999洞窟全景图", "存储的是999号洞窟的壁画", 3);
//    		imageDBTemp.createTable("999洞窟航片图", "存储的是999号洞窟的壁", 4);
    		
//    		int nTableID = imageDBTemp.getTableID("百度照片");
//    		String strRemark = imageDBTemp.getTableRemark(nTableID);
//    		strRemark += "已修改";
//    		imageDBTemp.setTableRemark(nTableID, strRemark);
//    		strRemark = imageDBTemp.getTableName(nTableID);
//    		imageDBTemp.setTableName(nTableID, "999洞窟全景图");
//    		imageDBTemp.deleteTable(nTableID);
//    		nTableID = imageDBTemp.getTableID("999洞窟普通图");
//    		imageDBTemp.clearTable(nTableID);
//    		Hashtable<Long, String> nImageID1 = new Hashtable<>();
//    		imageDBTemp.getImagesID(nTableID, nImageID1);
    		
    		/** 数据库图像测试 */
//    		ImageInfo imageInfo = new ImageInfo();
//    		imageInfo.nTableID = 16777216;
//    		imageInfo.strFilePath = "E:/ER256.tbm";
//    		imageInfo.strImageName = "西壁";
//    		imageInfo.strDescInfo = "百度美女图";
//    		imageInfo.dtAcquireTime = new Date();
//    		imageInfo.dtInputTime = new Date();
//    		imageInfo.dResolution = 5.4;
//    		imageInfo.dLeftBottomX = 500434.456;
//    		imageInfo.dLeftBottomY = 4555223.321;
//    		
//    		
//    				
//    		String strDirectory = "F:/pg/百度图片/";
//    		File file = new File(strDirectory);
//    		String[] imagePath = file.list();
//    		
//    		for (int i = 0; i < imagePath.length; i++) {
//    			imageInfo.strFilePath = strDirectory + imagePath[i];
//    			imageInfo.strImageName = imagePath[i].substring(0, imagePath[i].lastIndexOf("."));
//   			imageManage.addImage(imageInfo);
//    		}
//    		System.out.println(imageManage.getExceMessage());
//   		imageInfo = imageManage.getImageInfo(nImageID);
//    		imageInfo.strImageName += "已修改";
 //   		imageInfo.dtInputTime = new Date();
//    		imageManage.setImageInfo(nImageID, imageInfo);
    		
//    		imageManage.replaceImage(nImageID, "F:/002_3002.tbm");
//    		imageManage.addImage(imageInfo);
//   		imageManage.exportImage(nImageID, "F:/png_export.png");		
    		
//    		ThumbManage thumbManage = new ThumbManage(imageDBTemp.getConnHandle());
//    		String strFilePath = "F:/百度图片/";
//    		Iterator iterator = nImageID1.entrySet().iterator();
//    		while (iterator.hasNext()) {
//    			Map.Entry entry = (Map.Entry) iterator.next();
//    			thumbManage.exportThumb((long) entry.getKey(), strFilePath + (String) entry.getValue() + ".jpg");
//    		}
    		
//        	byte[] buffer = thumbManage.createTbmThumb(nImageID);
//    		thumbManage.setThumbData(nImageID, thumbManage.createTbmThumb(nImageID));
//          buffer = thumbManage.getThumbData(nImageID);
 
//        	FileOutputStream imageFile = new FileOutputStream("F:/6_tbm2.jpg");
//    		imageFile.write(buffer);
//    		imageFile.close();  	
    		
    		/** 数据库热点测试 */
//    		HotPntManage hotPntManage = new HotPntManage(imageDBTemp.getConnHandle());
//    		HotPntInfo hotPntInfo = new HotPntInfo();
//    		hotPntInfo.dPosX = 0.3;
//    		hotPntInfo.dPosY = 0.8;
//    		hotPntInfo.nImageID = nImageID;
//    		hotPntInfo.nLayerNum = 0;
//    		hotPntInfo.nType = 1;
//    		hotPntInfo.strName = "飞天图";
//    		hotPntInfo.strRemark = "这是69号洞窟东壁的仕女飞天图";
//    		
//    		ByteBuffer buffer = ByteBuffer.allocate(8);
//    		long linkImage = Long.parseLong("72057598332895309");
//    		buffer.putLong(0, linkImage);
//    		byte[] hotPntData = buffer.array();
//    		hotPntManage.addHotPnt(nImageID, hotPntInfo, hotPntData);
//    		
//    		LinkedList<HotPntInfo> testHotPnt = new LinkedList<>();
//    		hotPntManage.getHotPntInfo(nImageID, testHotPnt);
//    		HotPntInfo tempHotPntInfo = hotPntManage.getHotPntInfo(nImageID, testHotPnt.get(0).nID);
//    		tempHotPntInfo.strName += "已修改";
//    		hotPntManage.setHotPntInfo(nImageID, tempHotPntInfo.nID, tempHotPntInfo);
//    		hotPntData[7] = 54;
//    		hotPntManage.setHotPntData(nImageID, tempHotPntInfo.nID, hotPntData);
//    		hotPntData = hotPntManage.getHotPntData(nImageID, tempHotPntInfo.nID);
//    		buffer.put(hotPntData, 0, hotPntData.length);
//    		buffer.flip();
//    		long nTemp = buffer.getLong();
//    		hotPntManage.deleteHotPnt(nImageID, tempHotPntInfo.nID);
 //   		hotPntManage.deleteHotPnt(nImageID);
//    		hotPntManage.addHotPnt(nImageID, hotPntInfo, hotPntData);
    		
    		/** 数据库客户端用户测试 */
    		UserManage userManage = new UserManage(imageDBTemp.getConnHandle());
    		UserInfo userInfo = new UserInfo();
    		userInfo.createTime = new Date();
    		userInfo.lastLoginTime = new Date();
    		userInfo.nPermission = 0;
    		userInfo.nState = 1;
    		userInfo.nType = 1;
    		userInfo.strCreated = "杨冲";
    		userInfo.strEmail = "444845016@qq.com";
    		userInfo.strLastLoginIP = "192.168.2.107";
    		userInfo.strPassword = "ll123";
    		userInfo.strPhone = "15927542260";
    		userInfo.strRealName = "李亮";
    		userInfo.strRemark = "开发";
    		userInfo.strUserName = "ll";
    	//	System.out.println(userManage.getExceMessage());
    		
    		userManage.addUse(userInfo);
    		int nIsExist = userManage.userIsExist("yangchong609");
    		userInfo.strUserName = "hello";
    		userManage.addUse(userInfo);
    		
//    		LinkedList<UserInfo> userList = new LinkedList<>();
//    		userManage.getAllUserInfo(userList);
//    		UserInfo nTemp = userManage.getUserInfo(userList.get(0).nID);
//    		nTemp = userManage.getUserInfo("hello", 1, 1);
//    		nTemp = userManage.getUserInfo("hello", 0, 1);
//    		nTemp.strRemark = "宣传部";
//    		userManage.setUserInfo(nTemp);
//    		userManage.deleteUser(nTemp.nID);
//    		nIsExist = userManage.loginCheck("hello", "123456", 1);
//    		nIsExist = userManage.loginCheck("yangchong609", "123456", 1);
//    		userManage.deleteAllUser();
    		
    		imageDBTemp.closeConnect();

// 测试用户收藏接口
//		ConnInfo connInfoTemp = new ConnInfo();
//    	connInfoTemp.strDbName = "Test1";
//    	connInfoTemp.strPassword = "968132";
    	
//		DatabaseManage dbManage = new DatabaseManage();
//		if (dbManage.connectDB(connInfoTemp)) {
//			CollectManage collectManage = new CollectManage(dbManage.getConnHandle());
//			int nUserID = 3;
//			collectManage.createTable(nUserID);
//			collectManage.createTable(nUserID, "佛像", "这里面收集的都是佛像");
//			int nTableID = collectManage.getTableID(nUserID, "佛像");
//			collectManage.setTableName(nUserID, nTableID, "佛像（阿南）");
//			String strTableName = collectManage.getTableName(nUserID, nTableID);
//			strTableName = collectManage.getTableRemark(nUserID, nTableID);
//			collectManage.setTableRemark(nUserID, nTableID, strTableName + "（阿南）");
			
//			long nImageID = Long.parseLong("72057611217797120");
//			collectManage.addImage(nUserID, nTableID, nImageID);
//			nImageID = Long.parseLong("72057611217797121");
//			collectManage.addImage(nUserID, nTableID, nImageID);
//			String strImageName = collectManage.getImageName(nUserID, nTableID, nImageID);
//			System.out.println(strImageName);
//			collectManage.setImageName(nUserID, nTableID, nImageID, "你好");
//			Hashtable<Integer, String> nChildTabID = new Hashtable<>();
//			collectManage.getTablesID(nUserID, nChildTabID);
//			Hashtable<Long, String> nImagesID = new Hashtable<>();
//			collectManage.getImagesID(nUserID, nChildTabID.keySet().iterator().next(), nImagesID);
//			collectManage.removeImage(nUserID, nTableID, nImagesID.keySet().iterator().next());
//			
//			
//			collectManage.clearTable(nUserID, nTableID);
//			collectManage.deleteTable(nUserID);			
//		}	
    		
    	} else {
    		String strExec = imageDBTemp.getExceMessage();
    		System.out.printf(strExec);
    	}
    	
    	
    }
}
