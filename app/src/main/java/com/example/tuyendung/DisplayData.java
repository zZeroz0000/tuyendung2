package com.example.tuyendung;

import static com.example.tuyendung.DBmain.TABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    myAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_display_data );

        dBmain=new DBmain( this );
        findid();
        displayData();
        recyclerView.setLayoutManager( new LinearLayoutManager( this,RecyclerView.VERTICAL,false ) );

    }

    private void displayData() {
        sqLiteDatabase =dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery( "select * from "+TABLE+"",null );
        ArrayList<Model>models= new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt( 0 );
            byte[] avatar = cursor.getBlob( 1 );
            String name = cursor.getString( 2 );
            String description = cursor.getString( 3 );
            models.add( new Model( id, avatar, name, description ) );
        }
        cursor.close();
        myAdapter=new myAdapter( this,R.layout.singledata,models,sqLiteDatabase );
        recyclerView.setAdapter( myAdapter );
    }

    private void findid() {
        recyclerView = findViewById( R.id.rv );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myAdapter !=null){
            myAdapter.release();
        }
    }
}