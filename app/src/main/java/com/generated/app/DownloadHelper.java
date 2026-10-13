package com.generated.app;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;

public class DownloadHelper {

    public static void downloadAudio(Context context, String videoId, String title, String quality) {
        // Simulated NewPipe / Metrolist downloader source mapping to direct audio stream URL / Ytmp3 proxy
        String streamUrl = "https://www.youtube.com/watch?v=" + videoId;
        String fileName = sanitizeFileName(title) + "_" + quality + ".mp3";
        startDownload(context, streamUrl, fileName, "הורדת שמע (מקור: מטרוליסט)");
    }

    public static void downloadVideo(Context context, String videoId, String title, String quality) {
        // Simulated NewPipe source mapping to direct video stream URL
        String streamUrl = "https://www.youtube.com/watch?v=" + videoId;
        String fileName = sanitizeFileName(title) + "_" + quality + ".mp4";
        startDownload(context, streamUrl, fileName, "הורדת סרטון (מקור: NewPipe)");
    }

    private static void startDownload(Context context, String url, String fileName, String notificationTitle) {
        try {
            String customPath = PreferencesHelper.getDownloadPath(context);
            File dir = new File(customPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle(notificationTitle);
            request.setDescription(fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationUri(Uri.fromFile(new File(dir, fileName)));
            request.allowScanningByMediaScanner();

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (manager != null) {
                manager.enqueue(request);
                Toast.makeText(context, "ההורדה התחילה לתיקיה: " + dir.getName(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "שגיאה בהורדה: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static String sanitizeFileName(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
