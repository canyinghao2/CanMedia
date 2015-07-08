package com.canyinghao.canhelper.utils;

import android.util.Log;

/**
 * 日志打印工具类
 *
 * @author canyinghao
 *
 */
public class LogHelper {

    public static String TAG = "LogHelper";
    public static boolean DEBUG = true;

    public static void d(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.d(TAG, log);
        }

    }

    public static void d(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.d(tag, log);
        }

    }

    public static void e(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(className, log);
        }

    }

    public static void e(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(TAG, log);

        }

    }

    public static void e(String tag, String log, Throwable tb) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.e(tag, log, tb);

        }

    }

    public static void i(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.i(TAG, log);
        }

    }

    public static void i(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.i(tag, log);
        }
    }

    public static void v(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.v(TAG, log);
        }
    }

    public static void v(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.v(tag, log);
        }

    }

    public static void w(String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.w(TAG, log);
        }

    }

    public static void w(String tag, String log) {
        if (DEBUG) {
            StackTraceElement[] sElements = new Throwable().getStackTrace();
            String className = sElements[1].getFileName();
            String methodName = sElements[1].getMethodName();
            int lineNumber = sElements[1].getLineNumber();

            StringBuffer buffer = new StringBuffer();
            buffer.append(className + "---");
            buffer.append(methodName + "---");

            buffer.append(lineNumber + "---");

            buffer.append(log);
            log = buffer.toString();
            Log.w(tag, log);
        }
    }

}
