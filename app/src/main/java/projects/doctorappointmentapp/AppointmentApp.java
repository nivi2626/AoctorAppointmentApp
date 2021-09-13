package projects.doctorappointmentapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * main Application. initialize DB classes and fireBase
 */
public class AppointmentApp extends Application {
    private static AppointmentApp appInstance=null;
    private static DoctorsDB doctorsDB;
    // constants
    final static String doctorsCollection = "doctors";
    final static String patientsCollection = "patients";
    final static String MALE = "Male";
    final static String FEMALE = "Female";

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        doctorsDB = new DoctorsDB();
        FirebaseApp.initializeApp(this);
    }

    public static AppointmentApp getAppInstance() {
        return appInstance;
    }

    public static DoctorsDB getDoctorsDB() {
        return doctorsDB;
    }

}
