package com.fbu.icebreaker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;

import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ViewHolder> {

    private static final String TAG = "HobbiesAdapter";

    private final Context context;

    private final OnClickListener clickListener;

    private final List<Hobby> hobbies;

    public interface OnClickListener {
        void onHobbyClicked(int position);
    }

    public HobbiesAdapter(Context context, List<Hobby> hobbies, OnClickListener clickListener) {
        this.context = context;
        this.hobbies = hobbies;
        this.clickListener = clickListener;
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
    public int getItemCount() {
        return hobbies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener {

        private final TextView tvHobbyName;
        private final TextView tvDescription;

        private final ImageView ivHobbyImage;

        private final TagView tvTags;

        private final RelativeLayout rlHobby;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHobbyName = itemView.findViewById(R.id.tvHobbyName);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            ivHobbyImage = itemView.findViewById(R.id.ivHobbyImage);

            tvTags = itemView.findViewById(R.id.tvTag);

            rlHobby = itemView.findViewById(R.id.rlHobby);

            rlHobby.setOnCreateContextMenuListener(this);
        }

        public void bind(Hobby hobby) {
            tvHobbyName.setText(hobby.getName());
            tvTags.removeAllTags();
            String[] tags = hobby.getTags().toArray(new String[0]);
            for (String s : tags) {
                Random rnd = new Random();
                Tag tag = new Tag(s);
                tag.tagTextSize = 10f;
                tag.layoutColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                tvTags.addTag(tag);
            }

            tvDescription.setText(hobby.getDescription());
            tvDescription.setMaxLines(4);
            tvDescription.setEllipsize(TextUtils.TruncateAt.END);

            final int radius = 30;
            final int margin = 10;
            Glide.with(context)
                    .load(hobby.getImage())
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivHobbyImage);

            rlHobby.setOnClickListener(v -> clickListener.onHobbyClicked(getAdapterPosition()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete this item");
        }
    }


}
