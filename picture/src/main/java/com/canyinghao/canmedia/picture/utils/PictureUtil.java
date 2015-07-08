package com.canyinghao.canmedia.picture.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.canyinghao.canmedia.picture.bean.PictrueAibumBean;
import com.canyinghao.canmedia.picture.bean.PictrueItemBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureUtil {

	public static final int REQUEST_CODE_SEND_PICTURE = 10;
	public static final String DRR = "drr";
	public static final String MAX="max";



	/*
	 * 获取图片的字段信息
	 */
	static final String[] STORE_IMAGES = {
			MediaStore.Images.Media.DISPLAY_NAME, // 名称
			MediaStore.Images.Media.DATA, MediaStore.Images.Media.LONGITUDE, // 经度
			MediaStore.Images.Media._ID, // id
			MediaStore.Images.Media.BUCKET_ID, // dir id 目录
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME // dir name 目录名字
	};

	/*
	 * 按相册获取图片信息
	 */
	public static List<PictrueAibumBean> getPhotoAlbum(Context context) {
		List<PictrueAibumBean> aibumList = new ArrayList<PictrueAibumBean>();
		Cursor cursor = MediaStore.Images.Media.query(
				context.getContentResolver(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
		Map<String, PictrueAibumBean> countMap = new HashMap<String, PictrueAibumBean>();
		PictrueAibumBean pa = null;
		while (cursor.moveToNext()) {
			String path = cursor.getString(1);
			String id = cursor.getString(3);
			String dir_id = cursor.getString(4);
			String dir = cursor.getString(5);
			if (!new File(path).exists()) {
				continue;
			}

			if (!countMap.containsKey(dir_id)) {
				pa = new PictrueAibumBean();
				pa.setName(dir);
				pa.setBitmap(Integer.parseInt(id));
				pa.setCount("1");
				pa.getBitList().add(new PictrueItemBean(Integer.valueOf(id), path));
				countMap.put(dir_id, pa);
			} else {
				pa = countMap.get(dir_id);
				pa.setCount(String.valueOf(Integer.parseInt(pa.getCount()) + 1));
				pa.getBitList().add(new PictrueItemBean(Integer.valueOf(id), path));
			}
		}
		cursor.close();
		Iterable<String> it = countMap.keySet();
		for (String key : it) {
			aibumList.add(countMap.get(key));
		}
		return aibumList;
	}

}
