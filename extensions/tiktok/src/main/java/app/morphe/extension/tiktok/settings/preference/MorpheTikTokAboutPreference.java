/*
 * Forked from:
 * https://github.com/ReVanced/revanced-patches/blob/377d4e15016296b45d809697f7f69bce74badd3a/extensions/tiktok/src/main/java/app/revanced/extension/tiktok/settings/preference/ReVancedTikTokAboutPreference.java
 */

package app.morphe.extension.tiktok.settings.preference;

import android.content.Context;
import android.preference.Preference;
import android.view.View;

import app.morphe.extension.tiktok.Utils;

@SuppressWarnings("deprecation")
public class MorpheTikTokAboutPreference extends Preference {

    public MorpheTikTokAboutPreference(Context context) {
        super(context);

        setTitle("Support my work");
        setSummary("If you are using these patches, supporting the project helps me keep testing, improving, and maintaining them.");

        setOnPreferenceClickListener(pref -> {
            app.morphe.extension.shared.Utils.openLink("https://ko-fi.com/P5P5YOUU7");
            return true;
        });
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        Utils.setTitleAndSummaryColor(view);
    }
}

