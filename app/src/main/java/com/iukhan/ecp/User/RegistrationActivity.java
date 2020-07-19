
package com.iukhan.ecp.User;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iukhan.ecp.MainActivity;
import com.iukhan.ecp.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    private String lat = "30.18327";
    private String lon = "66.99645";
    private AutoCompleteTextView username, email, password, cnic, phone;
    private Button signup;
    private TextView test;
    private TextView signin,name;
    private RadioGroup genderRadio;
    private String gender = "Male", ip, Email, Password, CNIC, Name,Phone;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        signup = findViewById(R.id.btnSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeGUI();
            }
        });
        signin = findViewById(R.id.tvSignIn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void initializeGUI() {
        email = findViewById(R.id.atvEmailReg);
        cnic = findViewById(R.id.cnic);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        password = findViewById(R.id.atvPasswordReg);
        signin = findViewById(R.id.tvSignIn);
        signup = findViewById(R.id.btnSignUp);
        ProgressDialog progressDialog = new ProgressDialog(this);
        //TODO radio button
//        genderRadio = findViewById(R.id.genderBtn);
//        genderRadio.clearCheck();
//
//        int selectedId = genderRadio.getCheckedRadioButtonId();
//        RadioButton radioButton = findViewById(selectedId);
//        gender = radioButton.getText().toString();

//        loadCnic(cnic.getText().toString());
        if (name.getText() != null) {
            Email = email.getText().toString();
            Password = password.getText().toString();
            CNIC = cnic.getText().toString();
            Name = name.getText().toString();
            Phone = phone.getText().toString();
            //Authenticate with firebase auth
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if (TextUtils.isEmpty(Email)) {
                Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Password)) {
                Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                //Put data in users database
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference usersRef = ref.child("users");
                                User user = new User(Name, Email, CNIC, Phone, gender, lat, lon);
                                usersRef.push().setValue(user);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Couldn't register, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //get data from NADRA server
//    private void loadCnic(String cn) {
//        char[] x = cn.toCharArray();
//        if (x.length == 15 && (x[5] == '-' && x[13] == '-')) {
//            String URL_cnic = "192.168.43.33/read.php?cnic=".concat(cn);
//            RequestQueue rq = Volley.newRequestQueue(this);
//            StringRequest request = new StringRequest(Request.Method.GET, URL_cnic, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONArray array = new JSONArray(response);
//                        JSONObject cnic = array.getJSONObject(0);
//                        name = cnic.getString("name");
//                        Toast.makeText(getApplicationContext(), cnic.getString("name"), Toast.LENGTH_SHORT).show();
//                        lat = cnic.getDouble("lattitude") + "";
//                        lon = cnic.getDouble("longitude") + "";
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getApplicationContext(), "Could Not Retrieve CNIC Information Please Try Again", Toast.LENGTH_LONG).show();
////                    Toast.makeText(getApplicationContext(), URL_cnic, Toast.LENGTH_SHORT);
//                }
//            });
//            rq.add(request);
//        } else
//            Toast.makeText(getApplicationContext(), "Please enter cnic in correct Format[12345-6789123-8]", Toast.LENGTH_LONG).show();
//    }

//    void getApiUrl() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("IP of the Nadra Server");
//        final EditText input = new EditText(this);
//        input.setText("192.168.");
//        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
//        builder.setView(input);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ip = input.getText().toString();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//    }


}
