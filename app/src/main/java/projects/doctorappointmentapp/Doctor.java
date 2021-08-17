package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    public String uid;
    public String name = "";
    public String location = "";
    public String email = "";
    public Patient currentPatient;
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
        this.name = "Dr.?";
        this.email = "";
        this.location = "location";
        this.currentPatient = null;
        this.waiting_list = new ArrayList<Patient>();
    }




}
