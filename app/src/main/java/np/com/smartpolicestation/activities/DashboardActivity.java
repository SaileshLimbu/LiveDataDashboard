package np.com.smartpolicestation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import np.com.smartpolicestation.R;
import np.com.smartpolicestation.Shared;
import np.com.smartpolicestation.fragments.EQFragment;
import np.com.smartpolicestation.fragments.RFIDFragment;
import np.com.smartpolicestation.services.LiveDataService;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private TextView tvTitle;
    private NavigationView nav;
    private Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        shared = new Shared(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        tvTitle = findViewById(R.id.tvTitle);

        nav = findViewById(R.id.nav);

        nav.findViewById(R.id.viewEQ).setOnClickListener(this);
        nav.findViewById(R.id.viewRFID).setOnClickListener(this);

        showFragment(new EQFragment(), "Live Earthquake Data");
    }

    public void openDrawer(View view) {
        drawerLayout.openDrawer(Gravity.START);
    }

    private void showFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewEQ:
                showFragment(new EQFragment(), "Live EarthQuake Data");
                break;
            case R.id.viewRFID:
                showFragment(new RFIDFragment(), "Live RFID Data");
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected void onStart() {
        super.onStart();
        shared.setBackground(false);
        stopService(new Intent(this, LiveDataService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        shared.setBackground(true);
        startService(new Intent(this, LiveDataService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        shared.setBackground(false);
        stopService(new Intent(this, LiveDataService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shared.setBackground(true);
        startService(new Intent(this, LiveDataService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        shared.setBackground(true);
        startService(new Intent(this, LiveDataService.class));
    }
}
