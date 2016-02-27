package dev.larueinfo.alignlabsbenin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.models.Favoris;
import dev.larueinfo.alignlabsbenin.Single.SingleActualiteActivity;
import dev.larueinfo.alignlabsbenin.Single.SingleFavorisActivity;

public class FavorisActivity extends AppCompatActivity {
    private String MyPREFERENCES = "MyPrefs";
    private String sNom = "nameKey";
    private String sMail = "mailKey";
    private SharedPreferences sharedPreferences;
    private String txtnom, txtmail, txt, name, lien;
    private TextView txtxUser;
    private Firebase backend, backendLien;
    private ListView list;
    private FirebaseListAdapter listAdapter;
    public static final String KEY = "dev.buzzlivemessenger.alignlabsbenin.EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent imgIntent = getIntent();
        String ref = imgIntent.getStringExtra(SingleActualiteActivity.EXTRA_DATA);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //txtxUser = (TextView) findViewById(R.id.nomFav);
        SharedPreferences retrieve = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        txtmail = retrieve.getString("mailKey", null);
        list = (ListView) findViewById(R.id.listFav);
        if (txtmail != null) {
            backend = new Firebase("https://yadialigninfo.firebaseio.com/User/" + txtmail + "/Favoris/");
            listAdapter = new FirebaseListAdapter<Favoris>(this, Favoris.class, R.layout.items, backend) {
                @Override
                protected void populateView(View view, Favoris o) {
                    ImageView img = (ImageView) view.findViewById(R.id.avatarInfo);
                    Picasso.with(FavorisActivity.this)
                            .load(o.getGraphicDescription())
                            .placeholder(R.drawable.ic_image_black_48dp)
                            .error(R.drawable.ic_broken_image_black_48dp)
                            .into(img);
                    ((TextView) view.findViewById(R.id.titreInfoS)).setText(o.getArticleTitle());
                    ((TextView) view.findViewById(R.id.grdTitreInfo)).setText(o.getArticleDescription());
                    TextView time = (TextView) view.findViewById(R.id.dateInfo);
                    final CharSequence date_post = DateUtils.getRelativeTimeSpanString(
                            Long.parseLong(String.valueOf(o.getIssueTime())),
                            System.currentTimeMillis(), DateUtils.YEAR_IN_MILLIS);
                    time.setText(date_post);
                    lien = o.getLien();
                }
            };
            list.setStackFromBottom(true);
            list.setAdapter(listAdapter);
            // list.setStackFromBottom(true);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fv = listAdapter.getRef(position).getKey();
                    String str = "https://yadialigninfo.firebaseio.com/User/" + txtmail + "/Favoris/" + fv;
                    String vf = backend + fv;
                    Intent intSingle = new Intent(getApplicationContext(), SingleFavorisActivity.class);
                    intSingle.putExtra(KEY, str);
                    startActivity(intSingle);
                }
            });
        } else {
            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this, R.style.AppTheme);
            dialog.setTitle("Inscription");
            dialog.setIcon(R.drawable.logo_align);
            dialog.setMessage("Vous êtes pas encore inscris sur LaRue Info ? \n " +
                    "Inscrivez-vous maintenant SVP");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent o = new Intent(getApplicationContext(), SignActivity.class);
                    startActivity(o);
                }
            });
            dialog.show();
        }
    }

}
