package com.zojo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;

import com.zojo.mailclientandroid.R;

public class ReadMailActivity extends AppCompatActivity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_read_mail);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_left);
		toolbar.setLogo(R.drawable.ic_launcher);
		toolbar.setSubtitleTextColor(Color.WHITE);
		toolbar.setSubtitle(Html.fromHtml(getIntent().getStringExtra("EMAIL-TITLE").split(":")[1]));
		setSupportActionBar(toolbar);
		webView = (WebView) findViewById(R.id.webView);
		webView.loadData(getIntent().getStringExtra("EMAIL-CONTENT"), "text/html", "UTF-8");
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
