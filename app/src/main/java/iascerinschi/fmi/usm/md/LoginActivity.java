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
import android.widget.Toast;

import com.marozzi.roundbutton.RoundButton;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //[1]vert + Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    //[1]Trei puncte(verticale): crearea optiunilor "trimite feedback", "Detalii aplicatie".
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //[1]Trei puncte: executarea codului dorit la selectarea optiunilor alese
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_feedback) {
            Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_SHORT).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.yea_boii);
            mediaPlayer.start();
        }
        return super.onOptionsItemSelected(item);
    }

    public void tryLogin(View view) {
        final RoundButton lbtn = (RoundButton) findViewById(R.id.loginButton);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        lbtn.startAnimation();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 400);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                lbtn.revertAnimation();
            }
        }, 1200);
    }

    public void tryRegister(View view) {

        final RoundButton rbtn = (RoundButton) findViewById(R.id.registerButton);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        rbtn.startAnimation();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 400);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                rbtn.revertAnimation();
            }
        }, 1200);
    }
}
