package com.yjk.eventdemo.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yjk.eventdemo.R;
import com.yjk.eventdemo.base.BaseAdpter;
import com.yjk.eventdemo.model.EventData;

public class EventsAdapter extends BaseAdpter<EventData> {

    public EventsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_event_layout, viewGroup, false);
            viewHolder.tvActivityName = (TextView) convertView.findViewById(R.id.id_item_layout_statistics_activity_name);
            viewHolder.tvViewContent = (TextView) convertView.findViewById(R.id.id_item_layout_statistics_view_content);
            viewHolder.tvClickNum = (TextView) convertView.findViewById(R.id.id_item_layout_statistics_click_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EventData eventData = getItem(position);
        viewHolder.tvActivityName.setText("activity名字[" + eventData.getActivityName() + "]");
        viewHolder.tvViewContent.setText("View 描述[" + eventData.getViewContent() + "]");
        viewHolder.tvClickNum.setText("点击次数[" + eventData.getClickNum() + "]");

        return convertView;
    }

    class ViewHolder {
        public TextView tvActivityName, tvViewContent, tvClickNum;
    }
}
