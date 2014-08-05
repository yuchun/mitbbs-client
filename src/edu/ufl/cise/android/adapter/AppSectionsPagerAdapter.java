package edu.ufl.cise.android.adapter;

import edu.ufl.cise.android.fragment.ClubListFragment;
import edu.ufl.cise.android.fragment.HotBoardsFragment;
import edu.ufl.cise.android.fragment.HotClubsFragment;
import edu.ufl.cise.android.fragment.IndividualCenterFragment;
import edu.ufl.cise.android.fragment.SectionsFragment;
import edu.ufl.cise.android.fragment.TopArticlesFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
	private String[] titles = {"置顶文章","热门讨论区","热门俱乐部","分类讨论区","俱乐部","个人中心"};
    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new TopArticlesFragment();

            case 1:
            	return new HotBoardsFragment();
            case 2:
            	return new HotClubsFragment();
            case 3:
            	return new SectionsFragment();
            case 4:
            	return new ClubListFragment();
            case 5:
            	return new IndividualCenterFragment();
            default:
            	return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
