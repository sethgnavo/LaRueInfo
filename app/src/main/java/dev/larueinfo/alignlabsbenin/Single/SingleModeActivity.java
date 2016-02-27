package dev.larueinfo.alignlabsbenin.Single;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import dev.larueinfo.alignlabsbenin.ImageViewActivity;
import dev.larueinfo.alignlabsbenin.ModeActivity;
import dev.larueinfo.alignlabsbenin.models.Article;
import dev.larueinfo.alignlabsbenin.R;

public class SingleModeActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "dev.buzzlivemessenger.alignlabsbenin.Single.EXTRA_IMAGE";
    private Firebase backend, backendUser;
    private TextView articleTitle, articleDescription, rawHtmlContent, authorName, sourceName, issueTime;
    private ImageView graphicDescription, imageA, imageB, imageC;
    String share;
    private String m_Text = "";
    Article post;
    Firebase commentRef, likeRef;
    private String MyPREFERENCES = "MyPrefs";
    private String sNom = "nameKey";
    private String sMail = "mailKey";
    private TextView txtxUser, txtMail;
    private String txtnom, txtmail, lienKey, lien;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences retrieve = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        txtnom = retrieve.getString("nameKey", null);
        txtmail = retrieve.getString("mailKey", null);

        Intent intent = getIntent();
        final String date = intent.getStringExtra(ModeActivity.KEY);
        final Typeface contentFont = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        final Typeface descriptionFont = Typeface.createFromAsset(getAssets(), "fonts/hurtm.otf");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleModeActivity.this);
                builder.setTitle("Votre Commentaire");
                // Set up the input
                final EditText input = new EditText(SingleModeActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commentRef = backend.child("Comment");
                        m_Text = input.getText().toString();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("user", "rach");
                        map.put("message", m_Text);
                        map.put("time", String.valueOf(System.currentTimeMillis()));
                        commentRef.push().setValue(map);
                        Toast.makeText(getApplicationContext(), "Commentaire posté avec succes", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Mode/" + date);
        backendUser = new Firebase("https://yadialigninfo.firebaseio.com/");
        commentRef = backend.child("Comment");

        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(Article.class);
                lienKey = dataSnapshot.getRef().getKey();
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
                share = post.getArticleTitle();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        /*ListView list = (ListView) findViewById(R.id.listComment);
        FirebaseListAdapter listAdapte = new FirebaseListAdapter<Commentaires>(this, Commentaires.class, R.layout.itemscomment, commentRef) {
            @Override
            protected void populateView(View view, Commentaires o) {
                ((TextView) view.findViewById(R.id.nomComment)).setText(o.getUser());
                ((TextView) view.findViewById(R.id.messageComment)).setText(o.getMessage());
                TextView time = (TextView) view.findViewById(R.id.heureComment);
                final CharSequence date_post = DateUtils.getRelativeTimeSpanString(
                        Long.parseLong(String.valueOf(o.getTime())),
                        System.currentTimeMillis(), DateUtils.YEAR_IN_MILLIS);
                time.setText(date_post);
            }
        };
        list.setAdapter(listAdapte);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Handle menu actions
        if (id == R.id.partage) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application LaRue Info dans la rubrique Mode pour lire la suite");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        if (id == R.id.favoris) {
            Firebase commentRef = backendUser.child("User/"+txtmail+"/Favoris");
            //lien = backend+lienKey;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("articleTitle", post.getArticleTitle());
            map.put("rawHtmlContent", post.getRawHtmlContent());
            map.put("articleDescription", post.getArticleDescription());
            map.put("authorName", post.getAuthorName());
            map.put("sourceName", post.getSourceName());
            map.put("lien", backend.toString());
            map.put("graphicDescription", post.getGraphicDescription());
            map.put("issueTime", String.valueOf(System.currentTimeMillis()));
            commentRef.push().setValue(map);
            Toast.makeText(getApplicationContext(), "Vous avez Ajouter aux Favoris", Toast.LENGTH_LONG).show();
        }
        if(id==R.id.like)
        {
            Firebase likeRef = backend.child("Like");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user", "rach");
            map.put("like", "1");
            map.put("time", String.valueOf(System.currentTimeMillis()));
            likeRef.push().setValue(map);
            Toast.makeText(getApplicationContext(), "Vous avez aimé", Toast.LENGTH_LONG).show();
        }

        return super.
                onOptionsItemSelected(item);
    }*/

}