package com.example.fraseslmg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fraseslmg.R;
import com.example.fraseslmg.objetos.Groups;

import java.util.List;

public class GroupsAdapter extends BaseAdapter {

    Context context;
    List<Groups> list;

    public GroupsAdapter(Context context, List<Groups> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageViewGroup;
        TextView tv_name;
        TextView tv_desc;

        Groups g = list.get(position);

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view,null);
        }

        imageViewGroup = convertView.findViewById(R.id.imgViewGroup);
        tv_name = convertView.findViewById(R.id.tv_name_group);
        tv_desc = convertView.findViewById(R.id.tv_desc_group);

        imageViewGroup.setImageResource(g.getImg());
        tv_name.setText(g.getName());
        tv_desc.setText(g.getDesc());

        return convertView;
    }
}
