package projects.doctorappointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        Button registerButton = findViewById(R.id.register_button);
        Button logInButton = findViewById(R.id.log_in_button);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        RadioButton doctor_radio = findViewById(R.id.doctor_radio);
        RadioButton patient_radio = findViewById(R.id.patient_radio);


        // logIn listener
        logInButton.setOnClickListener(v->
        {
            logInButton.setAlpha(0.4f);
            if (doctor_radio.isChecked()) {
                // todo
//                Intent nextIntent = new Intent(this, ???.class);
            }
            else {
                // todo
            }
        });


        // register listener
        registerButton.setOnClickListener(v->
        {
            //todo - make sure doctor\patient are checked
            registerButton.setAlpha(0.4f);
            boolean doctorChecked = ((RadioButton) doctor_radio).isChecked();
            if (doctorChecked) {
                Intent nextIntent = new Intent(this, RegisterDoctor.class);
                startActivity(nextIntent);
            }
            else {
                // todo
            }
//            Intent nextIntent = new Intent(this, ???.class);
            //
        });


    }
}