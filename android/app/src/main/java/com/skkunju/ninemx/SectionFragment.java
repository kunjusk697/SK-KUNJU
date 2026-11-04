package com.skkunju.ninemx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SectionFragment extends Fragment {
    public static SectionFragment create(String title, String body) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("body", body);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments() == null ? new Bundle() : getArguments();
        ((TextView) view.findViewById(R.id.sectionTitle)).setText(args.getString("title"));
        ((TextView) view.findViewById(R.id.sectionBody)).setText(args.getString("body"));
    }
}
