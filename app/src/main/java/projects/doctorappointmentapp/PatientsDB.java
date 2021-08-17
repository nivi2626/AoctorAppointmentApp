package projects.doctorappointmentapp;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * patients DB
 */
public class PatientsDB {
    private static ArrayList<Patient> allPatient;
    FirebaseFirestore fireStore;

    PatientsDB() {
        allPatient = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        initialize();
    }

    public static Patient getByUid(String uid) {
        for (Patient p : allPatient) {
            if (p.uid.equals(uid)) {
                return p;
            }
        }
        return null;

    }

    /**
     * initialize the parameter allPatient - a list of all patients in the FireStore collection
     */
    private void initialize() {
        fireStore.collection(AppointmentApp.patientsCollection).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Patient patient = document.toObject(Patient.class);
                            allPatient.add(patient);
                        }
                    }
                });
    }

    /**
     * adds the given patient to the DB
     */
    public void addPatient(Patient patient){
        allPatient.add(patient);
    }

}
