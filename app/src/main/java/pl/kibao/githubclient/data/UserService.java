package pl.kibao.githubclient.data;

import java.util.List;

import rx.Observable;

public interface UserService {
    Observable<List<User>> users();
}
