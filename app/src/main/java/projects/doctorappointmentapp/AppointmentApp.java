package projects.doctorappointmentapp;

import android.app.Application;

public class AppointmentApp  extends Application {
    private static AppointmentApp appInstance=null;
    private DoctorsDB doctorsDB;
    // todo - change to constants to Strings (references and such)


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        doctorsDB = new DoctorsDB();


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
