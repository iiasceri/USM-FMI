package iascerinschi.fmi.usm.md.View.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import iascerinschi.fmi.usm.md.Model.Pojo;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ExamScheduleActivity;

/* Fragment used as page 1 */
public class WeekFragment extends android.support.v4.app.Fragment {

    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapterSchedule(getContext(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        mRecyclerViewItems.clear();
        addMenuItemsFromJson();

        return rootView;
    }

    private void addMenuItemsFromJson() {
        try {

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String jsonDataString = mPrefs.getString("Schedule", "");

            JSONArray zile = new JSONArray(jsonDataString);
            JSONArray menuItemsJsonArray;

            for (int i = 0; i < zile.length(); ++i) {

                JSONObject zi = zile.getJSONObject(i);
                menuItemsJsonArray = zi.getJSONArray("lectii");

                String ziTitle = zi.getString("numeZi");
                for (int j = 0; j < menuItemsJsonArray.length(); ++j) {

                    JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(j);

                    String menuItemDescription = menuItemObject.getString("disciplina") + " | paritate:  " + menuItemObject.getString("paritate");
                    String menuItemPrice = "(" + menuItemObject.getString("ora") + ")" + "  " + menuItemObject.getString("cabinet");
                    String menuItemCategory = menuItemObject.getString("tip") + "   " + menuItemObject.getString("profesor");
                    String menuItemImageName = "menu_item_image";

                    Pojo pojo = new Pojo(ziTitle, menuItemDescription, menuItemPrice,
                            menuItemCategory, menuItemImageName);
                    mRecyclerViewItems.add(pojo);
                }
            }

        } catch (JSONException exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }
}
