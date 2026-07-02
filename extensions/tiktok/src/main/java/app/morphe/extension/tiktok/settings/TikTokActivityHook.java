/*
 * Forked from:
 * https://github.com/ReVanced/revanced-patches/blob/377d4e15016296b45d809697f7f69bce74badd3a/extensions/tiktok/src/main/java/app/revanced/extension/tiktok/settings/TikTokActivityHook.java
 */

package app.morphe.extension.tiktok.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.morphe.extension.shared.Logger;
import app.morphe.extension.shared.Utils;
import app.morphe.extension.tiktok.settings.preference.SettingsUi;
import app.morphe.extension.tiktok.settings.preference.TikTokPreferenceFragment;

import com.bytedance.ies.ugc.aweme.commercialize.compliance.personalization.AdPersonalizationActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Hooks AdPersonalizationActivity to inject a custom {@link TikTokPreferenceFragment}.
 */
@SuppressWarnings({"deprecation", "NewApi", "unused"})
public class TikTokActivityHook {
    private static final String SETTINGS_ACTION = "morphe_settings";
    private static final String SETTINGS_EXTRA = "morphe";

    public static Object createSettingsEntry(String entryClazzName, String entryInfoClazzName) {
        try {
            Class entryClazz = Class.forName(entryClazzName);
            Class entryInfoClazz = Class.forName(entryInfoClazzName);
            Constructor entryConstructor = entryClazz.getConstructor(entryInfoClazz);
            Constructor entryInfoConstructor = entryInfoClazz.getDeclaredConstructors()[0];
            Object buttonInfo = entryInfoConstructor.newInstance(
                    "Morphe settings", null, (View.OnClickListener) view -> startSettingsActivity(), "morphe");
            return entryConstructor.newInstance(buttonInfo);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Initialize the settings menu.
     * @param base The activity to initialize the settings menu on.
     * @return Whether the settings menu should be initialized.
     */
    public static boolean initialize(AdPersonalizationActivity base) {
        Intent intent = base.getIntent();
        Bundle extras = intent.getExtras();
        if ((extras == null || !extras.getBoolean(SETTINGS_EXTRA, false)) && !SETTINGS_ACTION.equals(intent.getAction())) {
            return false;
        }

        SettingsStatus.load();
        Utils.setIsDarkModeEnabled(isDarkModeEnabled(base));
        SettingsUi.styleWindow(base);

        LinearLayout linearLayout = new LinearLayout(base);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(SettingsUi.background());
        linearLayout.setFitsSystemWindows(true);
        linearLayout.setTransitionGroup(true);

        FrameLayout toolbar = new FrameLayout(base);
        toolbar.setBackgroundColor(SettingsUi.background());
        toolbar.setPadding(0, SettingsUi.dp(base, 4), 0, 0);
        linearLayout.addView(toolbar, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                SettingsUi.dp(base, 72)
        ));

        ImageButton backButton = new ImageButton(base);
        backButton.setImageDrawable(SettingsUi.backArrowDrawable(base));
        backButton.setBackgroundColor(Color.TRANSPARENT);
        backButton.setPadding(SettingsUi.dp(base, 18), SettingsUi.dp(base, 16),
                SettingsUi.dp(base, 18), SettingsUi.dp(base, 16));
        backButton.setOnClickListener(view -> base.finish());
        FrameLayout.LayoutParams backParams = new FrameLayout.LayoutParams(
                SettingsUi.dp(base, 58),
                SettingsUi.dp(base, 58),
                Gravity.START | Gravity.CENTER_VERTICAL
        );
        backParams.setMargins(SettingsUi.dp(base, 3), 0, 0, 0);
        toolbar.addView(backButton, backParams);

        TextView title = SettingsUi.text(
                base,
                "Morphe settings",
                18,
                SettingsUi.textPrimary(),
                Typeface.BOLD
        );
        title.setGravity(Gravity.CENTER);
        toolbar.addView(title, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        FrameLayout fragment = new FrameLayout(base);
        fragment.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1));
        fragment.setBackgroundColor(SettingsUi.background());
        int fragmentId = View.generateViewId();
        fragment.setId(fragmentId);

        linearLayout.addView(fragment);
        base.setContentView(linearLayout);

        PreferenceFragment preferenceFragment = new TikTokPreferenceFragment();
        base.getFragmentManager().beginTransaction().replace(fragmentId, preferenceFragment).commit();

        return true;
    }

    private static void startSettingsActivity() {
        Context appContext = Utils.getContext();
        if (appContext != null) {
            Intent intent = new Intent(appContext, AdPersonalizationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(SETTINGS_ACTION);
            intent.putExtra(SETTINGS_EXTRA, true);
            appContext.startActivity(intent);
        } else {
            Logger.printDebug(() -> "Utils.getContext() return null");
        }
    }

    private static boolean isDarkModeEnabled(Context context) {
        final int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}

