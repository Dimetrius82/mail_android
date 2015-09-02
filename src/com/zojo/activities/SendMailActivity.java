package com.zojo.activities;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zojo.mailclientandroid.MailClientApp;
import com.zojo.mailclientandroid.R;

public class SendMailActivity extends AppCompatActivity {
	private Button send;
	private EditText subject, message, recipient;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_send_mail);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setSubtitleTextColor(Color.WHITE);
		toolbar.setNavigationIcon(R.drawable.ic_left);
		toolbar.setSubtitle(R.string.send_new);
		toolbar.setLogo(R.drawable.ic_launcher);
		setSupportActionBar(toolbar);

		send = (Button) findViewById(R.id.send);
		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		message = (EditText) findViewById(R.id.message);

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MailClientApp) SendMailActivity.this.getApplication()).getService().postNewMail("jovanibra@gmail.com", recipient.getText().toString(), subject.getText().toString(),
						message.getText().toString(), new Callback<Void>() {

							@Override
							public void success(Void arg0, Response arg1) {
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
