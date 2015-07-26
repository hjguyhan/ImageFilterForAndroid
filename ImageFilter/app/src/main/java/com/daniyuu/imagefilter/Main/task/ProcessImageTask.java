package com.daniyuu.imagefilter.Main.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniyuu.imagefilter.IImageFilter;
import com.daniyuu.imagefilter.Image;
import com.daniyuu.imagefilter.Main.FlexibleEdgeFilter;
import com.daniyuu.imagefilter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniyuu on 7/26/15.
 */
public class ProcessImageTask extends AsyncTask<Void, Void, Bitmap> {
    private IImageFilter filter;
    private Activity activity = null;
    private TextView textView = null;
    private ImageView imageView = null;
    private float alpha = 0.1F;

    public ProcessImageTask(Activity activity, IImageFilter imageFilter, TextView textView, ImageView imageView, float alpha) {
        this.activity = activity;
        this.textView = textView;
        this.imageView = imageView;
        this.filter = imageFilter;
        this.alpha = alpha;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        textView.setVisibility(View.VISIBLE);
        textView.setText(filter.getClass().getName());

    }

    public Bitmap doInBackground(Void... params) {
        Image img = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.flower);
            img = new Image(bitmap);
            if (filter != null) {
                img = filter.process(img);
                img.copyPixelsFromBuffer();
            }
            return overlay(img.getImage());
        } catch (Exception e) {
            if (img != null && img.destImage.isRecycled()) {
                img.destImage.recycle();
                img.destImage = null;
                System.gc();
            }
        } finally {
            if (img != null && img.image.isRecycled()) {
                img.image.recycle();
                img.image = null;
                System.gc();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

        List<Double> list = new ArrayList<Double>();


    }


    /**
     * 图片效果叠加
     * @param bmp 限制了尺寸大小的Bitmap
     * @return
     */
    private Bitmap overlay(Bitmap bmp)
    {
        long start = System.currentTimeMillis();
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // 对边框图片进行缩放
        Bitmap overlay = BitmapFactory.decodeResource(activity.getResources(), R.drawable.flower);
        int w = overlay.getWidth();
        int h = overlay.getHeight();
        float scaleX = width * 1F / w;
        float scaleY = height * 1F / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);

        Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);

        int pixColor = 0;
        int layColor = 0;

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixA = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;

        int layR = 0;
        int layG = 0;
        int layB = 0;
        int layA = 0;



        int[] srcPixels = new int[width * height];
        int[] layPixels = new int[width * height];
        bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
        overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);

        int pos = 0;
        for (int i = 0; i < height; i++)
        {
            for (int k = 0; k < width; k++)
            {
                pos = i * width + k;
                pixColor = srcPixels[pos];
                layColor = layPixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                pixA = Color.alpha(pixColor);

                layR = Color.red(layColor);
                layG = Color.green(layColor);
                layB = Color.blue(layColor);
                layA = Color.alpha(layColor);

                newR = (int) (pixR * alpha + layR * (1 - alpha));
                newG = (int) (pixG * alpha + layG * (1 - alpha));
                newB = (int) (pixB * alpha + layB * (1 - alpha));
                layA = (int) (pixA * alpha + layA * (1 - alpha));

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                newA = Math.min(255, Math.max(0, layA));

                srcPixels[pos] = Color.argb(newA, newR, newG, newB);
            }
        }

        bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

}
