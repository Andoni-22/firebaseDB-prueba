package com.example.fraseslmg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private TextView tv_mail;
    private Button btn_logout;
    private Button btn_save;
    private Button btn_recover;
    private Button btn_aux;
    private EditText et_name;
    private EditText et_apellidos;
    private EditText et_municipio;
    private EditText et_phone;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public HomeActivity(){}

    public HomeActivity(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_recover = (Button)findViewById(R.id.btn_recover);
        btn_aux = (Button)findViewById(R.id.btn_aux);
        et_name = (EditText)findViewById(R.id.et_name);
        et_apellidos = (EditText)findViewById(R.id.et_apellidos);
        et_municipio = (EditText)findViewById(R.id.et_municipio);
        et_phone = (EditText)findViewById(R.id.et_new_phone);
        tv_mail = (TextView) findViewById(R.id.tv_mail);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        if (user != null){
            tv_mail.setText(user.getEmail().toString());
        }
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserData();
            }
        });

        btn_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData();
            }
        });

        btn_aux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUserData() {
        et_name.setText("");
        et_apellidos.setText("");
        et_municipio.setText("");
        et_phone.setText("");
        DocumentReference docRef = db.collection("users").document(user.getEmail().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        et_name.setText(document.get("name").toString());
                        et_apellidos.setText(document.get("last_name").toString());
                        et_municipio.setText(document.get("city").toString());
                        et_phone.setText(document.get("phone").toString());
                    } else {

                    }
                } else {

                }
            }
        });

    }

    private void setUserData() {
        if((et_name.getText().length() > 0) && (et_apellidos.getText().length() > 0)
            && (et_municipio.getText().length() > 0) && (et_phone.getText().length()) > 0){
            Map<String, Object> object = new HashMap<>();
            object.put("name", et_name.getText().toString());
            object.put("last_name", et_apellidos.getText().toString());
            object.put("city", et_municipio.getText().toString());
            object.put("phone", et_phone.getText().toString());

            db.collection("users").document(user.getEmail().toString())
                    .set(object)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Datos añadidos con exito",Toast.LENGTH_LONG).show();
                            et_name.setText("");
                            et_apellidos.setText("");
                            et_municipio.setText("");
                            et_phone.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error",Toast.LENGTH_LONG).show();
                        }
                    });

        }else{
            if(et_name.getText().length() == 0){
                et_name.setError("Campo obligatorio");
            }
            if(et_apellidos.getText().length() == 0){
                et_apellidos.setError("Campo obligatorio");
            }
            if(et_municipio.getText().length() == 0){
                et_municipio.setError("Campo obligatorio");
            }
            if(et_phone.getText().length() == 0){
                et_phone.setError("Campo obligatorio");
            }
        }
    }

    private void logout() {
        mAuth.signOut();
        finish();
    }
}
