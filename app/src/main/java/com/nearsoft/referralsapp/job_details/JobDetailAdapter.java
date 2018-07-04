package com.nearsoft.referralsapp.job_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.ViewHolder> {
    private ArrayList<String> jobDescription;
    private ArrayList<String> mTitle;

    JobDetailAdapter(ArrayList<String> jobDescription, ArrayList<String> mTitle) {
        this.jobDescription = jobDescription;
        this.mTitle = mTitle;
    }

    @NonNull
    @Override
    public JobDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_job_detail, parent, false);

        return new JobDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(mTitle.get(position));
        holder.mDescription.setText(jobDescription.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mDescription;
        LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textTitle);
            mDescription = itemView.findViewById(R.id.textDescription);
            layout = itemView.findViewById(R.id.detailLayout);
        }
    }
}
