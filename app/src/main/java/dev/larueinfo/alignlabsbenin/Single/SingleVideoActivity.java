package dev.larueinfo.alignlabsbenin.Single;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import dev.larueinfo.alignlabsbenin.ActualiteFragment;
import dev.larueinfo.alignlabsbenin.models.Video;
import dev.larueinfo.alignlabsbenin.R;

public class SingleVideoActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "dev.buzzlivemessenger.alignlabsbenin.Single.EXTRA_IMAGE";
    private Firebase backend;
    private TextView articleTitle, rawHtmlContent, authorName, sourceName, issueTime;
    private String lien;
    private ImageView graphicDescription;
    String share;
    private ProgressDialog pDialog;
    private VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String date = intent.getStringExtra(ActualiteFragment.KEY);
        final Typeface contentFont = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        final Typeface descriptionFont = Typeface.createFromAsset(getAssets(), "fonts/hurtm.otf");
        videoview = (VideoView) findViewById(R.id.videoView);

        ImageButton sharer = (ImageButton) findViewById(R.id.sharing);
        sharer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application LaRue info dans la rubrique VIDEOS pour voir la vidéos");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application LaRue info dans la rubrique VIDEOS pour voir la vidéos");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/
                Toast.makeText(getApplicationContext(), "COMMENTAIRES BIENTOT", Toast.LENGTH_LONG).show();
            }
        });
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Video/" + date);

        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Video post = dataSnapshot.getValue(Video.class);

                pDialog = new ProgressDialog(SingleVideoActivity.this);
                // Set progressbar title
                pDialog.setTitle(post.getArticleTitle());
                // Set progressbar message
                pDialog.setMessage("Patientez svp ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                // Show progressbar
                pDialog.show();

                try {
                    // Start the MediaController
                    MediaController mediacontroller = new MediaController(
                            SingleVideoActivity.this);
                    mediacontroller.setAnchorView(videoview);
                    // Get the URL from String VideoURL
                    Uri video = Uri.parse(post.getLien());
                    videoview.setMediaController(mediacontroller);
                    videoview.setVideoURI(video);

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                videoview.requestFocus();
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                        videoview.start();
                    }
                });
                articleTitle = (TextView) findViewById(R.id.article_title);
                rawHtmlContent = (TextView) findViewById(R.id.raw_html_content);
                //articleDescription = (TextView) findViewById(R.id.article_description);
                authorName = (TextView) findViewById(R.id.author_name);
                //sourceName = (TextView) findViewById(R.id.source_name);
                issueTime = (TextView) findViewById(R.id.issue_time);
                //graphicDescription = (ImageView) findViewById(R.id.graphic_description);

                //time setup
                CharSequence charTtime = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(post.getIssueTime())),
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

                articleTitle.setText(post.getArticleTitle());
                rawHtmlContent.setText(Html.fromHtml(post.getRawHtmlContent()));
                //articleDescription.setText(post.getArticleDescription());
                rawHtmlContent.setTypeface(contentFont);
                //articleDescription.setTypeface(descriptionFont);
                authorName.setText(post.getAuthorName());
                //sourceName.setText(Html.fromHtml(post.getSourceName()));
                issueTime.setText(charTtime);

                //Handle show zoomable graphic description
                /*graphicDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = new String();
                        str = post.getGraphicDescription();
                        Intent o = new Intent(getApplicationContext(), ImageViewActivity.class);
                        o.putExtra(EXTRA_DATA, str);
                        startActivity(o);
                    }
                });*/

                share = post.getArticleTitle();
                lien = post.getLien();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
