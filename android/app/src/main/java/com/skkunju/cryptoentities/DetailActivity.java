package com.skkunju.cryptoentities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViewById(R.id.back).setOnClickListener(v -> finish());

        String name = getIntent().getStringExtra(MainActivity.EXTRA_ENTITY_NAME);
        TextView typeView = findViewById(R.id.detailType);
        TextView nameView = findViewById(R.id.detailName);
        TextView descriptionView = findViewById(R.id.detailDescription);
        LinearLayout relationshipList = findViewById(R.id.relationshipList);

        try {
            Catalog catalog = Catalog.load(this);
            Entity entity = catalog.find(name);
            if (entity == null) {
                nameView.setText(name);
                descriptionView.setText(R.string.catalog_error);
                return;
            }
            typeView.setText(entity.type);
            nameView.setText(entity.name);
            descriptionView.setText(entity.description);

            LayoutInflater inflater = LayoutInflater.from(this);
            int matches = 0;
            for (Relationship relationship : catalog.relationships) {
                if (!relationship.touches(entity.name)) {
                    continue;
                }
                matches++;
                View row = inflater.inflate(R.layout.item_relationship, relationshipList, false);
                TextView ends = row.findViewById(R.id.relationshipEnds);
                TextView body = row.findViewById(R.id.relationshipBody);
                ends.setText(relationship.source + " → " + relationship.target);
                body.setText(relationship.description);
                relationshipList.addView(row);
            }
            if (matches == 0) {
                TextView none = new TextView(this);
                none.setText(R.string.no_relationships);
                none.setTextColor(getColor(R.color.muted));
                none.setTextSize(15);
                relationshipList.addView(none);
            }
        } catch (Exception error) {
            Toast.makeText(this, R.string.catalog_error, Toast.LENGTH_LONG).show();
        }
    }
}
