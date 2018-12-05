package com.vaynefond.zhihudaily.ui.settings;

import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.text.format.Formatter;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpPreferenceFragment;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.di.ActivityScoped;

import javax.inject.Inject;

import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_AUTO_OFFLINE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_BIG_FONT_MODE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_CLEAR_CACHE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_COMMENT_SHARE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_FEEDBACK;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_NO_IMAGE_MODE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_PUSH;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_VERSION_CHECK;

@ActivityScoped
public class SettingsFragment extends BaseMvpPreferenceFragment<SettingsPresenter, SettingsContract.View>
        implements SettingsContract.View, Preference.OnPreferenceClickListener {
    private CheckBoxPreference mAutoOfflinePref;
    private CheckBoxPreference mNoImageModePref;
    private CheckBoxPreference mBigFontPref;
    private CheckBoxPreference mPushPref;
    private CheckBoxPreference mCommentSharePref;

    private Preference mClearCachePref;
    private Preference mVersionCheckPref;
    private Preference mFeedbackPref;

    @Inject
    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_preference);
        initPreference();
    }

    private void initPreference() {
        mAutoOfflinePref = (CheckBoxPreference) findPreference(PREF_KEY_AUTO_OFFLINE);
        mAutoOfflinePref.setOnPreferenceClickListener(this);

        mNoImageModePref = (CheckBoxPreference) findPreference(PREF_KEY_NO_IMAGE_MODE);
        mNoImageModePref.setOnPreferenceClickListener(this);

        mBigFontPref = (CheckBoxPreference) findPreference(PREF_KEY_BIG_FONT_MODE);
        mBigFontPref.setOnPreferenceClickListener(this);

        mPushPref = (CheckBoxPreference) findPreference(PREF_KEY_PUSH);
        mPushPref.setOnPreferenceClickListener(this);

        mCommentSharePref = (CheckBoxPreference) findPreference(PREF_KEY_COMMENT_SHARE);
        mCommentSharePref.setOnPreferenceClickListener(this);

        mClearCachePref = findPreference(PREF_KEY_CLEAR_CACHE);
        mClearCachePref.setOnPreferenceClickListener(this);

        mVersionCheckPref = findPreference(PREF_KEY_VERSION_CHECK);
        mVersionCheckPref.setOnPreferenceClickListener(this);

        mFeedbackPref = findPreference(PREF_KEY_FEEDBACK);
        mFeedbackPref.setOnPreferenceClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        mAutoOfflinePref.setChecked(mPresenter.loadAutoOfflinePref());
        mNoImageModePref.setChecked(mPresenter.loadNoImageModePref());
        mBigFontPref.setChecked(mPresenter.loadBigFontPref());
        mPushPref.setChecked(mPresenter.loadPushPref());
        mCommentSharePref.setChecked(mPresenter.loadCommentSharePref());
        mVersionCheckPref.setSummary(getString(R.string.app_version_summary, mPresenter.loadVersionNumber()));
        mPresenter.loadCacheSize();
    }

    @Override
    public void updateCacheSummary(long cacheSize) {
        mClearCachePref.setSummary(Formatter.formatFileSize(requireContext(), cacheSize));
    }

    @Override
    public void notifyCacheCleared(boolean succeed) {
        AppToast.showLongText(succeed ? R.string.clear_cache_success : R.string.clear_cache_failed);
    }

    @Override
    public void notifyVersionChecked() {
        AppToast.showLongText(R.string.app_version_latest);
    }

    @Override
    public void notifyFeedback() {
        AppToast.showLongText(R.string.pref_feedback);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case PREF_KEY_AUTO_OFFLINE:
                mPresenter.setAutoOfflinePref(((CheckBoxPreference) preference).isChecked());
                return true;
            case PREF_KEY_NO_IMAGE_MODE:
                mPresenter.setNoImageModePref(((CheckBoxPreference) preference).isChecked());
                return true;
            case PREF_KEY_BIG_FONT_MODE:
                mPresenter.setBigFontPref(((CheckBoxPreference) preference).isChecked());
                return true;
            case PREF_KEY_PUSH:
                mPresenter.setPushPref(((CheckBoxPreference) preference).isChecked());
                return true;
            case PREF_KEY_COMMENT_SHARE:
                mPresenter.setCommentSharePref(((CheckBoxPreference) preference).isChecked());
                return true;
            case PREF_KEY_CLEAR_CACHE:
                mPresenter.clearCache();
                return true;
            case PREF_KEY_VERSION_CHECK:
                mPresenter.checkVersionUpdate();
                return true;
            case PREF_KEY_FEEDBACK:
                mPresenter.feedback();
                return true;
        }

        return false;
    }
}
