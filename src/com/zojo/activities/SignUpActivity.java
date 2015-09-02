package com.zojo.activities;

import java.util.HashMap;
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

public class SignUpActivity extends AppCompatActivity {
	private EditText username, pass, name, surname, birthdate;
	private Button signUp;
	private MailClientApp globApp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_sign_up);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setSubtitleTextColor(Color.WHITE);
		toolbar.setSubtitle(R.string.send_new);
		toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);

		globApp = (MailClientApp) getApplication();

		signUp = (Button) findViewById(R.id.signUp);
		username = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.pass);
		name = (EditText) findViewById(R.id.name);
		surname = (EditText) findViewById(R.id.surname);
		birthdate = (EditText) findViewById(R.id.birthdate);

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Map<String, String> options = new HashMap<String, String>();
				options.put("username", username.getText().toString());
				options.put("password", pass.getText().toString());
				options.put("firstName", name.getText().toString());
				options.put("lastName", surname.getText().toString());
				options.put("dob", birthdate.getText().toString());
				globApp.getService().register(options, new Callback<Void>() {

					@Override
					public void failure(RetrofitError arg0) {
						arg0.printStackTrace();
					}

					@Override
					public void success(Void arg0, Response arg1) {
						globApp.getService().login(username.getText().toString().trim(), pass.getText().toString().trim(), new Callback<Map<String, String>>() {

							@Override
							public void success(Map<String, String> arg0, Response arg1) {
								Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
								SignUpActivity.this.startActivity(intent);
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
			}
		});
	}
}
