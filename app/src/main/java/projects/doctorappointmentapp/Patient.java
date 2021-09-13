package projects.doctorappointmentapp;

import java.util.ArrayList;
import java.util.List;

/**
 * object to represent a patient-user
 */
public class Patient extends User{
    public int age;
    private List<Doctor> appointments;
    public String medical_history;

    Patient(String uid, String name, String email, int age, String gender){
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.medical_history = "";
        this.appointments = new ArrayList<>();
    }

    Patient(){
        this.uid = "";
        this.gender = AppointmentApp.MALE;
        this.name = "";
        this.email = "";
        this.age = 0;
        this.medical_history = "";
        this.appointments = new ArrayList<>();

    }

    /**
     * adds the given doctor to the patient's appointments
     */
    public void addToAppointments(Doctor doctor) {
        appointments.add(doctor);
        updateFireStore(AppointmentApp.patientsCollection, uid, this);
    }

    /**
     * removes the given doctor from patient's appointments
     */
    public void removeFromAppointments(Doctor doctor){
        appointments.remove(doctor);
        updateFireStore(AppointmentApp.patientsCollection, uid, this);
    }

    public void sendNotification() {
        //todo
    }

    /**
     * @param doctor - check for appointments with this doctor
     * @return true if patient already scheduled an appointment with this doctor, false else
     */
    public Boolean checkIfScheduled(Doctor doctor){
        for (Doctor doc:appointments){
            if (doc == doctor) {
                return true;
            }
        }
        return false;
    }
}
