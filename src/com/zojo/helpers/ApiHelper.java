package com.zojo.helpers;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;

public interface ApiHelper {
	public static final String baseUrl = "http://192.168.0.219:8080/mail-client/";

	@Headers("Content-Type: application/json")
	@GET("/inbox")
	void getInbox(Callback<Map<String, Object>> model);

	@Headers("Content-Type: application/json")
	@GET("/sent")
	void getSent(Callback<Map<String, Object>> model);

	@Headers("Content-Type: application/json")
	@GET("/deleted")
	void getDeleted(Callback<Map<String, Object>> model);

	@FormUrlEncoded
	@POST("/homepage/sendmail")
	void postNewMail(@Field("sender") String sender, @Field("recipient") String recipient, @Field("subject") String subject, @Field("msg") String msg, Callback<Void> Void);

	@GET("/signin")
	void register(@QueryMap Map<String, String> options, Callback<Void> v);
	
	@FormUrlEncoded
	@POST("/login")
	void login(@Field("username") String username, @Field("password") String password, Callback<Map<String, String>> mails);
}
