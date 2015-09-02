package com.zojo.adapters;

import java.util.Map;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zojo.mailclientandroid.R;

public class DeletedAdapter extends ArrayAdapter<Map<String, String>> {

	private Activity activity;

	public DeletedAdapter(Activity activity) {
		super(activity, R.layout.listitem_mail);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (this.activity).getLayoutInflater();
			convertView = inflater.inflate(R.layout.listitem_mail, parent, false);
		}
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText(Html.fromHtml(((String) getItem(position).keySet().toArray()[0]).split(":")[1]));

		TextView content = (TextView) convertView.findViewById(R.id.content);
		content.setText(Html.fromHtml(getItem(position).get(getItem(position).keySet().toArray()[0])));

		TextView acc = (TextView) convertView.findViewById(R.id.acc);
		acc.setText(Html.fromHtml(((String) getItem(position).keySet().toArray()[0]).split(":")[0]));
		return convertView;
	}

}
