package com.zojo.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zojo.fragments.DeletedFragment;
import com.zojo.fragments.InboxFragment;
import com.zojo.fragments.NavigationDrawerFragment;
import com.zojo.fragments.SentFragment;
import com.zojo.mailclientandroid.R;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private Toolbar toolbar;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setLogo(R.drawable.ic_launcher);
		toolbar.setSubtitleTextColor(Color.WHITE);
		toolbar.setSubtitle(mTitle = getResources().getStringArray(R.array.menus)[0]);
		setSupportActionBar(toolbar);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		switch (position) {
		case 0:
			fragmentManager.beginTransaction().replace(R.id.container, new InboxFragment()).commit();
			break;
		case 1:
			fragmentManager.beginTransaction().replace(R.id.container, new SentFragment()).commit();
			break;
		case 2:
			fragmentManager.beginTransaction().replace(R.id.container, new DeletedFragment()).commit();
			break;
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getResources().getStringArray(R.array.menus)[0];
			break;
		case 2:
			mTitle = getResources().getStringArray(R.array.menus)[1];
			break;
		case 3:
			mTitle = getResources().getStringArray(R.array.menus)[2];
			break;
		}
	}

	public void restoreActionBar() {
		toolbar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.send_new) {
			Intent intent = new Intent(this, SendMailActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
