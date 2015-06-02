package lt.appcamp.stanleybet.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lt.appcamp.stanleybet.fragments.MainScreenFragment;

/**
 * Created by cauac on 05/03/14.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MainScreenFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(MainScreenFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Home";
        else
            return "Betting Slip";
    }
}

