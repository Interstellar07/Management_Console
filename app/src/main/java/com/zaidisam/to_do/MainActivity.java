package com.zaidisam.to_do;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
     public  RecyclerView rcv;
   public FloatingActionButton fab;
   public CheckBox chkbox;
    private DatabaseReference tododata,dtb;
   ArrayList<ToDoModel> arrtodo= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       // dtb = FirebaseDatabase.getInstance("https://money-manager-f4cfd-default-rtdb.firebaseio.com/").getReference().ch;
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.rcylview);
        rcv.setLayoutManager(new LinearLayoutManager(this));

            tododata =FirebaseDatabase.getInstance("https://money-manager-f4cfd-default-rtdb.firebaseio.com/").getReference().child("allwaste");

               tododata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String wastetype, time, date, id, location, wastenature,status,imgurl;
                int amountwaste;
                int s1,s2,s3;
                arrtodo.clear();
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    ToDoModel data = snap.getValue(ToDoModel.class);
                    wastetype = data.getWastetype().toString();
                    time = data.getTime();
                    date = data.getData();
                    location = data.getLocation();
                    wastenature = data.getWastenature();
                    imgurl = data.getImgurl();
                    amountwaste = data.getAmountwaste();
                    s1 = data.getS1();
                    s2= data.getS2();
                    s3 = data.getS3();
                   Log.d(TAG, snap.getKey());
                    Adapter adapter = new Adapter(MainActivity.this,arrtodo);
                    rcv.setAdapter(adapter);

                    arrtodo.add(new ToDoModel(wastetype,wastenature,time,date,"",location,amountwaste,"",imgurl,s1,s2,s3));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}