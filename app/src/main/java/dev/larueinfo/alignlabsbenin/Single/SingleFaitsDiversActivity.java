package dev.larueinfo.alignlabsbenin.Single;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.FaitsDiversFragment;
import dev.larueinfo.alignlabsbenin.Models.Article;
import dev.larueinfo.alignlabsbenin.R;

public class SingleFaitsDiversActivity extends AppCompatActivity {
    private Firebase backend;
    private TextView articleTitle, articleDescription, rawHtmlContent, authorName, sourceName, issueTime;
    private ImageView graphicDescription;
    String share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String date = intent.getStringExtra(FaitsDiversFragment.KEY);
        final Typeface contentFont = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        final Typeface descriptionFont = Typeface.createFromAsset(getAssets(), "fonts/hurtm.otf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application Buzzlive dans la rubrique Divers pour lire la suite");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Faitdivers/" + date);
        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Article post = snapshot.getValue(Article.class);
                articleTitle = (TextView) findViewById(R.id.article_title);
                articleDescription = (TextView) findViewById(R.id.article_description);
                rawHtmlContent = (TextView) findViewById(R.id.raw_html_content);
                authorName = (TextView) findViewById(R.id.author_name);
                sourceName = (TextView) findViewById(R.id.source_name);
                issueTime = (TextView) findViewById(R.id.issue_time);
                graphicDescription = (ImageView) findViewById(R.id.graphic_description);

                //time setup
                CharSequence charTime = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(post.getIssueTime())),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

//                String time = DateUtils.formatDateTime(getApplicationContext(),
//                        Long.parseLong(String.valueOf(post.getIssueTime())),
//                        DateUtils.FORMAT_SHOW_TIME);

                articleTitle.setText(post.getArticleTitle());
                articleDescription.setText(post.getArticleDescription());
                rawHtmlContent.setText(Html.fromHtml(post.getRawHtmlContent()));
                rawHtmlContent.setTypeface(contentFont);
                articleDescription.setTypeface(descriptionFont);
                authorName.setText(post.getAuthorName());
                if (post.getSourceName() != null) {
                    sourceName.setText(post.getSourceName());
                }
                issueTime.setText(charTime);
                if (post.getGraphicDescription() != "") {
                    Picasso.with(getApplication())
                            .load(post.getGraphicDescription())
                            .placeholder(android.R.drawable.ic_menu_view)
                            .error(android.R.drawable.ic_menu_view)
                            .resize(120, 120)
                            .into(graphicDescription);
                }

                share = post.getArticleTitle();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ImageGetter implements Html.ImageGetter {
        public Drawable getDrawable(String source) {
            int id;
            if (source.equals("stack.jpg")) {
                id = R.drawable.ic_launcher;
            } else if (source.equals("overflow.jpg")) {
                id = R.drawable.img_drawer;
            } else {
                return null;
            }
            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    }
}