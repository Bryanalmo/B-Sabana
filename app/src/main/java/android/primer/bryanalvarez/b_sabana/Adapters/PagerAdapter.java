package android.primer.bryanalvarez.b_sabana.Adapters;

import android.primer.bryanalvarez.b_sabana.Fragments.BienestarFragment;
import android.primer.bryanalvarez.b_sabana.Fragments.SerSabanaFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nayar on 10/04/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new BienestarFragment();
            case 1:
                return new SerSabanaFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
