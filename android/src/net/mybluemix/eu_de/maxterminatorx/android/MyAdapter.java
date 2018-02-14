package net.mybluemix.eu_de.maxterminatorx.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<User> userslist;
    public Context context;
    private Intent intent;
    private User user;

    public MyAdapter(List<User> usersList) {
        this.userslist = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        User user = userslist.get(position);
        holder.txtName.setText("Username :"+ user.getUsername());
        holder.txtScore.setText("Score :"+user.getScore());
        holder.txtFigths.setText("Figths :"+user.getFights());
        holder.txtWins.setText("Wins :"+user.getWins()+"");
        holder.txtLocation.setText("Location :"+ user.location );

    }

    @Override
    public int getItemCount() {
        return userslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtScore;
        private TextView txtFigths,txtWins,txtLocation;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtLocation = (TextView)itemView.findViewById(R.id.txtLocation);
            txtScore = (TextView)itemView.findViewById(R.id.txtScore);
            txtFigths = (TextView) itemView.findViewById(R.id.txtFigths);
            txtWins = (TextView) itemView.findViewById(R.id.txtWins);
        }
    }
}
