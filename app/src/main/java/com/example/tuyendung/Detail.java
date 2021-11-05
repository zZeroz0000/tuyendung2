package com.example.tuyendung;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        Bundle bundle = getIntent().getExtras();
        if(bundle ==null){
            return;
        }

        Model model = (Model) bundle.get("name");

        TextView tv = findViewById( R.id.name );
        TextView tv1 = findViewById( R.id.name2 );



        tv.setText(model.getName());
        tv1.setText(model.getDescription());

    }
}