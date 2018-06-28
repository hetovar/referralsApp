package com.nearsoft.referralsapp.job_openings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearsoft.referralsapp.R;

import java.util.ArrayList;

public class JobListingAdapter extends RecyclerView.Adapter<JobListingAdapter.ViewHolder>{
    private ArrayList<String> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
            layout = itemView.findViewById(R.id.itemLayout);
        }
    }

    JobListingAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
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
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
