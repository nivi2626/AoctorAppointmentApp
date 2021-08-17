package projects.doctorappointmentapp;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * object to represent a doctor-user
 */
public class Doctor extends User{
    public String location;
    private Patient currentPatient;
    public List<Patient> waiting_list;


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
    public String getWaitingListParsed() {
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
        updateFireStore(AppointmentApp.doctorsCollection, uid, this);
    }

    /***
     * removes patient to the doctors waiting list
     * @param patient - patient to remove
     */
    public void removeFromWaitingList(Patient patient) {
        waiting_list.remove(patient);
        updateFireStore(AppointmentApp.doctorsCollection, uid, this);
    }

    /***
     * sets the doctors current patient
     * @param patient - current patient
     */
    public void setCurrentPatient(Patient patient) {
        currentPatient = patient;
        if (currentPatient != null) {
            updateFireStore(AppointmentApp.doctorsCollection, uid, this);
        }
    }

    /**
     * returns current patient
     */
    public Patient getCurrentPatient() {
        return currentPatient;
    }
}
