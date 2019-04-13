package iascerinschi.fmi.usm.md.View.Schedule;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.Objects;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ToolbarActivity;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class ScheduleActivity extends ToolbarActivity {

    Toolbar toolbar;
    private RequestQueue mQueue;
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    int hour = calendar.get(Calendar.HOUR);

    CatLoadingView mView;


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

        /*
            Pe parcursul saptamanii arata orarul pentru ziua curenta
            Daca e dupa ora 18, orarul va fi aratat pentru ziua urmatoare

            Daca ne aflam Intre {Vineri:18} si {Duminica:21.59}
            va fi aratat orarul pe toata saptamana la general
        */
        if (!(day == 1 || day == 7)) {
            if (hour >= 18)
                day++;
        }

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (!mPrefs.contains("Schedule")) {
            JSONObject jo;
            try {
                jo = new JSONObject(mPrefs.getString("User", ""));
                mQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
                mQueue.start();
                mView = new CatLoadingView();
                mView.show(getSupportFragmentManager(), "");
                jsonGetSchedule(jo.getString("groupName"), jo.getString("subGroup"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
            // (fragments) to the ViewPager, which the ViewPager needs to display.
            mViewPager = findViewById(R.id.viewpager);
            mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

            // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
            // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
            // and when the ViewPager switches to a new page, the corresponding tab is selected)
            TabLayout tabLayout = findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(mViewPager);


            if (day == 1)
                day = 7;
            Objects.requireNonNull(tabLayout.getTabAt(day - 2)).select();
        }
        // Set the Toolbar as the activity's app bar (instead of the default ActionBar)

        //[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(Utilities.getParitateTitlu());
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

        MyPagerAdapter(FragmentManager fragmentManager) {
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

        String url = Utilities.getServerURL(getApplicationContext()) +
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

                                // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
                                // (fragments) to the ViewPager, which the ViewPager needs to display.
                                mViewPager = findViewById(R.id.viewpager);
                                mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

                                // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
                                // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
                                // and when the ViewPager switches to a new page, the corresponding tab is selected)
                                TabLayout tabLayout = findViewById(R.id.tab_layout);
                                tabLayout.setupWithViewPager(mViewPager);
                                if (day == 1)
                                    day = 7;
                                Objects.requireNonNull(tabLayout.getTabAt(day - 2)).select();
                                mView.dismiss();
                            }
                            else {
                                showAlert();
                            }

                        } catch (JSONException e) {
                            showAlert();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlert();
                error.printStackTrace();
            }

        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 100,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);

    }

    void showAlert() {
        mView.dismiss();
        AlertDialog alertDialog;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ScheduleActivity.this);
        builder.setMessage("La moment nu este orarul pentru grupa dvs");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
