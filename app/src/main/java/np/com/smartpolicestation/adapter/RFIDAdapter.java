package np.com.smartpolicestation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import np.com.smartpolicestation.R;
import np.com.smartpolicestation.Utils;
import np.com.smartpolicestation.models.RFID;

public class RFIDAdapter extends RecyclerView.Adapter<RFIDAdapter.Holder> {

    private Context context;
    private List<RFID> rfidList;
    private int lastPosition = -1;

    public RFIDAdapter(Context context, List<RFID> rfidList) {
        this.context = context;
        this.rfidList = rfidList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_rfid, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        RFID rfid = rfidList.get(i);

        holder.tvStatus.setText(rfid.getIsAuth().endsWith("true") ? "Access Granted" : "Access Denied");
        holder.tvStatus.setTextColor(rfid.getIsAuth().endsWith("true") ? context.getResources().getColor(R.color.colorGreen) : context.getResources().getColor(R.color.colorOrange));
        holder.imgStatus.setImageResource(rfid.getIsAuth().endsWith("true") ? R.drawable.success : R.drawable.denied);
        holder.tvTime.setText(Utils.getTimeAgo(Long.parseLong(rfid.getTimestamp())));
        setAnimation(holder.itemView, i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return rfidList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView imgStatus;
        private TextView tvStatus, tvTime;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
