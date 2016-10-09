package pl.kibao.githubclient.ui.users;

import dagger.Subcomponent;
import pl.kibao.githubclient.ui.ActivityModule;
import pl.kibao.githubclient.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class, UsersActivityModule.class})
public interface UsersActivityComponent {
    void inject(UsersActivity activity);
}
