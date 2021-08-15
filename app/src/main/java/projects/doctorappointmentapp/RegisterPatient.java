package projects.doctorappointmentapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterPatient extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name;
    EditText email;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_doctor); // todo - create patient layout
        mAuth = FirebaseAuth.getInstance();
    }
}
