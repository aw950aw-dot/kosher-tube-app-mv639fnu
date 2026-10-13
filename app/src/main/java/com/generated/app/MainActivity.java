package com.generated.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LinearLayout channelsContainer;
    private LinearLayout videosContainer;
    private EditText searchEditText;
    private List<YouTubeItem> approvedChannels = new ArrayList<>();
    private List<YouTubeItem> allVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

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
        titleView.setText(R.string.app_name);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setTextSize(20);
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        topBar.addView(titleView, titleParams);

        Button settingsButton = new Button(this);
        settingsButton.setText(R.string.settings_button);
        settingsButton.setTextColor(0xFFFFFFFF);
        settingsButton.setBackgroundResource(R.drawable.bg_button_secondary);
        settingsButton.setPadding(24, 12, 24, 12);
        settingsButton.setOnClickListener(v -> showPasswordDialog());
        topBar.addView(settingsButton);

        root.addView(topBar);

        // Content ScrollView
        ScrollView scrollView = new ScrollView(this);
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(24, 24, 24, 24);

        // Global Search Bar
        searchEditText = new EditText(this);
        searchEditText.setHint(R.string.global_search_hint);
        searchEditText.setBackgroundResource(R.drawable.bg_edittext);
        searchEditText.setPadding(24, 24, 24, 24);
        searchEditText.setTextSize(16);
        contentLayout.addView(searchEditText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterVideos(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Approved Channels Horizontal Section Title
        TextView channelsTitle = new TextView(this);
        channelsTitle.setText(R.string.approved_channels);
        channelsTitle.setTextSize(16);
        channelsTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        channelsTitle.setTextColor(0xFF333333);
        LinearLayout.LayoutParams lblParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lblParams.setMargins(0, 32, 0, 16);
        contentLayout.addView(channelsTitle, lblParams);

        // Horizontal Channels Container
        ScrollView horizontalScroll = new ScrollView(this);
        horizontalScroll.setHorizontalScrollBarEnabled(true);
        channelsContainer = new LinearLayout(this);
        channelsContainer.setOrientation(LinearLayout.HORIZONTAL);
        horizontalScroll.addView(channelsContainer);
        contentLayout.addView(horizontalScroll, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Videos Section Title
        TextView videosTitle = new TextView(this);
        videosTitle.setText("סרטונים אחרונים מהערוצים המאושרים:");
        videosTitle.setTextSize(16);
        videosTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        videosTitle.setTextColor(0xFF333333);
        LinearLayout.LayoutParams vLblParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vLblParams.setMargins(0, 32, 0, 16);
        contentLayout.addView(videosTitle, vLblParams);

        videosContainer = new LinearLayout(this);
        videosContainer.setOrientation(LinearLayout.VERTICAL);
        contentLayout.addView(videosContainer);

        scrollView.addView(contentLayout);
        root.addView(scrollView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        setContentView(root);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadApprovedChannels();
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings_title);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        TextView tv = new TextView(this);
        tv.setText(R.string.password_prompt);
        layout.addView(tv);

        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        input.setBackgroundResource(R.drawable.bg_edittext);
        input.setPadding(24, 24, 24, 24);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);
        layout.addView(input, params);

        builder.setView(layout);

        builder.setPositiveButton(R.string.submit, (dialog, which) -> {
            String pass = input.getText().toString();
            if (pass.equals(PreferencesHelper.getPassword(this))) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else {
                Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void loadApprovedChannels() {
        approvedChannels = PreferencesHelper.getApprovedChannels(this);
        channelsContainer.removeAllViews();
        videosContainer.removeAllViews();
        allVideos.clear();

        if (approvedChannels.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText(R.string.no_approved_channels);
            emptyView.setTextColor(0xFF757575);
            emptyView.setTextSize(14);
            channelsContainer.addView(emptyView);
            return;
        }

        for (YouTubeItem channel : approvedChannels) {
            // Add Channel Chip / Card
            View channelCard = createChannelCard(channel);
            channelsContainer.addView(channelCard);

            // Fetch videos for each approved channel
            YouTubeApiService.getChannelVideos(channel.getId(), null, new YouTubeApiService.Callback<List<YouTubeItem>>() {
                @Override
                public void onSuccess(List<YouTubeItem> result) {
                    allVideos.addAll(result);
                    displayVideos(allVideos);
                }
                @Override
                public void onError(Exception e) {}
            });
        }
    }

    private View createChannelCard(YouTubeItem channel) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setPadding(16, 16, 16, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(240, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 16, 0);
        card.setLayoutParams(params);

        TextView name = new TextView(this);
        name.setText(channel.getTitle());
        name.setTextColor(0xFF212121);
        name.setTextSize(14);
        name.setTypeface(null, android.graphics.Typeface.BOLD);
        name.setMaxLines(2);
        card.addView(name);

        card.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChannelDetailActivity.class);
            intent.putExtra("channel_id", channel.getId());
            intent.putExtra("channel_title", channel.getTitle());
            startActivity(intent);
        });

        return card;
    }

    private void displayVideos(List<YouTubeItem> videos) {
        videosContainer.removeAllViews();
        for (YouTubeItem video : videos) {
            View videoCard = createVideoCard(video);
            videosContainer.addView(videoCard);
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

        TextView channelName = new TextView(this);
        channelName.setText(video.getChannelTitle());
        channelName.setTextColor(0xFF757575);
        channelName.setTextSize(14);
        LinearLayout.LayoutParams chParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chParams.setMargins(0, 8, 0, 16);
        card.addView(channelName, chParams);

        // Buttons Layout
        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);

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

    private void filterVideos(String query) {
        List<YouTubeItem> filtered = new ArrayList<>();
        for (YouTubeItem v : allVideos) {
            if (v.getTitle().toLowerCase().contains(query.toLowerCase()) || v.getChannelTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(v);
            }
        }
        displayVideos(filtered);
    }
}
