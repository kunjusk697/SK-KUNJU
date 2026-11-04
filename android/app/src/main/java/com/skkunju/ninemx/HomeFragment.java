package com.skkunju.ninemx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.support).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.support),
                "Support is the headset control in the reference header. This model has no live agent and does not collect a contact request."));
        view.findViewById(R.id.notifications).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.notifications),
                "Notifications is the bell in the reference header. There are no alerts in this preview."));
        view.findViewById(R.id.actionDatabase).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.database),
                "Database is the first shortcut on the home screen. No records are stored here."));
        view.findViewById(R.id.actionInvite).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.invite),
                "Invite Friends is a shortcut in the reference. This model does not generate a referral code or share a signup link."));
        view.findViewById(R.id.actionEvents).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.events),
                "Global Event Center is a shortcut in the reference. No event is scheduled in this preview."));
        view.findViewById(R.id.actionChallenge).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.challenge),
                "Challenge is a shortcut in the reference. This model does not track tasks or rewards."));
        view.findViewById(R.id.rowAdvisor).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.fund_advisor),
                "The reference card is labeled Fund Advisor. This model does not provide an investment strategy."));
        view.findViewById(R.id.rowTrends).setOnClickListener(v -> PageActivity.open(requireContext(),
                getString(R.string.market_trends),
                "The reference card is labeled Market Trends. This model does not show live prices or trading conditions."));

        TextView invest = view.findViewById(R.id.tabInvest);
        TextView daily = view.findViewById(R.id.tabDaily);
        TextView work = view.findViewById(R.id.tabWork);
        invest.setOnClickListener(v -> selectTab(invest, daily, work));
        daily.setOnClickListener(v -> selectTab(daily, invest, work));
        work.setOnClickListener(v -> selectTab(work, invest, daily));

        view.findViewById(R.id.aiButton).setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ChatActivity.class)));
    }

    private void selectTab(TextView selected, TextView other, TextView another) {
        selected.setTextColor(requireContext().getColor(R.color.yellow));
        other.setTextColor(0xFFC8C8CE);
        another.setTextColor(0xFFC8C8CE);
    }
}
