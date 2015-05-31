package com.canyinghao.canmedia.utils.PlayerEngine;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canmedia.bean.music.AudioBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class PlayerEngineImpl implements IPlayerEngine {

    String TAG="PlayerEngineImpl";
	public PlayerEngineImpl() {
		if (null == mediaPlayerEngine) {
			LogHelper.logi(TAG + "---PlayerEngineImpl---",
                    "Line 19 New MediaPlayerEngine();");
			mediaPlayerEngine = new MediaPlayerEngine();


		}
	}

	public enum PlaybackMode {

		NORMAL, SHUFFLE, REPEAT, SHUFFLE_AND_REPEAT
	}

	private PlaybackMode playbackMode = PlaybackMode.NORMAL;

	private List<Integer> playbackOrder = new ArrayList<Integer>();


    private AudioBean bean;

	private int selectedOrderIndex = 0;

	private List<AudioBean> mediaList = new ArrayList<AudioBean>();

	private MediaPlayerEngine mediaPlayerEngine = null;

	@Override
	public void setOnCompletionListener(
			OnCompletionListener onCompletionListener) {
        LogHelper.logi(TAG + "---PlayerEngineImpl---",
				"Line 56 setOnCompletionListener;");
		mediaPlayerEngine.setOnCompletionListener(onCompletionListener);
	}

	private boolean isEmpty() {
		return mediaList.size() == 0;
	}

	private int getListSize() {
		return mediaList.size();
	}

	public PlaybackMode getPlaybackMode() {
		return playbackMode;
	}

	@Override
	public IPlayerEngine setPlaybackMode(PlaybackMode playbackMode) {
		this.playbackMode = playbackMode;
		calculateOrder(true);
        return this;
	}

	@Override
	public void forward(int time) {
		mediaPlayerEngine.seekTo(time);

	}

	@Override
	public boolean isPlaying() {
		return mediaPlayerEngine.isPlaying();
	}

	@Override
	public boolean isPause() {
		return mediaPlayerEngine.isPause();
	}

	private int getSelectedOrderIndexByPath(AudioBean bean) {
		int selectedIndex = mediaList.indexOf(bean);
		return playbackOrder.indexOf(selectedIndex);

	}

	private AudioBean getPathByPlaybackOrderIndex(int index) {
		return mediaList.get(playbackOrder.get(index));
	}

	@Override
	public void previous() {
		if (!isEmpty()) {
			selectedOrderIndex = getSelectedOrderIndexByPath(bean);
			selectedOrderIndex--;
			if (selectedOrderIndex < 0) {
				selectedOrderIndex = mediaList.size() - 1;
			}
			this.bean = getPathByPlaybackOrderIndex(selectedOrderIndex);
			mediaPlayerEngine.previousOrNext();
		}

	}

	@Override
	public void next() {
		if (!isEmpty()) {
			selectedOrderIndex = mediaList.indexOf(bean);
            LogHelper.logi(
							TAG + "---PlayerEngineImpl---",
							"Line 123 next():Path="
									+ bean.getPath()
									+ "***********selectedOrderIndex="
									+ selectedOrderIndex
									+ "***************************************playbackOrder="
									+ playbackOrder.toArray());
			// selected begins from zero.
			selectedOrderIndex++;
			selectedOrderIndex %= mediaList.size();
			this.bean = getPathByPlaybackOrderIndex(selectedOrderIndex);
            LogHelper.logi(
							TAG + "---PlayerEngineImpl---",
							"Line 123 next():next Path="
									+ bean.getPath()
									+ "***********next selectedOrderIndex="
									+ selectedOrderIndex
									+ "***************************************playbackOrder="
									+ playbackOrder.toArray());
			mediaPlayerEngine.previousOrNext();
		}

	}






	@Override
	public void pause() {
		mediaPlayerEngine.pause();

	}

	@Override
	public void play() {
		mediaPlayerEngine.play(bean.getPath());
	}

	@Override
	public void start() {
		mediaPlayerEngine.start();
	}

	@Override
	public void reset() {
		mediaPlayerEngine.reset();

	}

	@Override
	public void rewind(int time) {
		mediaPlayerEngine.seekTo(time);

	}

	@Override
	public void skipTo(int index) {

	}

	@Override
	public void stop() {
		mediaPlayerEngine.stop();
	}

	@Override
	public AudioBean getPlayingBean() {
		return this.bean;
	}

	@Override
	public IPlayerEngine setPlayingBean(AudioBean bean) {

        this.bean=bean;
        return  this;
	}

	@Override
	public IPlayerEngine setMediaPlayList(List<AudioBean> pathList) {
		this.mediaList = pathList;
		calculateOrder(true);
        return  this;

	}

	private void calculateOrder(boolean force) {
		int beforeSelected = 0;
		if (!playbackOrder.isEmpty()) {
			beforeSelected = playbackOrder.get(selectedOrderIndex);
			playbackOrder.clear();
		}
        LogHelper.logi(TAG + "---PlayerEngineImpl---",
						"Line 200 calculateOrder():beforeSelected="
								+ beforeSelected
								+ "***********selectedOrderIndex="
								+ selectedOrderIndex);
		for (int i = 0; i < getListSize(); i++) {
			playbackOrder.add(i, i);
		}

		if (null == playbackMode) {
			playbackMode = PlaybackMode.NORMAL;
		}
		switch (playbackMode) {
		case NORMAL: {
			break;
		}
		case SHUFFLE: {
			Collections.shuffle(playbackOrder);
			break;
		}
		case REPEAT: {
			selectedOrderIndex = beforeSelected;
			break;
		}
		case SHUFFLE_AND_REPEAT: {
			break;
		}
		default: {
			break;
		}
		}

	}

	private class MediaPlayerEngine extends MediaPlayer {

		private boolean isPause = false;

		public boolean isPause() {
			return isPause;
		}

		@Override
		public void reset() {
			isPause = false;
			super.reset();
		}

		public void play(String path) {
			try {

                reset();
				this.setDataSource(path);
				if (!isPause) {
					super.prepare();
				}
				super.start();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void stop() throws IllegalStateException {
			isPause = false;
			super.stop();
		}

		@Override
		public void pause() throws IllegalStateException {
			isPause = true;
			super.pause();
		}

		public void previousOrNext() {

            LogHelper.logi(TAG, "previousOrNext:path = " + bean.getPath());
			play(bean.getPath());

		}
	}

	@Override
	public String getCurrentTime() {
		return getTime(mediaPlayerEngine.getCurrentPosition());
	}

	@Override
	public String getDurationTime() {
		return getTime(mediaPlayerEngine.getDuration());
	}

	@Override
	public PlaybackMode getPlayMode() {
		return playbackMode;
	}

	private String getTime(int timeMs) {
		int totalSeconds = timeMs / 1000;// 获取文件有多少秒
		StringBuilder mFormatBuilder = new StringBuilder();
		Formatter mFormatter = new Formatter(mFormatBuilder, Locale
				.getDefault());
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;
		mFormatBuilder.setLength(0);

		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();// 格式化字符串
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	@Override
	public int getDuration() {
		return mediaPlayerEngine.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayerEngine.getCurrentPosition();
	}
}
