package com.example.tuyendung;

import static com.example.tuyendung.DBmain.TABLE;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model>modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public myAdapter(Context context,int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.singledata = singledata;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.singledata,null );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Model model = modelArrayList.get( position );
        byte[] image = model.getProavatar();
        Bitmap bitmap= BitmapFactory.decodeByteArray( image,0, image.length );
        holder.imageavatar.setImageBitmap( bitmap );
        holder.txtname.setText( model.getName() );
        holder.txtdescription.setText( model.getDescription() );

        holder.layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickDetail(model);
            }
        } );

        //flow menu
        holder.flowmenu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenumenu = new PopupMenu( context,holder.flowmenu );
                popupMenumenu.inflate( R.menu.flow_menu );
                popupMenumenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_menu:
                                Bundle bundle=new Bundle();
                                bundle.putInt("id",model.getId());
                                bundle.putByteArray( "avatar",model.getProavatar() );
                                bundle.putString("name",model.getName());
                                bundle.putString("description",model.getDescription());
                                Intent i = new Intent(context,MainActivity.class);
                                i.putExtra( "userdata",bundle );
                                context.startActivity(i);
                                break;
                            case R.id.delete_menu:
                                DBmain dBmain = new DBmain( context );
                                sqLiteDatabase = dBmain.getReadableDatabase();
                                long recdelete=sqLiteDatabase.delete( TABLE,"id="+model.getId(),null );
                                if(recdelete!=-1){
                                    Toast.makeText( context, "data deleted",Toast.LENGTH_SHORT ).show();
                                    //remove position after deleted
                                    modelArrayList.remove( position );
                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;

                            default:
                                return false;
                        }
                        return false;
                    }
                } );
                popupMenumenu.show();
            }
        } );
    }

    public void release(){
        context =null;
    }

    private void setOnClickDetail(Model model) {
        Intent i = new Intent(context,Detail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable( "name",model );
        i.putExtras( bundle );
        context.startActivity( i );
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageavatar;
        CardView layout;
        TextView txtname,txtdescription;
        ImageButton flowmenu;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imageavatar = (ImageView) itemView.findViewById( R.id.viewavatar );
            txtname = (TextView) itemView.findViewById( R.id.txt_name );
            txtdescription = (TextView) itemView.findViewById( R.id.txt_description );
            flowmenu = (ImageButton) itemView.findViewById( R.id.flowmenu );
            layout = itemView.findViewById( R.id.layout );
        }
    }
}
