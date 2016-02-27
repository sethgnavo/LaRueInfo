package dev.larueinfo.alignlabsbenin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.models.Video;
import dev.larueinfo.alignlabsbenin.Single.SingleVideoActivity;

public class VideoActivity extends AppCompatActivity {
    private Firebase backend;
    private ListView list;
    private FirebaseListAdapter listAdapter;
    public static final String KEY = "dev.buzzlivemessenger.alignlabsbenin.EXTRA_KEY";
    String str;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlippe, mViewFlipperA;
    private Animation.AnimationListener mAnimationListener;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Video");


        list = (ListView) findViewById(R.id.newsListView);

        listAdapter = new FirebaseListAdapter<Video>(this, Video.class, R.layout.itemsvideo, backend) {
            @Override
            protected void populateView(View view, Video o) {
                ImageView img = (ImageView) view.findViewById(R.id.avatarInfo);
                Picasso.with(getApplicationContext())
                        .load(o.getGraphicDescription())
                        .placeholder(R.drawable.ic_image_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(img);
                ((TextView) view.findViewById(R.id.titreInfoS)).setText(o.getArticleTitle());
                TextView time = (TextView) view.findViewById(R.id.dateInfo);
                final CharSequence date_post = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(o.getIssueTime())),
                        System.currentTimeMillis(), DateUtils.YEAR_IN_MILLIS);
                time.setText(date_post);


            }
        };
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str = listAdapter.getRef(position).getKey();
                Intent intSingle = new Intent(getApplicationContext(), SingleVideoActivity.class);
                intSingle.putExtra(KEY, str);
                startActivity(intSingle);
                //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        });
    }
}
