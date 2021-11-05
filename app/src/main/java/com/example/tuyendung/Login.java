package com.example.tuyendung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText email,password;
    Button btnSignIn;
    TextView tvCreateAccount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        email = findViewById( R.id.Email_login );

        password = findViewById( R.id.Password_login );

        progressBar= findViewById( R.id.progressBar1 );



        tvCreateAccount = findViewById( R.id.textView3 );
        tvCreateAccount.setOnClickListener( this );

        btnSignIn = findViewById( R.id.button );
        btnSignIn.setOnClickListener( this );

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textView3:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.button:
                userLogin();
                break;
        }
    }


    private void userLogin() {
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();


        if(mail.isEmpty()){
            email.setError( "Email is required" );
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher( mail ).matches()){
            email.setError( "Please enter valid email" );
            email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError( "Password is required" );
            password.requestFocus();
            return;
        }
        if(pass.length()< 6){
            password.setError( "Password must be 6 character above" );
            password.requestFocus();
            return;
        }
        progressBar.setVisibility( View.VISIBLE );

        mAuth.signInWithEmailAndPassword( mail,pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        if(mail.equals( "zzeroz0000@gmail.com") && pass.equals( "123456" ) ){
                            progressBar.setVisibility( View.GONE );
                            Toast.makeText( Login.this, "Hello Admin", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent(getApplicationContext(),AdminHome.class) );
                        }else {
                            progressBar.setVisibility( View.GONE );
                            Toast.makeText( Login.this, "Login successful", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent( getApplicationContext(), DisplayData.class ) );
                        }
                    }else{
                        progressBar.setVisibility( View.GONE );
                        user.sendEmailVerification();
                        Toast.makeText( Login.this, "Check your email to verify your account", Toast.LENGTH_SHORT ).show();
                    }
                }else{
                    Toast.makeText( Login.this, "Failed to login", Toast.LENGTH_SHORT ).show();
                    progressBar.setVisibility( View.GONE );
                }
            }
        } );
    }
}