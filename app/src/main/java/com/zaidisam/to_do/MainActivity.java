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
    private DatabaseReference tododata;
   ArrayList<ToDoModel> arrtodo= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.rcylview);
        rcv.setLayoutManager(new LinearLayoutManager(this));
           // arrtodo.add(new ToDoModel("ABCD Task","5th Jan","Priority: Low",1));

           fab = findViewById(R.id.fab);
           fab.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  addnote();
              }
          });
        tododata= FirebaseDatabase.getInstance().getReference(android_id).child("Tasks");

        
                // tododata.child("Task1").setValue("Test");
               tododata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String task,date,priority;
                int status;
                arrtodo.clear();
                Log.d(TAG, "Inside Data Change Main activity ");
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    Log.d(TAG, "Inside loop main axtivity");
                    ToDoModel data = snap.getValue(ToDoModel.class);
                    task = data.getTask().toString();
                    date = data.getDate().toString();
                    priority = data.getPriority().toString();
                   Log.d(TAG, snap.getKey());
                  //  Log.d(TAG, date);
                  //  Log.d(TAG, priority);
                    Adapter adapter = new Adapter(MainActivity.this,arrtodo);
                    rcv.setAdapter(adapter);

                    arrtodo.add(new ToDoModel(task,date,priority,data.getStatus(),snap.getKey()));


                  //  System.out.println("huehuehue"+arrtodo.get(1).priority);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       // for(int i=1;i<=2;i++)
          // System.out.println("huehuehue"+arrtodo.get(i).priority);





    }

    private void addnote() {
        Log.d(TAG, "Inside: ");


        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater =LayoutInflater.from(this);
        View myView =inflater.inflate(R.layout.input_layout,null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        final Spinner itemSpinner= myView.findViewById(R.id.itemsspinner);
        final EditText task = myView.findViewById(R.id.task);
        final Button cancel = myView.findViewById(R.id.cancel);
        final Button save = myView.findViewById(R.id.save);
        final EditText datetim = myView.findViewById(R.id.datetime);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tasktxt = task.getText().toString();
                String p = itemSpinner.getSelectedItem().toString();
                String datetime = datetim.getText().toString();

                String id = tododata.push().getKey();
                ToDoModel data = new ToDoModel(tasktxt,datetime,p,0,id);
                tododata.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
                dialog.dismiss();


            }
        });
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}