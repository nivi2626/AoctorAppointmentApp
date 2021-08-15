package projects.doctorappointmentapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class AppointmentApp  extends Application {
    private static AppointmentApp appInstance=null;
    private DoctorsDB doctorsDB;


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

//        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
//        FirebaseApp.initializeApp(options);


    }

    public static AppointmentApp getAppInstance()
    {
        return appInstance;
    }

    public DoctorsDB getDoctorsDB() {
        return doctorsDB;
    }
}
