package com.example.fraseslmg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1111;
    private EditText et_email;
    private EditText et_pwd;
    private Button btn_acceder;
    private Button btn_login;
    private Button btn_google;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        et_email = (EditText)findViewById(R.id.et_email);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        btn_acceder = (Button)findViewById(R.id.btn_acceder);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_google = (Button)findViewById(R.id.btn_google);
        mAuth = FirebaseAuth.getInstance();

        initGoogleClient();

        btn_acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceder();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser();
            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSigIn();
            }
        });

    }

    /**
     * Iniciamos el cliente de google
      */
    private void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * Cargamos el intent
     */
    private void googleSigIn() {
        mGoogleSignInClient.signOut();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Authenticate with Firebase
                loginfirebaseWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                showAlert();
            }
        }
    }

    private void loginfirebaseWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {// Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            AuthActivity.this.launchHome();
                        } else { // sign in fails
                            AuthActivity.this.showAlert();
                        }
                    }
                });
    }

    /**
     * con este metodo creamos un nuevo usuario en nuestra aplicacion,
     * de momento se crea desde la pestaña principal, pero esto se cambiara
     */
    private void newUser() {
        if(et_pwd.getText().length() > 0 && et_email.getText().length() > 0){
            mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        launchHome();
                    }else{
                        showAlert();
                    }
                }
            });
        }else{
            if(et_email.getText().length() == 0){
                et_email.setError("Debes introducir un email");
            }
            if(et_pwd.getText().length() == 0){
                et_pwd.setError("Debes introducir una contraseña");
            }
        }
    }

    /**
     * El motodo acceder se encarga del sig in de la aplicacion, usamo
     * el servicio de firebasedb para que esto sea mas sencillo y no tengamos
     * que crea un backend
     */
    private void acceder() {
        if(et_pwd.getText().length() > 0 && et_email.getText().length() > 0){
            mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        launchHome();
                    }else{
                        showAlert();
                    }
                }
            });
        }else{
            if(et_email.getText().length() == 0){
                et_email.setError("Debes introducir un email");
            }
            if(et_pwd.getText().length() == 0){
                et_pwd.setError("Debes introducir una contraseña");
            }
        }
    }

    /**
     * en caso de que algo salga mal lanzaremos este metodo
     */
    private void showAlert() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Error");
        dialogo1.setMessage("Algo salio mal, compruebe sus credenciales");
        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
        et_email.setText("");
        et_email.requestFocus();
        et_pwd.setText("");
    }

    /**
     * Este metodo es para lanzar el siguiente intent, de momento
     * es un activity, pero usaremos fragments mas adelante
     */
    private void launchHome() {
        Toast.makeText(this,"Inicio de sesion correcto", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
