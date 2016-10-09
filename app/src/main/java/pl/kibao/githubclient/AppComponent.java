package pl.kibao.githubclient;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import pl.kibao.githubclient.data.DataModule;
import pl.kibao.githubclient.ui.ActivityModule;
import pl.kibao.githubclient.ui.users.UsersActivityComponent;
import pl.kibao.githubclient.ui.users.UsersActivityModule;

@Singleton
@Component(
    modules = {
        AppModule.class,
        DataModule.class
    }
)
public interface AppComponent {
    Application application();

    GithubClientApplication githubClientApplication();

    UsersActivityComponent plus(ActivityModule activityModule, UsersActivityModule module);
}
