package pl.kibao.githubclient.ui.users;

import dagger.Module;
import dagger.Provides;
import pl.kibao.githubclient.ui.ActivityScope;

@Module
public class UsersActivityModule {
    private UsersActivity activity;

    public UsersActivityModule(UsersActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    public UsersActivity providesUsersActivity() {
        return activity;
    }

}
