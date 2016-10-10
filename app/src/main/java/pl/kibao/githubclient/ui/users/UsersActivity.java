package pl.kibao.githubclient.ui.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

import javax.inject.Inject;

import pl.kibao.githubclient.GithubClientApplication;
import pl.kibao.githubclient.R;
import pl.kibao.githubclient.data.User;
import pl.kibao.githubclient.ui.ActivityModule;

public class UsersActivity extends AppCompatActivity {
    @Inject
    UsersAdapter adapter;
    @Inject
    UsersPresenter presenter;

    private RecyclerView list;
    private CircularProgressView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.activity_users);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        loadingView = (CircularProgressView) findViewById(R.id.loadingView);
        list = (RecyclerView) findViewById(R.id.contentView);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        presenter.loadUserList();
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        list.setVisibility(View.INVISIBLE);
    }

    public void showData() {
        list.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    private void injectDependencies() {
        GithubClientApplication.appComponent(this)
            .plus(new ActivityModule(this), new UsersActivityModule(this))
            .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    public void setData(List<User> items) {
        adapter.updateItems(items);
    }
}
