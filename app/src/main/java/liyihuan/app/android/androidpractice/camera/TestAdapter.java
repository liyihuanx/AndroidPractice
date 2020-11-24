package liyihuan.app.android.androidpractice.camera;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.planet.adapter.PlanetAdapter;
import com.example.planet.utils.SizeUtils;

import java.util.ArrayList;

public class TestAdapter extends PlanetAdapter {
    ArrayList<String> label;
    int len = -1;
    public void setLabelList(ArrayList<String> label){
        this.label = label;
    }

    @Override
    public int getCount() {
        return label.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        int starWidth = SizeUtils.dp2px(context, 110.0f);
        int starHeight = SizeUtils.dp2px(context, 85.0f);
        int starPaddingTop = SizeUtils.dp2px(context, 30.0f);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(starWidth, starHeight);
        MyTextView myTextView  = new MyTextView(context);
        myTextView.setSign(getLabel());
        myTextView.setPadding(0, starPaddingTop, 0, 0);
        myTextView.setLayoutParams(layoutParams);
        return myTextView;
    }
    public String getLabel() {
        len ++;
        String str ;
        if (len >= label.size()){
            str = "1";
        } else {
            str = label.get(len);
        }
        return str;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPopularity(int position) {
        return position % 10;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}