package pl.kibao.githubclient.data;

import dagger.Module;
import dagger.Provides;
import pl.kibao.githubclient.api.ApiModule;
import pl.kibao.githubclient.api.RetrofitUsersApi;

@Module(includes = ApiModule.class)
public class DataModule {
    @Provides
    public UserService providesUserService(RetrofitUsersApi api) {
        return new RetrofitUserService(api);
    }
}
