package app.morphe.extension.tiktok.settings.preference;

import static app.morphe.extension.shared.Utils.isDarkModeEnabled;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;

public final class SettingsUi {
    public static final @ColorInt int ACCENT = Color.rgb(254, 44, 85);
    public static final @ColorInt int SWITCH_ON = Color.rgb(38, 210, 224);

    public static final @ColorInt int DARK_BACKGROUND = Color.BLACK;
    public static final @ColorInt int DARK_SURFACE = Color.rgb(31, 31, 31);
    public static final @ColorInt int DARK_SURFACE_LIFTED = Color.rgb(43, 43, 43);
    public static final @ColorInt int DARK_SWITCH_OFF = Color.rgb(79, 79, 79);
    public static final @ColorInt int DARK_BORDER = Color.rgb(62, 62, 62);
    public static final @ColorInt int DARK_DIVIDER = Color.rgb(54, 54, 54);
    public static final @ColorInt int DARK_TEXT_PRIMARY = Color.WHITE;
    public static final @ColorInt int DARK_TEXT_SECONDARY = Color.rgb(166, 166, 166);
    public static final @ColorInt int DARK_TEXT_DISABLED = Color.rgb(110, 110, 110);

    public static final @ColorInt int LIGHT_BACKGROUND = Color.rgb(245, 245, 245);
    public static final @ColorInt int LIGHT_SURFACE = Color.WHITE;
    public static final @ColorInt int LIGHT_SURFACE_LIFTED = Color.WHITE;
    public static final @ColorInt int LIGHT_SWITCH_OFF = Color.rgb(232, 232, 232);
    public static final @ColorInt int LIGHT_BORDER = Color.rgb(238, 238, 238);
    public static final @ColorInt int LIGHT_DIVIDER = Color.rgb(234, 234, 234);
    public static final @ColorInt int LIGHT_TEXT_PRIMARY = Color.BLACK;
    public static final @ColorInt int LIGHT_TEXT_SECONDARY = Color.rgb(132, 132, 132);
    public static final @ColorInt int LIGHT_TEXT_DISABLED = Color.rgb(172, 172, 172);

    private SettingsUi() {
    }

    public static boolean isDarkMode() {
        return isDarkModeEnabled();
    }

    public static int dp(Context context, int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    public static @ColorInt int background() {
        return isDarkMode() ? DARK_BACKGROUND : LIGHT_BACKGROUND;
    }

    public static @ColorInt int surface() {
        return isDarkMode() ? DARK_SURFACE : LIGHT_SURFACE;
    }

    public static @ColorInt int liftedSurface() {
        return isDarkMode() ? DARK_SURFACE_LIFTED : LIGHT_SURFACE_LIFTED;
    }

    public static @ColorInt int border() {
        return isDarkMode() ? DARK_BORDER : LIGHT_BORDER;
    }

    public static @ColorInt int divider() {
        return isDarkMode() ? DARK_DIVIDER : LIGHT_DIVIDER;
    }

    public static @ColorInt int textPrimary() {
        return isDarkMode() ? DARK_TEXT_PRIMARY : LIGHT_TEXT_PRIMARY;
    }

    public static @ColorInt int textSecondary() {
        return isDarkMode() ? DARK_TEXT_SECONDARY : LIGHT_TEXT_SECONDARY;
    }

    public static @ColorInt int textDisabled() {
        return isDarkMode() ? DARK_TEXT_DISABLED : LIGHT_TEXT_DISABLED;
    }

    public static @ColorInt int switchOff() {
        return isDarkMode() ? DARK_SWITCH_OFF : LIGHT_SWITCH_OFF;
    }

    public static void styleWindow(Activity activity) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }

        window.setStatusBarColor(background());
        window.setNavigationBarColor(background());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int flags = decorView.getSystemUiVisibility();
            if (isDarkMode()) {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            } else {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            }
            decorView.setSystemUiVisibility(flags);
        }
    }

    public static void stylePreferenceScreen(ListView listView) {
        Context context = listView.getContext();
        listView.setBackgroundColor(background());
        listView.setCacheColorHint(background());
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listView.setDividerHeight(0);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setClipToPadding(false);
        listView.setVerticalScrollBarEnabled(false);
        listView.setPadding(dp(context, 12), dp(context, 8), dp(context, 12), dp(context, 28));
    }

    public static void styleTitleAndSummary(View view) {
        Context context = view.getContext();
        view.setBackground(roundedSurface(context, 14, false));
        view.setMinimumHeight(dp(context, 78));
        view.setPadding(dp(context, 22), dp(context, 14), dp(context, 18), dp(context, 14));

        TextView title = view.findViewById(android.R.id.title);
        if (title != null) {
            title.setTextColor(textPrimary());
            title.setTextSize(17);
            title.setTypeface(title.getTypeface(), Typeface.BOLD);
            title.setSingleLine(false);
        }

        TextView summary = view.findViewById(android.R.id.summary);
        if (summary != null) {
            summary.setTextColor(textSecondary());
            summary.setTextSize(14);
            summary.setSingleLine(false);
            summary.setLineSpacing(0, 1.06f);
        }

        styleCompoundButtons(view);
    }

    public static void styleCategory(View view) {
        Context context = view.getContext();
        view.setBackgroundColor(background());
        view.setPadding(dp(context, 22), dp(context, 24), dp(context, 22), dp(context, 8));

        TextView title = view.findViewById(android.R.id.title);
        if (title != null) {
            title.setTextColor(textSecondary());
            title.setTextSize(15);
            title.setTypeface(title.getTypeface(), Typeface.BOLD);
        }
    }

    public static TextView text(Context context, String value, float sizeSp, int color, int style) {
        TextView textView = new TextView(context);
        textView.setText(value);
        textView.setTextColor(color);
        textView.setTextSize(sizeSp);
        textView.setTypeface(textView.getTypeface(), style);
        textView.setIncludeFontPadding(true);
        return textView;
    }

    public static GradientDrawable roundedSurface(Context context, int radiusDp, boolean lifted) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(lifted ? liftedSurface() : surface());
        drawable.setCornerRadius(dp(context, radiusDp));
        return drawable;
    }

    public static GradientDrawable borderedSurface(Context context, int radiusDp, boolean lifted) {
        GradientDrawable drawable = roundedSurface(context, radiusDp, lifted);
        drawable.setStroke(Math.max(1, dp(context, 1)), border());
        return drawable;
    }

    public static GradientDrawable bottomSheetSurface(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(liftedSurface());
        float radius = dp(context, 18);
        drawable.setCornerRadii(new float[]{
                radius, radius,
                radius, radius,
                0, 0,
                0, 0
        });
        return drawable;
    }

    public static void styleBottomSheetContainer(LinearLayout layout) {
        Context context = layout.getContext();
        layout.setBackground(bottomSheetSurface(context));
        layout.setPadding(dp(context, 22), dp(context, 18), dp(context, 22), dp(context, 18));
    }

    public static void styleDialog(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = Gravity.BOTTOM;
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            attributes.dimAmount = isDarkMode() ? 0.62f : 0.42f;
            attributes.y = 0;
            window.setAttributes(attributes);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

            View decorView = window.getDecorView();
            if (decorView != null) {
                decorView.setBackgroundColor(Color.TRANSPARENT);
                decorView.setPadding(0, 0, 0, 0);
            }
        }

        if (dialog instanceof AlertDialog) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            View content = alertDialog.findViewById(android.R.id.content);
            if (content != null) {
                content.setBackgroundColor(Color.TRANSPARENT);
            }
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_POSITIVE), true);
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE), false);
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL), false);
        }
    }

    public static void styleFramedDialog(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (dialog instanceof AlertDialog) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            View content = alertDialog.findViewById(android.R.id.content);
            if (content != null) {
                content.setBackgroundColor(Color.TRANSPARENT);
            }
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_POSITIVE), true);
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE), false);
            styleActionButton(alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL), false);
        }

        styleDialog(dialog);
    }

    public static void styleActionButton(Button button, boolean primary) {
        if (button == null) {
            return;
        }
        button.setTextColor(primary ? ACCENT : textPrimary());
        button.setAllCaps(false);
        button.setTypeface(button.getTypeface(), primary ? Typeface.BOLD : Typeface.NORMAL);
        button.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void styleTextAction(TextView button, boolean primary) {
        button.setTextColor(primary ? ACCENT : textPrimary());
        button.setTypeface(button.getTypeface(), primary ? Typeface.BOLD : Typeface.NORMAL);
    }

    public static void styleEditText(EditText editText) {
        editText.setTextColor(textPrimary());
        editText.setHintTextColor(textSecondary());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackgroundTintList(ColorStateList.valueOf(ACCENT));
        }
    }

    public static void styleSwitch(CompoundButton button) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked, android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled},
                new int[]{}
        };

        if (button instanceof Switch) {
            Switch switchView = (Switch) button;
            int[] thumbColors = new int[]{Color.WHITE, textDisabled(), Color.WHITE};
            int[] trackColors = new int[]{SWITCH_ON, divider(), switchOff()};
            switchView.setThumbTintList(new ColorStateList(states, thumbColors));
            switchView.setTrackTintList(new ColorStateList(states, trackColors));
        } else {
            button.setButtonTintList(new ColorStateList(
                    states,
                    new int[]{ACCENT, textDisabled(), textSecondary()}
            ));
        }
    }

    public static void styleCheckBox(CompoundButton button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int[][] states = new int[][]{
                    new int[]{android.R.attr.state_checked, android.R.attr.state_enabled},
                    new int[]{-android.R.attr.state_enabled},
                    new int[]{}
            };
            int[] colors = new int[]{ACCENT, textDisabled(), textSecondary()};
            button.setButtonTintList(new ColorStateList(states, colors));
        }
    }

    public static Drawable backArrowDrawable(Context context) {
        return new BackArrowDrawable(textPrimary(), dp(context, 3));
    }

    private static void styleCompoundButtons(View view) {
        if (view instanceof CompoundButton) {
            styleSwitch((CompoundButton) view);
            return;
        }

        if (!(view instanceof ViewGroup)) {
            return;
        }

        ViewGroup group = (ViewGroup) view;
        for (int i = 0; i < group.getChildCount(); i++) {
            styleCompoundButtons(group.getChildAt(i));
        }
    }

    private static final class BackArrowDrawable extends Drawable {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        BackArrowDrawable(int color, int strokeWidth) {
            paint.setColor(color);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setStrokeJoin(Paint.Join.MITER);
        }

        @Override
        public void draw(Canvas canvas) {
            android.graphics.Rect bounds = getBounds();
            float centerX = bounds.centerX();
            float centerY = bounds.centerY();
            float size = Math.min(bounds.width(), bounds.height()) * 0.42f;
            float left = centerX - size * 0.45f;
            float right = centerX + size * 0.45f;
            canvas.drawLine(right, centerY - size * 0.62f, left, centerY, paint);
            canvas.drawLine(left, centerY, right, centerY + size * 0.62f, paint);
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            paint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
