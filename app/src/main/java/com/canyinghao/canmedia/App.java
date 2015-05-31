package com.canyinghao.canmedia;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.canyinghao.canhelper.CanHelper;
import com.canyinghao.canhelper.FileHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canmedia.utils.PlayerEngine.IPlayerEngine;
import com.canyinghao.canmedia.utils.PlayerEngine.PlayerEngineImpl;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.anrwatchdog.ANRWatchDog;
import com.lidroid.xutils.DbUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import butterknife.ButterKnife;

public class App extends Application implements Thread.UncaughtExceptionHandler {

	private static App app;




	@Override
	public void onCreate() {



        super.onCreate();


		app = this;
        Fresco.initialize(this);
		ButterKnife.setDebug(BuildConfig.DEBUG);
		LogHelper.DEBUG=BuildConfig.DEBUG;

		CanHelper.init(this);
		ANRWatchDog anrWatchDog = new ANRWatchDog(2000);
		anrWatchDog.start();



		File file = new File(FileHelper.getInstance().getExternalStorePath(),
				"canmedia");

		
		if (!file.exists()) {
			file.mkdirs();
		}
//		Thread.setDefaultUncaughtExceptionHandler(this);

	}

	public static App getInstance() {
		if (app == null) {
			app = new App();
		}
		return app;

	}



	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		String eStr = getCrashReport(ex);
		LogHelper.loge("UncaughtException", eStr);
		try {
			File file = new File(FileHelper.getInstance()
					.getExternalStorePath(), "canmedia/faillog.txt");
			FileOutputStream f = new FileOutputStream(file);
			f.write(eStr.getBytes());
			f.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}

	private String getCrashReport(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String expcetionStr = sw.toString();
		try {
			sw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
		return expcetionStr;
	}




    private IPlayerEngine playerEngine;

    public IPlayerEngine getPlayerEngine() {
        if (null == playerEngine) {
            playerEngine = new PlayerEngineImpl();
        }
        return playerEngine;
    }

    private DbUtils dbUtils;

    public DbUtils getDbUtils() {
        if (null == dbUtils) {
            dbUtils = DbUtils.create(this);
        }
        return dbUtils;
    }


    /**
     * Retrieves application's version number from the manifest
     *
     * @return
     */
    public String getVersion() {
        String version = "0.0.0";

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

}
