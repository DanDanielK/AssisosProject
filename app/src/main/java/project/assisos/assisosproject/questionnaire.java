package project.assisos.assisosproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static project.assisos.assisosproject.R.id.Greetings;

public class questionnaire extends AppCompatActivity {
    Spinner spinner, spinner2;
    ArrayAdapter<CharSequence> adapter, adapter2;
    String relationship,education;
    private EditText Greetings;
    private FirebaseAuth firebaseAuth;
    private EditText Age,Sisters,Brothers,Ppl,Pets,Hobbies,Music_genres;
    private EditText Job,Wishes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        firebaseAuth = FirebaseAuth.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //--------------------
        Greetings=(EditText) findViewById(R.id.Greetings);
        Age=(EditText) findViewById(R.id.age);
        Sisters=(EditText) findViewById(R.id.sisters);
        Brothers=(EditText) findViewById(R.id.brothers);
        Ppl=(EditText) findViewById(R.id.ppl);
        Pets=(EditText) findViewById(R.id.pets);
        Hobbies=(EditText) findViewById(R.id.hobbies);
        Music_genres=(EditText) findViewById(R.id.music_genres);
        Job=(EditText) findViewById(R.id.job);
        Wishes=(EditText) findViewById(R.id.wishes);
        //--------------------

        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.single_or_not, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                relationship = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.student_or_not, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                education = parent.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //add birthday

    }


    public void goto_main_screen(View v) {

        processthequestionnaire();

    }



    private void processthequestionnaire(){
        final String greetings  = Greetings.getText().toString().trim();
        final String age  = Age.getText().toString().trim();
        final String sisters  = Sisters.getText().toString().trim();
        final String brothers  = Brothers.getText().toString().trim();
        final String ppl  = Ppl.getText().toString().trim();
        final String pets  = Pets.getText().toString().trim();
        final String hobbies  = Hobbies.getText().toString().trim();
        final String music_genres  = Music_genres.getText().toString().trim();
        final String job  = Job.getText().toString().trim();
        final String wishes  = Wishes.getText().toString().trim();

        if(TextUtils.isEmpty(greetings)){
            Toast.makeText(this,"Please answer question number 1",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this,"Please answer question number 2",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(brothers)||TextUtils.isEmpty(sisters)){
            Toast.makeText(this,"Please answer question number 3",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(ppl)){
            Toast.makeText(this,"Please answer question number 4",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pets)){
            Toast.makeText(this,"Please answer question number 6",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(hobbies)){
            Toast.makeText(this,"Please answer question number 7",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(music_genres)){
            Toast.makeText(this,"Please answer question number 8",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(job)){
            Toast.makeText(this,"Please answer question number 10",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(wishes)){
            Toast.makeText(this,"Please answer question number 11",Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Greetings").setValue(greetings);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Age").setValue(age);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Sisters").setValue(sisters);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Brothers").setValue(brothers);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Ppl").setValue(ppl);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Pets").setValue(pets);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobbies").setValue(hobbies);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Music_genres").setValue(music_genres);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Job").setValue(job);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Wishes").setValue(wishes);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Relationship").setValue(relationship);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Education").setValue(education);
        myRef1.child("users").child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("RegisterLevel").setValue("3");


        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);

    }



}