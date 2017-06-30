package hr.fer.android.hw0036488109.ivanovaaplikacija.networking;

import hr.fer.android.hw0036488109.ivanovaaplikacija.models.FormResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface used for Retrofit library usage.
 *
 * Created by Ivan on 29.6.2017..
 */
public interface RetrofitService {

    /**
     * Fetches data from provided path upon calling this method.
     *
     * @param path path to website containing JSON data
     * @return the data
     */
    @GET("{path}")
    Call<FormResponse> getData(@Path("path") String path);
}
