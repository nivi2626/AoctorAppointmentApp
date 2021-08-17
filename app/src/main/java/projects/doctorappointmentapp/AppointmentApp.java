package projects.doctorappointmentapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AppointmentApp  extends Application {
    private static AppointmentApp appInstance=null;
    private static DoctorsDB doctorsDB;
    final static String doctorsCollection = "doctors";
    final static String patientsCollection = "patients";


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        doctorsDB = new DoctorsDB();
        FirebaseApp.initializeApp(this);


//        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
//        FirebaseApp.initializeApp(options);


    }

    public static AppointmentApp getAppInstance()
    {
        return appInstance;
    }

    public static DoctorsDB getDoctorsDB() {
        return doctorsDB;
    }
}
