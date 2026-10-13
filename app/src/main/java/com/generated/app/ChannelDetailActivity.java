package com.generated.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ChannelDetailActivity extends Activity {

    private LinearLayout videosContainer;
    private String channelId;
    private String channelTitle;
    private List<YouTubeItem> channelVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        channelId = getIntent().getStringExtra("channel_id");
        channelTitle = getIntent().getStringExtra("channel_title");

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(0xFFFAFAFA);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Top Bar
        LinearLayout topBar = new LinearLayout(this);
        topBar.setOrientation(LinearLayout.HORIZONTAL);
        topBar.setGravity(Gravity.CENTER_VERTICAL);
        topBar.setPadding(24, 24, 24, 24);
        topBar.setBackgroundColor(0xFF1976D2);

        TextView titleView = new TextView(this);
        titleView.setText(channelTitle != null ? channelTitle : "ערוץ");
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setTextSize(18);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        topBar.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        root.addView(topBar);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(24, 24, 24, 24);

        // Channel Search Box
        EditText channelSearch = new EditText(this);
        channelSearch.setHint(R.string.channel_search_hint);
        channelSearch.setBackgroundResource(R.drawable.bg_edittext);
        channelSearch.setPadding(24, 24, 24, 24);
        channelSearch.setTextSize(16);
        contentLayout.addView(channelSearch, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        channelSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetchVideos(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        videosContainer = new LinearLayout(this);
        videosContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams vParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vParams.setMargins(0, 24, 0, 0);
        contentLayout.addView(videosContainer, vParams);

        scrollView.addView(contentLayout);
        root.addView(scrollView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        setContentView(root);
        fetchVideos(null);
    }

    private void fetchVideos(String query) {
        YouTubeApiService.getChannelVideos(channelId, query, new YouTubeApiService.Callback<List<YouTubeItem>>() {
            @Override
            public void onSuccess(List<YouTubeItem> result) {
                channelVideos = result;
                displayVideos(channelVideos);
            }
            @Override
            public void onError(Exception e) {}
        });
    }

    private void displayVideos(List<YouTubeItem> videos) {
        videosContainer.removeAllViews();
        for (YouTubeItem video : videos) {
            View card = createVideoCard(video);
            videosContainer.addView(card);
        }
    }

    private View createVideoCard(YouTubeItem video) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setPadding(16, 16, 16, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        card.setLayoutParams(params);

        TextView title = new TextView(this);
        title.setText(video.getTitle());
        title.setTextColor(0xFF212121);
        title.setTextSize(16);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        card.addView(title);

        // Buttons Layout
        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams blParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blParams.setMargins(0, 16, 0, 0);
        btnLayout.setLayoutParams(blParams);

        Button playButton = new Button(this);
        playButton.setText("צפה בסרטון");
        playButton.setTextColor(0xFFFFFFFF);
        playButton.setBackgroundResource(R.drawable.bg_button);
        playButton.setPadding(24, 12, 24, 12);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra("video_id", video.getId());
            intent.putExtra("video_title", video.getTitle());
            startActivity(intent);
        });
        btnLayout.addView(playButton);

        Button audioBtn = new Button(this);
        audioBtn.setText("הורד שמע");
        audioBtn.setTextColor(0xFFFFFFFF);
        audioBtn.setBackgroundResource(R.drawable.bg_button_secondary);
        LinearLayout.LayoutParams abParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        abParams.setMargins(16, 0, 0, 0);
        audioBtn.setLayoutParams(abParams);
        audioBtn.setPadding(24, 12, 24, 12);
        audioBtn.setOnClickListener(v -> DownloadHelper.downloadAudio(this, video.getId(), video.getTitle(), PreferencesHelper.getAudioQuality(this)));
        btnLayout.addView(audioBtn);

        Button videoBtn = new Button(this);
        videoBtn.setText("הורד סרטון");
        videoBtn.setTextColor(0xFFFFFFFF);
        videoBtn.setBackgroundResource(R.drawable.bg_button_secondary);
        LinearLayout.LayoutParams vbParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vbParams.setMargins(16, 0, 0, 0);
        videoBtn.setLayoutParams(vbParams);
        videoBtn.setPadding(24, 12, 24, 12);
        videoBtn.setOnClickListener(v -> DownloadHelper.downloadVideo(this, video.getId(), video.getTitle(), PreferencesHelper.getVideoQuality(this)));
        btnLayout.addView(videoBtn);

        card.addView(btnLayout);
        return card;
    }
}
