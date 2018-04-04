package com.lxl.simplemusicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextClock
import android.widget.TextView
import com.lxl.simplemusicplayer.R
import com.lxl.simplemusicplayer.entity.MusicInfo

class MusicInfoAdapter(private val context: Context,
                       private val dataSource: List<MusicInfo>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view: View
        var viewHolder: ViewHolder
        if (convertView == null){
            val item = LayoutInflater.from(context).inflate(R.layout.item_musicinfo_adapter,null)
            viewHolder = ViewHolder(item.findViewById(R.id.title))
            item.tag = viewHolder
            view = item
        }else{
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.title.text = dataSource[position].title
        return view
    }

    data class ViewHolder(val title: TextView)
}
