package com.example.smartnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth2;
    Button loginBtn;
    EditText loginEmail;
    EditText loginPass;
    TextView jumpSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=findViewById(R.id.loginBtn);
        loginEmail=findViewById(R.id.editTxtEmail2);
        loginPass=findViewById(R.id.editTxtPass2);
        jumpSignUp=findViewById(R.id.tViewJumpSignUp);

        firebaseAuth2=FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stLoginEmail=loginEmail.getText().toString();
                String stLoginPass=loginPass.getText().toString();
                if (TextUtils.isEmpty(stLoginEmail) || TextUtils.isEmpty(stLoginPass)) {
                    Toast.makeText(LoginActivity.this,"You Have to Fill Both Fields",Toast.LENGTH_LONG).show();
                }
                else
                    LoggingIn(stLoginEmail,stLoginPass);
            }
        });
        jumpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(LoginActivity.this,com.example.smartnotes.SignUpActivity.class);
                startActivity(intent2);
            }
        });

    }
    public void LoggingIn(final String Email,final String Pass){
        firebaseAuth2.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Done",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}