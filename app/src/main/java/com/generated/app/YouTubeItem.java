package com.generated.app;

import java.io.Serializable;

public class YouTubeItem implements Serializable {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelTitle;
    private String channelId;
    private boolean isVideo; // true if video, false if channel

    public YouTubeItem(String id, String title, String description, String thumbnailUrl, String channelTitle, String channelId, boolean isVideo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.channelTitle = channelTitle;
        this.channelId = channelId;
        this.isVideo = isVideo;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getChannelTitle() { return channelTitle; }
    public String getChannelId() { return channelId; }
    public boolean isVideo() { return isVideo; }
}
