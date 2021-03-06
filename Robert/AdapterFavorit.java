package com.project.robert.Robert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Robert on 01/11/2016.
 */

public class AdapterFavorit extends BaseAdapter {
    private static ArrayList<Barang> listBarang;
    private LayoutInflater mInflater;
    public AdapterFavorit(Context context, ArrayList<Barang> con) {
        listBarang = con;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listBarang.size();
    }
    @Override
    public Object getItem(int position) {
        return listBarang.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder;
// Initiate view holder
        if (convertView == null) {
            convertView = mInflater.inflate(android.support.com.project.robert.Robert.R.layout.list_item_favorit, null);
            mHolder = new ViewHolder();
            mHolder.tvNama_barang = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvNama_barang);
            mHolder.tvTerjual = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvTerjual);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder)convertView.getTag();
        }
// set view content
        mHolder.tvNama_barang.setText(listBarang.get(position).getNama_barang());
        mHolder.tvTerjual.setText("Terjual : "+listBarang.get(position).getTerjual());
        return convertView;
    }
    static class ViewHolder {
        TextView tvNama_barang;
        TextView tvTerjual;

    }
}