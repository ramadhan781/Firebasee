package com.firebasee.lukman.firebaseeC;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebasee.lukman.firebasee.MainActivity;
import com.firebasee.lukman.firebasee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

public class Daftar extends AppCompatActivity {

    private EditText Email, Password, Name, NPM;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference Database;
    private ProgressDialog proggres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        Email = (EditText)findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        Name = (EditText) findViewById(R.id.Name);
        NPM = (EditText) findViewById(R.id.NPM);
        firebaseAuth = FirebaseAuth.getInstance();
        proggres = new ProgressDialog(this);
        Database = FirebaseDatabase.getInstance().getReference().child("User");
    }

    public void daftar(View view) {
        final String name = Name.getText().toString().trim();
        final String npm = NPM.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))

            proggres.setMessage("Signing Up...");
            proggres.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String id_user = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference data_user = Database.child(id_user);
                    data_user.child("Nama").setValue(name);
                    data_user.child("NPM").setValue(npm);
                    proggres.dismiss();

                    Toast.makeText(Daftar.this, "Daftar succes", Toast.LENGTH_SHORT).show();
                    Intent e = new Intent(Daftar.this, MainActivity.class);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(e);
                }
                else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(Daftar.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}