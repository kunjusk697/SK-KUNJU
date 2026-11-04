package com.skkunju.cryptoentities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    public final List<Entity> entities;
    public final List<Relationship> relationships;

    public Catalog(List<Entity> entities, List<Relationship> relationships) {
        this.entities = entities;
        this.relationships = relationships;
    }

    public static Catalog load(Context context) throws Exception {
        StringBuilder json = new StringBuilder();
        try (InputStream input = context.getAssets().open("crypto-entities.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        JSONObject root = new JSONObject(json.toString());
        JSONArray entityArray = root.getJSONArray("entities");
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < entityArray.length(); i++) {
            JSONObject item = entityArray.getJSONObject(i);
            entities.add(new Entity(
                    item.optString("name"),
                    item.optString("type"),
                    item.optString("description")
            ));
        }
        JSONArray relationshipArray = root.getJSONArray("relationships");
        List<Relationship> relationships = new ArrayList<>();
        for (int i = 0; i < relationshipArray.length(); i++) {
            JSONObject item = relationshipArray.getJSONObject(i);
            relationships.add(new Relationship(
                    item.optString("source"),
                    item.optString("target"),
                    item.optString("description")
            ));
        }
        return new Catalog(entities, relationships);
    }

    public Entity find(String name) {
        for (Entity entity : entities) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }
}
