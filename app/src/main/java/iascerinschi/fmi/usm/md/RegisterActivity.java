package iascerinschi.fmi.usm.md;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;

public class RegisterActivity extends AppCompatActivity {

    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialTextField usernameMaterialTextField = findViewById(R.id.usernameMaterialTextFieldRegister);
        MaterialTextField mailMaterialTextField = findViewById(R.id.mailMaterialTextFieldRegister);
        MaterialTextField familynameMaterialTextField = findViewById(R.id.familynameMaterialTextFieldRegister);
        MaterialTextField passwordMaterialTextField = findViewById(R.id.passwordMaterialTextFieldRegister);
        MaterialTextField confirmPasswordMaterialTextField = findViewById(R.id.confirmPasswordMaterialTextFieldRegister);

        confirmPasswordMaterialTextField.expand();
        passwordMaterialTextField.expand();
        familynameMaterialTextField.expand();
        mailMaterialTextField.expand();
        usernameMaterialTextField.expand();


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

        rg = findViewById(R.id.genderRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonMale:
                        Toast.makeText(getApplicationContext(), "Ai ales barbat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonFemale:
                        Toast.makeText(getApplicationContext(), "Ai ales femeie", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Alegeti una din optiuni", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void tryRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
