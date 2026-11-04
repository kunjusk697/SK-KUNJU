package com.skkunju.cryptoentities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.Holder> {
    public interface OnEntityClick {
        void onClick(Entity entity);
    }

    private final List<Entity> all = new ArrayList<>();
    private final List<Entity> shown = new ArrayList<>();
    private final OnEntityClick listener;
    private String query = "";

    public EntityAdapter(OnEntityClick listener) {
        this.listener = listener;
    }

    public void setEntities(List<Entity> entities) {
        all.clear();
        all.addAll(entities);
        applyFilter();
    }

    public void setQuery(String query) {
        this.query = query == null ? "" : query;
        applyFilter();
    }

    public int visibleCount() {
        return shown.size();
    }

    private void applyFilter() {
        shown.clear();
        String needle = query.trim().toLowerCase(Locale.US);
        for (Entity entity : all) {
            if (needle.isEmpty()
                    || entity.name.toLowerCase(Locale.US).contains(needle)
                    || entity.type.toLowerCase(Locale.US).contains(needle)
                    || entity.description.toLowerCase(Locale.US).contains(needle)) {
                shown.add(entity);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Entity entity = shown.get(position);
        holder.type.setText(entity.type);
        holder.name.setText(entity.name);
        holder.summary.setText(entity.description);
        holder.itemView.setOnClickListener(v -> listener.onClick(entity));
    }

    @Override
    public int getItemCount() {
        return shown.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView type;
        final TextView name;
        final TextView summary;

        Holder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.entityType);
            name = itemView.findViewById(R.id.entityName);
            summary = itemView.findViewById(R.id.entitySummary);
        }
    }
}
