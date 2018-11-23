package com.example.neeraj.demohotel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neeraj.demohotel.rest.models.GuestModel;

import java.util.List;

/**
 * @author neeraj on 13/9/18.
 */
public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.ViewHolder> {
    List<GuestModel.DataBean> dataBeans;

    public GuestAdapter(List<GuestModel.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    @Override
    public GuestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(GuestAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView room_no, guest_name, arrival_time, departure_time;

        public ViewHolder(View itemView) {
            super(itemView);
            room_no = itemView.findViewById(R.id.room_no);
            guest_name = itemView.findViewById(R.id.guest_name);
            arrival_time = itemView.findViewById(R.id.arrival_time);
            departure_time = itemView.findViewById(R.id.departure_time);


        }

        public void setData(int position) {
            room_no.setText(dataBeans.get(position).getRoom_no());
            guest_name.setText(dataBeans.get(position).getName());
            arrival_time.setText(dataBeans.get(position).getArrival_date_time());
            departure_time.setText(dataBeans.get(position).getDeparture_date_time());
        }
    }
}
