package pl.kibao.githubclient.ui.users;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import pl.kibao.githubclient.data.UserService;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsersPresenter {
    private UsersActivity view;
    private UserService userService;
    private Subscription subscription;

    @Inject
    public UsersPresenter(UsersActivity view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    public void loadUserList() {
        view.setLoading(true);
        unsubscribe();
        subscription = userService.users()
            .zipWith(Observable.timer(300, TimeUnit.MILLISECONDS), (users, aLong) -> users)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(users -> {
                view.setData(users);
                view.setLoading(false);
            });
    }

    public void unsubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
