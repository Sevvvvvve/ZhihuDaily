package com.vaynefond.zhihudaily.utils;

import android.content.Context;

import java.io.File;

public class FileUtils {
    private static final String SYSTEM_DATA_DIR = "/data/data/";
    private static final String CACHE_DIR = "/cache";
    private static final String FILE_DIR = "/files";
    private static final String DATABASE_DIR = "/databases";
    private static final String PREFERENCE_DIR = "/shared_prefs";

    public static File getCacheDir(Context context) {
        return new File(SYSTEM_DATA_DIR + context.getPackageName() + CACHE_DIR);
    }

    public static File getDatabaseDir(Context context) {
        return new File(SYSTEM_DATA_DIR + context.getPackageName() + DATABASE_DIR);
    }

    public static File getPreferenceDir(Context context) {
        return new File(SYSTEM_DATA_DIR + context.getPackageName() + PREFERENCE_DIR);
    }

    public static File getFilesDir(Context context) {
        return new File(SYSTEM_DATA_DIR + context.getPackageName() + FILE_DIR);
    }

    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }

        long size = 0;
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                size += getFileSize(subFile);
            } else {
                size += subFile.length();
            }
        }

        return size;
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        for (File subFile : file.listFiles()) {
            if (subFile.isFile()) {
                subFile.delete();
            } else {
                deleteFile(subFile);
                subFile.delete();
            }
        }
    }
}
