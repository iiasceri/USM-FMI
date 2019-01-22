package iascerinschi.fmi.usm.md.View.Schedule;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.MainActivity;
import iascerinschi.fmi.usm.md.View.ToolbarActivity;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class ScheduleActivity extends ToolbarActivity {

    Toolbar toolbar;
    private RequestQueue mQueue;

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Titles of the individual pages (displayed in tabs)
    private final String[] PAGE_TITLES = new String[] {
            "L",
            "M",
            "Mi",
            "J",
            "V",
            "-"
    };

    // The fragments that are used as the individual pages
    private final android.support.v4.app.Fragment[] PAGES = new android.support.v4.app.Fragment[] {
            new MondayFragment(),
            new TuesdayFragment(),
            new WednesdayFragment(),
            new ThursdayFragment(),
            new FridayFragment(),
            new WeekFragment()
    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));

        if (!mPrefs.contains("Schedule")) {
            JSONObject jo = null;
            try {
                jo = new JSONObject(mPrefs.getString("User", ""));
                Log.i("user", mPrefs.getString("User", ""));
                jsonGetSchedule(jo.getString("groupName"), jo.getString("subGroup"));
                Log.i("groupName & subGroup", jo.getString("groupName") + jo.getString("subGroup"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Set the Toolbar as the activity's app bar (instead of the default ActionBar)

        //[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(Utilities.getParitateTitlu());

        // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
        // (fragments) to the ViewPager, which the ViewPager needs to display.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if (day == 1)
            day = 7;
        tabLayout.getTabAt(day-2).select();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }

    private void jsonGetSchedule(String groupName,
                                 String subGroup) {

        String url = Utilities.getServerURL() +
                "get_schedule?" +
                "groupName=" + groupName +
                "&subGroup=" + subGroup +
                "&scheduleType=weekly";

        Log.i("URL", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();

                            if (response.has("orar")) {

                                Log.i("schedule", response.getString("orar"));

                                String json = response.getString("orar");

                                prefsEditor.putString("Schedule", json);
                                prefsEditor.putString("ScheduleSuccess", "yes");
                                prefsEditor.apply();

                            }
                            else {
                                Log.e("error", "hmm");
                                prefsEditor.putString("ScheduleSuccess", "no");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        mQueue.add(request);

    }
}
