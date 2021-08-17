package projects.doctorappointmentapp;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * doctors DB
 */
public class DoctorsDB {
    private ArrayList<Doctor> allDoctors;
    private ArrayList<Doctor> availableDoctors;
    FirebaseFirestore fireStore;


    public DoctorsDB() {
        this.allDoctors = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        initializeAllDoctors();
    }

    /**
     * return doctor according to given position
     */
    Doctor getDoctorByPosition(int position) {
        return allDoctors.get(position);
    }

    /**
     * return available doctor according to given position
     */
    Doctor getAvailableDoctorByPosition(int position) {
        return availableDoctors.get(position);
    }

    /**
     * returns Doctor object according to uid
     */
    Doctor getDoctorByUid(String uid) {
        for (Doctor doc : allDoctors) {
            if (doc.uid.equals(uid)) {
                return doc;
            }
        }
        return null;
    }

    /**
     * returns number of doctors
     */
    int getAllCount() {
        return allDoctors.size();
    }

    /**
     * returns number of available doctors
     */
    int getFilteredCount() {
        return availableDoctors.size();
    }

    /**
     * initialize the parameter allDoctors - a list of all doctors in the FireStore collection
     */
    private void initializeAllDoctors() {
        fireStore.collection(AppointmentApp.doctorsCollection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Doctor doc = document.toObject(Doctor.class);
                                allDoctors.add(doc);
                            }
                        }
                    }
                });
    }


    /**
     * adds new doctor
     */
    public void addDoctor(Doctor newDoc) {
        allDoctors.add(newDoc);
    }

    /**
     * filters only the available doctors and saves the list to availableDoctors parameter
     */
    public void filter() {
        availableDoctors = new ArrayList<>();
        for (Doctor doc : allDoctors) {
            if (doc.currentPatient == null) {
                availableDoctors.add(doc);
            }
        }
    }



}

