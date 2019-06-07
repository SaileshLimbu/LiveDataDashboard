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
import np.com.smartpolicestation.models.EQ;

public class EqAdapter extends RecyclerView.Adapter<EqAdapter.Holder> {

    private Context context;
    private List<EQ> eqList;
    private int lastPosition = -1;

    public EqAdapter(Context context, List<EQ> eqList) {
        this.context = context;
        this.eqList = eqList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_eq, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        EQ eq = eqList.get(i);
        holder.tvMagnitude.setText(eq.getMagnitude() + " Richter Scale");
        holder.tvTime.setText(Utils.getTimeAgo(Long.parseLong(eq.getTimestamp())));
        int magnitude = Integer.parseInt(eq.getMagnitude());
        if (magnitude <= 3) {
            holder.imgAlert.setImageResource(R.drawable.normal);
            holder.tvMagnitude.setTextColor(context.getResources().getColor(R.color.colorNormal));
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorNormal));
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.colorNormal));
        } else if (magnitude > 3 && magnitude < 5) {
            holder.imgAlert.setImageResource(R.drawable.medium);
            holder.tvMagnitude.setTextColor(context.getResources().getColor(R.color.colorWarning));
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorWarning));
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWarning));
        } else if (magnitude > 5) {
            holder.imgAlert.setImageResource(R.drawable.danger);
            holder.tvMagnitude.setTextColor(context.getResources().getColor(R.color.colorDanger));
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorDanger));
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.colorDanger));
        }
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
        return eqList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView imgAlert;
        private TextView tvMagnitude, tvTime, tvStatus;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgAlert = itemView.findViewById(R.id.imgAlert);
            tvMagnitude = itemView.findViewById(R.id.tvMagnitude);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
