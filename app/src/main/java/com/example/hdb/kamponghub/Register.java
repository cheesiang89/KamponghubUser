package com.example.hdb.kamponghub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Register extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email,password;
    private Button signUpBtn;
    private ProgressDialog progressDialog;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.r_email);
        password = (EditText) findViewById(R.id.r_password);
        signUpBtn = (Button) findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        actionBar = getSupportActionBar();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                registerUser();
            }
        });
        actionBar.setDisplayHomeAsUpEnabled(true); //needed to set up the action bar to display the navigation back button to MainActivity
    }

    //method to create a navigation back button back to the MainActivity
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    //method to register the user email and password into firebase auth table
    protected void registerUser() {
        String emailText = email.getText().toString().trim();
        String passwordText  = password.getText().toString().trim();

        if(TextUtils.isEmpty(emailText)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passwordText)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty, display progress dialog
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);

                        }else{
                            //display some message here
                            Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                        }
                });

    }
}
