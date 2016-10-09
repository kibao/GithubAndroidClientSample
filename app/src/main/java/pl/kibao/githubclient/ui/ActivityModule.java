package pl.kibao.githubclient.ui;

import android.app.Activity;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public LayoutInflater providesLayoutInflater() {
        return activity.getLayoutInflater();
    }
}
