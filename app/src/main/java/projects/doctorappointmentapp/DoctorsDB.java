package projects.doctorappointmentapp;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorsDB {
    private ArrayList<Doctor> allDoctors;
    FirebaseFirestore fireStore;
    final String doctorsCollection = "doctors";


    public DoctorsDB() {
        this.allDoctors = new ArrayList<>();
        fireStore = FirebaseFirestore.getInstance();
        initializeAllDoctors();
    }

    Doctor getDoctorByPosition(int position) {
        return allDoctors.get(position);
    }

    Doctor getDoctorByEmail(String Email) {
        for (Doctor doc: allDoctors) {
            if (doc.email.equals(Email)) {
                return doc;
            }
        }
        return null;
    }

    int getCount() {
        return allDoctors.size();
    }

    private void initializeAllDoctors() {
        fireStore.collection(doctorsCollection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                allDoctors = (ArrayList<Doctor>) task.getResult().toObjects(Doctor.class);
                            }
                        }
                    }
                });
    }

    public void addDoctor(String uid, Doctor newDoc) {
        allDoctors.add(newDoc);
        uploadToFireStore(uid, newDoc);
    }


    private void uploadToFireStore(String uid, Doctor newDoc) {
        fireStore.collection("doctors").document(uid).set(newDoc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(RegisterDoctor.this, "Registered successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterDoctor.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }


}

