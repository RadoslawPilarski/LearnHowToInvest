package com.example.lhti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lhti-625e5-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name=findViewById(R.id.name);
        final EditText email=findViewById(R.id.mail);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conPassword);

        final Button registerBnt=findViewById(R.id.registerBnt);
        final TextView loginNowBnt=findViewById(R.id.loginNow);

        registerBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameTxt=name.getText().toString();
                final String emailTxt=email.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conPasswordTxt=conPassword.getText().toString();

                if(nameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Czegoś brakuje",Toast.LENGTH_LONG).show();
                }
                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(Register.this,"Hasła nie pasują",Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(nameTxt)){
                                Toast.makeText(Register.this,"Email jest już użyty",Toast.LENGTH_LONG).show();
                            }
                            else{
                                databaseReference.child("users").child(nameTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(nameTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(nameTxt).child("money").setValue(100000);
                                databaseReference.child("users").child(nameTxt).child("key").setValue(0);

                                Toast.makeText(Register.this,"Udało się utworzyć użutkownika",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}