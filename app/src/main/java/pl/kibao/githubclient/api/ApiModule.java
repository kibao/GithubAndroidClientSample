package pl.kibao.githubclient.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.kibao.githubclient.BuildConfig;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    public static final HttpUrl API_URL = HttpUrl.parse("https://api.github.com");

    @Provides
    @Singleton
    HttpUrl provideBaseUrl() {
        return API_URL;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor);

        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(
        HttpUrl baseUrl,
        OkHttpClient client,
        Converter.Factory converterFactory,
        CallAdapter.Factory callAdapterFactory
    ) {
        return createRetrofit(baseUrl, client, converterFactory, callAdapterFactory);
    }

    @Singleton
    @Provides
    Converter.Factory provideConverterFactory() {
        Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
            ? HttpLoggingInterceptor.Level.BODY
            : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Singleton
    @Provides
    public RetrofitUsersApi providesRetrofitUsersApi(Retrofit retrofit) {
        return retrofit.create(RetrofitUsersApi.class);
    }

    static Retrofit createRetrofit(HttpUrl baseUrl,
                                   OkHttpClient client,
                                   Converter.Factory converterFactory,
                                   CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build();
    }

}