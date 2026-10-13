package com.generated.app;

import android.app.Activity;
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
import android.widget.Toast;
import java.util.List;

public class SettingsActivity extends Activity {

    private EditText searchChannelInput;
    private LinearLayout searchResultsContainer;
    private LinearLayout approvedChannelsContainer;
    private List<YouTubeItem> approvedChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);
        root.setBackgroundColor(0xFFFAFAFA);

        TextView title = new TextView(this);
        title.setText(R.string.settings_title);
        title.setTextSize(22);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setTextColor(0xFF1976D2);
        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tParams.setMargins(0, 0, 0, 24);
        root.addView(title, tParams);

        // Password Change Section
        TextView pwTitle = new TextView(this);
        pwTitle.setText("שינוי סיסמת הגדרות:");
        pwTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(pwTitle);

        LinearLayout pwLayout = new LinearLayout(this);
        pwLayout.setOrientation(LinearLayout.HORIZONTAL);
        final EditText pwInput = new EditText(this);
        pwInput.setHint(R.string.new_password_hint);
        pwInput.setBackgroundResource(R.drawable.bg_edittext);
        pwInput.setPadding(24, 20, 24, 20);
        LinearLayout.LayoutParams pParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        pwLayout.addView(pwInput, pParams);

        Button pwBtn = new Button(this);
        pwBtn.setText(R.string.save);
        pwBtn.setTextColor(0xFFFFFFFF);
        pwBtn.setBackgroundResource(R.drawable.bg_button);
        pwBtn.setPadding(24, 12, 24, 12);
        pwBtn.setOnClickListener(v -> {
            String newPw = pwInput.getText().toString();
            if (!newPw.isEmpty()) {
                PreferencesHelper.setPassword(this, newPw);
                Toast.makeText(this, R.string.password_changed, Toast.LENGTH_SHORT).show();
                pwInput.setText("");
            }
        });
        LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bParams.setMargins(16, 0, 0, 0);
        pwLayout.addView(pwBtn, bParams);

        LinearLayout.LayoutParams pwLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pwLayoutParam.setMargins(0, 8, 0, 24);
        root.addView(pwLayout, pwLayoutParam);

        // Download Settings Section
        TextView dlTitle = new TextView(this);
        dlTitle.setText("הגדרות הורדה (איכות ותיקייה):");
        dlTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(dlTitle);

        LinearLayout dlLayout = new LinearLayout(this);
        dlLayout.setOrientation(LinearLayout.VERTICAL);
        dlLayout.setBackgroundResource(R.drawable.bg_card);
        dlLayout.setPadding(16, 16, 16, 16);

        TextView audioLbl = new TextView(this);
        audioLbl.setText(R.string.audio_quality);
        dlLayout.addView(audioLbl);
        final EditText audioQualityInput = new EditText(this);
        audioQualityInput.setText(PreferencesHelper.getAudioQuality(this));
        audioQualityInput.setBackgroundResource(R.drawable.bg_edittext);
        audioQualityInput.setPadding(16, 16, 16, 16);
        dlLayout.addView(audioQualityInput);

        TextView videoLbl = new TextView(this);
        videoLbl.setText(R.string.video_quality);
        LinearLayout.LayoutParams vlParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vlParams.setMargins(0, 16, 0, 0);
        dlLayout.addView(videoLbl, vlParams);
        final EditText videoQualityInput = new EditText(this);
        videoQualityInput.setText(PreferencesHelper.getVideoQuality(this));
        videoQualityInput.setBackgroundResource(R.drawable.bg_edittext);
        videoQualityInput.setPadding(16, 16, 16, 16);
        dlLayout.addView(videoQualityInput);

        TextView pathLbl = new TextView(this);
        pathLbl.setText(R.string.download_folder);
        LinearLayout.LayoutParams plParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plParams.setMargins(0, 16, 0, 0);
        dlLayout.addView(pathLbl, plParams);
        final EditText pathInput = new EditText(this);
        pathInput.setText(PreferencesHelper.getDownloadPath(this));
        pathInput.setBackgroundResource(R.drawable.bg_edittext);
        pathInput.setPadding(16, 16, 16, 16);
        dlLayout.addView(pathInput);

        Button saveDlBtn = new Button(this);
        saveDlBtn.setText("שמור הגדרות הורדה");
        saveDlBtn.setTextColor(0xFFFFFFFF);
        saveDlBtn.setBackgroundResource(R.drawable.bg_button);
        saveDlBtn.setPadding(24, 16, 24, 16);
        LinearLayout.LayoutParams sdlParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sdlParams.setMargins(0, 16, 0, 0);
        saveDlBtn.setOnClickListener(v -> {
            PreferencesHelper.setAudioQuality(this, audioQualityInput.getText().toString());
            PreferencesHelper.setVideoQuality(this, videoQualityInput.getText().toString());
            PreferencesHelper.setDownloadPath(this, pathInput.getText().toString());
            Toast.makeText(this, "הגדרות ההורדה נשמרו בהצלחה", Toast.LENGTH_SHORT).show();
        });
        dlLayout.addView(saveDlBtn, sdlParams);

        LinearLayout.LayoutParams dlCardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dlCardParams.setMargins(0, 8, 0, 24);
        root.addView(dlLayout, dlCardParams);

        // Channel Management Section
        TextView chMgmtTitle = new TextView(this);
        chMgmtTitle.setText("ניהול ערוצים מורשים לפי שם:");
        chMgmtTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(chMgmtTitle);

        searchChannelInput = new EditText(this);
        searchChannelInput.setHint(R.string.search_channels_hint);
        searchChannelInput.setBackgroundResource(R.drawable.bg_edittext);
        searchChannelInput.setPadding(24, 20, 24, 20);
        LinearLayout.LayoutParams sciParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sciParams.setMargins(0, 8, 0, 16);
        root.addView(searchChannelInput, sciParams);

        searchResultsContainer = new LinearLayout(this);
        searchResultsContainer.setOrientation(LinearLayout.VERTICAL);
        root.addView(searchResultsContainer);

        TextView approvedLbl = new TextView(this);
        approvedLbl.setText(R.string.approved_channels);
        approvedLbl.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams alParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alParams.setMargins(0, 24, 0, 16);
        root.addView(approvedLbl, alParams);

        approvedChannelsContainer = new LinearLayout(this);
        approvedChannelsContainer.setOrientation(LinearLayout.VERTICAL);
        root.addView(approvedChannelsContainer);

        searchChannelInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    searchYouTubeChannels(s.toString());
                } else {
                    searchResultsContainer.removeAllViews();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        scrollView.addView(root);
        setContentView(scrollView);
        loadApprovedChannelsList();
    }

    private void searchYouTubeChannels(String query) {
        YouTubeApiService.searchChannels(query, new YouTubeApiService.Callback<List<YouTubeItem>>() {
            @Override
            public void onSuccess(List<YouTubeItem> result) {
                searchResultsContainer.removeAllViews();
                for (YouTubeItem channel : result) {
                    View view = createSearchResultItem(channel);
                    searchResultsContainer.addView(view);
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(SettingsActivity.this, "שגיאה בחיפוש ערוצים", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View createSearchResultItem(YouTubeItem channel) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setBackgroundResource(R.drawable.bg_card);
        itemLayout.setPadding(16, 16, 16, 16);
        itemLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 12);
        itemLayout.setLayoutParams(params);

        TextView title = new TextView(this);
        title.setText(channel.getTitle());
        title.setTextColor(0xFF212121);
        title.setTextSize(15);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        itemLayout.addView(title, tParams);

        Button addBtn = new Button(this);
        addBtn.setText("אשר ערוץ");
        addBtn.setTextColor(0xFFFFFFFF);
        addBtn.setBackgroundResource(R.drawable.bg_button);
        addBtn.setPadding(24, 12, 24, 12);
        addBtn.setOnClickListener(v -> {
            approveChannel(channel);
        });
        itemLayout.addView(addBtn);

        return itemLayout;
    }

    private void approveChannel(YouTubeItem channel) {
        approvedChannels = PreferencesHelper.getApprovedChannels(this);
        for (YouTubeItem ch : approvedChannels) {
            if (ch.getId().equals(channel.getId())) {
                Toast.Already = Toast.makeText(this, "הערוץ כבר מאושר", Toast.LENGTH_SHORT);
                Toast.Already.show();
                return;
            }
        }
        approvedChannels.add(channel);
        PreferencesHelper.saveApprovedChannels(this, approvedChannels);
        Toast.makeText(this, "הערוץ אושר בהצלחה!", Toast.LENGTH_SHORT).show();
        loadApprovedChannelsList();
        searchResultsContainer.removeAllViews();
        searchChannelInput.setText("");
    }

    private void loadApprovedChannelsList() {
        approvedChannelsContainer.removeAllViews();
        approvedChannels = PreferencesHelper.getApprovedChannels(this);
        for (YouTubeItem channel : approvedChannels) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setBackgroundResource(R.drawable.bg_card);
            itemLayout.setPadding(16, 16, 16, 16);
            itemLayout.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 12);
            itemLayout.setLayoutParams(params);

            TextView title = new TextView(this);
            title.setText(channel.getTitle());
            title.setTextColor(0xFF212121);
            title.setTextSize(15);
            title.setTypeface(null, android.graphics.Typeface.BOLD);
            LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            itemLayout.addView(title, tParams);

            Button removeBtn = new Button(this);
            removeBtn.setText("הסר אישור");
            removeBtn.setTextColor(0xFFFFFFFF);
            removeBtn.setBackgroundResource(R.drawable.bg_button_secondary);
            removeBtn.setPadding(24, 12, 24, 12);
            removeBtn.setOnClickListener(v -> {
                approvedChannels.remove(channel);
                PreferencesHelper.saveApprovedChannels(this, approvedChannels);
                loadApprovedChannelsList();
            });
            itemLayout.addView(removeBtn);

            approvedChannelsContainer.addView(itemLayout);
        }
    }
}
