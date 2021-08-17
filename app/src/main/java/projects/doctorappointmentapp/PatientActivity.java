package projects.doctorappointmentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PatientActivity extends AppCompatActivity {
    private DoctorsListAdapter adapter = null;
    private DoctorsDB doctorsDB = null;
    private static Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);

        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getAppInstance().getDoctorsDB();
        }

        // find all the views
        Switch filter = (Switch) findViewById(R.id.filter);
        RecyclerView recView = findViewById(R.id.recycler);

        // set initial UI:
        filter.setChecked(false);

        // manage Recycler View
        recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.adapter = new DoctorsListAdapter();
        recView.setAdapter(adapter);

        // get patient's object
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        setPatient(uid);

        // filter listener
        filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(PatientActivity.this, "22222", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(PatientActivity.this, "1111",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setPatient(String uid) {
        FirebaseFirestore.getInstance().collection(AppointmentApp.patientsCollection).document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        patient = documentSnapshot.toObject(Patient.class);
                        adapter.patient = patient;
                    }
                });
    }

    public static Patient getPatient() {
        return patient;
    }
}
