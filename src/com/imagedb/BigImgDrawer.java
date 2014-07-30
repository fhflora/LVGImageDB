package com.imagedb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.imagedb.struct.ConnInfo;
import com.imagedb.struct.TnkTbmFileHead;

public class BigImgDrawer extends JPanel {
	private static final long serialVersionUID = 1L;
	ReadThread readThread; // 读数据的子线程

	BigImgManage bigImageManage; // 大图管理对象
	TnkTbmFileHead tbmFileHead; // 大图的头文件信息
	long nImageID; // 要显示的大图在数据库中的ID
	boolean isNeedGetBufData = false; // 是否需要从子线程中取数据

	int nCurLayer; // 当前的图层号
	int nLastLayer; // 修改前的图层号

	Enve2D CurEnve = new Enve2D(); // 当前窗口在图像上的坐标范围（相对坐标）
	Enve2D BufEnve = new Enve2D(); // 缓冲区在图像上的坐标范围（相对坐标）

	double dScale = 1.0; // 图层缩放的比例尺
	double dAddScale = 0.05; // 图层缩放间隔

	// 以下两个参数描述窗口的大小
	int nWinWidth = 1600;
	int nWinHeight = 1000;

	// 以下两个参数描述图层的源点（左下角）在窗口中的坐标
	int nLayerOrgX;
	int nLayerOrgY;

	// 以下两个参数描述图层当前的大小
	int nLayerCurWidth;
	int nLayerCurHeight;

	// 以下两个参数描述背景图层
	BufferedImage backgroundImage0;
	BufferedImage backgroundImage3;

	// 要显示的瓦片数据
	Hashtable<Integer, byte[]> curBlocksBuf = new Hashtable<>();
	Hashtable<Integer, Point> curDrawBlocksPos = new Hashtable<>();
	ArrayList<Integer> curDrawBlocksID = new ArrayList<>();

	public static void main(String[] args) {
		ConnInfo connInfoTemp = new ConnInfo();
		connInfoTemp.strHost = "192.168.2.103";
		connInfoTemp.strDbName = "Test1";
		connInfoTemp.strPassword = "968132";

		DatabaseManage dbManage = new DatabaseManage();
		if (dbManage.connectDB(connInfoTemp)) {
			Connection conn = dbManage.getConnHandle();
			long nImageID = Long.parseLong("144115188075855882");

			JFrame f = new JFrame();
			f.setLayout(new BorderLayout());
			f.setSize(1600, 1000);

			BigImgDrawer layer = new BigImgDrawer(new BigImgManage(conn),
					nImageID);
			layer.setBackground(Color.BLUE);
			layer.setSize(f.getWidth(), f.getHeight());

			f.getContentPane().add(layer);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			System.out.println(dbManage.getExceMessage());
		}
	}

	public BigImgDrawer(BigImgManage ImgManage, long nImgID) {
		bigImageManage = ImgManage;
		nImageID = nImgID;

		// 启动子线程
		readThread = new ReadThread("读取切片数据线程", ImgManage, nImgID);
		readThread.start();

		initialize(); // 初始化操作

		// 添加鼠标响应事件
		this.addMouseMotionListener(MBAdapter);
		this.addMouseListener(MBAdapter);
		this.addMouseWheelListener(MWListener);
	}

	/** 鼠标滑轮监听事件 */
	MouseWheelListener MWListener = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			Point curPnt = e.getPoint();

			if (e.getWheelRotation() < 0) {
				dScale += dAddScale;

				if (Math.abs(dScale - 2) < 0.000001 || dScale > 2) {
					if (nCurLayer < tbmFileHead.nLayerCount - 1) {
						nLayerOrgX = (int) (curPnt.x - (curPnt.x - nLayerOrgX)
								* 2 / (2 - dAddScale));
						nLayerOrgY = (int) (curPnt.y - (curPnt.y - nLayerOrgY)
								* 2 / (2 - dAddScale));

						dScale = dScale - 1.0;
						nCurLayer += 1;
					} else {
						dScale -= dAddScale;
					}

				} else {
					nLayerOrgX = (int) (curPnt.x - (curPnt.x - nLayerOrgX)
							* dScale / (dScale - dAddScale));
					nLayerOrgY = (int) (curPnt.y - (curPnt.y - nLayerOrgY)
							* dScale / (dScale - dAddScale));
				}
			} else {
				dScale -= dAddScale;

				if (Math.abs(dScale - 0.5) < 0.000001 || dScale < 0.5) {
					if (nCurLayer > 0) {
						nLayerOrgX = (int) (curPnt.x - (curPnt.x - nLayerOrgX)
								* 0.5 / (0.5 + dAddScale));
						nLayerOrgY = (int) (curPnt.y - (curPnt.y - nLayerOrgY)
								* 0.5 / (0.5 + dAddScale));

						dScale = dScale + 0.5;
						nCurLayer -= 1;
					} else {
						dScale += dAddScale;
					}
				} else {
					nLayerOrgX = (int) (curPnt.x - (curPnt.x - nLayerOrgX)
							* dScale / (dScale + dAddScale));
					nLayerOrgY = (int) (curPnt.y - (curPnt.y - nLayerOrgY)
							* dScale / (dScale + dAddScale));
				}
			}

			repaint();
		}
	};

	/** 鼠标按键监听事件 */
	MouseAdapter MBAdapter = new MouseAdapter() {
		boolean moveEnable = false;
		Point point1 = null;
		Point point2 = null;

		public void mousePressed(MouseEvent e) {
			moveEnable = true;
			point1 = e.getPoint();
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}

		public void mouseReleased(MouseEvent e) {
			moveEnable = false;
			point1 = null;
			point2 = null;
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		public void mouseDragged(MouseEvent e) {
			point2 = e.getPoint();

			if (moveEnable) {
				if (point1 != null && point2 != null) {
					int dx = point2.x - point1.x;
					int dy = point2.y - point1.y;

					nLayerOrgX += dx;
					nLayerOrgY += dy;

					point1 = point2;
					repaint();
				}
			}
		}
	};

	/** 进行初始化操作 */
	private void initialize() {

		// 获取该大图的头文件信息
		byte[] tbmHead = bigImageManage.getBlockData(nImageID,
				BigImgManage.getTbmHeadId(), 0);
		tbmFileHead = BigImgManage.parseTbmFileHead(tbmHead, true);

		// 根据屏幕大小计算要加载的图层
		int nLength = Math.max(nWinWidth, nWinHeight);
		nCurLayer = (int) (Math.log(nLength * 1.0 / tbmFileHead.nTileSize) / Math
				.log(2.0));
		nLastLayer = nCurLayer;

		// 计算当前图层的大小
		double dRatio = Math.pow(2.0, tbmFileHead.nLayerCount - nCurLayer - 1);
		nLayerCurHeight = (int) (tbmFileHead.nHeight / dRatio);
		nLayerCurWidth = (int) (tbmFileHead.nWidth / dRatio);

		// 计算图层的原点（左下角）在屏幕中的坐标
		nLayerOrgX = (nWinWidth - nLayerCurWidth) / 2;
		nLayerOrgY = (nWinHeight + nLayerCurHeight) / 2;

		// 加载背景图层数据
		try {
			byte[] dataBuffer = bigImageManage.getImage(nImageID, 0, "jpg");
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					dataBuffer);
			backgroundImage0 = ImageIO.read(inputStream);

			dataBuffer = bigImageManage.getImage(nImageID, 3, "jpg");
			inputStream = new ByteArrayInputStream(dataBuffer);
			backgroundImage3 = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		prepareFirstDrawData(); // 为第一次加载准备数据
	}

	/** 根据Enve范围获取图层上瓦片的行列号范围 */
	private Enve2I getFence(int nLayer, Enve2D temp) {
		Enve2I fence = new Enve2I();

		// 获取图层的标准大小
		double nRatio = Math.pow(2, tbmFileHead.nLayerCount - nLayer - 1);
		int nLayerHeight = (int) (tbmFileHead.nHeight / nRatio);
		int nLayerWidth = (int) (tbmFileHead.nWidth / nRatio);

		// 获取瓦片的行列号范围
		fence.XMin = (int) (temp.XMin * nLayerWidth / tbmFileHead.nTileSize);
		fence.YMin = (int) (temp.YMin * nLayerHeight / tbmFileHead.nTileSize);
		fence.XMax = (int) Math.ceil(temp.XMax * nLayerWidth
				/ tbmFileHead.nTileSize);
		fence.YMax = (int) Math.ceil(temp.YMax * nLayerHeight
				/ tbmFileHead.nTileSize);

		return fence;
	}

	/** 设置瓦片在屏幕中的显示位置 */
	private void setCurBlocksPos(Enve2D curEnve) {
		int nCode = 0;
		Enve2I fence = getFence(nCurLayer, curEnve);
		Hashtable<Integer, Point> temp = new Hashtable<>();

		// 计算瓦片在屏幕中的显示位置
		for (int nRow = fence.YMin; nRow < fence.YMax; nRow++) {
			for (int nCol = fence.XMin; nCol < fence.XMax; nCol++) {
				Point pntTemp = new Point();
				pntTemp.x = nLayerOrgX
						+ (int) (nCol * tbmFileHead.nTileSize * dScale);
				pntTemp.y = nLayerOrgY
						- (int) (nRow * tbmFileHead.nTileSize * dScale);

				nCode = ImageManage.getQueryCode(nCurLayer, nRow, nCol);
				temp.put(nCode, pntTemp);
			}
		}

		curDrawBlocksPos = temp;
	}

	/**
	 * 获取缓冲区瓦片的ID
	 * 
	 * @throws CloneNotSupportedException
	 */
	private ArrayList<Integer> getBufBlocksID(Enve2D bufEnve) {
		ArrayList<Integer> bufBlockID = new ArrayList<>();
		getBlocksID(nCurLayer, bufEnve, bufBlockID); // 获取同层缓冲区切片的ID

		// 获取上层缓冲区切片的ID
		if (0 != nCurLayer) {
			getBlocksID(nCurLayer - 1, bufEnve, bufBlockID);
		}

		// 获取下层缓冲区切片的ID
		if (nCurLayer != tbmFileHead.nLayerCount - 1) {
			getBlocksID(nCurLayer + 1, CurEnve, bufBlockID);
		}

		return bufBlockID;
	}

	/** 根据Enve范围获取图层上瓦片的ID */
	private void getBlocksID(int nLayer, Enve2D enveTemp,
			ArrayList<Integer> listID) {
		int nCode = 0;
		Enve2I fence = getFence(nLayer, enveTemp);

		for (int nRow = fence.YMin; nRow < fence.YMax; nRow++) {
			for (int nCol = fence.XMin; nCol < fence.XMax; nCol++) {
				nCode = ImageManage.getQueryCode(nLayer, nRow, nCol);
				listID.add(nCode);
			}
		}
	}

	/** 获取当前视口在图层上的Enve范围 */
	private Enve2D getCurEnve() {
		double nRatio = Math.pow(2, tbmFileHead.nLayerCount - nCurLayer - 1);
		nLayerCurHeight = (int) (tbmFileHead.nHeight * dScale / nRatio);
		nLayerCurWidth = (int) (tbmFileHead.nWidth * dScale / nRatio);

		Enve2D enveTemp = null;
		int nXMin = Math.max(nLayerOrgX, 0);
		int nYMin = Math.min(nLayerOrgY, nWinHeight);
		int nXMax = Math.min(nLayerOrgX + nLayerCurWidth, nWinWidth);
		int nYMax = Math.max(nLayerOrgY - nLayerCurHeight, 0);

		if (nXMin < nXMax && nYMin > nYMax) {
			enveTemp = new Enve2D();
			enveTemp.XMin = (nXMin - nLayerOrgX) * 1.0 / nLayerCurWidth;
			enveTemp.XMax = (nXMax - nLayerOrgX) * 1.0 / nLayerCurWidth;
			enveTemp.YMax = (nLayerOrgY - nYMax) * 1.0 / nLayerCurHeight;
			enveTemp.YMin = (nLayerOrgY - nYMin) * 1.0 / nLayerCurHeight;
		}

		return enveTemp;
	}

	/** 获取当前缓冲区在图层上的Enve范围 */
	private Enve2D getBufEnve(Enve2D temp) {
		Enve2D bufEnve = new Enve2D();

		try {
			bufEnve = (Enve2D) temp.clone();
			// double xRatio = nWinWidth / nLayerCurWidth;
			// double yRatio = nWinHeight / nLayerCurHeight;
			bufEnve.extend(bufEnve.getWidth() / 2, bufEnve.getHeight() / 2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return bufEnve;
	}

	/** 为程序第一次显示准备数据 */
	private void prepareFirstDrawData() {
		int nBlockID;
		byte[] data;
		prepareBlockID();

		for (int i = 0; i < curDrawBlocksID.size(); i++) {
			nBlockID = curDrawBlocksID.get(i);

			if (curBlocksBuf.containsKey(nBlockID)) {
				continue;
			}

			data = bigImageManage.getBlockData(nImageID, nBlockID, 1);
			curBlocksBuf.put(nBlockID, data);
		}
	}

	/** 准备要显示瓦片的ID */
	private int prepareBlockID() {
		if (null == readThread) {
			return -1;
		}

		// 获取当前窗口的Enve范围
		CurEnve = getCurEnve();

		if (null == CurEnve) {
			return -1;
		}

		// 获取要显示瓦片的ID与Pos
		ArrayList<Integer> dispBlockID = new ArrayList<>();
		getBlocksID(nCurLayer, CurEnve, dispBlockID);
		curDrawBlocksID = dispBlockID;
		setCurBlocksPos(CurEnve);

		// 判断是否要重新获取缓冲的范围
		if (nLastLayer != nCurLayer || !BufEnve.isIntersect(CurEnve)) {
			BufEnve = getBufEnve(CurEnve);
			ArrayList<Integer> bufBlockID = getBufBlocksID(BufEnve);

			readThread.setBlockBufID(bufBlockID);
			readThread.setInputChanged(true);

			curBlocksBuf = readThread.getBlockBuf();
		} else {
			if (isNeedGetBufData) {
				curBlocksBuf = readThread.getBlockBuf();
				isNeedGetBufData = false;
			}
		}

		return 0;
	}

	/** 按块显示图像 */
	public void drawBlocks(Graphics2D g2D) {

		// 先画背景图以防出现黑屏， 当前层数不大于3时，使用金字塔第0层数据，否则使用金字塔第3层数据
		if (nCurLayer <= 3) {
			g2D.drawImage(backgroundImage0, nLayerOrgX, nLayerOrgY
					- nLayerCurHeight, nLayerCurWidth, nLayerCurHeight, null);
		} else {
			g2D.drawImage(backgroundImage3, nLayerOrgX, nLayerOrgY
					- nLayerCurHeight, nLayerCurWidth, nLayerCurHeight, null);
		}

		// 瓦片的Image对象
		ByteArrayInputStream input;
		BufferedImage image;

		// 瓦片的大小
		int nBlockWidth;
		int nBlockHeight;

		int nBlockID = 0; // 瓦片的ID号
		Point blockPos; // 瓦片在屏幕中的显示位置
		byte[] dataBuffer; // 瓦片的数据

		for (int i = 0; i < curDrawBlocksID.size(); i++) {
			nBlockID = curDrawBlocksID.get(i);

			// 判断缓冲区是否包含该瓦片，如果不包含则跳过该瓦片的显示，用背景图代替
			if (!curBlocksBuf.containsKey(nBlockID)) {
				isNeedGetBufData = true;
				continue;
			}

			// 获取瓦片显示需要的信息
			dataBuffer = curBlocksBuf.get(nBlockID);
			blockPos = curDrawBlocksPos.get(nBlockID);
			input = new ByteArrayInputStream(dataBuffer);

			// 构建瓦片的Image并显示
			try {
				image = ImageIO.read(input);
				nBlockWidth = (int) (image.getWidth() * dScale);
				nBlockHeight = (int) (image.getHeight() * dScale);
				g2D.drawImage(image, blockPos.x, blockPos.y - nBlockHeight,
						nBlockWidth + 1, nBlockHeight + 1, null);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	/** 按图显示图像 */
	public void drawImage(Graphics2D g2D) {
		Enve2I fence = getFence(nCurLayer, CurEnve);
		int nRowCount = BigImgManage.getRowCount(nCurLayer, tbmFileHead);
		int nColCount = BigImgManage.getColCount(nCurLayer, tbmFileHead);

		// 获取要显示图像的大小
		int nDrawImgWidth = (fence.XMax - fence.XMin - 1)
				* tbmFileHead.nTileSize;
		int nDrawImgHeight = (fence.YMax - fence.YMin - 1)
				* tbmFileHead.nTileSize;
		double nRatio = Math.pow(2, tbmFileHead.nLayerCount - nCurLayer - 1);
		int nWidthAdd = (int) (tbmFileHead.nWidth / nRatio - (fence.XMax - 1)
				* tbmFileHead.nTileSize);
		int nHeightAdd = (int) (tbmFileHead.nHeight / nRatio - (fence.YMax - 1)
				* tbmFileHead.nTileSize);

		if (fence.XMax != nColCount) {
			nWidthAdd = tbmFileHead.nTileSize;
		}

		if (fence.YMax != nRowCount) {
			nHeightAdd = tbmFileHead.nTileSize;
		}

		nDrawImgWidth += nWidthAdd;
		nDrawImgHeight += nHeightAdd;

		BufferedImage drawImage = new BufferedImage(nDrawImgWidth,
				nDrawImgHeight, BufferedImage.TYPE_3BYTE_BGR);
		// 先画背景图以防出现黑屏， 当前层数不大于3时，使用金字塔第0层数据，否则使用金字塔第3层数据
		if (nCurLayer <= 3) {
			drawImage.getGraphics().drawImage(backgroundImage0, 0, 0,
					nDrawImgWidth, nDrawImgHeight, null);
		} else {
			drawImage.getGraphics().drawImage(backgroundImage3, 0, 0,
					nDrawImgWidth, nDrawImgHeight, null);
		}

		int nBlockID = 0; // 瓦片的ID号
		Point blockPos; // 瓦片在屏幕中的显示位置
		byte[] dataBuffer; // 瓦片的数据

		int nX = 0;
		int nY = 0;
		BufferedImage inputImage;
		ByteArrayInputStream inputStream;

		for (int i = 0; i < curDrawBlocksID.size(); i++) {
			nBlockID = curDrawBlocksID.get(i);

			// 判断缓冲区是否包含该瓦片，如果不包含则跳过该瓦片的显示，用背景图代替
			if (!curBlocksBuf.containsKey(nBlockID)) {
				continue;
			}

			// 获取瓦片显示需要的信息
			dataBuffer = curBlocksBuf.get(nBlockID);
			blockPos = curDrawBlocksPos.get(nBlockID);
			inputStream = new ByteArrayInputStream(dataBuffer);

			// 构建瓦片的Image并显示
			try {
				inputImage = ImageIO.read(inputStream);
				nX = (int) ((blockPos.x - nLayerOrgX) / dScale);
				nY = (int) ((blockPos.y - nLayerOrgY) / dScale + nDrawImgHeight)
						- inputImage.getHeight();
				drawImage.getGraphics().drawImage(inputImage, nX, nY,
						inputImage.getWidth(), inputImage.getHeight(), null);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}

		int nDrawWidth = (int) (drawImage.getWidth() * dScale);
		int nDrawHeight = (int) (drawImage.getHeight() * dScale);
		nX = (int) (nLayerOrgX + fence.XMin * tbmFileHead.nTileSize * dScale);
		nY = (int) (nLayerOrgY - fence.YMin * tbmFileHead.nTileSize * dScale - nDrawHeight);
		g2D.drawImage(drawImage, nX, nY, nDrawWidth, nDrawHeight, null);
	}

	/** 显示图像 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		if (null != readThread) {
			if (-1 != prepareBlockID()) {
				drawBlocks(g2D);
			}
		}
	}
}
