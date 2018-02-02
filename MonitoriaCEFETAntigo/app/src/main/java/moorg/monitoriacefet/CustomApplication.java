package moorg.monitoriacefet;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CustomApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
