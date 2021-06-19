package geokviz.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import geokviz.User;
import geokviz.UserAnswerQuestion;

public class AdapterQuestions extends RecyclerView.Adapter<AdapterQuestions.ViewHolder> {

    ArrayList<UserAnswerQuestion> list;


    public AdapterQuestions(ArrayList<UserAnswerQuestion> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterQuestions.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userquestion, parent, false);

        return new AdapterQuestions.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuestions.ViewHolder holder, int position) {

        holder.getUq().setText(list.get(position).getQuestion());
        holder.getUa().setText(list.get(position).getAnswer());

        if(list.get(position).getFlag())
            holder.getUa().setBackgroundResource(R.color.incorrect);
        else
            holder.getUa().setBackgroundResource(R.color.correct);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView uq;
        private final TextView ua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.uq = (TextView) itemView.findViewById(R.id.uqView);
            this.ua = (TextView) itemView.findViewById(R.id.uaView);
        }

        public TextView getUq() {
            return uq;
        }

        public TextView getUa() {
            return ua;
        }
    }
}
