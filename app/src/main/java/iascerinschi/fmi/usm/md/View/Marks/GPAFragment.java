package iascerinschi.fmi.usm.md.View.Marks;

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

import iascerinschi.fmi.usm.md.View.Marks.MarksRecyclerViewAdapter;
import iascerinschi.fmi.usm.md.Model.PojoMarks;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.ExamScheduleActivity;

/* Fragment used as page 1 */
public class GPAFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gpa, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new MarksRecyclerViewAdapter(getActivity(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        mRecyclerViewItems.clear();
        addMenuItemsFromJson();

        return rootView;
    }


    private void addMenuItemsFromJson() {
        try {


            String jsonDataString = readJsonDataFromFile();

            JSONArray semestre = new JSONArray(jsonDataString);
            JSONArray menuItemsJsonArray = new JSONArray();

            List<Float> mediiList = new ArrayList<>();

            for (int i = 0; i < semestre.length(); i++) {

                JSONObject semestru = semestre.getJSONObject(i);

                String  denSem = "Semestru " + semestru.get("idSemestru").toString();
                Float   mediaSem;
                Float   sumaSem = 0f;
                Float   counter = 0f;

                menuItemsJsonArray = semestru.getJSONArray("discipline");

                for (int j = 0; j < menuItemsJsonArray.length(); ++j) {

                    JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(j);

                    String nota = menuItemObject.getString("nota");
                    if (nota.equals("admis") || nota.equals("neadmis") || nota.equals("np")) {
                        System.out.println("its not number");
                    }
                    else {
                        sumaSem += Float.valueOf(nota);
                        counter++;
                    }
                }

                mediaSem = sumaSem / counter;
                mediiList.add(mediaSem);
                PojoMarks pojoMarks = new PojoMarks(denSem, mediaSem.toString());
                mRecyclerViewItems.add(pojoMarks);
            }

            Float mediaTotala = 0f;
            Float sumaMediilor = 0f;
            for (Object aMediiList : mediiList) sumaMediilor += (Float)aMediiList;
            mediaTotala = sumaMediilor / mediiList.size();
            PojoMarks pojoMarks = new PojoMarks("Media la toate semestrele", mediaTotala.toString());
            mRecyclerViewItems.add(pojoMarks);


        } catch (IOException | JSONException exception) {
            Log.e(ExamScheduleActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.menu_item_marks);
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
