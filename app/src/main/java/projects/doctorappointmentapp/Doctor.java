package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

/**
 * object to represent a doctor-user
 */
public class Doctor extends User{
    public String location;
    public List<Patient> waiting_list;


    Doctor(String uid, String name, String email, String location, String gender){
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.location = location;
        this.waiting_list = new ArrayList<Patient>();
    }

    Doctor(){
        this.uid = "";
        this.name = "Dr ...";
        this.gender = AppointmentApp.MALE;
        this.email = "";
        this.location = "location";
        this.waiting_list = new ArrayList<Patient>();
    }

    /***
     * @return the doctor's waiting list in a format of string - each patient's name in a new line
     */
    public String getWaitingListParsed() {
        StringBuilder result = new StringBuilder();
        for (int j=1; j<this.waiting_list.size(); j++) {
            Patient p = this.waiting_list.get(j);
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
        updateUserInFireStore(AppointmentApp.doctorsCollection, uid, this);
    }

    /***
     * removes patient to the doctors waiting list
     * @param patient - patient to remove
     */
    public void removeFromWaitingList(Patient patient) {
        waiting_list.remove(patient);
        updateUserInFireStore(AppointmentApp.doctorsCollection, uid, this);
    }


    /**
     * returns current patient
     */
    public Patient getCurrentPatient() {
        if (waiting_list.size() > 1) {
            return waiting_list.get(0);
        }
        return null;
    }
}
