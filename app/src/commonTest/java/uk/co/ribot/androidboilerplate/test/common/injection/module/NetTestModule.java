package uk.co.ribot.androidboilerplate.test.common.injection.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

@Module
public class NetTestModule {

    private final String mBaseUrl;

    public NetTestModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public Cache provideHttpCache(final Application application) {
        return mock(Cache.class);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return mock(Gson.class);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(final Application application, final Cache cache) {
        return mock(OkHttpClient.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(final Gson gson, final OkHttpClient okHttpClient) {
        return mock(Retrofit.class);
    }
}
