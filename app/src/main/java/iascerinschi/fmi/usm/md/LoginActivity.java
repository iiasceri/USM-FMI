package iascerinschi.fmi.usm.md;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.marozzi.roundbutton.RoundButton;

public class LoginActivity extends ToolbarActivity {

    Toolbar toolbar;
    String mail = "";
    String password = "";

    int LONG_ANIMATION_DURATION = 1200;
    int MEDIUM_ANIMATION_DURATION = 800;
    int SHORT_ANIMATION_DURATION = 220;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Primim height, width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        //UI
        final ImageView imageView = findViewById(R.id.fmi_logo_view_login);
        final RoundButton roundButton = findViewById(R.id.login2Button);
        final MaterialTextField materialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        final MaterialTextField materialTextField2 = findViewById(R.id.passwordMaterialTextFieldLogin);

        materialTextField.setAlpha(0f);
        materialTextField2.setAlpha(0f);
        roundButton.setAlpha(0f);

        /*[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        imageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(LONG_ANIMATION_DURATION);
        imageView.animate().translationXBy(-(width/3)).setDuration(LONG_ANIMATION_DURATION);
        imageView.animate().translationYBy(-(height/10) - 15).setDuration(LONG_ANIMATION_DURATION);

        //Animatia txt & buton
        materialTextField.animate().alpha(1f).setDuration(LONG_ANIMATION_DURATION);
        roundButton.animate().alpha(1f).setDuration(LONG_ANIMATION_DURATION);

        materialTextField.animate().translationYBy(-height/2 + 200).setDuration(LONG_ANIMATION_DURATION);
        materialTextField2.animate().translationYBy(-height/2 + 200).setDuration(LONG_ANIMATION_DURATION);
        materialTextField2.animate().translationXBy(1000).setDuration(MEDIUM_ANIMATION_DURATION);
        roundButton.animate().translationYBy(-height/2 + 200).setDuration(LONG_ANIMATION_DURATION);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                materialTextField.setHasFocus(true);
            }
        }, LONG_ANIMATION_DURATION);

    }

    public void errorToasts(View view, String hintAsError) {
        Toast toast= Toast.makeText(getApplicationContext(),
                hintAsError, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void animateMailField(View view) {
        final MaterialTextField materialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        materialTextField.animate().translationXBy(70).setDuration(SHORT_ANIMATION_DURATION);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                materialTextField.animate().translationXBy(-140).setDuration(SHORT_ANIMATION_DURATION);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialTextField.animate().translationXBy(70).setDuration(SHORT_ANIMATION_DURATION);

                    }
                }, SHORT_ANIMATION_DURATION);

            }
        }, SHORT_ANIMATION_DURATION);
    }

    public void tryLogin(View view) {

        //Primim height, width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;

        MaterialTextField materialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        MaterialTextField materialTextField2 = findViewById(R.id.passwordMaterialTextFieldLogin);

        mail = materialTextField.getEditText().getText().toString();
        password = materialTextField2.getEditText().getText().toString();

        if (mail.isEmpty()) {
            errorToasts(view, "Introduceti mail-ul dvs");
            animateMailField(view);
        }
        else if(password.isEmpty()) {

            //Animatia: bring password field and get rid of mail field
            materialTextField2.expand();
            materialTextField.animate().translationXBy(-1000).setDuration(MEDIUM_ANIMATION_DURATION);
            materialTextField.animate().alpha(0f).setDuration(MEDIUM_ANIMATION_DURATION);
            materialTextField2.animate().translationXBy(-1000).setDuration(MEDIUM_ANIMATION_DURATION);
            materialTextField2.animate().alpha(1f).setDuration(MEDIUM_ANIMATION_DURATION);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
