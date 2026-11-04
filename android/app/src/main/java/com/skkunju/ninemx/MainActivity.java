package com.skkunju.ninemx;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                show(new HomeFragment());
            } else if (id == R.id.nav_fund) {
                show(SectionFragment.create(getString(R.string.nav_fund),
                        "Fund is the second tab in the reference navigation. This model does not list products, yields, or subscriptions."));
            } else if (id == R.id.nav_spot) {
                show(SectionFragment.create(getString(R.string.nav_spot),
                        "Spot is the trading tab in the reference. This model does not quote markets or send orders."));
            } else if (id == R.id.nav_team) {
                show(SectionFragment.create(getString(R.string.nav_team),
                        "Team is the referral tab in the reference. This model does not create invite codes or track a downline."));
            } else if (id == R.id.nav_asset) {
                show(SectionFragment.create(getString(R.string.nav_asset),
                        "Asset is the holdings tab in the reference. This model does not show balances or accept deposits."));
            }
            return true;
        });
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    private void show(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
