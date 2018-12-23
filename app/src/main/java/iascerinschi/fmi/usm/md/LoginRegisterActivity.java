package iascerinschi.fmi.usm.md;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marozzi.roundbutton.RoundButton;

public class LoginRegisterActivity extends ToolbarActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

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

        RoundButton roundButton = findViewById(R.id.loginButton);
        roundButton.setAlpha(0);
        roundButton.animate().alpha(1).setDuration(2000);

        RoundButton roundButton2 = findViewById(R.id.registerButton);
        roundButton2.setAlpha(0);
        roundButton2.animate().alpha(1).setDuration(2000);
    }

    public void tryLogin(View view) {
        final RoundButton lbtn = findViewById(R.id.loginButton);
        final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void tryRegister(View view) {

        final RoundButton rbtn = findViewById(R.id.registerButton);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }


}
