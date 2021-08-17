package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    public String uid;
    public String name;
    public String email;
    public int age;
    public List<Doctor> appointments;
    public String medical_history;

    Patient(String uid, String name, String email, int age){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.age = age;
        this.medical_history = "";
        this.appointments = new ArrayList<Doctor>();
    }

    Patient(){
        this.uid = "";
        this.name = "";
        this.email = "";
        this.age = 0;
        this.medical_history = "";
        this.appointments = new ArrayList<Doctor>();

    }

}
