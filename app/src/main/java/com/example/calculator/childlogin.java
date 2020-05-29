package com.example.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class childlogin extends AppCompatActivity {

    private Button b6,b7;
    private EditText txtEmailAddress,txtPassword;
    // private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chilog);

        txtEmailAddress = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.pass);
        b6=(Button)findViewById(R.id.b6);
        b7=(Button)findViewById(R.id.b7);
        firebaseAuth = FirebaseAuth.getInstance();

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               (firebaseAuth.signInWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   //Log.e("ERROR", task.getResult().toString());
                                    Toast.makeText(childlogin.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor ed=sp.edit();
                                    ed.putString("ParentMail",txtEmailAddress.getText().toString());
                                    ed.commit();
                                    Intent intent = new Intent(childlogin.this, GoogleService.class);
                                    startService(intent);
                                } else {
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(childlogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });




            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchiReg();
            }
        });
    }

    public void openchiReg() {
        Intent intent = new Intent(childlogin.this, chiReg.class);
        startActivity(intent);
    }
}
