package com.example.smartnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth =FirebaseAuth.getInstance();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent=new Intent(MainActivity.this,com.example.smartnotes.LoginActivity.class);
                startActivity(intent);*/
                if (firebaseAuth.getCurrentUser()!=null){
                    Intent intent=new Intent(MainActivity.this,com.example.smartnotes.HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent2=new Intent(MainActivity.this,com.example.smartnotes.LoginActivity.class);
                    startActivity(intent2);
                }
            }
        },2500);
    }
}