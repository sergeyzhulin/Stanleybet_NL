package lt.appcamp.stanleybet.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lt.appcamp.stanleybet.fragments.LiveMatchFragment;
import lt.appcamp.stanleybet.fragments.MainScreenFragment;

/**
 * Created by cauac on 05/03/14.
 */
public class LiveMatchPagerAdapter extends FragmentStatePagerAdapter {
    public LiveMatchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new LiveMatchFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(LiveMatchFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        if (position == 0)
            return "Match: " + position;
//        else
//            return "Betting Slip";
    }
}

