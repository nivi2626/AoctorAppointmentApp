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

public class DoctorsDB {
    private ArrayList<Doctor> allDoctors;
    FirebaseFirestore fireStore;


    public DoctorsDB() {
        this.allDoctors = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        initializeAllDoctors();
    }

    Doctor getDoctorByPosition(int position) {
        return allDoctors.get(position);
    }

    Doctor getDoctorByUid(String uid) {
        for (Doctor doc : allDoctors) {
            if (doc.uid.equals(uid)) {
                return doc;
            }
        }
        return null;
    }

    int getCount() {
        return allDoctors.size();
    }

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

    public void addDoctor(String uid, Doctor newDoc) {
        allDoctors.add(newDoc);
    }


}

