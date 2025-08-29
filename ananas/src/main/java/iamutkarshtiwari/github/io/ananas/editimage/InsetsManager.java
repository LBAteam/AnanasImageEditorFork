package iamutkarshtiwari.github.io.ananas.editimage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

public class InsetsManager
{
    public static void setViewBelowSystemBars(Activity activity, View view, WindowInsetsCompat insets)
    {
        setViewBelowSystemBars(activity, view, insets, false);
    }

    public static void setViewBelowSystemBars(Activity activity, View view, WindowInsetsCompat insets, boolean systemActionBarOverlay)
    {
        if (view != null && insets != null) {
            view.setPadding(
                    view.getPaddingLeft(),
                    getSystemBarsTop(activity, view, insets, systemActionBarOverlay),
                    view.getPaddingRight(),
                    view.getPaddingBottom()
            );
        }
    }

    public static void setViewBelowSystemBarsAndAboveNavigationBars(Activity activity, View view, WindowInsetsCompat insets)
    {

        setViewBelowSystemBarsAndAboveNavigationBars(activity, view, insets, false);
    }

    public static void setViewBelowSystemBarsAndAboveNavigationBars(Activity activity, View view, WindowInsetsCompat insets, boolean systemActionBarOverlay)
    {

        if (view != null && insets != null) {
            view.setPadding(
                    view.getPaddingLeft(),
                    getSystemBarsTop(activity, view, insets, systemActionBarOverlay),
                    view.getPaddingRight(),
                    getNavigationBarsBottom(activity, view, insets)
            );
        }
    }

    private static int getSystemBarsTop(Activity activity, View view, WindowInsetsCompat insets, boolean systemActionBarOverlay)
    {
        int systemBarsTop = -1;

        if (systemActionBarOverlay) {
            systemBarsTop = getStatusBarHeight(view.getContext());
        }

        if (systemBarsTop == -1) {
            return insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
        }

        return systemBarsTop;
    }

    private static int getNavigationBarsBottom(Activity activity, View view, WindowInsetsCompat insets)
    {
        Insets navigationBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars());

        return navigationBars.bottom;
    }

    public static int getStatusBarHeight(Context context) {
        int result = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void hideSystemBarAndNavigation(Window window)
    {
        final WindowInsetsController insetsController = window.getInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            insetsController.setSystemBarsBehavior(
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }
    }

}
