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

public class AdapterHistory extends BaseAdapter {
    private static ArrayList<History> listHistory;
    private LayoutInflater mInflater;
    public AdapterHistory(Context context, ArrayList<History> con) {
        listHistory = con;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listHistory.size();
    }
    @Override
    public Object getItem(int position) {
        return listHistory.get(position);
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
            convertView = mInflater.inflate(android.support.com.project.robert.Robert.R.layout.list_item_history, null);
            mHolder = new ViewHolder();
            mHolder.tvNama_barang_history = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvNama_barang_history);
            mHolder.tvJumlah_history = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvJumlah_history);
            mHolder.tvTotal = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvTotal);
            mHolder.tvTanggal = (TextView) convertView.findViewById(android.support.com.project.robert.Robert.R.id.tvTanggal);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder)convertView.getTag();
        }
// set view content
        mHolder.tvNama_barang_history.setText(listHistory.get(position).getNama_barang());
        mHolder.tvJumlah_history.setText("Jumlah    : "+listHistory.get(position).getJumlah());
        mHolder.tvTotal.setText("Total         : Rp."+listHistory.get(position).getTotal()+",-");
        mHolder.tvTanggal.setText("Waktu Order :"+listHistory.get(position).getTanggal());
        return convertView;
    }
    static class ViewHolder {
        TextView tvNama_barang_history;
        TextView tvJumlah_history;
        TextView tvTotal;
        TextView tvTanggal;

    }
}