package omy.boston.mobilecommunications.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import omy.boston.mobilecommunications.fragments.CallFragment;
import omy.boston.mobilecommunications.fragments.EmailFragment;
import omy.boston.mobilecommunications.fragments.NetworkFragment;
import omy.boston.mobilecommunications.fragments.SmsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return CallFragment.newInstance(position + 1);
        }else if (position == 1){
            return SmsFragment.newInstance(position + 1);
        }else if (position == 2){
            return NetworkFragment.newInstance(position + 1);
        }else {
            return EmailFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Call";
            case 1:
                return "SMS";
            case 2:
                return "Network";
            case 3:
                return "Mail";
        }
        return null;
    }
}