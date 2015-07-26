package com.daniyuu.imagefilter.Main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniyuu.imagefilter.EdgeFilter;
import com.daniyuu.imagefilter.R;

public class EdgeFilterTest extends ActionBarActivity {

    private ImageView imageView;
    private ImageView iv_original;
    private TextView textView;
    private Button bt_start;
    private TextView et_select_r;
    private TextView et_select_g;
    private TextView et_select_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edge_filter_test);

        imageView = (ImageView) findViewById(R.id.iv_edge_show);
        iv_original = (ImageView) findViewById(R.id.iv_original_show);
        textView = (TextView) findViewById(R.id.tv_describe);

        bt_start = (Button) findViewById(R.id.bt_start);

        et_select_r = (EditText) findViewById(R.id.et_select_r);
        et_select_g = (EditText) findViewById(R.id.et_select_g);
        et_select_b = (EditText) findViewById(R.id.et_select_b);

        Bitmap bitmap = BitmapFactory.decodeResource(EdgeFilterTest.this.getResources(), R.drawable.flower);
        imageView.setImageBitmap(bitmap);
        iv_original.setImageBitmap(bitmap);


        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double r = Integer.parseInt(et_select_r.getText().toString()) * 0.01;
                double g = Integer.parseInt(et_select_g.getText().toString()) * 0.01;
                double b = Integer.parseInt(et_select_b.getText().toString()) * 0.01;
                float alpha = 0.9F;

                FilterInfo filterInfo = new FilterInfo(R.drawable.flower, new FlexibleEdgeFilter());
                FlexibleEdgeFilter filter = (FlexibleEdgeFilter) filterInfo.filter;
                filter.setLuminance(r, g, b);

                new processImageTask(EdgeFilterTest.this, filter, textView, imageView,alpha).execute();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edge_filter_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
