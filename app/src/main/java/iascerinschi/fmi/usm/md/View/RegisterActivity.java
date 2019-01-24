package iascerinschi.fmi.usm.md.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.marozzi.roundbutton.RoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class RegisterActivity extends AppCompatActivity {

    RadioGroup rg;
    List<String> groupNames = new LinkedList<>();
    MaterialTextField usernameMaterialTextField;
    MaterialTextField mailMaterialTextField;
    MaterialTextField familynameMaterialTextField;
    MaterialTextField passwordMaterialTextField;
    MaterialTextField confirmPasswordMaterialTextField;
    String gender = "";
    String groupName = "";
    String subGroup = "";

    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usernameMaterialTextField = findViewById(R.id.usernameMaterialTextFieldRegister);
        mailMaterialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        familynameMaterialTextField = findViewById(R.id.familynameMaterialTextFieldRegister);
        passwordMaterialTextField = findViewById(R.id.passwordMaterialTextFieldRegister);
        confirmPasswordMaterialTextField = findViewById(R.id.confirmPasswordMaterialTextFieldRegister);

        EditText userNameEditText = usernameMaterialTextField.getEditText();
        EditText mailEditText = mailMaterialTextField.getEditText();
        EditText familyNameEditText = familynameMaterialTextField.getEditText();
        EditText passwordEditText = passwordMaterialTextField.getEditText();
        EditText confirmPasswordEditText = confirmPasswordMaterialTextField.getEditText();

        userNameEditText.setText("");
        mailEditText.setText("");
        familyNameEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");

        confirmPasswordMaterialTextField.expand();
        passwordMaterialTextField.expand();
        familynameMaterialTextField.expand();
        mailMaterialTextField.expand();
        usernameMaterialTextField.expand();


        EditText e3 = confirmPasswordMaterialTextField.findViewById(R.id.confirmPasswordEditTextRegister);

        e3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollView scrollLayout = findViewById(R.id.scrollView);
                    View lastChild = scrollLayout.getChildAt(scrollLayout.getChildCount() - 1);
                    int bottom = lastChild.getBottom() + scrollLayout.getPaddingBottom();
                    int sy = scrollLayout.getScrollY();
                    int sh = scrollLayout.getHeight();
                    int delta = bottom - (sy + sh);

                    scrollLayout.smoothScrollBy(0, delta);
                }
            }
        });

        rg = findViewById(R.id.genderRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonMale:
//                        Toast.makeText(getApplicationContext(), "Ai ales barbat", Toast.LENGTH_SHORT).show();
                        gender = "male";
                        break;
                    case R.id.radioButtonFemale:
//                        Toast.makeText(getApplicationContext(), "Ai ales femeie", Toast.LENGTH_SHORT).show();
                        gender = "female";
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Alegeti genul", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);


        jsonParseGroupNames();
        // Start the queue
        mRequestQueue.start();

        /*
             Wait until groupNames will be parsed by jsonParseGroupNames()
         */
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                subGroup = "I";
                if (groupNames != null) {
                    groupName = groupNames.get(0);
                }
                else {
                    groupName = "IA1602rom";
                }

                MaterialSpinner groupMaterialSpinner = findViewById(R.id.groupSpinner);
                groupMaterialSpinner.setItems(groupNames);
                groupMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        groupName = item;
                    }
                });
            }
        }, 1000);

        MaterialSpinner subGroupMaterialSpinner = findViewById(R.id.subGroupSpinner);
        subGroupMaterialSpinner.setItems("I (Securitate)", "II (Design)");
        subGroupMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                switch (item) {
                    case "I (Securitate)":
                        subGroup = "I";
                        break;
                    case "II (Design)":
                        subGroup = "II";
                        break;
                    default:
                        System.out.println("error wrong subGroup");
                        break;
                }
            }
        });

        RoundButton registerButton = findViewById(R.id.registerButton);
        // Instantiate the cache
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();
        jsonParseGroupNames();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonRegisterUser(usernameMaterialTextField.getEditText().getText().toString(),
                        mailMaterialTextField.getEditText().getText().toString(),
                        familynameMaterialTextField.getEditText().getText().toString(),
                        passwordMaterialTextField.getEditText().getText().toString(),
                        gender, groupName, subGroup);
            }
        });
    }

    private void jsonParseGroupNames() {

        String url = Utilities.getServerURL() + "getGroupNames";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("groupNames");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                groupNames.add((String)jsonArray.get(i));
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

        mRequestQueue.add(request);
    }

    private void jsonRegisterUser(String username, String mail, String familyName,
                                  String password, String gender, String groupName, String subGroup) {

        familyName = familyName.replaceAll(" ", "%20");

        String url = Utilities.getServerURL() +
                "register?" +
                "username=" + username +
                "&mail=" + mail +
                "&familyName=" + familyName +
                "&password=" + password +
                "&gender=" + gender +
                "&groupName=" + groupName +
                "&subGroup=" + subGroup;

       Log.i("URL", url);

       JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            SharedPreferences.Editor prefsEditor = mPrefs.edit();

                            if (response.getString("status").equals("success")) {


                                Log.i("user", response.getString("user"));

                                String json = response.getString("user");

                                prefsEditor.putString("User", json);
                                prefsEditor.putString("RegisterSuccess", "yes");
                                prefsEditor.apply();

                                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else {

                                AlertDialog alertDialog;
                                AlertDialog.Builder builder;
                                builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Inregistrarea nu a mers cu success");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recreate();
                                    }
                                });
                                alertDialog = builder.create();
                                alertDialog.show();

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

        mRequestQueue.add(request);

    }
}
