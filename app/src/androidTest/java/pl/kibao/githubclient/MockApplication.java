package pl.kibao.githubclient;

import android.support.test.InstrumentationRegistry;

public class MockApplication extends GithubClientApplication {

    private AppComponent appComponent;

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    @Override
    public AppComponent appComponent() {
        if (appComponent == null) {
            return super.appComponent();
        }
        return appComponent;
    }

    public static MockApplication applicationContext() {
        return (MockApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}
