package lt.appcamp.stanleybet.views;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by cauac on 05/03/14.
 */
public class FrameLayoutInterceptTouch extends FrameLayout {
    public FrameLayoutInterceptTouch(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
