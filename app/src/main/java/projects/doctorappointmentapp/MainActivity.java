package projects.doctorappointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDB;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


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
            boolean doctorChecked = ((RadioButton) doctor_radio).isChecked();
            boolean patientChecked = ((RadioButton) patient_radio).isChecked();
            if (!doctorChecked & !patientChecked) {
                Toast.makeText(this, "Must choose user type - doctor or patient", Toast.LENGTH_LONG).show();
            }

            String user_email = editEmail.getText().toString().trim();
            String user_password = editPassword.getText().toString().trim();
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if (doctor_radio.isChecked()) {
                                    Intent nextIntent = new Intent(MainActivity.this, DoctorActivity.class);
                                    startActivity(nextIntent);
                                }
                                else {
                                    Intent nextIntent = new Intent(MainActivity.this, PatientActivity.class);
                                    startActivity(nextIntent);                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        });


        // register listener
        registerButton.setOnClickListener(v->
        {
            boolean doctorChecked = ((RadioButton) doctor_radio).isChecked();
            boolean patientChecked = ((RadioButton) patient_radio).isChecked();
            if (!doctorChecked & !patientChecked) {
                Toast.makeText(this, "Must choose user type - doctor or patient", Toast.LENGTH_LONG).show();
            }
            else if (doctorChecked) {
                    Intent nextIntent = new Intent(this, RegisterDoctor.class);
                    startActivity(nextIntent);
                }
            else {
                Intent nextIntent = new Intent(this, RegisterPatient.class);
                startActivity(nextIntent);
            }

        });
    }
}