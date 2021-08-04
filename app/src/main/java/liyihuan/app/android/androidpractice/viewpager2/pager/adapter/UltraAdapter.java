package liyihuan.app.android.androidpractice.viewpager2.pager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import liyihuan.app.android.androidpractice.R;

public class UltraAdapter extends RecyclerView.Adapter<UltraAdapter.ViewHolder> {
    ArrayList<Integer> pic_list;

    public void setPic_list(ArrayList<Integer> pic_list) {
        this.pic_list = pic_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ultra_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(pic_list.get(position));
    }

    @Override
    public int getItemCount() {
        return pic_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pager_img);
        }
    }
}
