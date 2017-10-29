package at.devfest.app.utils;

import android.os.Looper;

import at.devfest.app.BuildConfig;

/**
 * Preconditions inspired by
 * https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Preconditions.java
 */
public final class Preconditions {

    private Preconditions() {
        throw new UnsupportedOperationException();
    }

    public static void checkNotOnMainThread() {
        if (BuildConfig.DEBUG && isOnMainThread()) {
            throw new IllegalStateException("This method must not be called on the main thread");
        }
    }

    private static boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
