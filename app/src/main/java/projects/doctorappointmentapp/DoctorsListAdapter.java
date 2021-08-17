package projects.doctorappointmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Adapter for doctors recycleView
 */
public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorHolder> {
    private DoctorsDB doctorsDB;
    public Patient patient;
    private Context context;
    Boolean showFiltered = false;

    /**
     * constructor - initialize the doctorsDB and the patient
     */
    DoctorsListAdapter(Patient givenPatient){
        super();
        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getDoctorsDB();
        }

        if (this.patient == null) {
            this.patient = givenPatient;
        }
    }


    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorHolder(view);
    }

    /**
     * contains:
     *  - schedule appointment listener
     *  - cancel appointment listener
     *  - waiting list popUp listener
     */
    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        this.context = holder.doctor_text.getContext();

        // find the doctor to show
        Doctor doctor;
        if (showFiltered) {
            doctor = doctorsDB.getAvailableDoctorByPosition(position);
        }
        else {
            doctor = doctorsDB.getDoctorByPosition(position);
        }

        // set UI
        holder.doctor_name.setText(doctor.name);
        holder.doctor_text.setText(doctor.location);
        if (patient.checkIfScheduled(doctor)) {
            holder.appointmentButton.setClickable(false);
            holder.appointmentButton.setAlpha(0.5f);
            holder.cancelButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.appointmentButton.setAlpha(1f);
            holder.appointmentButton.setClickable(true);
            holder.cancelButton.setVisibility(View.INVISIBLE);
        }

        // schedule appointment listener
        holder.appointmentButton.setOnClickListener(v->
        {
            patient.addToAppointments(doctor);
            if (doctor.getCurrentPatient() == null) {
                doctor.setCurrentPatient(patient);
            }
            else {
                doctor.addToWaitingList(patient);
            }
            holder.appointmentButton.setAlpha(0.5f);
            holder.appointmentButton.setClickable(false);
            holder.cancelButton.setVisibility(View.VISIBLE);
        });

        // cancel appointment listener
        holder.cancelButton.setOnClickListener(v->
        {
            if (doctor.getCurrentPatient() != patient) {
                patient.removeFromAppointments(doctor);
                doctor.removeFromWaitingList(patient);
            }
            holder.appointmentButton.setAlpha(1f);
            holder.appointmentButton.setClickable(true);
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
            waiting_list.setText(doctor.getWaitingListParsed());

            close_button.setOnClickListener(v1 -> popupWindow.dismiss());
            popupWindow.showAsDropDown(popupView, 0, 0);        });
    }

    /**
     * @return total number of doctors to show
     */
    @Override
    public int getItemCount() {
        if (showFiltered) {
            return doctorsDB.getFilteredCount();
        }
        return doctorsDB.getAllCount();
    }

    /**
     * shows only available doctors
     */
    public void showFiltered() {
        showFiltered = true;
        doctorsDB.filter();
        notifyDataSetChanged();
    }

    /**
     * shows all doctors (not only available)
     */
    public void showAll() {
        showFiltered = false;
        notifyDataSetChanged();
    }



}
