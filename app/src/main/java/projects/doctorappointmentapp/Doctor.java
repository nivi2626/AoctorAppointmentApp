package projects.doctorappointmentapp;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

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
        this.name = "Dr.?";
        this.email = "";
        this.location = "location";
        this.currentPatient = null;
        this.waiting_list = new ArrayList<Patient>();
    }

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

    public void addToWaitingList(Patient patient){
        waiting_list.add(patient);
        updateFireStore();
    }

    public void setCurrentPatient(Patient patient) {
        currentPatient = patient;
        updateFireStore();
    }

    private void updateFireStore(){
        fireStore.collection(AppointmentApp.doctorsCollection).document(uid).set(this);
    }




}
