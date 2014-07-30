package com.imagedb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadThread extends Thread {

	// 缓冲区中 Block的 ID及 Data
	private Hashtable<Integer, byte[]> BlockBuf = new Hashtable<>();
	public ArrayList<Integer> bufBlockID = new ArrayList<>();

	boolean bChanged = false; // 主线程的请求是否更新
	boolean bRuning = true; // 子线程是否仍在运行
	private Lock lock = new ReentrantLock(); // 子线程的锁

	private BigImgManage bigImgManage; // 进行大图操作的对象
	private long nImageID; // 大图的ID

	public ReadThread(String threadName, BigImgManage bigImageManage,
			long nImgID) {
		super(threadName);
		Thread.currentThread().setPriority(7);

		nImageID = nImgID;
		bigImgManage = bigImageManage;
	}

	public void run() {
		while (bRuning) {
			if (isInputChanged()) {
				getBlocksForDisplay();
			}
		}
	}

	/** 判断主线程的请求是否更新 */
	public boolean isInputChanged() {
		lock.lock();
		boolean temp = bChanged;
		lock.unlock();
		return temp;
	}

	/** 获取缓冲区 Block的数据 */
	public Hashtable<Integer, byte[]> getBlockBuf() {
		lock.lock();
		Hashtable<Integer, byte[]> temp = new Hashtable<>();
		byte[] dataBuffer;
		int nID;

		for (Iterator<Entry<Integer, byte[]>> it = BlockBuf.entrySet()
				.iterator(); it.hasNext();) {
			Entry<Integer, byte[]> Block = it.next();

			nID = Block.getKey();
			dataBuffer = Block.getValue().clone();

			temp.put(nID, dataBuffer);
		}

		lock.unlock();
		return temp;
	}

	/** 获取缓冲区 Block的ID */
	public ArrayList<Integer> getBlockBufID() {
		lock.lock();
		ArrayList<Integer> temp = new ArrayList<>();

		for (int i = 0; i < bufBlockID.size(); i++) {
			temp.add(bufBlockID.get(i));
		}

		lock.unlock();
		return temp;
	}

	/** 修改缓冲区 Block的ID */
	public void setBlockBufID(ArrayList<Integer> temp) {
		lock.lock();
		bufBlockID = temp;
		lock.unlock();
	}

	/** 修改主线程的请求标记 */
	public void setInputChanged(boolean temp) {
		lock.lock();
		bChanged = temp;
		lock.unlock();
	}

	/** 修改子线程的运行标记 */
	public void setThreadRuning(boolean temp) {
		lock.lock();
		bRuning = temp;
		lock.unlock();
	}

	/** 修改缓冲区 Block的数据 */
	public void setBlockBuf(Hashtable<Integer, byte[]> temp) {
		lock.lock();
		BlockBuf = temp;
		lock.unlock();
	}

	/** 为主线程准备显示的数据 */
	public int getBlocksForDisplay() {
		setInputChanged(false);

		// 获取缓冲区切片的ID
		ArrayList<Integer> BlockIDBuf = getBlockBufID();
		HashSet<Integer> BlockBufIDNeed = new HashSet<>();

		if (0 == BlockIDBuf.size()) {
			return -1;
		}

		int nID;
		byte[] dataBuf;
		Hashtable<Integer, byte[]> tempBlockBuf = getBlockBuf();

		// 填充缓冲区
		for (int i = 0; i < BlockIDBuf.size(); i++) {

			// 同步检测是否输入ID已更新
			if (isInputChanged()) {
				return 0;
			}

			nID = BlockIDBuf.get(i);
			BlockBufIDNeed.add(nID);

			if (tempBlockBuf.containsKey(nID)) {
				continue;
			}

			dataBuf = bigImgManage.getBlockData(nImageID, nID, 1);
			tempBlockBuf.put(nID, dataBuf);
		}

		setBlockBuf(tempBlockBuf);

		// 删除不需要的瓦片
		for (Iterator<Entry<Integer, byte[]>> it = tempBlockBuf.entrySet()
				.iterator(); it.hasNext();) {
			Entry<Integer, byte[]> nBlockID = it.next();

			if (BlockBufIDNeed.contains(nBlockID.getKey())) {
				continue;
			}

			it.remove();
		}

		return 0;
	}
}
