package com.example.smartnotes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class noteDetailsActivity extends AppCompatActivity {
    TextView tVContent;
    TextView tVTitle;
    TextView tVDate;
    Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data=getIntent();

        tVContent=findViewById(R.id.tVNoteDetailContent);
        tVTitle=findViewById(R.id.tVNoteDetailTitle);
        tVDate=findViewById(R.id.tVNoteDetailDate);
        tVContent.setMovementMethod(new ScrollingMovementMethod());


        tVContent.setText(data.getStringExtra("Content"));
        tVTitle.setText(data.getStringExtra("Title"));
        tVDate.setText(data.getStringExtra("Date"));

        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),EditNoteActivity.class);
                intent.putExtra("Title",data.getStringExtra("Title"));
                intent.putExtra("Content",data.getStringExtra("Content"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                startActivity(intent);
                            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}