package projects.doctorappointmentapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientsDB {
    private static ArrayList<Patient> allPatient;
    FirebaseFirestore fireStore;

    PatientsDB() {
        this.allPatient = new ArrayList<>();
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
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Patient patient = document.toObject(Patient.class);
                                allPatient.add(patient);
                            }
                        }
                    }
                });
    }

}
