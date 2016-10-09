package pl.kibao.githubclient;

import android.app.Application;
import android.content.Context;

import pl.kibao.githubclient.data.DataModule;

public class GithubClientApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }


    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .dataModule(new DataModule())
            .build();
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    public static AppComponent appComponent(Context context) {
        return ((GithubClientApplication) (context.getApplicationContext())).appComponent();
    }
}
