package dev.larueinfo.alignlabsbenin.Single;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import dev.larueinfo.alignlabsbenin.Models.Article;
import dev.larueinfo.alignlabsbenin.PeopleFragment;
import dev.larueinfo.alignlabsbenin.R;

public class SinglePeopleActivity extends AppCompatActivity {
    private Firebase backend;
    TextView  articleTitle, rawHtmlContent,authorName,sourceName,issueTime;
    String share;
    String ti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getApplicationContext());
        setContentView(R.layout.activity_single_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent = getIntent();
        final String date = intent.getStringExtra(PeopleFragment.KEY);
        final Typeface custFace = Typeface.createFromAsset(getAssets(),"fonts/font.ttf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application LaRue Info dans la rubrique Actualité pour lire la suite ou télécharger sur playstore");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        backend = new Firebase("https://yadialigninfo.firebaseio.com/People/"+date);
        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Article post = snapshot.getValue(Article.class);
                articleTitle = (TextView) findViewById(R.id.article_title);
                rawHtmlContent = (TextView) findViewById(R.id.raw_html_content);
                authorName = (TextView) findViewById(R.id.author_name);
                sourceName = (TextView) findViewById(R.id.source_name);
                issueTime = (TextView) findViewById(R.id.issue_time);

                //time setup
                CharSequence charTtime = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(post.getIssueTime())),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

                articleTitle.setText(post.getArticleTitle());
//                rawHtmlContent.setText(Html.fromHtml(post.getRawHtmlContent()));
                authorName.setText(post.getAuthorName());
                sourceName.setText(post.getSourceName());
                issueTime.setText(charTtime);
                share = post.getArticleTitle();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
