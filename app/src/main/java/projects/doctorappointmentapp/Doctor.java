package projects.doctorappointmentapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/***
 * object to represent a doctor-user
 */
public class Doctor {
    public String uid;
    public String name;
    public String location;
    public String email;
    public Patient currentPatient;
    public List<Patient> waiting_list;
    FirebaseFirestore  fireStore= FirebaseFirestore.getInstance();;


    Doctor(String uid, String name, String email, String location){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.location = location;
        this.currentPatient = null;
        this.waiting_list = new ArrayList<Patient>();
    }

    Doctor(){
        this.name = "Dr ...";
        this.email = "";
        this.location = "location";
        this.currentPatient = null;
        this.waiting_list = new ArrayList<Patient>();
    }

    /***
     * @return the doctor's waiting list in a format of string - each patient's name in a new line
     */
    public String getWaitingList() {
        StringBuilder result = new StringBuilder();
        for (Patient p:this.waiting_list) {
            if (!result.toString().equals("")) {
                result.append("\n");
            }
            result.append(p.name);
        }
        return String.valueOf(result);
    }

    /***
     * adds new patient to the doctors waiting list
     * @param patient - patient to add
     */
    public void addToWaitingList(Patient patient){
        waiting_list.add(patient);
        updateFireStore();
    }

    /***
     * removes patient to the doctors waiting list
     * @param patient - patient to remove
     */
    public void removeFromWaitingList(Patient patient) {
        waiting_list.remove(patient);
        updateFireStore();
    }

    /***
     * sets the doctors current patient
     * @param patient - current patient
     */
    public void setCurrentPatient(Patient patient) {
        currentPatient = patient;
        updateFireStore();
    }

    /***
     * updates the doctor in fireStore
     */
    private void updateFireStore(){
        fireStore.collection(AppointmentApp.doctorsCollection).document(uid).set(this);
    }

}
