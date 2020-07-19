package com.iukhan.ecp.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iukhan.ecp.MainActivity;
import com.iukhan.ecp.R;
import com.iukhan.ecp.UserConstants;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private AutoCompleteTextView email, password;
    private TextView forgotPass, signUp;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initializeGUI();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inEmail = email.getText().toString();
                String inPassword = password.getText().toString();
                if (validateInput(inEmail, inPassword)) {
                    signUser(inEmail, inPassword);
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PWresetActivity.class));
            }
        });
    }

    public void saveData() {
        final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("users");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        if (uid.equals(snapshot1.child("email").getValue())) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("name", snapshot1.child("name").getValue().toString());
                            editor.putString("email", snapshot1.child("email").getValue().toString());
                            editor.putString("phone", snapshot1.child("phone").getValue().toString());
                            editor.putString("cnic", snapshot1.child("cnic").getValue().toString());
                            editor.putString("lat", snapshot1.child("latitude").getValue().toString());
                            editor.putString("long", snapshot1.child("longitude").getValue().toString());
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //end
}

    public void signUser(String email, String password) {
        progressDialog.setMessage("Verificating...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    saveData();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initializeGUI() {

        email = findViewById(R.id.atvEmailLog);
        password = findViewById(R.id.atvPasswordLog);
        forgotPass = findViewById(R.id.tvForgotPass);
        signUp = findViewById(R.id.tvSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            saveData();
        }

    }

    public boolean validateInput(String inemail, String inpassword) {

        if (inemail.isEmpty()) {
            email.setError("Email field is empty.");
            return false;
        }
        if (inpassword.isEmpty()) {
            password.setError("Password is empty.");
            return false;
        }

        return true;
    }

}
