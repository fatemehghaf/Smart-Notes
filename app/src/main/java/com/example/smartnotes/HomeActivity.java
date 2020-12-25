package com.example.smartnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView noteLists;
    //
    Adapter adapter;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter<Note,NoteViewHolder>noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.navView);
        nav_view.setNavigationItemSelectedListener(this);
        noteLists=findViewById(R.id.recNoteList);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        firestore=FirebaseFirestore.getInstance();

        Query query=firestore.collection("Notes").orderBy("Date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> allNotes=new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();

        noteAdapter=new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull final Note model) {
                holder.noteTitle.setText(model.getTitle());
                holder.noteContent.setText(model.getContent());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), noteDetailsActivity.class);
                        intent.putExtra("Title", model.getTitle());
                        intent.putExtra("Content", model.getContent());
                        intent.putExtra("Date",model.getDate());
                        v.getContext().startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_view_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };

        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteLists.setAdapter(noteAdapter);


        FloatingActionButton fab = findViewById(R.id.AddNoteFlBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AddNoteActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mAddNotes:
                startActivity(new Intent(HomeActivity.this,AddNoteActivity.class));
                break;
            default:
                Toast.makeText(this,"Coming Soon...",Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mSetting){
            Toast.makeText(this,"Coming Soon...",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteContent;
        TextView noteDate;
        View view;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.txtTitles);
            noteContent=itemView.findViewById(R.id.txtContent);
            view= itemView;

        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        noteAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (noteAdapter!=null)
        noteAdapter.startListening();
    }
}




