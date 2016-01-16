package dev.larueinfo.alignlabsbenin.Single;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import dev.larueinfo.alignlabsbenin.ActualiteFragment;
import dev.larueinfo.alignlabsbenin.Models.Model;
import dev.larueinfo.alignlabsbenin.R;

public class SingleActualiteActivity extends AppCompatActivity {
    private Firebase backend;
    TextView titre, grdTitre, time, auteur, desc, pTitre1, desc_pTitre1, pTitre2, desc_pTitre2, pTitre3, desc_pTitre3, source;
    ImageView imagePrincipale, img_pTitre1, img_pTitre2, img_pTitre3;
    String share;
    String ti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Firebase.setAndroidContext(getApplicationContext());
        setContentView(R.layout.activity_single_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String date = intent.getStringExtra(ActualiteFragment.KEY);
        final Typeface custFace = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share + "\n" + "Rdv sur votre Application Buzzlive dans la rubrique Actualité pour lire la suite");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Actualites/" + date);
        backend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Model post = snapshot.getValue(Model.class);
                titre = (TextView) findViewById(R.id.titreInfoS);
                grdTitre = (TextView) findViewById(R.id.grdTitre);
                desc = (TextView) findViewById(R.id.descinfoS);
                auteur = (TextView) findViewById(R.id.auteurInfo);
                time = (TextView) findViewById(R.id.dateInfo);
                pTitre1 = (TextView) findViewById(R.id.pTitre1);
                pTitre2 = (TextView) findViewById(R.id.pTitre2);
                pTitre3 = (TextView) findViewById(R.id.pTitre3);
                desc_pTitre1 = (TextView) findViewById(R.id.desc_pTitre1);
                desc_pTitre2 = (TextView) findViewById(R.id.desc_pTitre2);
                desc_pTitre3 = (TextView) findViewById(R.id.desc_pTitre3);
                source = (TextView) findViewById(R.id.sourceInfoS);
                //time = (TextView) findViewById(R.id.dateInfo);


                imagePrincipale = (ImageView) findViewById(R.id.imageInfoS);
                img_pTitre1 = (ImageView) findViewById(R.id.img_pTitre1);
                img_pTitre2 = (ImageView) findViewById(R.id.img_pTitre2);
                img_pTitre3 = (ImageView) findViewById(R.id.img_pTitre3);

                grdTitre.setText(post.getSource());
                titre.setText(post.getTitre());
                desc.setText(post.getDesc());
                auteur.setText(post.getAuteur());
                source.setText(post.getSource());
                getSupportActionBar().setTitle("Actualités ");
                if (post.getTime() != null) {
                    CharSequence date_post_xago = DateUtils.getRelativeTimeSpanString(
                            Long.parseLong(String.valueOf(post.getTime())),
                            System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
                    time.setText(date_post_xago);
                }
                Picasso.with(getApplication())
                        .load(post.getImagePrincipale())
                        .placeholder(android.R.drawable.ic_menu_view)
                        .error(android.R.drawable.ic_menu_view)
                        .resize(120, 120)
                        .into(imagePrincipale);

                if (post.getImg_pTitre1()!="") {
                    Picasso.with(getApplication())
                            .load(post.getImg_pTitre1())
                            .placeholder(android.R.drawable.ic_menu_view)
                            .error(android.R.drawable.ic_menu_view)
                            .resize(120, 120)
                            .into(img_pTitre1);
                }
                if (post.getImg_pTitre2() != "") {
                    Picasso.with(getApplication())
                            .load(post.getImg_pTitre2())
                            .placeholder(android.R.drawable.ic_menu_view)
                            .error(android.R.drawable.ic_menu_view)
                            .resize(120, 120)
                            .into(img_pTitre2);
                }
                if (post.getImg_pTitre3() != "") {
                    Picasso.with(getApplication())
                            .load(post.getImg_pTitre3())
                            .placeholder(android.R.drawable.ic_menu_view)
                            .error(android.R.drawable.ic_menu_view)
                            .resize(120, 120)
                            .into(img_pTitre3);
                }
                if (post.getptitre1() != "") {
                    pTitre1.setText(post.getptitre1());
                }
                if (post.getptitre2() != "") {
                    pTitre2.setText(post.getptitre2());
                }
                if (post.getptitre3() != "") {
                    pTitre3.setText(post.getptitre3());
                }
                if (post.getDesc_pTitre1() != "") {
                    desc_pTitre1.setText(post.getDesc_pTitre1());
                }
                if (post.getDesc_pTitre2() != "") {
                    desc_pTitre2.setText(post.getDesc_pTitre2());
                }
                if (post.getDesc_pTitre3() != "") {
                    desc_pTitre3.setText(post.getDesc_pTitre3());
                }
                share = post.getTitre().toString();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}