package projects.doctorappointmentapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

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
        setContentView(R.layout.activity_main);

        if (doctorsDB == null) {
            doctorsDB = AppointmentApp.getAppInstance().getDoctorsDB();
        }

        // find all the views
        Switch filter = findViewById(R.id.filter);
        RecyclerView recView = findViewById(R.id.recycler);

        // manage Recycler View
        recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.adapter = new DoctorsListAdapter();
        recView.setAdapter(adapter);


        // addTask listener
        filter.setOnClickListener(v ->
        {
           //todo - filter to show only available docs
        });
    }

}
