package com.iukhan.ecp.Home.ui.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.iukhan.ecp.R;
import com.iukhan.ecp.User.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    TextView name, cnic, email, phone;
    Button logout;
    public static final String MyPREFERENCES = "MyPrefs";

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences prefs = getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_profile, null, false);
        name = view.findViewById(R.id.name);
        cnic = view.findViewById(R.id.cnic);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                prefs.edit().clear().apply();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Toast.makeText(getActivity(), prefs.getString("name", "No name defined"), Toast.LENGTH_LONG).show();
        name.setText(prefs.getString("name", "No name defined"));
        email.setText(prefs.getString("email", "No name defined"));
        phone.setText(prefs.getString("phone", "No name defined"));
        cnic.setText(prefs.getString("cnic", "No name defined"));

        return view;
    }
}