package at.devfest.app.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.ViewTreeObserver;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public final class Views {

    private Views() {
        throw new UnsupportedOperationException();
    }

    @TargetApi(JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (App.isCompatible(JELLY_BEAN)) {
            viewTreeObserver.removeOnGlobalLayoutListener(listener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(listener);
        }
    }

    public static int getActionBarSize(Context context) {
        if (context == null) {
            return 0;
        }

        Resources.Theme curTheme = context.getTheme();
        if (curTheme == null) {
            return 0;
        }

        int[] attrs = {android.R.attr.actionBarSize};
        TypedArray att = curTheme.obtainStyledAttributes(attrs);
        if (att == null) {
            return 0;
        }

        float size = att.getDimension(0, 0);
        att.recycle();
        return (int) size;
    }
}
