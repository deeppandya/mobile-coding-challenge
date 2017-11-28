package mainpackage.traderevtest.networkcalls;

import java.util.List;
import io.reactivex.Observable;
import mainpackage.traderevtest.model.UnsplashPhoto;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by deepp on 2017-11-28.
 */

public interface UnsplashPhotoService {

    @GET("photos?")
    Observable<List<UnsplashPhoto>> getUnsplashPhotos(@Query("client_id") String clientId, @Query("page") String page, @Query("per_page") String perPage, @Query("order_by") String orderBy);
}
