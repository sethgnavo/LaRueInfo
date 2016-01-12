package dev.larueinfo.alignlabsbenin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView imageView = (ImageView) findViewById(R.id.logo);
        LinearLayout layout = (LinearLayout) findViewById(R.id.container);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim3);
        imageView.startAnimation(animation);

        LayoutAnimationController animation2 = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_anim);
        layout.setLayoutAnimation(animation2);
    }
}
