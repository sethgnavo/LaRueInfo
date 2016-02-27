package dev.larueinfo.alignlabsbenin.Single;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.ImageViewActivity;
import dev.larueinfo.alignlabsbenin.ModeActivity;
import dev.larueinfo.alignlabsbenin.R;
import dev.larueinfo.alignlabsbenin.models.Mode;

public class BeautyActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "dev.larueinfo.alignlabsbenin.Single.EXTRA_IMAGE";
    private Firebase backend, backendUser;
    private TextView articleTitle, articleDescription, rawHtmlContent, authorName, sourceName, issueTime;
    private ImageView graphicDescription, imageA, imageB, imageC;
    String share;
    private String m_Text = "";
    Mode post;
    Firebase commentRef, likeRef;
    private String MyPREFERENCES = "MyPrefs";
    private String sNom = "nameKey";
    private String sMail = "mailKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleTitle = (TextView) findViewById(R.id.article_title);
        rawHtmlContent = (TextView) findViewById(R.id.raw_html_content);
        articleDescription = (TextView) findViewById(R.id.article_description);
        authorName = (TextView) findViewById(R.id.author_name);
        sourceName = (TextView) findViewById(R.id.source_name);
        issueTime = (TextView) findViewById(R.id.issue_time);
        graphicDescription = (ImageView) findViewById(R.id.graphic_description);
        imageA = (ImageView) findViewById(R.id.imgMode2);
        imageB = (ImageView) findViewById(R.id.imgMode3);
        imageC = (ImageView) findViewById(R.id.imgMode4);


        Intent intent = getIntent();
        final String date = intent.getStringExtra(ModeActivity.KEY);
        final Typeface contentFont = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        final Typeface descriptionFont = Typeface.createFromAsset(getAssets(), "fonts/hurtm.otf");

        backend = new Firebase("https://yadialigninfo.firebaseio.com/Mode").child("date");
        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(Mode.class);

                //time setup
//                CharSequence charTtime = DateUtils.getRelativeTimeSpanString(
//                        Long.parseLong(String.valueOf(post.getIssueTime())),
//                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);


                if (post.getGraphicDescription() != "") {
                    Picasso.with(getApplication())
                            .load(post.getGraphicDescription())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(graphicDescription);
                }
                /*if (post.getImage1() != "") {
                    Picasso.with(getApplication())
                            .load(post.getImage1())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(graphicDescription);
                }
                if (post.getImage2() != "") {
                    Picasso.with(getApplication())
                            .load(post.getImage2())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(graphicDescription);
                }
                if (post.getImage3() != "") {
                    Picasso.with(getApplication())
                            .load(post.getImage3())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(graphicDescription);
                }*/

                //Handle show zoomable graphic description
                graphicDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = new String();
                        str = post.getGraphicDescription();
                        Intent o = new Intent(getApplicationContext(), ImageViewActivity.class);
                        o.putExtra(EXTRA_DATA, str);
                        startActivity(o);
                    }
                });
                /*
                //Handle show zoomable graphic description
                imageA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = new String();
                        str = post.getImage1();
                        Intent o = new Intent(getApplicationContext(), ImageViewActivity.class);
                        o.putExtra(EXTRA_DATA, str);
                        startActivity(o);
                    }
                });
                //Handle show zoomable graphic description
                imageB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = new String();
                        str = post.getImage2();
                        Intent o = new Intent(getApplicationContext(), ImageViewActivity.class);
                        o.putExtra(EXTRA_DATA, str);
                        startActivity(o);
                    }
                });
                //Handle show zoomable graphic description
                imageC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = new String();
                        str = post.getImage3();
                        Intent o = new Intent(getApplicationContext(), ImageViewActivity.class);
                        o.putExtra(EXTRA_DATA, str);
                        startActivity(o);
                    }
                });*/

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Vous avez Ajouter aux Favoris", Toast.LENGTH_LONG).show();
            }
        });
    }

}
