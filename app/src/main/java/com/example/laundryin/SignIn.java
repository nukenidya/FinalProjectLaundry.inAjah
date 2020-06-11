package com.example.laundryin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundryin.Common.Common;
import com.example.laundryin.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPass;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPass = (MaterialEditText)findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Silahkan Tunggu...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if User Not exist in Database
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                            //Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            //setPhone
                            user.setPhone(edtPhone.getText().toString());
                            if (user.getPassword().equals(edtPass.getText().toString())) {
                                Intent homeintent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeintent);
                                finish();
                                //Toast.makeText(SignIn.this, "Sign In Berhasil !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Password Salah !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Pengguna Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                btnSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

    }
}
