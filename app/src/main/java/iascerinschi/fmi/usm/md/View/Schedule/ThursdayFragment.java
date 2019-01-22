package iascerinschi.fmi.usm.md.View.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iascerinschi.fmi.usm.md.Model.Pojo;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ExamScheduleActivity;
import iascerinschi.fmi.usm.md.View.RecyclerViewAdapter;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

/* Fragment used as page 1 */
public class ThursdayFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RequestQueue mQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thursday, container, false);

        mQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        JSONObject jo = null;
        try {
            jo = new JSONObject(mPrefs.getString("User", ""));
            Log.i("user", mPrefs.getString("User", ""));

            if (!mPrefs.contains("Schedule"))
                jsonGetSchedule(jo.getString("groupName"), jo.getString("subGroup"));

            Log.i("groupName & subGroup", jo.getString("groupName") + jo.getString("subGroup"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(getContext(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        mRecyclerViewItems.clear();
        addMenuItemsFromJson();

        String jsonDataString = mPrefs.getString("ExamSchedule", "");


        return rootView;
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

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
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

    private void addMenuItemsFromJson() {
        try {


            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String jsonDataString = mPrefs.getString("Schedule", "");

            JSONArray zile = new JSONArray(jsonDataString);
            JSONArray menuItemsJsonArray = new JSONArray();

            for (int i = 0; i < zile.length(); i++) {

                JSONObject zi = zile.getJSONObject(i);

                if (zi.get("numeZi").equals("Joi")) {
                    menuItemsJsonArray = zi.getJSONArray("lectii");
                }
            }

            String paritate = Utilities.getParitate();

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String strParitate = menuItemObject.getString("paritate");

                if (strParitate.equals(paritate) || strParitate.equals("-")) {

                    String menuItemName = "(" + menuItemObject.getString("ora") + ")" + "  " +  menuItemObject.getString("disciplina");
                    String menuItemDescription = menuItemObject.getString("profesor");
                    String menuItemPrice = menuItemObject.getString("cabinet");
                    String menuItemCategory = menuItemObject.getString("tip");
                    String menuItemImageName = "menu_item_image";

                    Pojo pojo = new Pojo(menuItemName, menuItemDescription, menuItemPrice,
                            menuItemCategory, menuItemImageName);
                    mRecyclerViewItems.add(pojo);
                }
            }
        } catch (JSONException exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }


}
