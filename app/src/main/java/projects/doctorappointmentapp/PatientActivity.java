package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * defines the patients' screen - list of all doctors and option to filter only available ones
 */
public class PatientActivity extends AppCompatActivity {
    private DoctorsListAdapter adapter = null;
    private DoctorsDB doctorsDB = null;
    Patient patient;
    RecyclerView recView;
    Switch filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);

        // find patient
        String uid = getIntent().getStringExtra("uid");
        setPatient(uid);
//        patient = PatientsDB.getByUid(uid);


        // get Data base
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getDoctorsDB();
        }
//        if (PatentsDB == null) {
//            PatentsDB = AppointmentApp.getPatientsDB();
//        }

        // find all the views
        filter = (Switch) findViewById(R.id.filter);
        recView = findViewById(R.id.recycler);

        // set initial UI:
        filter.setChecked(false);


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


    /**
     * Override onBackPressed so that pushing back will lead to main activity
     */
    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(PatientActivity.this, MainActivity.class);
        startActivity(backIntent);
    }

    /**
     * get the current user (patient) object
     *
     * @param uid - patient user id
     */
    private void setPatient(String uid) {
        FirebaseFirestore.getInstance().collection(AppointmentApp.patientsCollection).document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                patient = document.toObject(Patient.class);

                                // set the doctors
                                recView.setLayoutManager(new LinearLayoutManager(PatientActivity.this, RecyclerView.VERTICAL, false));
                                adapter = new DoctorsListAdapter(patient);
                                recView.setAdapter(adapter);
                            }
                        }
                        else
                            {
                                Toast.makeText(PatientActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}