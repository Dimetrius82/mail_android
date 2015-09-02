package com.zojo.mailclientandroid;

import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zojo.helpers.ApiHelper;

public class MailClientApp extends Application {

	public static final String SHARED_PREF_KEY = "MAIL_CLIENT_SHARED_PREF_KEY";
	private RestAdapter restAdapter;
	private SharedPreferences sharedPref;

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate() {
		super.onCreate();

		final CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);

		restAdapter = new RestAdapter.Builder().setEndpoint(ApiHelper.baseUrl).setRequestInterceptor(new RequestInterceptor() {
			@Override
			public void intercept(RequestFacade requestFacade) {
				for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
					Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
					String expires = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(expiration);
					String cookieValue = cookie.getName() + "=" + cookie.getValue() + "; " + "path=" + cookie.getPath() + "; " + "domain=" + cookie.getDomain() + ";" + "expires=" + expires;
					requestFacade.addHeader("Cookie", cookieValue);
				}
			}
		}).build();
		sharedPref = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
	}

	public ApiHelper getService() {
		return restAdapter.create(ApiHelper.class);
	}

	public void cacheItem(String key, Object obj) {
		sharedPref.edit().putString(key, new Gson().toJson(obj)).commit();
	}

	public Object readCache(String key, Type type) {
		return new Gson().fromJson(sharedPref.getString(key, ""), type);
	}
}
