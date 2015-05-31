package com.canyinghao.canmedia.bean.picture;

public class PictrueItemBean extends PictureBaseBean {

	private int  photoID;
	private boolean select;
	private String path;
	public PictrueItemBean(int id, String path) {
        super();
		photoID = id;
		select = false;
		this.path=path;
	}
	
	public PictrueItemBean(int id, boolean flag) {
        super();
		photoID = id;
		select = flag;
	}
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPhotoID() {
		return photoID;
	}
	public void setPhotoID(int photoID) {
		this.photoID = photoID;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	@Override
	public String toString() {
		return "PhotoItem [photoID=" + photoID + ", select=" + select + "]";
	}
}
