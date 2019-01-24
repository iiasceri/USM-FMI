package iascerinschi.fmi.usm.md.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.Utilities.Utilities;
import iascerinschi.fmi.usm.md.View.Marks.MarksActivity;
import iascerinschi.fmi.usm.md.View.Schedule.ScheduleActivity;

public class MainActivity extends ToolbarActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        //[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //[2]ori
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        View hView =  navigationView.getHeaderView(0);
        TextView nameTextView = hView.findViewById(R.id.nameTextViewDrawer);
        ImageView genderImageView = hView.findViewById(R.id.genderImageViewDrawer);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JSONObject obj;
        String name = "Linda Figlind";
        String gender = "";

        try {
            String json = mPrefs.getString("User", "");
            obj = new JSONObject(json);
            name = obj.getString("familyName");
            gender = obj.getString("gender");

            Log.i("name", name);
            Log.i("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameTextView.setText(name);
        if (gender.equals("male"))
            genderImageView.setImageDrawable(getResources().getDrawable(R.drawable.male_user));

        TextView t4 = findViewById(R.id.textView4);
        TextView t5 = findViewById(R.id.textView5);

        t4.setAlpha(0f);
        t5.setAlpha(0f);

        //[3]browser
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        if (Utilities.checkConnection(getApplicationContext())) {
            webView.loadUrl("http://fmi.usm.md");
//            SharedPreferences.Editor prefsEditor = mPrefs.edit();
//            prefsEditor.putString("WebViewWasLoaded", "yes");
//            prefsEditor.apply();
        } else {

//            if (!mPrefs.contains("WebViewWasLoaded")) {
                t4.setAlpha(1f);
                t5.setAlpha(1f);
//            }

            Snackbar.make(findViewById(R.id.layoutMain), "Verificati Conexiunea La Internet Pentru a Putea Inoi Datele! (Nu e obligatoriu daca deja le-ati descarcat)", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    //[2]Trei linii(orizontale): executarea codului la select. optiunilor gen "orar" etc.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if (id == R.id.nav_subheader_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.navigation_schedule) {
//            Toast.makeText(getApplicationContext(), "Schedule", Toast.LENGTH_SHORT).show();
//            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.orarhh);
//            mediaPlayer.start();

            Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
            startActivity(intent);
        }
        if (id == R.id.navigation_schedule_exams) {
            Intent intent = new Intent(getApplicationContext(), ExamScheduleActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.navigation_marks) {
            Intent intent = new Intent(getApplicationContext(), MarksActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_subheader_messages) {
            Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_subheader_logout) {
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.remove("User");
            prefsEditor.remove("ExamSchedule");
            prefsEditor.remove("Schedule");
            prefsEditor.remove("ID");
            prefsEditor.remove("Marks");
            prefsEditor.apply();

            Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
