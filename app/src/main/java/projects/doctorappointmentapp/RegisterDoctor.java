package projects.doctorappointmentapp;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Register doctor screen
 */
public class RegisterDoctor extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    EditText name_edit;
    EditText email_edit;
    EditText location_edit;
    EditText password_edit;
    Button registerButton;
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_doctor);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

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
            if (checkDetails()) {
                registerDoctor();
            }
        });
    }

    /**
     * checks if the values the user insert are valid
     */
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

    /**
     * saves doctor as user and saves it's object to fireStore "doctors" collection
     */
    private void registerDoctor() {
        // create new doctor
        String name = this.name_edit.getText().toString();
        String email = this.email_edit.getText().toString();
        String location = this.location_edit.getText().toString();
        String gender = AppointmentApp.MALE; //todo - get from UI
        String password = this.password_edit.getText().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Doctor newDoc = new Doctor(uid, name, email, location, gender);

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(RegisterDoctor.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AppointmentApp.getDoctorsDB().addDoctor(newDoc);
                        newDoc.updateUserInFireStore(AppointmentApp.doctorsCollection, uid , newDoc,
                                RegisterDoctor.this, "Registered successfully",
                                "Error");

                        progressBar.setVisibility(View.GONE);

                        Intent nextIntent = new Intent(RegisterDoctor.this, DoctorActivity.class);
                        nextIntent.putExtra("uid", uid);
                        startActivity(nextIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterDoctor.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
