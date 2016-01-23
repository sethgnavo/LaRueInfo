package dev.larueinfo.alignlabsbenin.setup;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * @author Seth-Pharès Gnavo (sethgnavo)
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
