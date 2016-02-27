package dev.larueinfo.alignlabsbenin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import dev.larueinfo.alignlabsbenin.dialogs.AboutDialog;
import dev.larueinfo.alignlabsbenin.dialogs.HelpDialog;

public class HomeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private TextView toolBarTitle;

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Typeface custFace = Typeface.createFromAsset(getAssets(), "fonts/silent_reaction.ttf");
        toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolBarTitle.setTypeface(custFace);
    }

    private void setupTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.md_pink_A700));
        tabLayout.setSelectedTabIndicatorHeight(4);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_text_normal),
                getResources().getColor(R.color.tab_text_selected));
    }

    private void setupNavigationDrawer(Bundle s) {
        //setup the primary items
        PrimaryDrawerItem homeDrawer = new PrimaryDrawerItem().withName("Accueil").withIdentifier(1);
        //add inscription
        PrimaryDrawerItem videosDrawer = new PrimaryDrawerItem().withName("Vidéos").withIdentifier(2);
        PrimaryDrawerItem modeDrawer = new PrimaryDrawerItem().withName("Mode & Beauté").withIdentifier(3);
        PrimaryDrawerItem docsDrawer = new PrimaryDrawerItem().withName("Allô Doc !").withIdentifier(4);
        PrimaryDrawerItem emploiDrawer = new PrimaryDrawerItem().withName("PK'emplois").withIdentifier(5);
        PrimaryDrawerItem favouritesDrawer = new PrimaryDrawerItem().withName("Favoris").withBadgeStyle(new BadgeStyle()
                .withTextColor(Color.WHITE)
                .withColorRes(R.color.colorAccent)).withIdentifier(6);
        //setup the secondary items
        SecondaryDrawerItem settingsDrawer = new SecondaryDrawerItem().withName("Paramètres").withIdentifier(7);
        SecondaryDrawerItem SignDrawer = new SecondaryDrawerItem().withName("Me connecter").withIdentifier(8);
        SecondaryDrawerItem helpFeedbackDrawer = new SecondaryDrawerItem().withName("Aide et feedback").withIdentifier(9);

        //setup the account header
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.img_drawer)
                        //.addProfiles(profile)
                .withSavedInstance(s)
                .build();

        //now create your drawer and pass the AccountHeader.Result
        new DrawerBuilder().withActivity(this).withAccountHeader(headerResult)
                //additional drawer setup as shown above
                .build();

        //create the drawer and remember the 'Drawer' result object
        result = new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(s)
                .withShowDrawerOnFirstLaunch(true)
                .addDrawerItems(homeDrawer, videosDrawer, modeDrawer, docsDrawer, emploiDrawer, favouritesDrawer, new DividerDrawerItem(), settingsDrawer, SignDrawer, helpFeedbackDrawer)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long id = drawerItem.getIdentifier();//pour simplifier le code
                        if (drawerItem != null) {
                            if (id == 1) {
                                //nothing yet
                            } else if (id == 2) {
                                startActivity(new Intent(getApplicationContext(), VideoActivity.class));
                            } else if (id == 3) {
                                startActivity(new Intent(getApplicationContext(), ModeActivity.class));
                            } else if (id == 4) {
                                startActivity(new Intent(getApplicationContext(), DocActivity.class));
                            } else if (id == 5) {
                                startActivity(new Intent(getApplicationContext(), EmploiActivity.class));
                            } else if (id == 6) {
                                startActivity(new Intent(getApplicationContext(), FavorisActivity.class));
                            } else if (id == 7) {
                                startActivity(new Intent(getApplicationContext(), SignActivity.class));
                            } else if (id == 8) {
                                Toast.makeText(getApplicationContext(), "Veuillez contacter le 64967477", Toast.LENGTH_LONG).show();
                            } else if (id == 9) {
                                startActivity(new Intent(getApplicationContext(), FavorisActivity.class));
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(s)
                .build();

        result.setSelection(homeDrawer);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FaitsDiversFragment tab1 = new FaitsDiversFragment();
                    return tab1;
                case 1:
                    ActualiteFragment tab2 = new ActualiteFragment();
                    return tab2;
                case 2:
                    PeopleFragment tab3 = new PeopleFragment();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Divers";
                case 1:
                    return "Actualités";
                case 2:
                    return "People";
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolBar();
        setupNavigationDrawer(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        setupTabLayout();
        Typeface custFace = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Handle menu actions
        if (id == R.id.help) {
            HelpDialog.show(this);
        }
        if (id == R.id.info) {
            AboutDialog.show(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}