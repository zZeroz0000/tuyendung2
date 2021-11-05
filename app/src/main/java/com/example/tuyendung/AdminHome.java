package com.example.tuyendung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import pl.droidsonroids.gif.GifImageView;

public class AdminHome extends AppCompatActivity {

    GifImageView gif_logout,gif_edit;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_home );

        mAuth = FirebaseAuth.getInstance();

        gif_edit = findViewById( R.id.gif_edit );
        gif_logout = findViewById( R.id.gif_logout );

        gif_edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(),MainActivity.class) );
            }
        } );

        gif_logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext(),Login.class));
            }
        } );
    }

}