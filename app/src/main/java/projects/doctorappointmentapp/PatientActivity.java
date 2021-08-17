package projects.doctorappointmentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * defines the patients' screen - list of all doctors and option to filter only available ones
 */
public class PatientActivity extends AppCompatActivity {
    private DoctorsListAdapter adapter = null;
    private DoctorsDB doctorsDB = null;
    private PatientsDB PatentsDB = null;
    FirebaseFirestore fireStore; // ?
    Patient patient;
    RecyclerView recView;
    Switch filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);
        fireStore = FirebaseFirestore.getInstance(); // ?

        // get Data bases
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getAppInstance().getDoctorsDB();
        }
        if (PatentsDB == null) {
            PatentsDB = AppointmentApp.getAppInstance().getPatentsDB();
        }

        // find all the views
        filter = (Switch) findViewById(R.id.filter);
        recView = findViewById(R.id.recycler);

        // set initial UI:
        filter.setChecked(false);

        // find patient
        String uid = getIntent().getStringExtra("uid");
        patient = PatientsDB.getByUid(uid);

        // recycleView
        recView.setLayoutManager(new LinearLayoutManager(PatientActivity.this, RecyclerView.VERTICAL, false));
        adapter = new DoctorsListAdapter(patient);
        recView.setAdapter(adapter);


        // filter listener
        filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapter.showFiltered();
                } else {
                    adapter.showAll();
                }
            }

        });
    }
}