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
            Boolean flag = checkDetails();
            if (flag) {
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
     * saves doctor as user and saves the doctor-object to fireStore
     */
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
                        Doctor newDoc = new Doctor(uid, name, email, location);
                        AppointmentApp.getDoctorsDB().addDoctor(newDoc);
//                        uploadToFireStore(uid, newDoc);
                        newDoc.updateFireStoreWithToast(AppointmentApp.doctorsCollection, uid , newDoc,
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
                        Toast.makeText(RegisterDoctor.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

//    private void uploadToFireStore(String uid, Doctor newDoc) {
//        fireStore.collection(AppointmentApp.doctorsCollection).document(uid).set(newDoc)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(RegisterDoctor.this, "Registered successfully", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast t = Toast.makeText(RegisterDoctor.this, "Error", Toast.LENGTH_LONG);
//                    }
//                });
//    }

}
