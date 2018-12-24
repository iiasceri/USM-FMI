package iascerinschi.fmi.usm.md;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.github.florent37.materialtextfield.MaterialTextField;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialTextField mailMaterialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        MaterialTextField passwordMaterialTextField = findViewById(R.id.passwordMaterialTextFieldRegister);
        MaterialTextField confirmPasswordMaterialTextField = findViewById(R.id.confirmPasswordMaterialTextFieldRegister);

        confirmPasswordMaterialTextField.expand();
        passwordMaterialTextField.expand();
        mailMaterialTextField.expand();

        EditText e3 = confirmPasswordMaterialTextField.findViewById(R.id.confirmPasswordEditTextRegister);

        e3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ScrollView scrollLayout = findViewById(R.id.scrollView);
                    View lastChild = scrollLayout.getChildAt(scrollLayout.getChildCount() - 1);
                    int bottom = lastChild.getBottom() + scrollLayout.getPaddingBottom();
                    int sy = scrollLayout.getScrollY();
                    int sh = scrollLayout.getHeight();
                    int delta = bottom - (sy + sh);

                    scrollLayout.smoothScrollBy(0, delta);
                }
            }
        });

    }

    public void tryRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
