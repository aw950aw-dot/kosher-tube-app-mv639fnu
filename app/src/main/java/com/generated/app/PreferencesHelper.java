package com.generated.app;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferencesHelper {
    private static final String PREF_NAME = "KosherTubePrefs";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_APPROVED_CHANNELS = "approved_channels";
    private static final String KEY_AUDIO_QUALITY = "audio_quality";
    private static final String KEY_VIDEO_QUALITY = "video_quality";
    private static final String KEY_DOWNLOAD_PATH = "download_path";

    public static String getPassword(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PASSWORD, "1234");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PASSWORD, password).apply();
    }

    public static List<YouTubeItem> getApprovedChannels(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> set = prefs.getStringSet(KEY_APPROVED_CHANNELS, new HashSet<>());
        List<YouTubeItem> list = new ArrayList<>();
        for (String itemStr : set) {
            String[] parts = itemStr.split("\\|\\|");
            if (parts.length >= 4) {
                list.add(new YouTubeItem(parts[0], parts[1], "", parts[2], parts[3], parts[0], false));
            } else if (parts.length >= 2) {
                list.add(new YouTubeItem(parts[0], parts[1], "", "", parts[1], parts[0], false));
            }
        }
        return list;
    }

    public static void saveApprovedChannels(Context context, List<YouTubeItem> channels) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> set = new HashSet<>();
        for (YouTubeItem ch : channels) {
            set.add(ch.getId() + "||" + ch.getTitle() + "||" + ch.getThumbnailUrl() + "||" + ch.getChannelTitle());
        }
        prefs.edit().putStringSet(KEY_APPROVED_CHANNELS, set).apply();
    }

    public static String getAudioQuality(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_AUDIO_QUALITY, "128kbps");
    }

    public static void setAudioQuality(Context context, String quality) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_AUDIO_QUALITY, quality).apply();
    }

    public static String getVideoQuality(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_VIDEO_QUALITY, "720p");
    }

    public static void setVideoQuality(Context context, String quality) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_VIDEO_QUALITY, quality).apply();
    }

    public static String getDownloadPath(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_DOWNLOAD_PATH, android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
    }

    public static void setDownloadPath(Context context, String path) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_DOWNLOAD_PATH, path).apply();
    }
}
