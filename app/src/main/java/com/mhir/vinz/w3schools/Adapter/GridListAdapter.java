package com.mhir.vinz.w3schools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhir.vinz.w3schools.R;

public class GridListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] socialImage;
    private  String[] socialWord;

    public GridListAdapter(Context c, int[] socialImage, String[] socialWord) {
        context = c;
        this.socialImage = socialImage;
        this.socialWord = socialWord;
    }

    @Override
    public int getCount() {
        return socialWord.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_image);
        TextView textView = (TextView) convertView.findViewById(R.id.grid_text);

        imageView.setImageResource(socialImage[position]);
        textView.setText(socialWord[position]);

        return convertView;
    }
}