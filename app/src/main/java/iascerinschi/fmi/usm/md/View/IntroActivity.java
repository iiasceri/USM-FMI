package iascerinschi.fmi.usm.md.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import iascerinschi.fmi.usm.md.MyApplication;
import iascerinschi.fmi.usm.md.R;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Salut", "La moment, in aplicatie sunt urmatoarele functionalitati: Orar, Orar Examene, Note Semestriale, Delogare(sterge datele temporare)", R.drawable.blink_small, Color.parseColor("#04a01b")));
        addSlide(AppIntroFragment.newInstance("Atentie", "Aplicatia este inca in dezvoltare", R.drawable.v2beta, Color.parseColor("#04a01b")));
        setBarColor(Color.parseColor("#04a01b"));
        showSkipButton(false);
        setProgressButtonEnabled(true);
        showStatusBar(false);
        //setFlowAnimation();
        setBackButtonVisibilityWithDone(true);
        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        // Do something when users tap on Skip button.
        MyApplication.setIntroShown(this, true);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        // Do something when users tap on Done button.
        MyApplication.setIntroShown(this, true);

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}