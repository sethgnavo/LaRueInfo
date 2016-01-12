package dev.larueinfo.alignlabsbenin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import dev.larueinfo.alignlabsbenin.Models.Model;
import dev.larueinfo.alignlabsbenin.Single.SingleActualiteActivity;

public class ActualiteFragment extends Fragment {
    private Firebase backend;
    private ListView list;
    private FirebaseListAdapter listAdapter;
    public static final String KEY = "dev.buzzlivemessenger.alignlabsbenin.EXTRA_KEY";
    String str;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlippe,mViewFlipperA;
    private Animation.AnimationListener mAnimationListener;
    private Context mContext;
    public ImageView img1,img2,img3;
    public TextView txt1,txt2,txt3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase.setAndroidContext(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        backend = new Firebase("https://yadialigninfo.firebaseio.com/Actualites");

        /*@SuppressWarnings("deprecation")
        final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
        mContext = getActivity();
        mViewFlipperA = (ViewFlipper) getActivity().findViewById(R.id.view_flipper);
        mViewFlipperA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
        img1 = (ImageView)view.findViewById(R.id.viewer1);
        img1.setImageResource(R.mipmap.anonn);
        txt1 = (TextView)view.findViewById(R.id.text_pub_1);
        txt1.setText("Vos Pub ici qui défilent ! Contacter le 64967477");
        img2 = (ImageView)view.findViewById(R.id.viewer2);
        img2.setImageResource(R.mipmap.logo);
        img3 = (ImageView)view.findViewById(R.id.viewer3);
        img3.setImageResource(R.mipmap.alissa2);

        //sets auto flipping
        mViewFlipperA.setAutoStart(true);
        mViewFlipperA.setFlipInterval(4000);
        mViewFlipperA.startFlipping();

        //animation listener
        mAnimationListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                //animation started event
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                //TODO animation stopped event
            }
        };*/
        img1 = (ImageView)view.findViewById(R.id.viewer1);
        img1.setImageResource(R.mipmap.anonn);
        txt1 = (TextView)view.findViewById(R.id.text_pub_1);
        txt1.setText("Vos Pub ici qui défilent ! Contacter le 64967477");
        list = (ListView) view.findViewById(R.id.newsListView);
        listAdapter = new FirebaseListAdapter<Model>(getActivity(), Model.class, R.layout.items, backend) {
            @Override
            protected void populateView(View view, Model o) {
                ImageView img = (ImageView) view.findViewById(R.id.avatarInfo);
                Picasso.with(getActivity())
                        .load(o.getImagePrincipale())
                        .placeholder(android.R.drawable.ic_menu_view)
                        .error(android.R.drawable.ic_menu_view)
                        .into(img);
                ((TextView) view.findViewById(R.id.titreInfoS)).setText(o.getTitre());
                ((TextView) view.findViewById(R.id.grdTitreInfo)).setText(o.getGrdTitre());
                TextView time = (TextView) view.findViewById(R.id.dateInfo);
                //final CharSequence date_post = DateUtils.getRelativeTimeSpanString(
                //        Long.parseLong(String.valueOf(o.getTime())),
                //       System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
                //time.setText(date_post);
            }
        };
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str = listAdapter.getRef(position).getKey();
                Intent intSingle = new Intent(getActivity(), SingleActualiteActivity.class);
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
