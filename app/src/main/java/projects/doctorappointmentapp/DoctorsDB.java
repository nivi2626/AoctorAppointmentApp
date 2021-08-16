package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

public class DoctorsDB {
    private ArrayList<Doctor> allDoctors;

    public DoctorsDB() {
        this.allDoctors = new ArrayList<>();
    }

    Doctor getDoctor(int position) {
        return allDoctors.get(position);
    }

    int getCount(){
        return allDoctors.size();
    }

    private void initializeAllDoctors() {
        // todo - initialize from fireStore
    }

}

