package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorActivity extends AppCompatActivity {
    private static Doctor doctor;
    // TODO - change current patient after X min

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);

        // find views
        TextView currentPatient = findViewById(R.id.current_patient_name);
        TextView next_patients_names = findViewById(R.id.next_patients_names);

        // get doctor's object
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        Doctor doc = AppointmentApp.getDoctorsDB().getDoctorByUid(uid);

        // set UI
        if (doc.currentPatient == null){
            currentPatient.setText("");
        }
        else {
            currentPatient.setText(doc.currentPatient.name);
        }
        next_patients_names.setText(parseWaitingList(doc));
    }

    private String parseWaitingList(Doctor doc){
        StringBuilder result = new StringBuilder();
        for (Patient p:doc.waiting_list) {
            if (!result.toString().equals("")) {
                result.append("\n");
            }
            result.append(p.name);
        }
        return String.valueOf(result);
    }

}
