package com.studio.bean;

import java.util.HashMap;
import java.util.SortedMap;

public class TransactionResponse {
	private boolean transactionStatus;
	private String message;
	private String frameID;
	private String mirrorID;
	private String photoID;
	private SortedMap<String, PhotosBean> photoMap;
	private SortedMap<String, MirrorBean> mirrorMap;
	private SortedMap<String, FrameBean> frameMap;
	private HashMap<String, UserAdminOverview> userMap;
	private String billID;
	private SortedMap<String, BillBean> billMAP;
	
	
	public String getBillID() {
		return billID;
	}
	public void setBillID(String billID) {
		this.billID = billID;
	}
	public SortedMap<String, BillBean> getBillMAP() {
		return billMAP;
	}
	public void setBillMAP(SortedMap<String, BillBean> billMAP) {
		this.billMAP = billMAP;
	}
	public HashMap<String, UserAdminOverview> getUserMap() {
		return userMap;
	}
	public void setUserMap(HashMap<String, UserAdminOverview> userMap) {
		this.userMap = userMap;
	}
	public boolean isTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(boolean transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFrameID() {
		return frameID;
	}
	public void setFrameID(String frameID) {
		this.frameID = frameID;
	}
	public String getMirrorID() {
		return mirrorID;
	}
	public void setMirrorID(String mirrorID) {
		this.mirrorID = mirrorID;
	}
	public String getPhotoID() {
		return photoID;
	}
	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}
	public SortedMap<String, PhotosBean> getPhotoMap() {
		return photoMap;
	}
	public void setPhotoMap(SortedMap<String, PhotosBean> pMap) {
		this.photoMap = pMap;
	}
	public SortedMap<String, MirrorBean> getMirrorMap() {
		return mirrorMap;
	}
	public void setMirrorMap(SortedMap<String, MirrorBean> mMap) {
		this.mirrorMap = mMap;
	}
	public SortedMap<String, FrameBean> getFrameMap() {
		return frameMap;
	}
	public void setFrameMap(SortedMap<String, FrameBean> fMap) {
		this.frameMap = fMap;
	}
}
