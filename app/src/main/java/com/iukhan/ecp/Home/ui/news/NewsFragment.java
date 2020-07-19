package com.iukhan.ecp.Home.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iukhan.ecp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private static List<News> lstBook;
    private View view;
    private DatabaseReference mDatabase;
    private ValueEventListener postListener;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstBook = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("news");
        FirebaseDatabase.getInstance().getReference().child("news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    News ne = snapshot.getValue(News.class);
                    lstBook.add(ne);
                    RecyclerView myrv = view.findViewById(R.id.my_recycle_view);
                    RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), lstBook);
                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
                    myrv.setLayoutManager(gridLayoutManager);
                    myrv.setAdapter(myAdapter);
//                    Toast.makeText(getContext(), lstBook.get(1).getTitle(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        view = inflater.inflate(R.layout.fragment_news, null, false);
        return view;
    }


}