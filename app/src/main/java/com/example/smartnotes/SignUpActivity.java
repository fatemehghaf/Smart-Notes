package com.example.smartnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference databaseRef;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    EditText userID;
    EditText Email;
    EditText Pass;
    Button SubmitSignin;
    TextView jumpLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userID=findViewById(R.id.editTxtId);
        Email=findViewById(R.id.editTxtEmail);
        Pass=findViewById(R.id.editTxtPass);
        SubmitSignin=findViewById(R.id.submitBtn);
        jumpLogin=findViewById(R.id.loginBtn);

        databaseRef= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        jumpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
            }
        });

        SubmitSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stID=userID.getText().toString();
                String stEmail=Email.getText().toString();
                String stPass=Pass.getText().toString();
                if (TextUtils.isEmpty(stID)||TextUtils.isEmpty(stEmail)||TextUtils.isEmpty(stPass)){
                    Toast.makeText(SignUpActivity.this,"Fill all fields!",Toast.LENGTH_LONG).show();
                }
                else if (stPass.length()>8){
                    Toast.makeText(SignUpActivity.this,"The Password must be at least 8 Characters!",Toast.LENGTH_LONG).show();
                }
                else Register(stID,stEmail,stPass);
            }
        });
    }
    public void Register(final String ID, final String Email, final String Pass){
    firebaseAuth.createUserWithEmailAndPassword(Email,Pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            HashMap<String,Object>data=new HashMap<>();
            data.put("Username",ID);
            data.put("Username",Email);
            data.put("Username",Pass);
            databaseRef.child("User").child(firebaseAuth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this,"Successfuly Registered",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(SignUpActivity.this,HomeActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                    }
                }
            });


        }
    });
    }
}