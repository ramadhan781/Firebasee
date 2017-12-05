package com.firebasee.lukman.firebasee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebasee.lukman.firebaseeC.Daftar;
import com.firebasee.lukman.firebaseeC.login;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button kirimData;
    private TextView tampilData;
    private Firebase kirim, tampil;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    Intent masukLogin = new Intent(MainActivity.this, login.class);
                    startActivity(masukLogin);
                }
            }
        };

        auth.addAuthStateListener(authListener);


        kirimData = (Button) findViewById(R.id.btnkirim);
        kirim = new Firebase("https://cfirebase-aee07.firebaseio.com/");

        kirimData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase dataChild = kirim.child("Name");
                dataChild.setValue("Hati-Hati utnuk perjalanan pulangnya nanti sayang, Karna aku ga bisa jagain sayang dari jahatnya angin malam");
            }
        });

        tampilData = (TextView) findViewById(R.id.text);
        tampil = new Firebase("https://cfirebase-aee07.firebaseio.com/User/3ePySAZUIsbZ0ShKFtsRFfhaMMA2/Nama");

        tampil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tampilData.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void logout(View view) {
        auth.signOut();
    }
}
