package com.nearsoft.referralsapp.send_referral;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;

import java.util.ArrayList;

class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.ViewHolder>{
    private ArrayList<Recruiter> recruiters;
    private ReferralAdapterListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mRecruiterName;
        LinearLayout layout;
        //TODO: Change to fresco container.
        ImageView mRecruiterImage;

        ViewHolder(View itemView) {
            super(itemView);
            mRecruiterName = itemView.findViewById(R.id.recruiter_name);
            layout = itemView.findViewById(R.id.itemLayout);
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
        // TODO: Set the picture uri to the imageview holder

//        applyClickEvents(viewHolder, position);
    }

    private void applyClickEvents(ReferralAdapter.ViewHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recruiter recruiter = recruiters.get(position);
                listener.onRowClicked(recruiters.get(position));
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
