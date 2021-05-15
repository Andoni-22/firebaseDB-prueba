package com.example.fraseslmg.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fraseslmg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private View root;
    private EditText et_username_usr;
    private EditText et_name_usr;
    private EditText et_apellido_usr;
    private EditText et_email_usr;
    private EditText et_phone_usr;

    private Button btn_editar_usr;
    private Button btn_save_usr;
    private Button btn_log_out;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;



    public UserFragment() {
        // Required empty public constructor
    }
    public UserFragment(FirebaseFirestore db) {
        this.db = db;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user, container, false);

        et_username_usr = (EditText)root.findViewById(R.id.et_username_usr);
        et_name_usr = (EditText)root.findViewById(R.id.et_name_usr);
        et_apellido_usr = (EditText)root.findViewById(R.id.et_apellido_usr);
        et_email_usr = (EditText)root.findViewById(R.id.et_email_usr);
        et_phone_usr = (EditText)root.findViewById(R.id.et_phone_usr);
        btn_editar_usr = (Button)root.findViewById(R.id.btn_editar_usr);
        btn_save_usr = (Button)root.findViewById(R.id.btn_save_usr);
        btn_log_out = (Button)root.findViewById(R.id.btn_log_out);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        load_user_data();

        btn_editar_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_save_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                //TODO
            }
        });
        return root;
    }

    private void load_user_data() {
        et_username_usr.setText("");
        et_name_usr.setText("");
        et_apellido_usr.setText("");
        et_email_usr.setText("");
        et_phone_usr.setText("");

        DocumentReference docRef = db.collection("users").document(firebaseUser.getEmail().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        et_username_usr.setText(document.get("username").toString());
                        et_name_usr.setText(document.get("name").toString());
                        et_apellido_usr.setText(document.get("last_name").toString());
                        et_email_usr.setText(firebaseUser.getEmail().toString());
                        et_phone_usr.setText(document.get("phone").toString());
                    } else {

                    }
                } else {

                }
            }
        });
    }

}
