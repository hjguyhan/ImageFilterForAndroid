package com.daniyuu.imagefilter.Main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniyuu.imagefilter.Main.object.FilterInfo;
import com.daniyuu.imagefilter.R;

import java.util.List;

/**
 * Created by Daniyuu on 7/26/15.
 */
public class ImageFilterAdapter extends ArrayAdapter<FilterInfo> {
    private List<FilterInfo> filterInfos;
    private Context context;
    private int resourceId;


    public ImageFilterAdapter(Context con, int resource, List<FilterInfo> filterArray) {
        super(con, resource, filterArray);
        context = con;
        resourceId = resource;
        filterInfos = filterArray;

    }

    public int getCount() {
        return filterInfos.size();
    }

    public FilterInfo getItem(int position) {
        return position < filterInfos.size() ? filterInfos.get(position)
                : null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceId, viewGroup, false);
            holder = new ViewHolder();

            holder.iv_abstract = (ImageView) view.findViewById(R.id.iv_abstract);
            holder.tv_type = (TextView) view.findViewById(R.id.tv_filter_name);

            Bitmap bmImg = BitmapFactory
                    .decodeResource(context.getResources(),
                            filterInfos.get(position).filterID);
            int width = bmImg.getWidth();
            int height = bmImg.getHeight();
            bmImg.recycle();
            holder.iv_abstract.setImageResource(filterInfos.get(position).filterID);
//            holder.iv_abstract.setLayoutParams(new Gallery.LayoutParams(width, height));
            holder.iv_abstract.setScaleType(ImageView.ScaleType.FIT_CENTER);


            holder.tv_type.setText(filterInfos.get(position).filter.getClass().getName());
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }


    public static class ViewHolder {
        ImageView iv_abstract;
        TextView tv_type;
    }
}
