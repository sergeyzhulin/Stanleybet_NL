package lt.appcamp.stanleybet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.UnsupportedEncodingException;

import lt.appcamp.stanleybet.activities.MainActivity;
import lt.appcamp.stanleybet.adapters.LiveMatchPagerAdapter;
import lt.appcamp.stanleybet.adapters.MainPagerAdapter;
import lt.appcamp.stanleybet.helper.ConWin;
import lt.appcamp.stanleybet.views.DraggableGridView;
import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.views.LockedViewPager;

/**
 * Created by cauac on 05/03/14.
 */
public class MainScreenFragment extends Fragment {
    public static final String ARG_OBJECT = "main_screen";
    private DraggableGridView mDGV;
    private LiveMatchPagerAdapter mLiveMatchPagerAdapter;
    private LockedViewPager mLiveMatchViewPager;
    private int[] sportsImages = {R.drawable.slide_new, R.drawable.slide_basketball, R.drawable.slide_football, R.drawable.slide_hockey, R.drawable.slide_tennis};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_sport, container, false);
        Bundle args = getArguments();
        mDGV = (DraggableGridView) rootView.findViewById(R.id.dgv);

        if (args.getInt(ARG_OBJECT) != 1) {
            ((FrameLayout) rootView.findViewById(R.id.dragger_layout)).setVisibility(View.GONE);
        } else if (args.getInt(ARG_OBJECT) == 1) {
            //Application main tabs swipe implementation
            mLiveMatchPagerAdapter = new LiveMatchPagerAdapter(getActivity().getSupportFragmentManager());
            mLiveMatchViewPager = (LockedViewPager) rootView.findViewById(R.id.live_pager);
            mLiveMatchViewPager.setAdapter(mLiveMatchPagerAdapter);

            ((FrameLayout) rootView.findViewById(R.id.live_matches)).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        ((MainActivity) getActivity()).stopSwipe(true);
                    }
                    else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                        ((MainActivity) getActivity()).stopSwipe(false);
                    }
                    return false;
                }
            });

            //Bind the title indicator to the adapter
            CirclePageIndicator circleIndicator = (CirclePageIndicator) rootView.findViewById(R.id.live_match_circle_indicator);
            circleIndicator.setViewPager(mLiveMatchViewPager);
        }

        SharedPreferences favSlideBar = getActivity().getSharedPreferences(ConWin.BUNDLE_FAVORITE_SLIDE_BAR, Context.MODE_PRIVATE);
        if (!favSlideBar.contains(ConWin.BUNDLE_FAVORITE_SLIDE_LIST) && (savedInstanceState == null || savedInstanceState.isEmpty())) {
            ImageView tv = new ImageView(getActivity());
            tv.setBackgroundResource(R.drawable.slide_new);
            tv.setTag(new Integer(0));
            mDGV.addView(tv);

            ImageView tv1 = new ImageView(getActivity());
            tv1.setBackgroundResource(R.drawable.slide_basketball);
            tv1.setTag(new Integer(1));
            mDGV.addView(tv1);

            ImageView tv2 = new ImageView(getActivity());
            tv2.setBackgroundResource(R.drawable.slide_football);
            tv2.setTag(new Integer(2));
            mDGV.addView(tv2);

            ImageView tv3 = new ImageView(getActivity());
            tv3.setBackgroundResource(R.drawable.slide_hockey);
            tv3.setTag(new Integer(3));
            mDGV.addView(tv3);

            ImageView tv4 = new ImageView(getActivity());
            tv4.setBackgroundResource(R.drawable.slide_tennis);
            tv4.setTag(new Integer(4));
            mDGV.addView(tv4);
        } else if (favSlideBar.contains(ConWin.BUNDLE_FAVORITE_SLIDE_LIST)) {
            String bytesString = favSlideBar.getString(ConWin.BUNDLE_FAVORITE_SLIDE_LIST, "");
            byte[] bytes = bytesString.getBytes();
            Parcel getBundle = Parcel.obtain();
            Bundle result;
            try {
                getBundle.unmarshall(bytes, 0, bytes.length);
                getBundle.setDataPosition(0);
                result = (Bundle) getBundle.readValue(Bundle.class.getClassLoader());

            } finally {
                getBundle.recycle();
                //p2.recycle();
            }


            int[] sports = result.getIntArray(ConWin.BUNDLE_FAVORITE_SLIDE_LIST);
            for (int i = 0; i < sports.length; i++) {
                ImageView tv = new ImageView(getActivity());
                tv.setBackgroundResource(sportsImages[sports[i]]);
                tv.setTag(new Integer(sports[i]));
                mDGV.addView(tv);
            }
        } else if (savedInstanceState != null && !savedInstanceState.isEmpty() && savedInstanceState.containsKey(ConWin.BUNDLE_FAVORITE_SLIDE_LIST)) {
            int[] sports = savedInstanceState.getIntArray(ConWin.BUNDLE_FAVORITE_SLIDE_LIST);
            for (int i = 0; i < sports.length; i++) {
                ImageView tv = new ImageView(getActivity());
                tv.setBackgroundResource(sportsImages[sports[i]]);
                tv.setTag(new Integer(sports[i]));
                mDGV.addView(tv);
            }
        }


        if (args.getInt(ARG_OBJECT) == 2) {
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_betslip, container, false);

        }

        //((TextView) rootView.findViewById(R.id.fragment_name)).setText(Integer.toString(args.getInt(ARG_OBJECT)));

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)  {
        outState.putIntArray(ConWin.BUNDLE_FAVORITE_SLIDE_LIST, getFavoriteSportsOrder());

        SharedPreferences favSlideBar = getActivity().getSharedPreferences(ConWin.BUNDLE_FAVORITE_SLIDE_BAR, Context.MODE_PRIVATE);
        Parcel storeBundle = Parcel.obtain();
        try {
            storeBundle.writeValue(outState);
            SharedPreferences.Editor edit = favSlideBar.edit();
            edit.putString(ConWin.BUNDLE_FAVORITE_SLIDE_LIST, new String(storeBundle.marshall(), "UTF-8"));
            edit.commit();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            storeBundle.recycle();
        }
    }

    @Override
    public void onDestroy() {
        Bundle outState = new Bundle();
        outState.putIntArray(ConWin.BUNDLE_FAVORITE_SLIDE_LIST, getFavoriteSportsOrder());

        SharedPreferences favSlideBar = getActivity().getSharedPreferences(ConWin.BUNDLE_FAVORITE_SLIDE_BAR, Context.MODE_PRIVATE);
        Parcel storeBundle = Parcel.obtain();
        try {
            storeBundle.writeValue(outState);
            SharedPreferences.Editor edit = favSlideBar.edit();
            edit.putString(ConWin.BUNDLE_FAVORITE_SLIDE_LIST, new String(storeBundle.marshall(), "UTF-8"));
            edit.commit();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            storeBundle.recycle();
        }

        super.onDestroy();
    }

    private int[] getFavoriteSportsOrder() {
        int[] sports = new int[mDGV.getChildCount()];
        int count = mDGV.getChildCount();
        for (int n = 0; n < count; n++ ) {
            if (n == count) return sports;
            ImageView img = (ImageView) mDGV.getChildAt(n);
            switch ((Integer) img.getTag()) {
                case 0:
                    sports[n] = 0;
                    break;
                case 1:
                    sports[n] = 1;
                    break;
                case 2:
                    sports[n] = 2;
                    break;
                case 3:
                    sports[n] = 3;
                    break;
                case 4:
                    sports[n] = 4;
                    break;
//                default:
//                    sports[n] = 0;
//                    break;
            }
        }

        return sports;
    }
}

