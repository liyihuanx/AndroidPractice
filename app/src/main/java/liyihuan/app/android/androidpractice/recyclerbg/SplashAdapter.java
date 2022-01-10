package liyihuan.app.android.androidpractice.recyclerbg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import liyihuan.app.android.androidpractice.R;


public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {

    private int imgWidth;


    public SplashAdapter(Context context) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_splash2, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       /* ViewGroup.LayoutParams lp = holder.item_bg.getLayoutParams();
        lp.width = imgWidth;
        lp.height =imgWidth*5;
        holder.item_bg.setLayoutParams(lp);*/
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_bg;

        public ViewHolder(final View itemView) {
            super(itemView);
            item_bg = itemView.findViewById(R.id.item_bg);
        }

    }

}
