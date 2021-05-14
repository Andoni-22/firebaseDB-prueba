package com.example.fraseslmg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity{

    private EditText et_email;
    private EditText et_new_pwd;
    private EditText et_cfrm_pwd;
    private EditText et_new_name;
    private EditText et_new_apellidos;
    private EditText et_new_phone;

    private Button btn_abort;
    private Button btn_new;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private GoogleSignInClient mGoogleSignInClient;

    public NewUserActivity(){}

    public NewUserActivity(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        et_email = (EditText)findViewById(R.id.et_email);
        et_new_pwd = (EditText)findViewById(R.id.et_new_pwd);
        et_cfrm_pwd = (EditText)findViewById(R.id.et_cfrm_pwd);
        et_new_name = (EditText)findViewById(R.id.et_new_name);
        et_new_apellidos = (EditText)findViewById(R.id.et_new_apellidos);
        et_new_phone = (EditText)findViewById(R.id.et_new_phone);
        btn_abort = (Button)findViewById(R.id.btn_abort);
        btn_new = (Button)findViewById(R.id.btn_new);

        initGoogleClient();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        btn_abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setText("");
                et_new_pwd.setText("");
                et_cfrm_pwd.setText("");
                et_new_name.setText("");
                et_new_apellidos.setText("");
                et_new_phone.setText("");
                finish();
            }
        });
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_user();
            }
        });
    }

    private void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void add_new_user() {
        if(et_email.getText().length() > 0 && et_new_pwd.getText().length() > 0
            && et_cfrm_pwd.getText().length() > 0 && et_new_name.getText().length() > 0
            && et_new_apellidos.getText().length() > 0 && et_new_phone.getText().length() > 0){
            if(et_new_pwd.getText().toString().equalsIgnoreCase(et_cfrm_pwd.getText().toString())){
                mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_new_pwd.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Map<String, Object> object = new HashMap<>();
                                    object.put("name", et_new_name.getText().toString());
                                    object.put("last_name", et_new_apellidos.getText().toString());
                                    object.put("phone", et_new_phone.getText().toString());

                                    user = FirebaseAuth.getInstance().getCurrentUser();

                                    db.collection("users").document(user.getEmail().toString())
                                            .set(object)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getApplicationContext(), "Datos añadidos con exito",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al añadir tus datos",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }else{
                                    Toast.makeText(getApplicationContext(), "Error al crear usuario",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }else{
                et_cfrm_pwd.setError("Error, las contraseñas no coincides");
            }
        }else{
            Toast.makeText(getApplicationContext(), "Datos mal mal mal",Toast.LENGTH_LONG).show();
        }
    }
}
