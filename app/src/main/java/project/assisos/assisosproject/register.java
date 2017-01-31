package project.assisos.assisosproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class register extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextLastName;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextConfirmPassword;
    private EditText editTextPassword;
    private Button buttonSignup;

    private ProgressDialog progressDialog;
    private RadioButton TermsOfService;
    private String registerLevel;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null.
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity

            //check if the user answer the questionnaire and select his friend.

            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), main_screen.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName=(EditText) findViewById(R.id.editTextName);
        editTextLastName=(EditText) findViewById(R.id.editTextLastName);
        editTextPhone=(EditText) findViewById(R.id.editTextPhone);
        editTextConfirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);
        TermsOfService=(RadioButton)findViewById(R.id.radioButton) ;
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String confirmPassword  = editTextConfirmPassword.getText().toString().trim();
        final String name  = editTextName.getText().toString().trim();
        final String lastName  = editTextLastName.getText().toString().trim();
        final String phone  = editTextPhone.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)||password.length()<6){
            Toast.makeText(this,"Please enter password with 6 or more characters",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword))
        {
            Toast.makeText(this,"invalid confirm password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastName))
        {
            Toast.makeText(this,"Please enter your last name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please enter your phone number",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!TermsOfService.isChecked())
        {
            Toast.makeText(this,"Accept terms of service.",Toast.LENGTH_SHORT).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            DatabaseReference  myRef1 = FirebaseDatabase.getInstance().getReference();
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Email").setValue(email);
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Password").setValue(password);
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name").setValue(name);
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LastName").setValue(lastName);
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Phone").setValue(phone);
                            myRef1.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("RegisterLevel").setValue("1");






                            finish();
                            startActivity(new Intent(getApplicationContext(),friend_list.class));
                        }else{
                            //display some message here
                            Toast.makeText(register.this,"Registration Error:invalid email",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }
    }
}