package com.lunalevel.up.Activities;

import android.content.ContentProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lunalevel.up.R;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
    private static File byteRaw=null;
    private static boolean doesExist=false;

    //cred
    private static boolean credExists=false;
    private static boolean autoIn=false;
    private static Toast unrememberToast,remembertoast;

    //control booleans
    boolean firstUse=false;

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

        //set view and style layout

        setContentView(R.layout.activity_main_login);

        emailEntry=(AutoCompleteTextView) findViewById(R.id.loginEmailEntry);
        passwordEntry=(EditText) findViewById(R.id.loginPasswordEntry);

        //define toasts for sec exchange
        unrememberToast=Toast.makeText(MainLoginActivity.this,R.string.message_rem_on,Toast.LENGTH_SHORT);
        remembertoast=Toast.makeText(MainLoginActivity.this,R.string.message_rem_off,Toast.LENGTH_SHORT);

        //style system ui for text view


        View urToastView=unrememberToast.getView();
        urToastView.setBackground(ContextCompat.getDrawable(MainLoginActivity.this,R.drawable.rounded_corner));
        urToastView.findViewById(android.R.id.message).setBackgroundColor(Color.BLACK);

        View rToastView=remembertoast.getView();
        rToastView.setBackground(ContextCompat.getDrawable(MainLoginActivity.this,R.drawable.rounded_corner));
        rToastView.findViewById(android.R.id.message).setBackgroundColor(Color.BLACK);



        ((CheckBox) findViewById(R.id.login_checkbox_remeber)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    remembertoast.show();
                    if(credExists) {
                        prefEditor.remove("crd");
                        prefEditor.remove("em");
                        prefEditor.commit();
                        credExists=false;
                    }
                }
                if(isChecked){
                    if(!credExists)
                        unrememberToast.show();

                }
            }
        });


        //handle saved sec //this mode is auto, can also do a fill mode
        if(pref.contains("crd")){
            credExists=true;
            if(autoIn) {
                attemptLogin(pref.getString("crd", null), pref.getString("em", null));
            }
            else{
                ((CheckBox) findViewById(R.id.login_checkbox_remeber)).setChecked(true);

                emailEntry.setText(new String(Base64.decode(pref.getString("em",null),Base64.URL_SAFE)));
                passwordEntry.setText(new String(Base64.decode(pref.getString("crd",null),Base64.DEFAULT)));
            }
        }

        progressBar=(ProgressBar) findViewById(R.id.loginProgressBar);
        signInButton=(Button) findViewById(R.id.email_sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidPass(passwordEntry.getText().toString()) && isValidEmail(emailEntry.getText().toString())){
                //begin login attempt
                    attemptLogin(Base64.encodeToString(passwordEntry.getText().toString().getBytes(),Base64.DEFAULT), Base64.encodeToString(emailEntry.getText().toString().getBytes(),Base64.URL_SAFE));
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
       //handle remember pass

        CheckBox remPass= (CheckBox) findViewById(R.id.login_checkbox_remeber);
        remPass.setVisibility(View.GONE);
        if(remPass.isChecked() && !credExists){
            prefEditor.putString("crd",password);
            prefEditor.putString("em",email);
            prefEditor.apply();


        }






        userAuth.signInWithEmailAndPassword(new String(Base64.decode(email,Base64.URL_SAFE)), new String(Base64.decode(password,Base64.DEFAULT)))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("login", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            //proceed with initial steps


                            doesExist = true;

                            //for security should probably check to see if it already exists will do later
                            //second gather data into file
                            //second lets deal with the backend, we need to check if this is a new account by the nonexistence of a profile
                            fireData = FirebaseDatabase.getInstance();
                            DatabaseReference profile = fireData.getReferenceFromUrl("https://upapp-ee14d.firebaseio.com/Service/Users/Profiles");
                            final StorageReference initPath = FirebaseStorage.getInstance().getReference("Service/Users/Profiles");
                            //perform check for user.dat which is the file dropped after completing profile;
                            initPath.child("Profiles/" + userAuth.getCurrentUser().getUid() + "/data/user.dat").getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.err.println(e.getLocalizedMessage() + " Don't worry this is just a check");
                                    doesExist = false;
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    System.out.println(FirebaseStorage.getInstance().getReference("Service/Users/Profiles"));
                                    System.out.println("Found Profile? ->" + doesExist);

                                    if (!doesExist) {
                                        //user does not have profile on record!
                                        StorageReference initOut = initPath.child("Profiles/" + userAuth.getCurrentUser().getUid() + "/data/profile.dat");

                                        try {

                                            File initDat = new File(MainLoginActivity.this.getApplicationContext().getFilesDir().getAbsolutePath() + "\\User\\Data\\Profile.txt");
                                            PrintWriter pw = new PrintWriter(initDat);
                                            pw.println();
                                            pw.println("Begin User Data");
                                            pw.println();
                                            pw.println("UID: " + userAuth.getCurrentUser().getUid());
                                            pw.println("MAIL: " + userAuth.getCurrentUser().getEmail());
                                            //additional stuff
                                            pw.close();

                                            //sec
                                            byte[] raw = Base64.encode(IOUtils.toByteArray(new FileInputStream(initDat)), Base64.DEFAULT); //packaging complete
                                            boolean fileSec = initDat.delete();

                                            //byteText
                                            byteRaw = new File(MainLoginActivity.this.getApplicationContext().getFilesDir().getAbsolutePath() + "\\User\\Data\\Profile.txt");
                                            FileUtils.writeByteArrayToFile(byteRaw, raw);


                                        } catch (IOException ioe) {
                                            System.err.println(ioe.getLocalizedMessage());
                                        }


                                        //update database keys?


                                        //push data to storage
                                        if (byteRaw != null) {
                                            initOut.putFile(Uri.fromFile(byteRaw)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                    try {
                                                        System.out.println("Task Complete..." + " " + new Scanner(new FileInputStream(byteRaw)).nextLine());
                                                    } catch (FileNotFoundException fnf) {
                                                        System.err.println(fnf.getLocalizedMessage());
                                                    }
                                                }
                                            });

                                            firstUse = true;
                                        }
                                    }
                                    else{
                                        firstUse=false;
                                    }

                                if (!firstUse) {
                                    Intent loggedIn = new Intent(MainLoginActivity.this, HomeScreenActivity.class);
                                    Toast welcomeToast = Toast.makeText(MainLoginActivity.this, R.string.message_welcome, Toast.LENGTH_SHORT);
                                    View welcomeToastView = welcomeToast.getView();
                                    welcomeToastView.setBackground(ContextCompat.getDrawable(MainLoginActivity.this, R.drawable.rounded_corner));
                                    welcomeToastView.findViewById(android.R.id.message).setBackgroundColor(Color.BLACK);
                                    welcomeToast.show();
                                    startActivity(loggedIn);
                                    MainLoginActivity.this.finish();
                                } else {
                                    Intent toProfileCreate = new Intent(MainLoginActivity.this, ProfileCreateActivity.class);
                                    startActivity(toProfileCreate);
                                    MainLoginActivity.this.finish();
                                }



                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                }

                            });
                        }

                            if (!task.isSuccessful()) {
                                Log.w("login", "signInWithEmail:failed", task.getException());
                                Toast badPassToast = Toast.makeText(MainLoginActivity.this, R.string.auth_fail,
                                        Toast.LENGTH_SHORT);
                                View badPassToastView = badPassToast.getView();
                                badPassToastView.setBackgroundColor(Color.BLACK);
                                badPassToastView.setBackground(ContextCompat.getDrawable(MainLoginActivity.this, R.drawable.rounded_corner));
                                badPassToast.show();

                                progressBar.setVisibility(View.INVISIBLE);
                                signInButton.setVisibility(View.VISIBLE);

                            }
                        }

                        });







    }

}

