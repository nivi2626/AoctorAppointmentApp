package projects.doctorappointmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorHolder> {
    private DoctorsDB doctorsDB;

    DoctorsListAdapter(){
        super();
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getAppInstance().getDoctorsDB();
        }
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
    }

    @Override
    public int getItemCount() {
        return doctorsDB.getCount();
    }
}
