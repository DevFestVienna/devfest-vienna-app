package at.devfest.app.ui.speakers.details;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import at.devfest.app.R;

public class SpeakerDetailsLinkIcon extends AppCompatImageView {

    private final int defaultColor;
    private final int pressedColor;
    private Rect rect;

    public SpeakerDetailsLinkIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        pressedColor = ContextCompat.getColor(context, R.color.primary_text);
        defaultColor = ContextCompat.getColor(context, android.R.color.white);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPressedColor();
                break;
            case MotionEvent.ACTION_UP:
                setDefaultColor();
                break;
            case MotionEvent.ACTION_MOVE:
                // User moved outside bounds
                if (rect != null && !rect.contains(getLeft() + (int) event.getX(), getTop() + (int) event.getY())) {
                    setDefaultColor();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setDefaultColor() {
        setColorFilter(defaultColor);
        rect = null;
    }

    private void setPressedColor() {
        setColorFilter(pressedColor);
        rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
    }
}
