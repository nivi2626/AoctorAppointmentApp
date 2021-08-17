package projects.doctorappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/***
 *  defines the doctors' screen - current patient, list of all waiting patients and button for
 *  done with patient
 */
public class DoctorActivity extends AppCompatActivity {
    private static Doctor doctor;
    TextView currentPatient;
    TextView next_patients_names;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);

        // find views
        currentPatient = findViewById(R.id.current_patient_name);
        next_patients_names = findViewById(R.id.next_patients_names);
        Button next_patient_button = findViewById(R.id.button_done);

        // get doctor's object
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        doctor = AppointmentApp.getDoctorsDB().getDoctorByUid(uid);
        setUI();

        // set next patient listener
        next_patient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doctor.waiting_list.size() > 0) {
                    Patient next = doctor.waiting_list.get(0); // find next patient
                    next.sendNotification();
                    next.removeFromAppointments(doctor);
                    doctor.setCurrentPatient(next);
                    doctor.waiting_list.remove(0);
                } else {
                    doctor.setCurrentPatient(null);
                }
                setUI();
            }
        });
    }

    /**
     * sets the UI according to the doctor fields
     */
    private void setUI() {
        if (doctor.getCurrentPatient() == null) {
            currentPatient.setText("");
        } else {
            currentPatient.setText(doctor.getCurrentPatient().name);
        }
        next_patients_names.setText(doctor.getWaitingListParsed());
    }

    /**
     * Override onBackPressed so that pushing back will lead to main activity
     */
    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(DoctorActivity.this, MainActivity.class);
        startActivity(backIntent);
    }
}
