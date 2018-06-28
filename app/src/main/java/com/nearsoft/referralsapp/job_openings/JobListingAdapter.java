package com.nearsoft.referralsapp.job_openings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.NearsoftJob;
import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobListingAdapter extends RecyclerView.Adapter<JobListingAdapter.ViewHolder>{
    private ArrayList<NearsoftJob> nearsoftJobs;
    private Context context;
    private JobListingAdapterListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
            layout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public JobListingAdapter(ArrayList<NearsoftJob> nearsoftJobs, Context context, JobListingAdapterListener listener) {
        this.nearsoftJobs = nearsoftJobs;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = nearsoftJobs.get(position).getTitle();
        holder.mTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        return nearsoftJobs.size();
    }

    public interface JobListingAdapterListener {
        void onRowClicked();
    }
}
