package projects.doctorappointmentapp;

import android.annotation.SuppressLint;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PatientActivity extends AppCompatActivity {
    private DoctorsListAdapter adapter = null;
    private DoctorsDB doctorsDB = null;

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

        // manage Recycler View
        recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.adapter = new DoctorsListAdapter();
        recView.setAdapter(adapter);

        // set initial UI:
//        filter.setChecked(false);


//        // addTask listener
//        filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(PatientActivity.this, "22222", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(PatientActivity.this, "1111",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

}
