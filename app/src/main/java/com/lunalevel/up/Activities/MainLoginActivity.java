package com.lunalevel.up.Activities;

import android.content.ContentProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lunalevel.up.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;


public class MainLoginActivity extends AppCompatActivity  {



    // UI references.
    private AutoCompleteTextView emailEntry;
    private EditText passwordEntry;
    private ProgressBar progressBar;
    private Button signInButton;
    private View mLoginFormView;

    //preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;


    //for interfacing with firebase
    private FirebaseAuth userAuth;
    private FirebaseDatabase fireData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init global vars
        pref = getPreferences(MODE_PRIVATE);
        prefEditor=pref.edit();
        userAuth=FirebaseAuth.getInstance();

        prefEditor.putBoolean("isFirstRun",true);
        prefEditor.apply();

        if(pref.getBoolean("isFirstRun",false))
        {
            //first run ops here
            System.out.println("First run tasks...");
            prefEditor.putBoolean("isFirstRun",false);//reset b
        }

        //debug bypass
        //Intent debugTo=new Intent(this,HomeScreenActivity.class);
        //startActivity(debugTo);

        //add to bundle



        setContentView(R.layout.activity_main_login);

        emailEntry=(AutoCompleteTextView) findViewById(R.id.loginEmailEntry);
        passwordEntry=(EditText) findViewById(R.id.loginPasswordEntry);
        progressBar=(ProgressBar) findViewById(R.id.loginProgressBar);
        signInButton=(Button) findViewById(R.id.email_sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidPass(passwordEntry.getText().toString()) && isValidEmail(emailEntry.getText().toString())){
                //begin login attempt
                    attemptLogin(passwordEntry.getText().toString(), emailEntry.getText().toString());
                    signInButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);



                }
            }
        });


        //styling
        progressBar.setVisibility(View.INVISIBLE);





    }


    private boolean isValidPass(String text){
        return text.length() >=6;
    }
    private boolean isValidEmail(String text){
        return (text.contains("@"))&& (text.length()>=4);
    }

    private void attemptLogin(String password, String email){
        userAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("login", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()) {
                            //proceed with initial steps
                            Toast.makeText(MainLoginActivity.this, R.string.message_welcome, Toast.LENGTH_SHORT).show();

                            //first lets deal with the backend, we need to check if this is a new account by the nonexistence of a profile
                            fireData=FirebaseDatabase.getInstance();
                            DatabaseReference profile= fireData.getReferenceFromUrl("https://upapp-ee14d.firebaseio.com/Service/Users/Profiles");


                            //set profile data? will plan to intent to anothr screen to do this but i have to successfulyl write to the database
                            Map<String,Object> update= new HashMap<>();

                            update.put(userAuth.getCurrentUser().getUid(), userAuth.getCurrentUser().getEmail());

                            profile.updateChildren(update);


                            Toast.makeText(MainLoginActivity.this, profile.getKey()  +" connection established!", Toast.LENGTH_SHORT).show();

                            //object to byte array


                            //progress he intents
                            Bundle firebaseInfo=new Bundle();
                            firebaseInfo.putString("Current User",userAuth.getCurrentUser().getUid());
                            firebaseInfo.putSerializable("userAuth",userAuth.getClass());
                            firebaseInfo.putSerializable("data",fireData.getClass());
                            Intent loggedIn=new Intent(MainLoginActivity.this,HomeScreenActivity.class);
                            loggedIn.putExtra("saved",firebaseInfo);
                            startActivity(loggedIn);
                            MainLoginActivity.this.finish();





                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("login", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainLoginActivity.this, R.string.auth_fail,
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            signInButton.setVisibility(View.VISIBLE);

                        }

                        // ...
                    }
                });
    }



}

