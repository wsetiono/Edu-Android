package com.william.education.Remote;

import com.william.education.Model.entityDataUser;
import com.william.education.Model.entityLoginUser;
import com.william.education.Model.entityRegisterUser;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMyAPI {

    //https://localhost:5001/api/users/register
    @POST("api/users/register")
//    Observable<entityDataUser> registerUser(@Body entityRegisterUser user);
    Observable<entityDataUser> registerUser(@Body entityRegisterUser user);

    //https://localhost:5001/api/users/login
    @POST("api/users/login")
//    Observable<entityDataUser> loginUser(@Body entityLoginUser user);
    Observable<entityDataUser> loginUser(@Body entityLoginUser user);

    //https://localhost:5001/api/users/logout
    @POST("api/users/logout")
    Observable<String> logoutUser();
}
