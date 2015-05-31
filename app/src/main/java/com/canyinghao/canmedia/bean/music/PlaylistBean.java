package com.canyinghao.canmedia.bean.music;

import com.canyinghao.canmedia.bean.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlaylistBean extends BaseBean{

	private Long id;

	private String name;

	private Date addDate;

	private Date updateDate;

	private Integer countAudio;

    private List<AudioBean> list;

    public List<AudioBean> getList() {

        if (list==null){

            list=new ArrayList<>();
        }


        return list;
    }

    public void setList(List<AudioBean> list) {
        this.list = list;
    }

    public Long getId() {
		return id;
	}

	public Integer getCountAudio() {
		return countAudio;
	}

	public void setCountAudio(Integer countAudio) {
		this.countAudio = countAudio;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
