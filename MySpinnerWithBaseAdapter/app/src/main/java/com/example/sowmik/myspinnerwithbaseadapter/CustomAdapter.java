package com.example.sowmik.myspinnerwithbaseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] countryNames,population;
    int[] flags;

    CustomAdapter(Context context,int[] flags,String[] countryNames,String[] population)
    {
        this.context = context;
        this.flags = flags;
        this.countryNames = countryNames;
        this.population = population;
    }


    @Override
    public int getCount() {
        return countryNames.length;
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

        if(convertView==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sample_view,null,false);
        }


        ImageView imageView = convertView.findViewById(R.id.imageviewid);
        imageView.setImageResource(flags[position]);

        TextView textView = convertView.findViewById(R.id.countrynamesid);
        textView.setText(countryNames[position]);

        TextView pp = convertView.findViewById(R.id.populationid);
        pp.setText(population[position]);

        return convertView;
    }
}
