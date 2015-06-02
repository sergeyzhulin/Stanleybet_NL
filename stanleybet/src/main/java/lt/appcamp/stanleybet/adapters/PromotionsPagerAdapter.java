package lt.appcamp.stanleybet.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lt.appcamp.stanleybet.fragments.LoginFragment;
import lt.appcamp.stanleybet.fragments.PromotionsFragment;

/**
 * Created by cauac on 05/03/14.
 */
public class PromotionsPagerAdapter extends FragmentStatePagerAdapter {
    public PromotionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PromotionsFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(PromotionsFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        if (position == 0)
            return "Promotions";
//        else
//            return "Betting Slip";
    }
}

