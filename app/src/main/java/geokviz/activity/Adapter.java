package geokviz.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import geokviz.User;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<User> list;


    public Adapter(ArrayList<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getUNView().setText(list.get(position).getUsername());
        viewHolder.getPointsView().setText(list.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
