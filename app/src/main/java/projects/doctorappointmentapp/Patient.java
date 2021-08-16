package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    String name;
    String email;
    int age;
    String medical_history;

    Patient(String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
        this.medical_history = "";
    }

    Patient(){
        this.name = "name";
        this.email = "";
        this.age = 0;
        this.medical_history = "";
    }

}
