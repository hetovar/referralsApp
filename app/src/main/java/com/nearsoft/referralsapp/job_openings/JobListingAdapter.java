package com.nearsoft.referralsapp.job_openings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.JobDescription;
import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobListingAdapter extends RecyclerView.Adapter<JobListingAdapter.ViewHolder> {
    private ArrayList<NearsoftJob> nearsoftJobs;
    private JobListingAdapterListener listener;

    JobListingAdapter(ArrayList<NearsoftJob> nearsoftJobs, JobListingAdapterListener listener) {
        this.nearsoftJobs = nearsoftJobs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_job_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = nearsoftJobs.get(position).getTitle();
        holder.mTextView.setText(title);

        applyClickEvents(holder, position);
    }

    private void applyClickEvents(ViewHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NearsoftJob nearsoftJob = nearsoftJobs.get(position);
                listener.onRowClicked(nearsoftJob.getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearsoftJobs.size();
    }

    public interface JobListingAdapterListener {
        void onRowClicked(JobDescription jobDescription);
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
}
