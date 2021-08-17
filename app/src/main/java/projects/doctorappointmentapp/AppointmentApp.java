package projects.doctorappointmentapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * main Application. initialize DB classes and fireBase
 */
public class AppointmentApp extends Application {
    private static AppointmentApp appInstance=null;
    private static DoctorsDB doctorsDB;
    private static PatientsDB patientsDB;
    final static String doctorsCollection = "doctors";
    final static String patientsCollection = "patients";


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        doctorsDB = new DoctorsDB();
        patientsDB = new PatientsDB();
        FirebaseApp.initializeApp(this);
    }

    public static AppointmentApp getAppInstance()
    {
        return appInstance;
    }

    public static DoctorsDB getDoctorsDB() {
        return doctorsDB;
    }

    public static PatientsDB getPatientsDB() {
        return patientsDB;

    }
}
