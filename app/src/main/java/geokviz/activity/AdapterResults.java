package geokviz.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import geokviz.User;
import geokviz.fragments.ResultFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdapterResults extends RecyclerView.Adapter<AdapterResults.ViewHolder> {

    ArrayList<User> list;
    Context acontext;

    public AdapterResults(ArrayList<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem, parent, false);
        this.acontext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                User usr = list.get(position);
                bundle.putParcelable("user",usr);

                AppCompatActivity act = (AppCompatActivity) acontext;
                ResultFragment fragment = new ResultFragment();
                fragment.setArguments(bundle);
                FragmentManager fm = act.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentResults,fragment,"");
                ft.commit();
            }
        });
        viewHolder.getUNView().setText(list.get(position).getUsername());
        viewHolder.getPointsView().setText(list.get(position).getPoints().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView username;
        private final TextView points;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            username = (TextView) view.findViewById(R.id.username);
            points = (TextView) view.findViewById(R.id.points);
        }

        public TextView getUNView() {
            return username;
        }

        public TextView getPointsView() {
            return points;
        }
    }
}
