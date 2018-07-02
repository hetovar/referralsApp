package com.nearsoft.referralsapp.send_referral;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;
import com.nearsoft.referralsapp.Recruiter;
import com.nearsoft.referralsapp.job_openings.JobListingAdapter;

import java.util.ArrayList;

class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.ViewHolder>{
    private ArrayList<Recruiter> recruiters;
    private ReferralAdapterListener listener;

    public ReferralAdapter(ArrayList<Recruiter> recruiters, ReferralAdapterListener listener) {
        this.recruiters = recruiters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
            layout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public interface ReferralAdapterListener {

    }
}
