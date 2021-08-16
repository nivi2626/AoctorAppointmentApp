package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);

        // find views
        TextView currentPatient = findViewById(R.id.current_patient_name);
        TextView next_patients_names = findViewById(R.id.next_patients_names);

        // get doctor's UI
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        // set UI
        Doctor doc = AppointmentApp.getDoctorsDB().getDoctorByEmail(email);
        currentPatient.setText(doc.currentPatient);
        next_patients_names.setText(parseWaitingList(doc));

    }

    private String parseWaitingList(Doctor doc){
        // todo
        return "";
    }

}
