package projects.doctorappointmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorHolder> {
    private DoctorsDB doctorsDB;
    private Patient patient;

    DoctorsListAdapter(Patient patient){
        super();
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getAppInstance().getDoctorsDB();
        }
        this.patient = patient;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor doctor = doctorsDB.getDoctorByPosition(position);
        // set UI
        holder.doctor_name.setText(doctor.name);
        holder.doctor_text.setText(doctor.location);
        holder.appointmentButton.setAlpha(1f);
        holder.cancelButton.setVisibility(View.INVISIBLE);

        holder.appointmentButton.setOnClickListener(v->
        {
            if (doctor.currentPatient == null) {
                doctor.currentPatient = patient;
            }
            else {
                doctor.waiting_list.add(patient);
            }
            holder.appointmentButton.setAlpha(0.5f);
            holder.cancelButton.setVisibility(View.VISIBLE);
        });

        holder.cancelButton.setOnClickListener(v->
        {
            if (doctor.currentPatient != patient) {
                doctor.waiting_list.remove(patient);
            }
            holder.appointmentButton.setAlpha(1f);
            holder.cancelButton.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return doctorsDB.getCount();
    }

}
