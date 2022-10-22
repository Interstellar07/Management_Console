package com.zaidisam.to_do;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter <Adapter.viewholder>{
    private DatabaseReference tododata;
    public DatabaseReference dtb;


   Context context;
   ArrayList<ToDoModel> arrayList;
   public String id;

     Adapter(Context context , ArrayList<ToDoModel> arrayList)
     {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todolayout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

         String s0 = arrayList.get(position).s0;
         String id = arrayList.get(position).id;
         System.out.println("AAH"+s0);
        System.out.println("AAHID"+id);
         dtb = FirebaseDatabase.getInstance("https://money-manager-f4cfd-default-rtdb.firebaseio.com/").getReference().child("Waste Generations").child(s0).child(id);
        tododata =FirebaseDatabase.getInstance("https://money-manager-f4cfd-default-rtdb.firebaseio.com/").getReference().child("allwaste");
         holder.wastetype.setText("Waste Type: "+ arrayList.get(position).wastetype);
         holder.date.setText("Date: "+arrayList.get(position).data);
         holder.location.setText("Location: "+arrayList.get(position).location);
         if(arrayList.get(position).s1==1&&arrayList.get(position).s2==0&&arrayList.get(position).s3==0)
         holder.status.setText("Status: Not Disposed");
         else if(arrayList.get(position).s1==1&&arrayList.get(position).s2==1&&arrayList.get(position).s3==0)
             holder.status.setText("Status: Request Acknowledged");
         else if(arrayList.get(position).s1==1&&arrayList.get(position).s2==1&&arrayList.get(position).s3==1)
             holder.status.setText("Status: Disposed");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView type;
                TextView nature;
                TextView weight;
                TextView date;
                TextView location;
                ImageView image;
                Button share;
                CheckBox s1,s2,s3;
                AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View myView = inflater.inflate(R.layout.expandedview,null);
                type = myView.findViewById(R.id.type);
                share = myView.findViewById(R.id.share);
                s1= myView.findViewById(R.id.step1);
                s2 = myView.findViewById(R.id.step2);
                s3 = myView.findViewById(R.id.step3);

                  s1.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          if(s1.isChecked()) {
                              dtb.child("s1").setValue(1);
                              tododata.child(id).child("s1").setValue(1);
                          }
                          else {
                              dtb.child("s1").setValue(0);
                              tododata.child(id).child("s1").setValue(0);

                          }
                      }


                  });
                  s2.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          if(s1.isChecked()) {
                              dtb.child("s2").setValue(1);
                              tododata.child(id).child("s2").setValue(1);
                          }
                          else {
                              dtb.child("s2").setValue(0);
                              tododata.child(id).child("s2").setValue(0);

                          }


                      }
                  });
                s3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(s1.isChecked()) {
                            dtb.child("s3").setValue(1);
                            tododata.child(id).child("s3").setValue(1);
                        }
                        else {
                            dtb.child("s3").setValue(0);
                            tododata.child(id).child("s3").setValue(0);

                        }

                    }
                });


                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Uri uri = Uri.parse("smsto:" + );
                        String w= "*GARBAGE DISPOSAL ORDER*\n\n"+
                        "*Details:*\n" +"1. Type: "+
                                arrayList.get(position).wastetype+"\n"+"2. Nature: "+arrayList.get(position).wastenature+"\n"+"3. Weight: "+arrayList.get(position).amountwaste+"\n"+"4. Date: "+arrayList.get(position).data.toString()+"\n"+"5. Location: "+arrayList.get(position).location;
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.setType("text/plain").putExtra(Intent.EXTRA_TEXT,w);
                        i.setPackage("com.whatsapp");
                        startActivity(context,i,null);
                    }
                });
                type.setText("Type: "+arrayList.get(position).wastetype);
                nature = myView.findViewById(R.id.Nature);
                nature.setText("Nature: "+arrayList.get(position).wastenature);
                weight = myView.findViewById(R.id.Weight);
                weight.setText("Weight: "+arrayList.get(position).amountwaste);
                date = myView.findViewById(R.id.Date);
                date.setText("Date: "+arrayList.get(position).data.toString());
                location = myView.findViewById(R.id.Location);
                location.setText("Location: "+arrayList.get(position).location);
                image = myView.findViewById(R.id.wasteImage);
                Picasso.get()
                        .load(arrayList.get(position).imgurl)
                        .into(image);
                myDialog.setView(myView);
                final AlertDialog dialog = myDialog.create();
                myDialog.show();
                dialog.setCancelable(false);
            }
        });

        Picasso.get()
                .load(arrayList.get(position).imgurl)
                .into(holder.wstimg);
        System.out.println("AAAAAH"+arrayList.get(position).imgurl);




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView wastetype,location,date,status;
        ImageView wstimg;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            wstimg = itemView.findViewById(R.id.imageview);
           wastetype = itemView.findViewById(R.id.Rtvwsttype);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            status = itemView.findViewById(R.id.status);

        }
    }
}
