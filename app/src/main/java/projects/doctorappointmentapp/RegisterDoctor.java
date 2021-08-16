package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDoctor extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name_edit;
    EditText email_edit;
    EditText location_edit;
    EditText password_edit;
    Button registerButton;
    FirebaseDatabase firebaseDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_doctor);
        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();

        // find views
        name_edit = findViewById(R.id.name_edit);
        email_edit = findViewById(R.id.edit_email);
        password_edit = findViewById(R.id.password_edit);
        location_edit = findViewById(R.id.location_edit);
        registerButton = findViewById(R.id.register_button);

        // set listener
        registerButton.setOnClickListener(v -> {
            Boolean flag = checkDetails();
            if (flag) {
                registerDoctor();
                Intent nextIntent = new Intent(this, DoctorActivity.class);
                startActivity(nextIntent);
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Doctor newDoc = new Doctor(name, email, location);
                    firebaseDB.getReference("doctors")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(newDoc).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterDoctor.this,
                                        "Registered successfully",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterDoctor.this,
                                        "ERROR", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterDoctor.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
