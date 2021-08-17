package projects.doctorappointmentapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * abstract user class
 */
public abstract class User {
    public String uid;
    public String name;
    public String email;
    private FirebaseFirestore fireStore;

    /**
     * updates user in fireStore
     */
    protected void updateFireStore(String collection, String uid , User user){
        FirebaseFirestore fireStore= FirebaseFirestore.getInstance();;
        fireStore.collection(collection).document(uid).set(user);
    }

    /**
     * updates user in fireStore and prefo
     */
    protected void updateFireStoreWithToast(String collection, String uid , User user, Context context, String successMsg, String failureMsg){
        FirebaseFirestore fireStore= FirebaseFirestore.getInstance();;
        fireStore.collection(collection).document(uid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, successMsg, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, failureMsg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
