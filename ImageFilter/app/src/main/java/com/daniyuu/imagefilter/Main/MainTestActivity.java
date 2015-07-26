package com.daniyuu.imagefilter.Main;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daniyuu.imagefilter.AutoAdjustFilter;
import com.daniyuu.imagefilter.BannerFilter;
import com.daniyuu.imagefilter.BigBrotherFilter;
import com.daniyuu.imagefilter.BlackWhiteFilter;
import com.daniyuu.imagefilter.BlindFilter;
import com.daniyuu.imagefilter.BlockPrintFilter;
import com.daniyuu.imagefilter.BrickFilter;
import com.daniyuu.imagefilter.BrightContrastFilter;
import com.daniyuu.imagefilter.CleanGlassFilter;
import com.daniyuu.imagefilter.ColorQuantizeFilter;
import com.daniyuu.imagefilter.ColorToneFilter;
import com.daniyuu.imagefilter.ComicFilter;
import com.daniyuu.imagefilter.Distort.BulgeFilter;
import com.daniyuu.imagefilter.Distort.RippleFilter;
import com.daniyuu.imagefilter.Distort.TwistFilter;
import com.daniyuu.imagefilter.Distort.WaveFilter;
import com.daniyuu.imagefilter.EdgeFilter;
import com.daniyuu.imagefilter.FeatherFilter;
import com.daniyuu.imagefilter.FillPatternFilter;
import com.daniyuu.imagefilter.FilmFilter;
import com.daniyuu.imagefilter.FocusFilter;
import com.daniyuu.imagefilter.GammaFilter;
import com.daniyuu.imagefilter.GaussianBlurFilter;
import com.daniyuu.imagefilter.Gradient;
import com.daniyuu.imagefilter.HslModifyFilter;
import com.daniyuu.imagefilter.IImageFilter;
import com.daniyuu.imagefilter.IllusionFilter;
import com.daniyuu.imagefilter.InvertFilter;
import com.daniyuu.imagefilter.LensFlareFilter;
import com.daniyuu.imagefilter.LightFilter;
import com.daniyuu.imagefilter.LomoFilter;
import com.daniyuu.imagefilter.Main.adapter.ImageFilterAdapter;
import com.daniyuu.imagefilter.Main.object.FilterInfo;
import com.daniyuu.imagefilter.Main.task.ProcessImageTask;
import com.daniyuu.imagefilter.MirrorFilter;
import com.daniyuu.imagefilter.MistFilter;
import com.daniyuu.imagefilter.MonitorFilter;
import com.daniyuu.imagefilter.MosaicFilter;
import com.daniyuu.imagefilter.NeonFilter;
import com.daniyuu.imagefilter.NightVisionFilter;
import com.daniyuu.imagefilter.NoiseFilter;
import com.daniyuu.imagefilter.OilPaintFilter;
import com.daniyuu.imagefilter.OldPhotoFilter;
import com.daniyuu.imagefilter.PaintBorderFilter;
import com.daniyuu.imagefilter.PixelateFilter;
import com.daniyuu.imagefilter.PosterizeFilter;
import com.daniyuu.imagefilter.R;
import com.daniyuu.imagefilter.RadialDistortionFilter;
import com.daniyuu.imagefilter.RainBowFilter;
import com.daniyuu.imagefilter.RaiseFrameFilter;
import com.daniyuu.imagefilter.RectMatrixFilter;
import com.daniyuu.imagefilter.ReflectionFilter;
import com.daniyuu.imagefilter.ReliefFilter;
import com.daniyuu.imagefilter.SaturationModifyFilter;
import com.daniyuu.imagefilter.SceneFilter;
import com.daniyuu.imagefilter.SepiaFilter;
import com.daniyuu.imagefilter.SharpFilter;
import com.daniyuu.imagefilter.ShiftFilter;
import com.daniyuu.imagefilter.SmashColorFilter;
import com.daniyuu.imagefilter.SoftGlowFilter;
import com.daniyuu.imagefilter.SupernovaFilter;
import com.daniyuu.imagefilter.Textures.CloudsTexture;
import com.daniyuu.imagefilter.Textures.LabyrinthTexture;
import com.daniyuu.imagefilter.Textures.MarbleTexture;
import com.daniyuu.imagefilter.Textures.TextileTexture;
import com.daniyuu.imagefilter.Textures.TexturerFilter;
import com.daniyuu.imagefilter.Textures.WoodTexture;
import com.daniyuu.imagefilter.ThreeDGridFilter;
import com.daniyuu.imagefilter.ThresholdFilter;
import com.daniyuu.imagefilter.TileReflectionFilter;
import com.daniyuu.imagefilter.TintFilter;
import com.daniyuu.imagefilter.VideoFilter;
import com.daniyuu.imagefilter.VignetteFilter;
import com.daniyuu.imagefilter.VintageFilter;
import com.daniyuu.imagefilter.WaterWaveFilter;
import com.daniyuu.imagefilter.XRadiationFilter;
import com.daniyuu.imagefilter.YCBCrLinearFilter;
import com.daniyuu.imagefilter.ZoomBlurFilter;

import java.util.ArrayList;
import java.util.List;

public class MainTestActivity extends ActionBarActivity {
    private Context context;

    private List<FilterInfo> filterArray;
    private Gallery gallery;

    private ImageFilterAdapter filterAdapter;
    private LinearLayout ll_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        context = MainTestActivity.this;

        init();
        initView();


        LoadImageFilter();
        addListener();
    }

    private void init() {
        filterArray = new ArrayList<FilterInfo>();
        setFilterArray();
    }

    private void initView() {
        gallery = (Gallery) findViewById(R.id.galleryFilter);
        ll_show = (LinearLayout) findViewById(R.id.ll_show_process_img);
    }


    private void LoadImageFilter() {

        filterAdapter = new ImageFilterAdapter(
                context, R.layout.item_filter_abstract, getFilterArray());
        gallery.setAdapter(filterAdapter);
        gallery.setSelection(2);
        gallery.setAnimationDuration(3000);

    }

    private void addListener(){
        LinearLayout plate = new LinearLayout(context);
        plate.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        plate.setLayoutParams(p);

        final ImageView imageView = new ImageView(context);
        final TextView textView = new TextView(context);

        plate.addView(imageView);
        plate.addView(textView);

        ll_show.addView(plate);

        final float alpha = 0.9F;

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                IImageFilter filter = (IImageFilter) filterAdapter.getItem(position).filter;
                new ProcessImageTask(MainTestActivity.this, filter,textView,imageView,alpha).execute();
            }
        });
    }

    private List<FilterInfo> getFilterArray() {
        return filterArray;
    }

    private void setFilterArray() {

        filterArray.add(new FilterInfo(R.drawable.video_filter1, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED)));
        filterArray.add(new FilterInfo(R.drawable.video_filter2, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_TRIPED)));
        filterArray.add(new FilterInfo(R.drawable.video_filter3, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_3X3)));
        filterArray.add(new FilterInfo(R.drawable.video_filter4, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_DOTS)));
        filterArray.add(new FilterInfo(R.drawable.tilereflection_filter1, new TileReflectionFilter(20, 8, 45, (byte) 1)));
        filterArray.add(new FilterInfo(R.drawable.tilereflection_filter2, new TileReflectionFilter(20, 8, 45, (byte) 2)));
        filterArray.add(new FilterInfo(R.drawable.fillpattern_filter, new FillPatternFilter(MainTestActivity.this, R.drawable.texture1)));
        filterArray.add(new FilterInfo(R.drawable.fillpattern_filter1, new FillPatternFilter(MainTestActivity.this, R.drawable.texture2)));
        filterArray.add(new FilterInfo(R.drawable.mirror_filter1, new MirrorFilter(true)));
        filterArray.add(new FilterInfo(R.drawable.mirror_filter2, new MirrorFilter(false)));
        filterArray.add(new FilterInfo(R.drawable.ycb_crlinear_filter, new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.3f, 0.3f))));
        filterArray.add(new FilterInfo(R.drawable.ycb_crlinear_filter2, new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f))));
        filterArray.add(new FilterInfo(R.drawable.texturer_filter, new TexturerFilter(new CloudsTexture(), 0.8f, 0.8f)));
        filterArray.add(new FilterInfo(R.drawable.texturer_filter1, new TexturerFilter(new LabyrinthTexture(), 0.8f, 0.8f)));
        filterArray.add(new FilterInfo(R.drawable.texturer_filter2, new TexturerFilter(new MarbleTexture(), 1.8f, 0.8f)));
        filterArray.add(new FilterInfo(R.drawable.texturer_filter3, new TexturerFilter(new WoodTexture(), 0.8f, 0.8f)));
        filterArray.add(new FilterInfo(R.drawable.texturer_filter4, new TexturerFilter(new TextileTexture(), 0.8f, 0.8f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter, new HslModifyFilter(20f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter0, new HslModifyFilter(40f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter1, new HslModifyFilter(60f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter2, new HslModifyFilter(80f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter3, new HslModifyFilter(100f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter4, new HslModifyFilter(150f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter5, new HslModifyFilter(200f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter6, new HslModifyFilter(250f)));
        filterArray.add(new FilterInfo(R.drawable.hslmodify_filter7, new HslModifyFilter(300f)));

        //v0.3
        filterArray.add(new FilterInfo(R.drawable.zoomblur_filter, new ZoomBlurFilter(30)));
        filterArray.add(new FilterInfo(R.drawable.threedgrid_filter, new ThreeDGridFilter(16, 100)));
        filterArray.add(new FilterInfo(R.drawable.colortone_filter, new ColorToneFilter(Color.rgb(33, 168, 254), 192)));
        filterArray.add(new FilterInfo(R.drawable.colortone_filter2, new ColorToneFilter(0x00FF00, 192)));//green
        filterArray.add(new FilterInfo(R.drawable.colortone_filter3, new ColorToneFilter(0xFF0000, 192)));//blue
        filterArray.add(new FilterInfo(R.drawable.colortone_filter4, new ColorToneFilter(0x00FFFF, 192)));//yellow
        filterArray.add(new FilterInfo(R.drawable.softglow_filter, new SoftGlowFilter(10, 0.1f, 0.1f)));
        filterArray.add(new FilterInfo(R.drawable.tilereflection_filter, new TileReflectionFilter(20, 8)));
        filterArray.add(new FilterInfo(R.drawable.blind_filter1, new BlindFilter(true, 96, 100, 0xffffff)));
        filterArray.add(new FilterInfo(R.drawable.blind_filter2, new BlindFilter(false, 96, 100, 0x000000)));
        filterArray.add(new FilterInfo(R.drawable.raiseframe_filter, new RaiseFrameFilter(20)));
        filterArray.add(new FilterInfo(R.drawable.shift_filter, new ShiftFilter(10)));
        filterArray.add(new FilterInfo(R.drawable.wave_filter, new WaveFilter(25, 10)));
        filterArray.add(new FilterInfo(R.drawable.bulge_filter, new BulgeFilter(-97)));
        filterArray.add(new FilterInfo(R.drawable.twist_filter, new TwistFilter(27, 106)));
        filterArray.add(new FilterInfo(R.drawable.ripple_filter, new RippleFilter(38, 15, true)));
        filterArray.add(new FilterInfo(R.drawable.illusion_filter, new IllusionFilter(3)));
        filterArray.add(new FilterInfo(R.drawable.supernova_filter, new SupernovaFilter(0x00FFFF, 20, 100)));
        filterArray.add(new FilterInfo(R.drawable.lensflare_filter, new LensFlareFilter()));
        filterArray.add(new FilterInfo(R.drawable.posterize_filter, new PosterizeFilter(2)));
        filterArray.add(new FilterInfo(R.drawable.gamma_filter, new GammaFilter(50)));
        filterArray.add(new FilterInfo(R.drawable.sharp_filter, new SharpFilter()));

        //v0.2
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new ComicFilter()));
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene())));//green
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene1())));//purple
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene2())));//blue
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene3())));
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new FilmFilter(80f)));
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new FocusFilter()));
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new CleanGlassFilter()));
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FF00)));//green
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FFFF)));//yellow
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0xFF0000)));//blue
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new LomoFilter()));

        //v0.1
        filterArray.add(new FilterInfo(R.drawable.invert_filter, new InvertFilter()));
        filterArray.add(new FilterInfo(R.drawable.blackwhite_filter, new BlackWhiteFilter()));
        filterArray.add(new FilterInfo(R.drawable.edge_filter, new EdgeFilter()));
        filterArray.add(new FilterInfo(R.drawable.pixelate_filter, new PixelateFilter()));
        filterArray.add(new FilterInfo(R.drawable.neon_filter, new NeonFilter()));
        filterArray.add(new FilterInfo(R.drawable.bigbrother_filter, new BigBrotherFilter()));
        filterArray.add(new FilterInfo(R.drawable.monitor_filter, new MonitorFilter()));
        filterArray.add(new FilterInfo(R.drawable.relief_filter, new ReliefFilter()));
        filterArray.add(new FilterInfo(R.drawable.brightcontrast_filter, new BrightContrastFilter()));
        filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter, new SaturationModifyFilter()));
        filterArray.add(new FilterInfo(R.drawable.threshold_filter, new ThresholdFilter()));
        filterArray.add(new FilterInfo(R.drawable.noisefilter, new NoiseFilter()));
        filterArray.add(new FilterInfo(R.drawable.banner_filter1, new BannerFilter(10, true)));
        filterArray.add(new FilterInfo(R.drawable.banner_filter2, new BannerFilter(10, false)));
        filterArray.add(new FilterInfo(R.drawable.rectmatrix_filter, new RectMatrixFilter()));
        filterArray.add(new FilterInfo(R.drawable.blockprint_filter, new BlockPrintFilter()));
        filterArray.add(new FilterInfo(R.drawable.brick_filter, new BrickFilter()));
        filterArray.add(new FilterInfo(R.drawable.gaussianblur_filter, new GaussianBlurFilter()));
        filterArray.add(new FilterInfo(R.drawable.light_filter, new LightFilter()));
        filterArray.add(new FilterInfo(R.drawable.mosaic_filter, new MistFilter()));
        filterArray.add(new FilterInfo(R.drawable.mosaic_filter, new MosaicFilter()));
        filterArray.add(new FilterInfo(R.drawable.oilpaint_filter, new OilPaintFilter()));
        filterArray.add(new FilterInfo(R.drawable.radialdistortion_filter, new RadialDistortionFilter()));
        filterArray.add(new FilterInfo(R.drawable.reflection1_filter, new ReflectionFilter(true)));
        filterArray.add(new FilterInfo(R.drawable.reflection2_filter, new ReflectionFilter(false)));
        filterArray.add(new FilterInfo(R.drawable.saturationmodify_filter, new SaturationModifyFilter()));
        filterArray.add(new FilterInfo(R.drawable.smashcolor_filter, new SmashColorFilter()));
        filterArray.add(new FilterInfo(R.drawable.tint_filter, new TintFilter()));
        filterArray.add(new FilterInfo(R.drawable.vignette_filter, new VignetteFilter()));
        filterArray.add(new FilterInfo(R.drawable.autoadjust_filter, new AutoAdjustFilter()));
        filterArray.add(new FilterInfo(R.drawable.colorquantize_filter, new ColorQuantizeFilter()));
        filterArray.add(new FilterInfo(R.drawable.waterwave_filter, new WaterWaveFilter()));
        filterArray.add(new FilterInfo(R.drawable.vintage_filter, new VintageFilter()));
        filterArray.add(new FilterInfo(R.drawable.oldphoto_filter, new OldPhotoFilter()));
        filterArray.add(new FilterInfo(R.drawable.sepia_filter, new SepiaFilter()));
        filterArray.add(new FilterInfo(R.drawable.rainbow_filter, new RainBowFilter()));
        filterArray.add(new FilterInfo(R.drawable.feather_filter, new FeatherFilter()));
        filterArray.add(new FilterInfo(R.drawable.xradiation_filter, new XRadiationFilter()));
        filterArray.add(new FilterInfo(R.drawable.nightvision_filter, new NightVisionFilter()));

        filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter, null/* �˴������ԭͼЧ�� */));

        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_test, menu);
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
