package com.canyinghao.canmedia.music.utils.PlayerEngine;

import android.media.MediaPlayer.OnCompletionListener;

import com.canyinghao.canmedia.music.bean.AudioBean;

import java.util.List;

public interface IPlayerEngine {

	void play();


	void reset();

	void stop();

	void pause();

	void previous();

	void next();

	void skipTo(int index);

	void forward(int time);

	void rewind(int time);

	boolean isPlaying();

	boolean isPause();

    AudioBean getPlayingBean();

    IPlayerEngine setPlayingBean(AudioBean bean);

    IPlayerEngine setMediaPlayList(List<AudioBean> pathList);

	void start();

	void setOnCompletionListener(OnCompletionListener onCompletionListener);

    IPlayerEngine setPlaybackMode(PlayerEngineImpl.PlaybackMode playbackMode);

	PlayerEngineImpl.PlaybackMode getPlayMode();

	String getCurrentTime();

	String getDurationTime();

	int getDuration();

	int getCurrentPosition();

}
