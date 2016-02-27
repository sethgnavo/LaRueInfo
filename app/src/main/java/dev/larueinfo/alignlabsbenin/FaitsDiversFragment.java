package dev.larueinfo.alignlabsbenin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.models.Article;
import dev.larueinfo.alignlabsbenin.Single.SingleFaitsDiversActivity;

public class FaitsDiversFragment extends Fragment {
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
    public ImageView img1, img2, img3;
    public TextView txt1, txt2, txt3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Firebase.setAndroidContext(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Faitdivers");
        //Firebase ref = backend.child("issueTime");
        Query queryRef = backend.orderByChild("issueTime");
        //Query queryRef = backend.orderByChild("issueTime").startAt().limitToLast(15);


        img1 = (ImageView) view.findViewById(R.id.viewer1);
        img1.setImageResource(R.mipmap.anonn);
        txt1 = (TextView) view.findViewById(R.id.text_pub_1);
        txt1.setText("Vos Pub ici qui défilent ! Contacter le 64967477");
        list = (ListView) view.findViewById(R.id.newsListView);
        listAdapter = new FirebaseListAdapter<Article>(getActivity(), Article.class, R.layout.items, queryRef) {
            @Override
            protected void populateView(View view, Article o) {
                ImageView img = (ImageView) view.findViewById(R.id.avatarInfo);
                Picasso.with(getActivity())
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
            }

        };
        list.setStackFromBottom(true);
        list.setAdapter(listAdapter);
        // list.setStackFromBottom(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str = listAdapter.getRef(position).getKey();
                Intent intSingle = new Intent(getActivity(), SingleFaitsDiversActivity.class);
                intSingle.putExtra(KEY, str);
                startActivity(intSingle);
            }
        });

        return view;

    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipperA.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipperA.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    // controlling animation
                    mViewFlipperA.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipperA.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipperA.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipperA.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    // controlling animation
                    mViewFlipperA.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipperA.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}
