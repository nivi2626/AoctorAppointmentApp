package projects.doctorappointmentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterPatient extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    EditText name_edit;
    EditText email_edit;
    EditText age_edit;
    EditText password_edit;
    Button registerButton;
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_patient);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        // find views
        name_edit = findViewById(R.id.name_edit);
        email_edit = findViewById(R.id.edit_email);
        password_edit = findViewById(R.id.password_edit);
        age_edit = findViewById(R.id.edit_age);
        registerButton = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressBar);

        //set UI
        progressBar.setVisibility(View.INVISIBLE);

        // set register listener
        registerButton.setOnClickListener(v -> {
            Boolean flag = checkDetails();
            if (flag) {
                registerPatient();
            }
        });
    }

    private Boolean checkDetails() {
        String name = this.name_edit.getText().toString();
        String email = this.email_edit.getText().toString();
        String age = this.age_edit.getText().toString();
        String password = this.password_edit.getText().toString();

        if (name.length() == 0) {
            this.name_edit.setError("Name is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email_edit.setError("Email is not valid");
            return false;
        }
        if (age.length() == 0) {
            this.age_edit.setError("Age is required");
            return false;
        }
        int intAge = Integer.parseInt(this.age_edit.getText().toString());
        if (intAge < 0 || intAge > 120) {
            this.age_edit.setError("Age is not valid");
            return false;
        }
        if (password.length() < 6) {
            this.password_edit.setError("password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private void registerPatient() {
        String name = this.name_edit.getText().toString();
        String email = this.email_edit.getText().toString();
        int age = Integer.parseInt(this.age_edit.getText().toString());
        String password = this.password_edit.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(RegisterPatient.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Patient newPatient = new Patient(uid, name, email, age);
                        uploadToFireStore(uid, newPatient);
                        progressBar.setVisibility(View.GONE);
                        Intent nextIntent = new Intent(RegisterPatient.this, PatientActivity.class);
                        nextIntent.putExtra("uid", uid);
                        startActivity(nextIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterPatient.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void uploadToFireStore(String uid, Patient newUser) {
        fireStore.collection(AppointmentApp.patientsCollection).document(uid).set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterPatient.this, "Registered successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterPatient.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

}

