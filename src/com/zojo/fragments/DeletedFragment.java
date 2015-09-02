package com.zojo.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.zojo.activities.ReadMailActivity;
import com.zojo.adapters.DeletedAdapter;
import com.zojo.mailclientandroid.MailClientApp;
import com.zojo.mailclientandroid.R;

public class DeletedFragment extends Fragment {

	private View fragment;
	private ListView list;
	private ProgressBar loader;
	private DeletedAdapter adapter;
	private MailClientApp globApp;
	private AppCompatActivity activity;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragment = inflater.inflate(R.layout.fragment_list, container, false);
		list = (ListView) fragment.findViewById(R.id.list);
		loader = (ProgressBar) fragment.findViewById(R.id.loader);
		activity = (AppCompatActivity) getActivity();
		adapter = new DeletedAdapter(activity);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, ReadMailActivity.class);
				intent.putExtra("EMAIL-CONTENT", adapter.getItem(position).get(adapter.getItem(position).keySet().toArray()[0]));
				intent.putExtra("EMAIL-TITLE", (String) adapter.getItem(position).keySet().toArray()[0]);
				activity.startActivity(intent);
			}
		});
		globApp = (MailClientApp) activity.getApplication();
		Type type = new TypeToken<ArrayList<Map<String, String>>>() {
		}.getType();
		ArrayList<Map<String, String>> cached = (ArrayList<Map<String, String>>) globApp.readCache("DELETED", type);
		if (cached != null && cached.size() > 0) {
			adapter.addAll(cached);
			loader.setVisibility(View.INVISIBLE);
			list.setVisibility(View.VISIBLE);
		}
		((MailClientApp) activity.getApplication()).getService().getDeleted(new Callback<Map<String, Object>>() {

			@Override
			public void success(Map<String, Object> arg0, Response arg1) {
				ArrayList<Map<String, String>> message = (ArrayList<Map<String, String>>) arg0.get("message");
				adapter.clear();
				adapter.addAll(message);
				list.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loader.setVisibility(View.INVISIBLE);
				list.setVisibility(View.VISIBLE);
				globApp.cacheItem("DELETED", message);
			}

			@Override
			public void failure(RetrofitError arg0) {
				arg0.printStackTrace();
			}
		});
		return fragment;
	}
}
