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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText email,name,password,repassword;
    Button btnReg;
    FirebaseAuth mAuth;
    TextView tvSignIn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        mAuth = FirebaseAuth.getInstance();




        btnReg =  findViewById( R.id.button2 );
        btnReg.setOnClickListener( this );
        tvSignIn =  findViewById( R.id.textView3 );
        tvSignIn.setOnClickListener( this );

        progressBar=(ProgressBar) findViewById( R.id.progressBar );

        email = findViewById( R.id.Email_register );
        name = findViewById( R.id.Name_register );
        password =  findViewById( R.id.Password_register );
        repassword =  findViewById( R.id.Repassword_register );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView3:
                startActivity( new Intent(this,Login.class) );
                break;
            case R.id. button2:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String mail = email.getText().toString().trim();
        String fullname = name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String repass = repassword.getText().toString().trim();

        if(mail.isEmpty()){
            email.setError( "Email is required" );
            email.requestFocus();
            return;
        }
        if(fullname.isEmpty()){
            name.setError( "Name is required" );
            name.requestFocus();
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
        if(!Patterns.EMAIL_ADDRESS.matcher( mail ).matches()){
            email.setError( "Please provide valid email" );
            email.requestFocus();
            return;
        }
        if(pass.equals( repass )==false){
            repassword.setError( "Password is not match" );
            repassword.requestFocus();
            return;
        }
        progressBar.setVisibility( View.VISIBLE );
        mAuth.createUserWithEmailAndPassword(mail,pass )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(mail,fullname);

                    FirebaseDatabase.getInstance().getReference("Users").child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid() ).setValue( user )
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    
                                    if(task.isSuccessful()){
                                        Toast.makeText( Register.this, "User has been registered successfully", Toast.LENGTH_LONG ).show();
                                        progressBar.setVisibility( View.GONE );
                                        startActivity( new Intent(getApplicationContext(),Login.class) );
                                    }else{
                                        Toast.makeText( Register.this,"Failed to register! try again",Toast.LENGTH_LONG ).show();
                                        progressBar.setVisibility( View.GONE );
                                    }
                                }
                            } );
                }else{
                    Toast.makeText( Register.this, "Failed to register", Toast.LENGTH_LONG ).show();
                    progressBar.setVisibility( View.GONE );
                }
            }
        } );
    }
}