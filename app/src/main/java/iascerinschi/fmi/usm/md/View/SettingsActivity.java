package iascerinschi.fmi.usm.md.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.marozzi.roundbutton.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Set;

import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class SettingsActivity extends ToolbarActivity {

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


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
        toolbarTitle.setText("Setari");

        MaterialTextField idnp = findViewById(R.id.IDMaterialTextFieldSettings);
        idnp.expand();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (mPrefs.contains("ID")) {
            String idnpString = mPrefs.getString("ID", "");

            String fragmentString = "";
            StringBuilder sb = new StringBuilder();
            sb.append(idnpString.charAt(0));
            sb.append(idnpString.charAt(1));
            sb.append(idnpString.charAt(2));
            sb.append("*");
            sb.append("*");
            sb.append("*");
            sb.append(idnpString.charAt(idnpString.length()-3));
            sb.append(idnpString.charAt(idnpString.length()-2));
            sb.append(idnpString.charAt(idnpString.length()-1));
            fragmentString = sb.toString();

            idnp.getEditText().setText(fragmentString);
        }
        else {
            AlertDialog alertDialog;
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setMessage("Pentru a continua introduceti IDNP dvs");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    recreate();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }



        final EditText idnpEditText = idnp.findViewById(R.id.IDEditTextSettings);

        idnpEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    idnpEditText.setText("");
                    MaterialTextField mtf = findViewById(R.id.IDMaterialTextFieldSettings);
                    mtf.setHasFocus(true);
                }

                return true; // return is important...
            }
        });

        RoundButton confirm = findViewById(R.id.confirmRoundButtonSettings);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTextField idnpTMP = findViewById(R.id.IDMaterialTextFieldSettings);
                String idnpStringToPrefs = idnpTMP.getEditText().getText().toString();

                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                prefsEditor.putString("ID", idnpStringToPrefs);
                prefsEditor.apply();

                if (!mPrefs.contains("Marks")) {
                    mQueue = Volley.newRequestQueue(getApplicationContext());
                    jsonGetMarks(mPrefs.getString("ID", ""));
                }
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
//            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void jsonGetMarks(String idnp) {

        String url = Utilities.getServerURL() +
                "get_marks?" +
                "id=" + idnp;

        Log.i("URL", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();

                            if (response.has("semestre")) {

                                Log.i("note", response.getString("semestre"));

                                String json = response.getString("semestre");

                                prefsEditor.putString("Marks", json);
                                prefsEditor.putString("MarksSuccess", "yes");
                                prefsEditor.apply();

                            }
                            else {
                                Log.e("error", "hmm");
                                prefsEditor.putString("MarksSuccess", "no");
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

        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);

    }
}
