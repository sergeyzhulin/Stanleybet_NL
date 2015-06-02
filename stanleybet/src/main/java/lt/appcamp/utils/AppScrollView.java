package lt.appcamp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Tadas on 7/2/13.
 */
public class AppScrollView extends ScrollView {
    /** tag for logs */
    protected static final String TAG = AppScrollView.class.getSimpleName();

    private float xDistance;
    private float yDistance;
    private float lastX;
    private float lastY;

    /** Gesture detector */
    protected GestureDetector mGestureDetector;
    protected View.OnTouchListener mGestureListener;

    public AppScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = event.getX();
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float curX = event.getX();
                final float curY = event.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance) return false;
                break;
        }

        return super.onInterceptTouchEvent(event);
    }
}
