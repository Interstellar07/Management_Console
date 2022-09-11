package com.zaidisam.to_do;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter <Adapter.viewholder>{
    private DatabaseReference tododata;


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
         int status=0;
        String android_id = Settings.Secure.getString(this.context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
         tododata = FirebaseDatabase.getInstance().getReference(android_id).child("Tasks").child(arrayList.get(position).key);
             holder.task.setText(arrayList.get(position).task);
             holder.date.setText("Due Date: "+arrayList.get(position).date);
             holder.priority.setText("Priority: "+arrayList.get(position).priority);
             status= arrayList.get(position).status;
             id = arrayList.get(position).key.toString();
           if (status== 1) {
            holder.chkbox.setChecked(true);
            holder.task.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.priority.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.date.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else
            holder.chkbox.setChecked(false);

             holder.chkbox.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     tododata = FirebaseDatabase.getInstance().getReference(android_id).child("Tasks").child(arrayList.get(position).key);
                     System.out.println("oiiiiiiiiiiiiiiiiii"+holder.getAdapterPosition());

                     System.out.println("THISSSSSSSSSSSS clicled");
                     if(holder.chkbox.isChecked()) {
                         Toast.makeText(context,"Yayy....Task Completed",Toast.LENGTH_SHORT).show();
                         tododata.child("status").setValue(1);
                         holder.task.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                         holder.priority.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                         holder.date.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                     }
                     else
                         tododata.child("status").setValue(0);

                 }
             });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tododata = FirebaseDatabase.getInstance().getReference(android_id).child("Tasks").child(arrayList.get(position).key);
                Toast.makeText(context,"Task Deleted",Toast.LENGTH_SHORT).show();
                id = arrayList.get(position).key.toString();
                tododata.removeValue();


            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView task,date,priority;
        CheckBox chkbox;
        int status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            task=(TextView)itemView.findViewById(R.id.chkboxtxt);
            date = itemView.findViewById(R.id.date);
            chkbox = itemView.findViewById(R.id.chkboxtxt);
            priority = itemView.findViewById(R.id.priority);

        }
    }
}
