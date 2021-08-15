package projects.doctorappointmentapp;

import java.util.List;
import java.util.Set;

public class DoctorsDB {
    private List<Doctor> allDoctors;

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

