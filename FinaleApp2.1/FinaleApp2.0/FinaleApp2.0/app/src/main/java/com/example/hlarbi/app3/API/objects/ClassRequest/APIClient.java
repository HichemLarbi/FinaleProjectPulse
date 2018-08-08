package com.example.hlarbi.app3.API.objects.ClassRequest;

import com.example.hlarbi.app3.API.objects.Oauth.AccessToken;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/* ClassRequest Package : in this package you find the main http request that are used for Retrofit Classes.
  *Here are post requests to get AccessToken using get and setter for the callback response.
  * There is a point to improve : make the header dynamic.
   * */

public interface APIClient {
    @Headers({
            "Authorization: Basic MjJDVDJEOjFhMjZhZDNhYzJkNGZiMmE4Y2ZhNzQxMGJkNTg0N2Ji",
                    "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("/oauth2/token")
    Call<AccessToken> getNewAccessToken(

            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code,
            @Field("code_verifier") String base);

    @FormUrlEncoded
    @POST("/oauth2/token")
    Call<AccessToken> getRefreshAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);

}



