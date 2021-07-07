package liyihuan.app.android.androidpractice.danmu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import liyihuan.app.android.androidpractice.R;
import liyihuan.app.android.androidpractice.utils.ScreenUtils;


public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {

    private int imgWidth;
    private ArrayList<DanMuBean> arrayList;

    private RecyclerView recyclerView;

    public SplashAdapter(Context context, RecyclerView recyclerView, ArrayList<DanMuBean> arrayList) {
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_splash, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DanMuBean data = arrayList.get(position % arrayList.size());
        holder.tvItem.setText(data.getName() + "---" + data.getContent());
        holder.tvItem.setOnClickListener(v -> {
            if (childClickLis != null) {
                childClickLis.Click(position);
            }
        });
//        holder.tvItem.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d("QWER", "ACTION_DOWN: ");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.d("QWER", "ACTION_MOVE: ");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.d("QWER", "ACTION_UP: ");
//                        break;
//                }
//                return true;
//            }
//        });
       /* ViewGroup.LayoutParams lp = holder.item_bg.getLayoutParams();
        lp.width = imgWidth;
        lp.height =imgWidth*5;
        holder.item_bg.setLayoutParams(lp);*/
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE / 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }

    private childClick childClickLis;

    public void setChildClickLis(childClick childClickLis) {
        this.childClickLis = childClickLis;
    }

    public interface childClick{
        void Click(int position);
    }

}
