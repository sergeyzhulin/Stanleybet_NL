package lt.appcamp.stanleybet.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lt.appcamp.stanleybet.fragments.LoginFragment;
import lt.appcamp.stanleybet.fragments.NewAccountFragment;

/**
 * Created by cauac on 05/03/14.
 */
public class NewAccountPagerAdapter extends FragmentStatePagerAdapter {
    public NewAccountPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new NewAccountFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(NewAccountFragment.ARG_OBJECT, i + 1);
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
            return "Create new account, step: " + position;
//        else
//            return "Betting Slip";
    }
}

