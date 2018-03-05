package uk.co.ribot.androidboilerplate.data.remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

public interface RibotsService {

    String ENDPOINT = "https://api.ribot.io/";
    String BASE_URL = "http://mock.api";

    @GET("ribots")
    Observable<List<Ribot>> getRibots();

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static RibotsService newRibotsService(Retrofit retrofit) {
            return retrofit.create(RibotsService.class);
        }
    }
}
