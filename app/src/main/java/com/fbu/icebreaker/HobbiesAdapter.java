package com.fbu.icebreaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.subclasses.Hobby;

import java.util.List;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ViewHolder> {

    private Context context;
    private List<Hobby> hobbies;

    public HobbiesAdapter(Context context, List<Hobby> hobbies) {
        this.context = context;
        this.hobbies = hobbies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hobby, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hobby hobby = hobbies.get(position);
        holder.bind(hobby);
    }

    @Override
    public int getItemCount() { return hobbies.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHobbyName;
        private TextView tvEmoji;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
            tvEmoji = itemView.findViewById(R.id.tvEmoji);
        }

        public void bind(Hobby hobby) {
            tvHobbyName.setText(hobby.getName());
            tvEmoji.setText(hobby.getEmoji());
        }
    }
}