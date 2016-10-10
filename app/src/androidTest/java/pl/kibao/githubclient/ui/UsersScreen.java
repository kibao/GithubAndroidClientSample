package pl.kibao.githubclient.ui;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import pl.kibao.githubclient.AppComponent;
import pl.kibao.githubclient.AppModule;
import pl.kibao.githubclient.MockApplication;
import pl.kibao.githubclient.R;
import pl.kibao.githubclient.data.DataModule;
import pl.kibao.githubclient.data.User;
import pl.kibao.githubclient.data.UserService;
import pl.kibao.githubclient.ui.users.UsersActivity;
import pl.kibao.githubclient.utils.TestUtils;
import rx.subjects.PublishSubject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static pl.kibao.githubclient.utils.RecyclerViewMatcher.withRecyclerView;
import static pl.kibao.githubclient.utils.ViewAssertion.viewIsNotVisible;
import static pl.kibao.githubclient.utils.ViewAssertion.viewIsVisible;

@RunWith(AndroidJUnit4.class)
public class UsersScreen {
    @Mock
    UserService userService;

    @Rule
    public final DaggerMockRule<AppComponent> rule =
        new DaggerMockRule<>(AppComponent.class,
            new AppModule(MockApplication.applicationContext()),
            new DataModule()
        ).set(component -> {
            MockApplication app = MockApplication.applicationContext();
            app.setAppComponent(component);
        });

    @Rule
    public IntentsTestRule<UsersActivity> intentsTestRule =
        new IntentsTestRule<>(UsersActivity.class, false, false);
    private PublishSubject<List<User>> usersSubject;


    @Before
    public void setUp() {
        usersSubject = PublishSubject.create();
        Mockito.when(userService.users())
            .thenReturn(usersSubject);
        intentsTestRule.launchActivity(null);
    }

    @Test
    public void shouldShowOnlyLoadingViewDuringLoading() {
        viewIsNotVisible(R.id.loadingView, android.R.id.content);
        viewIsNotVisible(R.id.errorView, android.R.id.content);
        viewIsVisible(R.id.contentView, android.R.id.content);
    }

    @Test
    public void showContentViewOnSuccessRemote() {
        usersSubject.onNext(validUsersResult());
        usersSubject.onCompleted();

        TestUtils.waitFor(400);

        viewIsNotVisible(R.id.loadingView, android.R.id.content);
        viewIsNotVisible(R.id.errorView, android.R.id.content);
        viewIsVisible(R.id.contentView, android.R.id.content);

        onView(withRecyclerView(R.id.contentView).atPositionOnView(0, R.id.name))
            .check(matches(withText("kibao")));
        onView(withRecyclerView(R.id.contentView).atPositionOnView(1, R.id.name))
            .check(matches(withText("john.doe")));
        onView(withRecyclerView(R.id.contentView).atPositionOnView(2, R.id.name))
            .check(matches(withText("MyOrg")));
    }

    @Test
    public void shouldShowErrorViewOnRemoteError() {
        usersSubject.onError(new Exception("Remote exception"));

        viewIsNotVisible(R.id.loadingView, android.R.id.content);
        viewIsNotVisible(R.id.contentView, android.R.id.content);
        viewIsVisible(R.id.errorView, android.R.id.content);
    }

    private List<User> validUsersResult() {
        List<User> result = new ArrayList<>();
        result.add(createUser(1156678, "kibao", "User"));
        result.add(createUser(1000000, "john.doe", "User"));
        result.add(createUser(31337, "MyOrg", "Organization"));
        return result;
    }

    private User createUser(int id, String username, String type) {
        User user = new User();
        user.id = id;
        user.username = username;
        user.avatarUrl = "https://avatars.githubusercontent.com/u/" + id + "?v=3";
        user.type = type;
        return user;
    }
}
