package dev.larueinfo.alignlabsbenin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.Single.SingleFaitsDiversActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewActivity extends AppCompatActivity {
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent imgIntent = getIntent();
        String ref = imgIntent.getStringExtra(SingleFaitsDiversActivity.EXTRA_DATA);
        ImageView imageView = (ImageView) findViewById(R.id.img_graphic_description);

        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setZoomable(true);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);

        Picasso.with(getApplication())
                .load(ref)
                .placeholder(R.drawable.ic_image_black_48dp)
                .error(R.drawable.ic_broken_image_black_48dp)
                .into(imageView);
    }
}