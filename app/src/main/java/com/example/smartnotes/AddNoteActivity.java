package com.example.smartnotes;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNoteActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    EditText noteTitle;
    EditText noteContent;

    ProgressBar progressBarSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firestore=FirebaseFirestore.getInstance();
        noteTitle=findViewById(R.id.editTxtAddNoteTitle);
        noteContent=findViewById(R.id.editTxtAddNoteContent);
        progressBarSave=findViewById(R.id.progressBar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stNoteTitle=noteTitle.getText().toString();
                String stNoteContent=noteContent.getText().toString();

                if (stNoteTitle.isEmpty()||stNoteContent.isEmpty()){
                    Toast.makeText(AddNoteActivity.this,"Can not Save. Note Fields Are Empty.",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBarSave.setVisibility(View.VISIBLE);

                DocumentReference focRef=firestore.collection("Notes").document();
                Map<String,Object> Note=new HashMap<>();
                Note.put("Title",stNoteTitle);
                Note.put("Content",stNoteContent);

                focRef.set(Note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNoteActivity.this,"Note Added Successfuly",Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNoteActivity.this,"An Error Has Occured Try Again",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}