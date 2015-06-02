package lt.appcamp.stanleybet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.activities.MainActivity;
import lt.appcamp.stanleybet.views.DraggableGridView;

/**
 * Created by cauac on 05/03/14.
 */
public class LiveMatchFragment extends Fragment {
    public static final String ARG_OBJECT = "live_match";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.live_event, container, false);

        return rootView;
    }

}

