package iascerinschi.fmi.usm.md.View.Marks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import iascerinschi.fmi.usm.md.View.Marks.MarksRecyclerViewAdapter;
import iascerinschi.fmi.usm.md.Model.PojoMarks;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ExamScheduleActivity;

/* Fragment used as page 1 */
public class S5Fragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_s5, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new MarksRecyclerViewAdapter(getContext(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        mRecyclerViewItems.clear();
        addMenuItemsFromJson();

        return rootView;
    }


    private void addMenuItemsFromJson() {
        try {


            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String jsonDataString = mPrefs.getString("Marks", "");

            JSONArray semestre = new JSONArray(jsonDataString);
            JSONArray menuItemsJsonArray = new JSONArray();

            for (int i = 0; i < semestre.length(); i++) {

                JSONObject semestru = semestre.getJSONObject(i);

                if ((Integer) semestru.get("idSemestru") == 5) {
                    menuItemsJsonArray = semestru.getJSONArray("discipline");
                }
            }

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String denumire = menuItemObject.getString("denumire").replaceFirst("redNota", "");
                String nota = " (" + menuItemObject.getString("nota").replaceFirst("redNota", "") + ")";

                PojoMarks pojoMarks = new PojoMarks(denumire, nota);
                mRecyclerViewItems.add(pojoMarks);
            }
        } catch (JSONException exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

}
