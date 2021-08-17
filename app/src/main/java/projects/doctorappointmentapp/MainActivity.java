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

/**
 * sign-in and register screen
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
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
                return;
            }

            String user_email = editEmail.getText().toString().trim();
            String user_password = editPassword.getText().toString().trim();
            if (user_email.length() == 0 || user_password.length() == 0) {
                Toast.makeText(MainActivity.this, "Must enter Email and password", Toast.LENGTH_LONG).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                boolean isDoctor = isDoctor(uid);
                                if (doctor_radio.isChecked() & isDoctor) {
                                    Intent nextIntent = new Intent(MainActivity.this, DoctorActivity.class);
                                    nextIntent.putExtra("uid", uid);
                                    startActivity(nextIntent);
                                }
                                else if (patient_radio.isChecked() & !isDoctor){
                                    Intent nextIntent = new Intent(MainActivity.this, PatientActivity.class);
                                    nextIntent.putExtra("uid", uid);
                                    startActivity(nextIntent);
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "user does not match type (doctor or patient)",Toast.LENGTH_LONG).show();
                                }
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
                    Intent DoctorIntent = new Intent(this, RegisterDoctor.class);
                    startActivity(DoctorIntent);
                }
            else {
                Intent PatientIntent = new Intent(this, RegisterPatient.class);
                startActivity(PatientIntent);
            }

        });
    }

    /**
     * checks if the given uid belongs to a doctor-user
     */
    private boolean isDoctor(String uid) {
        if (AppointmentApp.getDoctorsDB().getDoctorByUid(uid) != null) {
            return true;
        };
        return false;
    }
}