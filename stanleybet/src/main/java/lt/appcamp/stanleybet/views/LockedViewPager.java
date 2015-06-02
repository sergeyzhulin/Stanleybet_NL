package lt.appcamp.stanleybet.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.w3c.dom.Attr;

/**
 * Created by cauac on 06/03/14.
 */
public class LockedViewPager extends ViewPager {
    private boolean lockSwipe = false;

    public LockedViewPager(Context context) {
        super(context);
    }

    public LockedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLock (boolean lockSwipe) {
        this.lockSwipe = lockSwipe;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.lockSwipe) {
            return false;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.lockSwipe) {
            return false;
        }

        return super.onInterceptTouchEvent(event);
    }

}
