package com.canyinghao.canmedia.music.utils.PlayerEngine;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;

import com.canyinghao.otherlibrary.ProgressWheel;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player implements OnBufferingUpdateListener, OnCompletionListener,
		OnPreparedListener {

	public MediaPlayer mediaPlayer; 
	private SeekBar seekBar; 
	private ProgressWheel pw;
	private Timer mTimer = new Timer(); 
   
	private OnPreparedCallBack callback;
	public Player(SeekBar seekBar,ProgressWheel pw,OnPreparedCallBack callback) {
		super();
		this.seekBar = seekBar;
		this.pw=pw;
		this.callback=callback;
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setLooping(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		mTimer.schedule(timerTask, 0, 1000);
	}
	public Player(ProgressWheel pw,OnPreparedCallBack callback) {
		
		this(null,pw,callback);
	}
	public Player(SeekBar seekBar,OnPreparedCallBack callback) {
		
		this(seekBar,null,callback);
	}


	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (mediaPlayer.isPlaying() && (seekBar==null||seekBar.isPressed() == false)) {
				handler.sendEmptyMessage(0);
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();
			if (duration > 0) {
			
				if (seekBar!=null) {
					long pos = seekBar.getMax() * position / duration;
					seekBar.setProgress((int) pos);
				}
				if (pw!=null) {
					long pos = 360 * position / duration;
					pw.setProgress((int) pos);
				}
				
			}
		};
	};

	public void play() {
		mediaPlayer.start();
	}

	
	public void playUrl(String url) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); 
			mediaPlayer.prepare(); 
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void pause() {
		
		mediaPlayer.pause();
		
	}
	
	public boolean isPlaying(){
		
		return mediaPlayer.isPlaying();
	}
	public boolean isLooping(){
		
		return mediaPlayer.isLooping();
	}

	
	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void onPrepared(final MediaPlayer mp) {
		mp.start();
		if (callback!=null) {
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					callback.onPrepared(Player.this,mp);
					
				}
			}, 1000);
			
		}
		
		Log.e("mediaPlayer", "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("mediaPlayer", "onCompletion");
	}


	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if (seekBar!=null) {
			seekBar.setSecondaryProgress(percent);
			int currentProgress = seekBar.getMax()
					* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
			Log.e(currentProgress + "% play", percent + " buffer");
		}
		
		
		
	}
	
	
	public interface OnPreparedCallBack{
		
		void onPrepared(Player player, MediaPlayer mp);
		
		
	}

}
