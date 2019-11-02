/**
 * This is for getGroupView = list_item and getChildView = list_item_second.
 * This is where Parent item and sub parent are being programmatically
 * made to enable the expandable between header and submenu.
 */

package com.mhir.vinz.w3schools.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhir.vinz.w3schools.MainActivity;
import com.mhir.vinz.w3schools.R;

import java.util.List;

public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<String[]>data;

    String[] headers;

    public SecondLevelAdapter(Context context, String[] headers, List<String[]>data) {
        this.context = context;
        this.data = data;
        this.headers = headers;
    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);

        return children.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] childData;
        childData = data.get(groupPosition);

        return childData[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpandable, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        TextView text = (TextView) convertView.findViewById(R.id.lblListItem);
        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText);

        //set icon on parent item

        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.ic_txt_item);
        imageIcon.setImageResource(R.drawable.ic_programming);

        /*
         * this is the arrow image
         * on expand, this is change another image and on close it change to +
         */
        TextView textHighlight = (TextView) convertView.findViewById(R.id.lblListItem);
        ImageView image_arrow = (ImageView) convertView.findViewById(R.id.arrow_sub);
        if (isExpandable) {
            textHighlight.setTextColor(ContextCompat.getColor(context, R.color.highlight_childText));
            image_arrow.setImageResource(R.drawable.ic_collapse_sub);

        } else {
            textHighlight.setTextColor(ContextCompat.getColor(context, R.color.normal_childText));
            image_arrow.setImageResource(R.drawable.ic_expandable_sub);
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_second, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);

        String[] childArray=data.get(groupPosition);

        String text = childArray[childPosition];

        textView.setText(text);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
