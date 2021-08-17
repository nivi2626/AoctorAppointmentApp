package projects.doctorappointmentapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorHolder> {
    private DoctorsDB doctorsDB;
    public Patient patient;
    private Context context;

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
        this.context = holder.doctor_text.getContext();

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

        // show popUp with the waiting list
        holder.waitingListButton.setOnClickListener(v->
        {
            // set popUp
            View popupView = LayoutInflater.from(context).inflate(R.layout.waiting_list_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            Button close_button = popupView.findViewById(R.id.close_button);
            TextView dr_name = popupView.findViewById(R.id.dr_name);
            TextView waiting_list = popupView.findViewById(R.id.waiting_list);

            //set UI
            dr_name.setText(doctor.name);
            waiting_list.setText(doctor.getWaitingList());

            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(popupView, 0, 0);        });
    }



    @Override
    public int getItemCount() {
        return doctorsDB.getCount();
    }

}
