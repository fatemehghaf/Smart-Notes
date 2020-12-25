package com.example.smartnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {
Intent data;
EditText editNoteTitle;
EditText editNoteContent;
TextView tVEditDate;
Toolbar toolbar;
FirebaseFirestore firestore;
ProgressBar progressBarSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data=getIntent();

        editNoteTitle=findViewById(R.id.editTxtEditNoteTitle);
        editNoteContent=findViewById(R.id.editTxtEditNoteContent);
        tVEditDate=findViewById(R.id.tVEditDate);
        progressBarSave=findViewById(R.id.progressBarEdit);

        String stNoteTitleEdit=data.getStringExtra("Title");
        String stNoteContentEdit=data.getStringExtra("Content");
        String stNoteDateEdit=data.getStringExtra("Date");

        editNoteTitle.setText(stNoteTitleEdit);
        editNoteContent.setText(stNoteContentEdit);
        tVEditDate.setText(stNoteDateEdit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        tVEditDate.setText(currentDateTime);

        firestore=FirebaseFirestore.getInstance();

        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stNoteTitle=editNoteTitle.getText().toString();
                String stNoteContent=editNoteContent.getText().toString();
                String stNoteDate=tVEditDate.getText().toString();

                if (stNoteTitle.isEmpty()||stNoteContent.isEmpty()){
                    Toast.makeText(EditNoteActivity.this,"Can not Save. Note Fields Are Empty.",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBarSave.setVisibility(View.VISIBLE);

                DocumentReference docRef=firestore.collection("Notes").document(data.getStringExtra("noteId"));
                Map<String,Object> Note=new HashMap<>();
                Note.put("Title",stNoteTitle);
                Note.put("Content",stNoteContent);
                Note.put("Date",stNoteDate);

                docRef.update(Note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditNoteActivity.this,"Note Added Successfuly",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditNoteActivity.this,"An Error Has Occured Try Again",Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.close_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mClose){
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}