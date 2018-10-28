package com.example.vishnuk.pick_a_bite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.vishnuk.pick_a_bite.TabActivities.Arabic;
import com.example.vishnuk.pick_a_bite.TabActivities.Chinese_delights;
import com.example.vishnuk.pick_a_bite.TabActivities.Kababs;
import com.example.vishnuk.pick_a_bite.TabActivities.Non_veg_appetizers;
import com.example.vishnuk.pick_a_bite.TabActivities.Non_veg_gravy;
import com.example.vishnuk.pick_a_bite.TabActivities.Rice;
import com.example.vishnuk.pick_a_bite.TabActivities.Rice_n_noodles;
import com.example.vishnuk.pick_a_bite.TabActivities.Salads;
import com.example.vishnuk.pick_a_bite.TabActivities.Starters;
import com.example.vishnuk.pick_a_bite.TabActivities.Veg_Gravy;


public class TabbedActivity extends AppCompatActivity {

     public static int mNotificationCounter;
     public int counter = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(android.R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences badge_count;
        badge_count = getSharedPreferences("Saving badge count", Context.MODE_PRIVATE);
        //set the sharedpref
        SharedPreferences.Editor editor = badge_count.edit();
        editor.putInt("counter", counter);
        editor.apply();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences badge_count;
        badge_count = getSharedPreferences("Saving badge count", Context.MODE_PRIVATE);
        //get the sharepref
        counter = badge_count.getInt("counter", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        mNotificationCounter = counter;
        // Create a condition (hide it if the count < 0)
        if (mNotificationCounter >= 0) {
            BadgeCounters.update(this,
                    menu.findItem(R.id.cart_badge_in_tabview),
                    R.drawable.ic_shopping_cart_36dp,
                    BadgeCounters.BadgeColor.BLUE,
                    counter);
        } else {
            BadgeCounters.hide(menu.findItem(R.id.cart_badge_in_tabview));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            case R.id.cart_badge_in_tabview:
                counter++;
                mNotificationCounter = counter;
                BadgeCounters.update(item, counter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_arabic, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position) {
                case 0:
                    fragment = new Arabic();
                    break;
                case 1:
                    fragment = new Kababs();
                    break;
                case 2:
                    fragment = new Rice();
                    break;
                case 3:
                    fragment = new Veg_Gravy();
                    break;
                case 4:
                    fragment = new Non_veg_gravy();
                    break;
                case 5:
                    fragment = new Starters();
                    break;
                case 6:
                    fragment = new Salads();
                    break;
                case 7:
                    fragment = new Non_veg_appetizers();
                    break;
                case 8:
                    fragment = new Chinese_delights();
                    break;
                case 9:
                    fragment = new Rice_n_noodles();
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 10 total pages.
            return 10;
        }
    }
}
