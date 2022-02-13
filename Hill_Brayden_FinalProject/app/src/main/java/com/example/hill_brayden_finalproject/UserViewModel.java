package com.example.hill_brayden_finalproject;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hill_brayden_finalproject.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserViewModel extends ViewModel {
//    Default flag means no user is signed in.
    Boolean signedFlag = false;
    Boolean scanSuccessFlag = true;
    MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    MutableLiveData<User> sendbackUser = new MutableLiveData<>();
    MutableLiveData<User> scannedUser = new MutableLiveData<>();

    public UserViewModel() {
        super();
    }

//    Initialize a user into the DB, using the UID from auth. || SENDBACK USER
    public static boolean initUser(String name, String UID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String TAG = "DB_newUser";
        final boolean[] flag = {true};

        User newUser = new User(UID,name,"","","","","","","");

        db.collection("users").document(UID)
                .set(newUser)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing document", e);
                    flag[0]=false;
                });

        return flag[0];
    }

//    Set a user in the live data for persistence || USER
    public void setUser(FirebaseUser uid) {
        this.user.setValue(uid);
    }

//    Return the FIREBASE AUTH user || USER
    public FirebaseUser getUser() {
        return user.getValue();
    }


//    Update Data || SENDBACK USER
    public boolean saveData(Map<String, Object> infoDict) {
        boolean[] flag = {true};
        String UID = user.getValue().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User saveUpdate = new User(sendbackUser.getValue().getUID(), sendbackUser.getValue().getName(),
                (String) infoDict.get("email"),
                (String) infoDict.get("phone"),
                (String) infoDict.get("role"),
                (String) infoDict.get("facebook"),
                (String) infoDict.get("instagram"),
                (String) infoDict.get("linkedin"),
                (String) infoDict.get("twitter"));

//        Save contact info
        db.collection("users").document(UID)
                .set(saveUpdate)
                .addOnSuccessListener(aVoid -> Log.d("INFO_UPDATE", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> {
                    Log.w("CONTACT", "Error writing document", e);
                    flag[0]=false;
                });



        return flag[0];
    }


//    Load stored data from Firestore || SENDBACK USER
    public MutableLiveData<User> loadData() {
        String UID = getUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(UID);


//        Retrieve name / UID info
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            sendbackUser.postValue(user);
        });

        return sendbackUser;
    }


//    Attempt to load data from QR CODE, if returns true, values have loaded into scannedUser || SCANNED USER
    public boolean attemptUidFetch(String scannedUID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef;

        try {
            docRef = db.collection("users").document(scannedUID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

//        Retrieve name / UID info
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            System.out.println("Scanned UID represents " + user.getName());
            scannedUser.postValue(user);
        }).addOnFailureListener(fail -> scanSuccessFlag = false);

        return scanSuccessFlag;
    }

//    Return the MLD user from scanned QR || SCANNED USER
    public MutableLiveData<User> getScannedUser () {
        return scannedUser;
    }
}
