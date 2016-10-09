package pl.kibao.githubclient.api;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitUsersApi {
    @GET("users")
    Observable<List<UserResponse>> users();
}
