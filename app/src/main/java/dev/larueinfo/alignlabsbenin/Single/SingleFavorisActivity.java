package dev.larueinfo.alignlabsbenin.Single;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.FavorisActivity;
import dev.larueinfo.alignlabsbenin.ImageViewActivity;
import dev.larueinfo.alignlabsbenin.models.Favoris;
import dev.larueinfo.alignlabsbenin.R;

public class SingleFavorisActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "dev.buzzlivemessenger.alignlabsbenin.Single.EXTRA_IMAGE";
    private Firebase backend;
    private TextView articleTitle, articleDescription, rawHtmlContent, authorName, sourceName, issueTime;
    private ImageView graphicDescription;
    String share;
    Favoris post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String lien = intent.getStringExtra(FavorisActivity.KEY);
        final Typeface contentFont = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        final Typeface descriptionFont = Typeface.createFromAsset(getAssets(), "fonts/hurtm.otf");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        backend = new Firebase(lien);

        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(Favoris.class);
                articleTitle = (TextView) findViewById(R.id.article_title);
                rawHtmlContent = (TextView) findViewById(R.id.raw_html_content);
                articleDescription = (TextView) findViewById(R.id.article_description);
                authorName = (TextView) findViewById(R.id.author_name);
                sourceName = (TextView) findViewById(R.id.source_name);
                issueTime = (TextView) findViewById(R.id.issue_time);
                graphicDescription = (ImageView) findViewById(R.id.graphic_description);

                //time setup
                CharSequence charTtime = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(post.getIssueTime())),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

                articleTitle.setText(post.getArticleTitle());
                rawHtmlContent.setText(Html.fromHtml(post.getRawHtmlContent()));
                articleDescription.setText(post.getArticleDescription());
                rawHtmlContent.setTypeface(contentFont);
                articleDescription.setTypeface(descriptionFont);
                authorName.setText(post.getAuthorName());
                sourceName.setText(Html.fromHtml(post.getSourceName()));
                issueTime.setText(charTtime);
                if (post.getGraphicDescription() != "") {
                    Picasso.with(getApplication())
                            .load(post.getGraphicDescription())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(graphicDescription);
                }

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

                share = post.getArticleTitle();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Handle menu actions
        if (id == R.id.partage) {

        }

        if (id == R.id.favoris) {

        }
        if(id==R.id.like)
        {

        }

        return super.
                onOptionsItemSelected(item);
    }*/

}