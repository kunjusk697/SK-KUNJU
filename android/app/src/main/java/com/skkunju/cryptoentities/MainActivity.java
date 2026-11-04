package com.skkunju.cryptoentities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_ENTITY_NAME = "entity_name";

    private Catalog catalog;
    private EntityAdapter adapter;
    private TextView count;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = findViewById(R.id.catalogCount);
        empty = findViewById(R.id.empty);
        EditText search = findViewById(R.id.search);
        RecyclerView list = findViewById(R.id.entityList);

        adapter = new EntityAdapter(entity -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(EXTRA_ENTITY_NAME, entity.name);
            startActivity(intent);
        });
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setQuery(s.toString());
                renderCount();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        try {
            catalog = Catalog.load(this);
            adapter.setEntities(catalog.entities);
            renderCount();
        } catch (Exception error) {
            Toast.makeText(this, R.string.catalog_error, Toast.LENGTH_LONG).show();
            count.setText(R.string.catalog_error);
        }
    }

    private void renderCount() {
        if (catalog == null) {
            return;
        }
        int visible = adapter.visibleCount();
        count.setText(getString(R.string.catalog_count, visible, catalog.entities.size(), catalog.relationships.size()));
        empty.setVisibility(visible == 0 ? TextView.VISIBLE : TextView.GONE);
    }
}
