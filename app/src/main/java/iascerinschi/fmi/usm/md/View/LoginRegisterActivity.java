package iascerinschi.fmi.usm.md.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.marozzi.roundbutton.RoundButton;

import iascerinschi.fmi.usm.md.MyApplication;
import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.Utilities.Utilities;

public class LoginRegisterActivity extends ToolbarActivity {

    private static final String DEFAULT_SERVER = Utilities.getDefaultServerIp();
    int taps = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        //  ToDo: Planned to fix background before release
        //prefsEditor.remove("isIntroShown").apply();

        prefsEditor.putString("Server", DEFAULT_SERVER);

        prefsEditor.apply();

        if (mPrefs.contains("User")) {
            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        /*[1] ... Verticale + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        */

        //[?] Animatii
        TextView textView = findViewById(R.id.textView);
        textView.setAlpha(0);
        textView.animate().alpha(1).setDuration(900);

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setAlpha(0);
        textView2.animate().alpha(1).setDuration(900);

        ImageView imageView = findViewById(R.id.fmi_logo_view);
        imageView.setAlpha(0f);
        imageView.animate().alpha(1).setDuration(1700);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taps > 3) {
                    LayoutInflater li = LayoutInflater.from(getApplicationContext());
                    final View editDialogView = li.inflate(R.layout.edit_dialogs, null);
                    AlertDialog alertDialog;
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(LoginRegisterActivity.this);
                    builder.setView(editDialogView);
                    builder.setMessage("Introduceti IP Adresa");
                    builder.setPositiveButton("Salveaza", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText serverEditText = editDialogView.findViewById(R.id.serverEditTextSettings);

                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            prefsEditor.remove("Server");
                            prefsEditor.putString("Server", serverEditText.getText().toString());
                            prefsEditor.apply();
                        }
                    });
                    builder.setNegativeButton("Inapoi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
                taps++;
            }
        });

        RoundButton roundButton = findViewById(R.id.loginButton);
        roundButton.setAlpha(0);
        roundButton.animate().alpha(1).setDuration(2000);

        RoundButton roundButton2 = findViewById(R.id.registerButton);
        roundButton2.setAlpha(0);
        roundButton2.animate().alpha(1).setDuration(2000);

        if (!MyApplication.isIntroShown(this)) {
            startActivity(new Intent(this, IntroActivity.class));
            overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }

        if (Utilities.checkConnection(getApplicationContext())) {
        } else {
            Snackbar.make(findViewById(R.id.layoutLoginRegister), "Verificati Conexiunea la Internet!", Snackbar.LENGTH_LONG).show();
        }
    }

    public void tryLogin(View view) {
        final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void tryRegister(View view) {
        final Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


}
