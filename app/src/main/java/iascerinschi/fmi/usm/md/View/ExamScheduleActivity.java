package iascerinschi.fmi.usm.md.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iascerinschi.fmi.usm.md.Model.Pojo;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class ExamScheduleActivity extends ToolbarActivity {

    private RecyclerView mRecyclerView;
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);

        mQueue = Volley.newRequestQueue(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JSONObject jo = null;
        try {
            jo = new JSONObject(mPrefs.getString("User", ""));
            Log.i("user", mPrefs.getString("User", ""));

            if (!mPrefs.contains("ExamSchedule"))
                jsonGetExamSchedule(jo.getString("groupName"), jo.getString("subGroup"));

            Log.i("groupName & subGroup", jo.getString("groupName") + jo.getString("subGroup"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        toolbarTitle.setText("Succese La Examene!");


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(this, mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);


        addMenuItemsFromJson();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMenuItemsFromJson() {
        try {

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String jsonDataString = mPrefs.getString("ExamSchedule", "");
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String menuItemName = "(" + menuItemObject.getString("ora") + ")  " +  menuItemObject.getString("ziExamen") + " " + menuItemObject.getString("dataExamen");
                String menuItemDescription = menuItemObject.getString("disciplina");
                String menuItemPrice =menuItemObject.getString("cabinet");
                String menuItemCategory = menuItemObject.getString("asistent");
                String menuItemImageName = "menu_item_image";

                Pojo pojo = new Pojo(menuItemName, menuItemDescription, menuItemPrice,
                        menuItemCategory, menuItemImageName);

                mRecyclerViewItems.add(pojo);
            }
        } catch (Exception exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }


    private void jsonGetExamSchedule(String groupName,
                                     String subGroup) {

        String url = Utilities.getServerURL() +
                "get_schedule?" +
                "groupName=" + groupName +
                "&subGroup=" + subGroup +
                "&scheduleType=exam";

        Log.i("URL", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();

                            if (response.has("orar")) {

                                Log.i("exam schedule", response.getString("orar"));

                                String json = response.getString("orar");

                                prefsEditor.putString("ExamSchedule", json);
                                prefsEditor.putString("ExamScheduleSuccess", "yes");
                                prefsEditor.apply();
                            }
                            else {
                                Log.e("error", "hmm");
                                prefsEditor.putString("ExamScheduleSuccess", "no");
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