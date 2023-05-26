package com.example.lhti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText login;
    EditText password;
    CheckBox rememberMe;
    Button loginBnt;
    TextView registerNowBnt;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lhti-625e5-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        loginBnt=findViewById(R.id.loginBnt);
        registerNowBnt=findViewById(R.id.registerNow);

        loginBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginTxt=login.getText().toString();
                final String passwordTxt=password.getText().toString();

                if(loginTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this,"Brakuje hasla lub loginu",Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(loginTxt)){
                                final String getPassword = snapshot.child(loginTxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this,"Loguje",Toast.LENGTH_LONG).show();

                                    SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putString("Unm",loginTxt );
                                    Ed.commit();

                                    Intent intent=new Intent(Login.this,MainActivity.class);
                                    intent.putExtra("loginName",loginTxt);
                                    startActivity(intent);
                                   // startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this,"Błędne hasło",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this,"Błędne hasło",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        registerNowBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}