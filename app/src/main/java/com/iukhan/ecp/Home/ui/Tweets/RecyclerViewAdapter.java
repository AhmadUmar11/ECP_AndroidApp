package com.iukhan.ecp.Home.ui.Tweets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iukhan.ecp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private FragmentActivity mContext;
    private List<Tweet> mData;
    private View view;
    private ViewGroup vg;
    NavController navController;

    public RecyclerViewAdapter(FragmentActivity mContext, List<Tweet> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        vg = parent;
        view = mInflater.inflate(R.layout.tweetcard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.user.setText(mData.get(position).getuser());
        holder.text.setText(mData.get(position).gettext());
        holder.link.setText(mData.get(position).getLink());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController  = Navigation.findNavController(view);
                Bundle args = new Bundle();
                args.putString("Link", mData.get(position).getLink());
                navController.navigate(R.id.action_tweetFragment_to_tweetShowFragment,args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user, text, link;
        CardView cardView;
        public MyViewHolder(final View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.twt_user);
            text = itemView.findViewById(R.id.twt_text);
            link = itemView.findViewById(R.id.twt_link);
            cardView = itemView.findViewById(R.id.card_view);
        }

    }


}