package com.generated.app;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YouTubeApiService {
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    public static void searchChannels(String query, Callback<List<YouTubeItem>> callback) {
        executor.execute(() -> {
            try {
                String encoded = URLEncoder.encode(query, "UTF-8");
                String urlStr = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=channel&q=" + encoded + "&maxResults=15&key=" + Constants.YT_API_KEY;
                String jsonStr = fetchUrl(urlStr);
                List<YouTubeItem> list = new ArrayList<>();
                if (jsonStr != null) {
                    JSONObject obj = new JSONObject(jsonStr);
                    JSONArray items = obj.optJSONArray("items");
                    if (items != null) {
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            JSONObject idObj = item.getJSONObject("id");
                            String channelId = idObj.optString("channelId");
                            JSONObject snippet = item.getJSONObject("snippet");
                            String title = snippet.optString("title");
                            String desc = snippet.optString("description");
                            JSONObject thumbs = snippet.optJSONObject("thumbnails");
                            String thumbUrl = "";
                            if (thumbs != null) {
                                JSONObject def = thumbs.optJSONObject("default");
                                if (def != null) thumbUrl = def.optString("url");
                            }
                            list.add(new YouTubeItem(channelId, title, desc, thumbUrl, title, channelId, false));
                        }
                    }
                }
                handler.post(() -> callback.onSuccess(list));
            } catch (Exception e) {
                handler.post(() -> callback.onError(e));
            }
        });
    }

    public static void getChannelVideos(String channelId, String query, Callback<List<YouTubeItem>> callback) {
        executor.execute(() -> {
            try {
                String urlStr;
                if (query != null && !query.trim().isEmpty()) {
                    String encoded = URLEncoder.encode(query, "UTF-8");
                    urlStr = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channelId + "&type=video&q=" + encoded + "&maxResults=25&key=" + Constants.YT_API_KEY;
                } else {
                    urlStr = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channelId + "&type=video&order=date&maxResults=25&key=" + Constants.YT_API_KEY;
                }
                String jsonStr = fetchUrl(urlStr);
                List<YouTubeItem> list = new ArrayList<>();
                if (jsonStr != null) {
                    JSONObject obj = new JSONObject(jsonStr);
                    JSONArray items = obj.optJSONArray("items");
                    if (items != null) {
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject item = items.getJSONObject(i);
                            JSONObject idObj = item.getJSONObject("id");
                            String videoId = idObj.optString("videoId");
                            if (videoId.isEmpty()) continue;
                            JSONObject snippet = item.getJSONObject("snippet");
                            String title = snippet.optString("title");
                            String desc = snippet.optString("description");
                            String chTitle = snippet.optString("channelTitle");
                            String chId = snippet.optString("channelId");
                            JSONObject thumbs = snippet.optJSONObject("thumbnails");
                            String thumbUrl = "";
                            if (thumbs != null) {
                                JSONObject med = thumbs.optJSONObject("medium");
                                if (med != null) thumbUrl = med.optString("url");
                                else {
                                    JSONObject def = thumbs.optJSONObject("default");
                                    if (def != null) thumbUrl = def.optString("url");
                                }
                            }
                            list.add(new YouTubeItem(videoId, title, desc, thumbUrl, chTitle, chId, true));
                        }
                    }
                }
                handler.post(() -> callback.onSuccess(list));
            } catch (Exception e) {
                handler.post(() -> callback.onError(e));
            }
        });
    }

    private static String fetchUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
