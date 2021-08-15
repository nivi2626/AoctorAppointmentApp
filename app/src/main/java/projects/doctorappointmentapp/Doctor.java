package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    String name = "";
    String location = "";
    String email = "";
    String currentPatient;
    List<String> waiting_list;

    Doctor(String name, String email, String location){
        this.name = name;
        this.email = email;
        this.location = location;
        this.currentPatient = "";
        this.waiting_list = new ArrayList<String>();
    }

    Doctor(){
        this.name = "name";
        this.email = "";
        this.location = "location";
        this.currentPatient = "";
        this.waiting_list = new ArrayList<String>();
    }




}
