package pl.kibao.githubclient.data;

import java.util.ArrayList;
import java.util.List;

import pl.kibao.githubclient.api.RetrofitUsersApi;
import pl.kibao.githubclient.api.UserResponse;
import rx.Observable;

public class RetrofitUserService implements UserService {
    private RetrofitUsersApi api;

    public RetrofitUserService(RetrofitUsersApi api) {
        this.api = api;
    }

    public Observable<List<User>> users() {
        return api.users().map(usersResponse -> {
            List<User> list = new ArrayList<>(usersResponse.size());
            for (int i = 0; i < usersResponse.size(); i++) {
                UserResponse userResponse = usersResponse.get(i);
                User user = new User();
                user.id = userResponse.id;
                user.username = userResponse.login;
                user.avatarUrl = userResponse.avatarUrl;
                user.type = userResponse.type;
                list.add(user);
            }
            return list;
        });
    }
}
