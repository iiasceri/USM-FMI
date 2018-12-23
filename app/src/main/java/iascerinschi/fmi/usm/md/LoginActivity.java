package iascerinschi.fmi.usm.md;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.marozzi.roundbutton.RoundButton;

public class LoginActivity extends ToolbarActivity {

    Toolbar toolbar;
    int firstAnimationDuration = 1500;
    String mail = "";
    String password = "";


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
        final MaterialTextField materialTextField = findViewById(R.id.materialTextField);
        final MaterialTextField materialTextField2 = findViewById(R.id.materialTextField2);

        materialTextField.setAlpha(0f);
        materialTextField2.setAlpha(0f);
        roundButton.setAlpha(0f);

        /*[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        imageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(firstAnimationDuration);
        imageView.animate().translationXBy(-(width/3)).setDuration(firstAnimationDuration);
        imageView.animate().translationYBy(-(height/10) - 15).setDuration(firstAnimationDuration);

        //Animatia txt & buton
        materialTextField.animate().alpha(1f).setDuration(firstAnimationDuration);
        roundButton.animate().alpha(1f).setDuration(firstAnimationDuration);

        materialTextField.animate().translationYBy(-height/2 + 200).setDuration(firstAnimationDuration);
        materialTextField2.animate().translationYBy(-height/2 + 200).setDuration(firstAnimationDuration);
        materialTextField2.animate().translationXBy(1000).setDuration(800);
        roundButton.animate().translationYBy(-height/2 + 200).setDuration(firstAnimationDuration);

    }

    public void errorToasts(View view, String hintAsError) {
        Toast toast= Toast.makeText(getApplicationContext(),
                hintAsError, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void animateMailField(View view) {
        final MaterialTextField materialTextField = findViewById(R.id.materialTextField);
        materialTextField.animate().translationXBy(70).setDuration(220);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                materialTextField.animate().translationXBy(-140).setDuration(220);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialTextField.animate().translationXBy(70).setDuration(220);

                    }
                }, 220);

            }
        }, 220);



    }

    public void tryLogin(View view) {

        //Primim height, width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;

        MaterialTextField materialTextField = findViewById(R.id.materialTextField);
        MaterialTextField materialTextField2 = findViewById(R.id.materialTextField2);

        mail = materialTextField.getEditText().getText().toString();
        password = materialTextField2.getEditText().getText().toString();

        if (mail.equals("")) {
            errorToasts(view, "Introduceti mail-ul dvs");
            animateMailField(view);
        }
        else if(password.equals("")) {
            materialTextField2.setHasFocus(true);
            materialTextField.animate().translationXBy(-1000).setDuration(800);
            materialTextField.animate().alpha(0f).setDuration(800);
            materialTextField2.animate().translationXBy(-1000).setDuration(800);
            materialTextField2.animate().alpha(1f).setDuration(800);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        /*
        if (password.equals("") || password.equals(" ")) {
            errorToasts(view, "Introduceti parola dvs");
        }*/
    }
}
