package com.mhir.vinz.w3schools.Adapter;
/*
 * The layouts to be exported are getGroupView = list_group and getChildView = list .
 * This is where Parent item and sub parent are being programmatically
 * made to enable the expandable between submenu and childItem.
 */
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mhir.vinz.w3schools.MainActivity;
import com.mhir.vinz.w3schools.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ThreeLevelListAdapter extends BaseExpandableListAdapter {

    String[] parentHeaders;
    List<String[]> secondLevel;
    private Context context;
    List<LinkedHashMap<String, String[]>> data;

    public ThreeLevelListAdapter(Context context, String[] parentHeaders, List<String[]> secondLevel, List<LinkedHashMap<String, String[]>> data) {
        this.context = context;
        this.parentHeaders = parentHeaders;
        this.secondLevel = secondLevel;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return secondLevel.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // no idea why this code is working
        return 1;
    }
    /*public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (groupPosition != 1) {
            childCount = data.get(getGroupCount()).size();
        }
        return childCount;
    }*/

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int group, int child) {
        return child;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_group, parent, false);
        TextView textTitle = (TextView)convertView.findViewById(R.id.lblListHeader);

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/DroidSans.ttf");

        textTitle.setTypeface(font, Typeface.BOLD);
        textTitle.setText(this.parentHeaders[groupPosition]);

        /*
         * this is the +/- on expanded list view.
         * on expand, this is change to - and on close it change to +
         */
        TextView textHighlight = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageView image_arrow = (ImageView) convertView.findViewById(R.id.arrow);
        if (isExpanded) {
            textHighlight.setTextColor(ContextCompat.getColor(context, R.color.highlight_childText));
            image_arrow.setImageResource(R.drawable.ic_collapse);
        } else {
            textHighlight.setTextColor(ContextCompat.getColor(context, R.color.normal_childText));
            image_arrow.setImageResource(R.drawable.ic_expandable);
        }
        //set icon on parent item

        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.ic_txt);
        imageIcon.setImageResource(MainActivity.icon[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        /**
         * This codes set the SecondLevelAdapter so that it will
         * include on the expandableList
         */
        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);

        final String[] headers = secondLevel.get(groupPosition);


        final List<String[]> childData = new ArrayList<>();
        final HashMap<String, String[]> secondLevelData=data.get(groupPosition);

        for(String key : secondLevelData.keySet())
        {
            childData.add(secondLevelData.get(key));
        }
        secondLevelELV.setAdapter(new SecondLevelAdapter(context, headers,childData));
        secondLevelELV.setGroupIndicator(null);

        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        /**
         * It should include to set the onChildLick so that the childList
         * or the third level list will be clickable.
         */
        secondLevelELV.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        secondLevelELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                return false;
            }
        });

        secondLevelELV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                //change the fragment when the child or sub menu is clicked
                /*String selectedItem = ((List)(lstChild.get(parentHeaders[groupPosition]))).get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (parentHeaders[0].equals(parentHeaders[groupPosition]))
                    MainActivity.navigationManager.showFragment(selectedItem);
                else
                    throw new IllegalArgumentException("Not supported fragment");*/

                MainActivity.mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
