package com.daniyuu.imagefilter.Main;

import android.graphics.Color;
import android.graphics.Paint;

import com.daniyuu.imagefilter.EdgeFilter;
import com.daniyuu.imagefilter.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniyuu on 7/26/15.
 */
public class FlexibleEdgeFilter extends EdgeFilter {

    private double r_scale = 0.299;
    private double g_scale = 0.58;
    private double b_scale = 0.11;


    //@Override
    public Image process(Image imageIn) {
        // Image size
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        boolean[][] mask = null;
        Paint grayMatrix[] = new Paint[256];

        // Init gray matrix
        for (int i = 0; i <= 255; i++) {
            Paint p = new Paint();
            p.setColor(Color.rgb(i, i, i));
            grayMatrix[i] = p;
        }

        int[][] luminance = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mask != null && !mask[x][y]) {
                    continue;
                }
                luminance[x][y] = (int) luminance(imageIn.getRComponent(x, y), imageIn.getGComponent(x, y), imageIn.getBComponent(x, y));
            }
        }


        int grayX, grayY;
        int magnitude;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                if (mask != null && !mask[x][y]) {
                    continue;
                }

                grayX = -luminance[x - 1][y - 1] + luminance[x - 1][y - 1 + 2] - 2 * luminance[x - 1 + 1][y - 1] + 2 * luminance[x - 1 + 1][y - 1 + 2] - luminance[x - 1 + 2][y - 1] + luminance[x - 1 + 2][y - 1 + 2];
                grayY = luminance[x - 1][y - 1] + 2 * luminance[x - 1][y - 1 + 1] + luminance[x - 1][y - 1 + 2] - luminance[x - 1 + 2][y - 1] - 2 * luminance[x - 1 + 2][y - 1 + 1] - luminance[x - 1 + 2][y - 1 + 2];

                // Magnitudes sum
                magnitude = 255 - Image.SAFECOLOR(Math.abs(grayX) + Math.abs(grayY));
                Paint grayscaleColor = grayMatrix[magnitude];

                // Apply the color into a new image
                imageIn.setPixelColor(x, y, grayscaleColor.getColor());
            }
        }

        return imageIn;
    }


    /**
     * Apply the luminance
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    private int luminance(int r, int g, int b) {
        return (int) ((r_scale * r) + (g_scale * g) + (b_scale * b));
    }

    public List<Double> getLuminance(){
        List<Double> list = new ArrayList<Double>();
        list.add(this.r_scale);
        list.add(this.g_scale);
        list.add(this.b_scale);

        return list;
    }

    public void setLuminance(double r_scale, double g_scale, double b_scale){
        this.r_scale = r_scale;
        this.b_scale = b_scale;
        this.g_scale = g_scale;
    }
}
