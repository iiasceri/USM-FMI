package iascerinschi.fmi.usm.md.View;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import iascerinschi.fmi.usm.md.R;

public abstract class ToolbarActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
