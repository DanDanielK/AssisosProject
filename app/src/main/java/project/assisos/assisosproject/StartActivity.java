package project.assisos.assisosproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String registerLevel="error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){

            //that means user is already logged in

            //check if the user answer the questionnaire and select his friend.

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("RegisterLevel").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    registerLevel =(String)dataSnapshot.getValue();
                   /*
                   1-> finish the register screen
                   2->finish  the friend_list screen
                   3->finish the questionnaire
                    */

                    if(registerLevel.equals("1"))
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(),friend_list.class));
                    }
                    else if(registerLevel.equals("2"))
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(),questionnaire.class));
                    }
                    else if(registerLevel.equals("3"))
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(),main_screen.class));
                    }
                    else
                    {
                        setContentView(R.layout.activity_start);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }
        else
        {
            setContentView(R.layout.activity_start);
        }

    }

    public void goto_login(View v)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }




    public void goto_register(View view)
    {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);

    }
}
