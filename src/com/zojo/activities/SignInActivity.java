package com.zojo.activities;

import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zojo.mailclientandroid.MailClientApp;
import com.zojo.mailclientandroid.R;

public class SignInActivity extends AppCompatActivity {

	private EditText username, pass;
	private Button signIn, signUp;
	private MailClientApp globApp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_sign_in);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setSubtitleTextColor(Color.WHITE);
		toolbar.setSubtitle(R.string.send_new);
		toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);

		globApp = (MailClientApp) getApplication();

		signIn = (Button) findViewById(R.id.signIn);
		signUp = (Button) findViewById(R.id.signUp);
		username = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.pass);

		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MailClientApp) SignInActivity.this.getApplication()).getService().login(username.getText().toString().trim(), pass.getText().toString().trim(), new Callback<Map<String, String>>() {

					@Override
					public void success(Map<String, String> arg0, Response arg1) {
						Intent intent = new Intent(SignInActivity.this, MainActivity.class);
						SignInActivity.this.startActivity(intent);
						globApp.cacheItem("ACCOUNT", new Gson().toJson(arg0));
						globApp.cacheItem("USERNAME", username.getText().toString().trim());
						globApp.cacheItem("PASSWORD", pass.getText().toString().trim());
						finish();
					}

					@Override
					public void failure(RetrofitError arg0) {
						arg0.printStackTrace();
					}
				});
			}
		});
		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
				SignInActivity.this.startActivity(intent);
				finish();
			}
		});
	}
}
