package com.nearsoft.referralsapp.send_referral;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;

import java.util.ArrayList;

class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.ViewHolder>{
    private ArrayList<Recruiter> recruiters;
    private ReferralAdapterListener listener;
    private int selectedPos = RecyclerView.NO_POSITION;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mRecruiterName;
        LinearLayout layout;
        SimpleDraweeView mRecruiterImage;

        ViewHolder(View itemView) {
            super(itemView);
            mRecruiterName = itemView.findViewById(R.id.recruiter_name);
            layout = itemView.findViewById(R.id.recruiter_item_layout);
            mRecruiterImage = itemView.findViewById(R.id.recruiter_image);
        }
    }

    public ReferralAdapter(ArrayList<Recruiter> recruiters, ReferralAdapterListener listener) {
        this.recruiters = recruiters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_recruiter, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = recruiters.get(position).getName();
        String pictureUri = recruiters.get(position).getPicture();

        viewHolder.mRecruiterName.setText(name);
        viewHolder.layout.setSelected(selectedPos == position);
        viewHolder.mRecruiterImage.setImageURI(pictureUri);

        applyClickEvents(viewHolder, position);
    }

    private void applyClickEvents(final ReferralAdapter.ViewHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClicked(recruiters.get(position));

                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recruiters.size();
    }

    public interface ReferralAdapterListener {
        void onRowClicked(Recruiter recruiter);
    }
}
