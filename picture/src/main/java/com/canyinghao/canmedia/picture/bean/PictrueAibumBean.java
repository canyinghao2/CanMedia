package com.canyinghao.canmedia.picture.bean;

import java.util.ArrayList;
import java.util.List;


public class PictrueAibumBean extends PictureBaseBean{

	private String name;   //相册名字
	private String count; //数量
	private int  bitmap;  // 相册第一张图片

	
	private List<PictrueItemBean> bitList = new ArrayList<PictrueItemBean>();
	
	public PictrueAibumBean() {
        super();
	}
	
	
	public PictrueAibumBean(String name, String count, int bitmap) {

		super();
		this.name = name;
		this.count = count;
		this.bitmap = bitmap;
	}


	public List<PictrueItemBean> getBitList() {
		return bitList;
	}

	public void setBitList(List<PictrueItemBean> bitList) {
		this.bitList = bitList;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getBitmap() {
		return bitmap;
	}
	public void setBitmap(int bitmap) {
		this.bitmap = bitmap;
	}
	@Override
	public String toString() {
		return "PhotoAibum [name=" + name + ", count=" + count + ", bitmap="
				+ bitmap + ", bitList=" + bitList + "]";
	}
}
