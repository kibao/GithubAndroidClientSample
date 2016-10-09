package pl.kibao.githubclient;

import android.app.Application;
import android.app.Service;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private GithubClientApplication application;

    public AppModule(GithubClientApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public GithubClientApplication provideGithubKataApplication() {
        return application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }

}
