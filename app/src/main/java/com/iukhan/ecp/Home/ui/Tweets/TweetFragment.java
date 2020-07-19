package com.iukhan.ecp.Home.ui.Tweets;

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

public class TweetFragment extends Fragment {
    private static List<Tweet> twtList;
    private View view;
    private DatabaseReference mDatabase;
    private ValueEventListener postListener;

    public TweetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twtList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("tweets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tweet tweet = snapshot.getValue(Tweet.class);
                    twtList.add(tweet);
                    RecyclerView myrv = view.findViewById(R.id.tweet_recycle_view);
                    RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), twtList);
                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
                    myrv.setLayoutManager(gridLayoutManager);
                    myrv.setAdapter(myAdapter);
//                    Toast.makeText(getContext(), twtList.get(1).getTitle(), Toast.LENGTH_LONG).show();
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
        view = inflater.inflate(R.layout.fragment_tweet, null, false);
        return view;
    }


}