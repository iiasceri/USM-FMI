package iascerinschi.fmi.usm.md.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.marozzi.roundbutton.RoundButton;
import java.util.Objects;

import iascerinschi.fmi.usm.md.R;
import iascerinschi.fmi.usm.md.View.Marks.MarksActivity;

public class SettingsActivity extends ToolbarActivity {

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Setari");

        MaterialTextField idnp = findViewById(R.id.IDMaterialTextFieldSettings);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!mPrefs.contains("ID")) {
            AlertDialog alertDialog;
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setMessage("Introduceti IDNP dvs pentru a putea vizualiza notele");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MaterialTextField idnp = findViewById(R.id.IDMaterialTextFieldSettings);
                    idnp.setHasFocus(true);
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            String idnpString = mPrefs.getString("ID", "");
            assert idnpString != null;
            String fragmentString =
                    String.valueOf(idnpString.charAt(0)) +
                            idnpString.charAt(1) +
                            idnpString.charAt(2) +
                            "∙∙∙∙∙∙∙" +
                            idnpString.charAt(idnpString.length() - 3) +
                            idnpString.charAt(idnpString.length() - 2) +
                            idnpString.charAt(idnpString.length() - 1);

            idnp.getEditText().setText(fragmentString);
        }
        idnp.setHasFocus(true);
        final EditText idnpEditText = idnp.findViewById(R.id.IDEditTextSettings);

        idnpEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    idnpEditText.setText("");
                    MaterialTextField idnp = findViewById(R.id.IDMaterialTextFieldSettings);
                    idnp.expand();
                    idnp.setHasFocus(true);
                }

                return true;
            }
        });

        RoundButton save = findViewById(R.id.saveRoundButtonSettings);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTextField idnpTMP = findViewById(R.id.IDMaterialTextFieldSettings);
                if (idnpTMP.getEditText().getText().toString().length() != 13) {
                    AlertDialog alertDialog;
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("IDNP introdus nu e de 13 cifre!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idnpEditText.setText("");
                            MaterialTextField idnp = findViewById(R.id.IDMaterialTextFieldSettings);
                            idnp.expand();
                            idnp.setHasFocus(true);
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
                String idnpStringToPrefs = idnpTMP.getEditText().getText().toString();

                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putString("ID", idnpStringToPrefs).apply();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (item.getItemId() == android.R.id.home) {
            if (Objects.equals(mPrefs.getString("LastActivity", ""), "MarksActivity")) {
                final Intent intent = new Intent(getApplicationContext(), MarksActivity.class);
                startActivity(intent);
            }
            else if (Objects.equals(mPrefs.getString("LastActivity", ""), "AnotherActivity")) {
                //Replace MainActivity.class with AnotherActivity
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else {
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
