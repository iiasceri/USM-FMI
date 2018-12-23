package iascerinschi.fmi.usm.md;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.marozzi.roundbutton.RoundButton;

public class LoginActivity extends ToolbarActivity {

    Toolbar toolbar;

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
        materialTextField.setAlpha(0f);
        roundButton.setAlpha(0f);

        /*[1]vert + Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        //Animatia imaginei
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.5f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.5f);
        scaleDownX.setDuration(1500);
        scaleDownY.setDuration(1500);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();
        imageView.animate().translationXBy(-(width/3)).setDuration(1500);
        imageView.animate().translationYBy(-(height/10)).setDuration(1500);

        //Animatia txt & buton
        materialTextField.animate().alpha(1f).setDuration(1500);
        roundButton.animate().alpha(1f).setDuration(1500);

        materialTextField.animate().translationYBy(-height/2 + 200).setDuration(1500);
        roundButton.animate().translationYBy(-height/2 + 200).setDuration(1500);

    }

    public void tryLogin(View view) {
        final RoundButton lbtn = findViewById(R.id.login2Button);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }
}
