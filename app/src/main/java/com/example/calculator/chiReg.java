package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chiReg extends AppCompatActivity {

    private EditText name,mob,email,pass;
    private FirebaseDatabase mDatabase;

    // perform click event on button
    private Button b10;
    private FirebaseAuth firebaseAuth;
    DatabaseReference dbref;
    childRegisterDB regdb;  //from class RegisterDB
    String email1;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_reg);

        firebaseAuth=FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        mob=(EditText)findViewById(R.id.mob);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        b10=(Button)findViewById(R.id.b10 );
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1= name.getText().toString().trim();
                String mob1=mob.getText().toString().trim();
                email1=email.getText().toString().trim();
                password=pass.getText().toString().trim();


                if(TextUtils.isEmpty(name1))
                {
                    Toast.makeText(chiReg.this,"Name field is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mob1))
                {
                    Toast.makeText(chiReg.this,"Mobile number field is empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email1))
                {
                    Toast.makeText(chiReg.this,"Email field is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(chiReg.this,"Password field is empty",Toast.LENGTH_SHORT).show();
                    return;
                }



                regdb=new childRegisterDB();
                dbref= FirebaseDatabase.getInstance().getReference().child("childRegisterDB");
                String userId = dbref.push().getKey();

                //Adding to database
                regdb.setName(name1);
                regdb.setMob(mob1);
                regdb.setEmail(email1);
                regdb.setPass(password);
                regdb.setChildId(userId);

                dbref.child(userId).setValue(regdb);

                Toast.makeText(chiReg.this,"Registered in DB",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(parReg.this, parentlogin.class);
                //startActivity(intent);

                (firebaseAuth.createUserWithEmailAndPassword(email1,password))
                        .addOnCompleteListener(chiReg.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(chiReg.this, "Registeration Successfull", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(chiReg.this, childlogin.class);
                                    startActivity(intent);


                                }
                                else
                                {
                                    FirebaseAuthException e=(FirebaseAuthException)task.getException();
                                    Toast.makeText(chiReg.this, "Unsuccessful"+e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });

    }
}