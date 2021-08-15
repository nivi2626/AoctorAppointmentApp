package projects.doctorappointmentapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorHolder extends RecyclerView.ViewHolder{
    View view;
    Button appointmentButton;
    TextView doctor_name;
    TextView doctor_text;


    public DoctorHolder(@NonNull View itemView) {
        super(itemView);
        // find views
        this.view = itemView;
        this.appointmentButton = itemView.findViewById(R.id.appointmentButton);
        this.doctor_name = itemView.findViewById(R.id.doctor_name);
        this.doctor_text = itemView.findViewById(R.id.doctor_text);

    }
}
