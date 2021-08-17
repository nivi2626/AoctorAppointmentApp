package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * defines the patients' screen - list of all doctors and option to filter only available ones
 */
public class PatientActivity extends AppCompatActivity {
    private DoctorsListAdapter adapter = null;
    private DoctorsDB doctorsDB = null;
    private PatientsDB PatentsDB = null;
    Patient patient;
    RecyclerView recView;
    Switch filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);

        // get Data bases
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getDoctorsDB();
        }
        if (PatentsDB == null) {
            PatentsDB = AppointmentApp.getPatientsDB();
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
                }
                else {
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
}