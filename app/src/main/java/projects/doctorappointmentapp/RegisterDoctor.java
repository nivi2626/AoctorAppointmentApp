package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterDoctor extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name_edit;
    EditText email_edit;
    EditText location_edit;
    EditText password_edit;
    Button registerButton;
    ProgressBar progressBar;

//    FirebaseFirestore fireStore;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_doctor);
        mAuth = FirebaseAuth.getInstance();
//        fireStore = FirebaseFirestore.getInstance();


        // find views
        name_edit = findViewById(R.id.name_edit);
        email_edit = findViewById(R.id.edit_email);
        password_edit = findViewById(R.id.password_edit);
        location_edit = findViewById(R.id.location_edit);
        registerButton = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressBar);

        //set UI
        progressBar.setVisibility(View.INVISIBLE);

        // set listener
        registerButton.setOnClickListener(v -> {
            Boolean flag = checkDetails();
            if (flag) {
                registerDoctor();
            }
        });
    }

    private Boolean checkDetails() {
        String name = this.name_edit.getText().toString();
        String email = this.email_edit.getText().toString();
        String password = this.password_edit.getText().toString();
        if (name.length() == 0) {
            this.name_edit.setError("Name is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email_edit.setError("Email is not valid");
            return false;
        }
        if (password.length() < 6) {
            this.password_edit.setError("password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private void registerDoctor() {

        String name = this.name_edit.getText().toString();
        String email = this.email_edit.getText().toString();
        String location = this.location_edit.getText().toString();
        String password = this.password_edit.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(RegisterDoctor.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Doctor newDoc = new Doctor(name, email, location);
                        AppointmentApp.getDoctorsDB().addDoctor(uid, newDoc);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterDoctor.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        Intent nextIntent = new Intent(RegisterDoctor.this, DoctorActivity.class);
                        nextIntent.putExtra("email", email);
                        startActivity(nextIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterDoctor.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
