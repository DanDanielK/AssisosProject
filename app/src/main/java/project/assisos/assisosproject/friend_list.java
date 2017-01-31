package project.assisos.assisosproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class friend_list extends AppCompatActivity {

    private EditText FriendName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        FriendName = (EditText) findViewById(R.id.FriendName);
    }

    public void goto_questionnaire(View v) {
        registerUser();
    }

    private void registerUser() {

        String FName = FriendName.getText().toString().trim();
        if (TextUtils.isEmpty(FName)) {
            Toast.makeText(this, "Please enter your friend name", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("FriendName").setValue(FName);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("RegisterLevel").setValue("2");


        finish();
        startActivity(new Intent(getApplicationContext(),questionnaire.class));
    }
}