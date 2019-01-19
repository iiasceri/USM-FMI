package iascerinschi.fmi.usm.md.View.Schedule;

import android.os.Bundle;
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

import iascerinschi.fmi.usm.md.Model.Pojo;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ExamScheduleActivity;
import iascerinschi.fmi.usm.md.View.RecyclerViewAdapter;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

/* Fragment used as page 1 */
public class MondayFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monday, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(getActivity(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        mRecyclerViewItems.clear();
        addMenuItemsFromJson();

        return rootView;
    }


    private void addMenuItemsFromJson() {
        try {


            String jsonDataString = readJsonDataFromFile();

            JSONArray zile = new JSONArray(jsonDataString);
            JSONArray menuItemsJsonArray = new JSONArray();

            for (int i = 0; i < zile.length(); i++) {

                JSONObject zi = zile.getJSONObject(i);

                if (zi.get("numeZi").equals("Luni")) {
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
        } catch (IOException | JSONException exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.menu_item_weekly);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

}
