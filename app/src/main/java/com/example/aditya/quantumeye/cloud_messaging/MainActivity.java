package com.example.aditya.quantumeye.cloud_messaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.camera.CameraActivity;
import com.example.aditya.quantumeye.login.LoginActivity;
import com.example.aditya.quantumeye.utils.QuantumUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private TextView mGradeTextView;
    private RecyclerView mAlertListRecyclerView;
    private Toolbar mToolbar;

    private MainAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Handler mHandler = new Handler();

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mGradeTextView = (TextView) findViewById(R.id.main_grade_textView);
        mAlertListRecyclerView = (RecyclerView) findViewById(R.id.main_notifications_recyclerView);

        mAdapter = new MainAdapter(MainActivity.this, QuantumUtils.notificationAlerts);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAlertListRecyclerView.setLayoutManager(mLayoutManager);
        mAlertListRecyclerView.setAdapter(mAdapter);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mHandler.postDelayed(this, 100);
            }
        }, 100);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alert Notifications";
            String description = "Notify's about dangerous/illegal items";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(QuantumUtils.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_open_scanner : {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_main_logout : {
                mEditor = mPreferences.edit();
                mEditor.putString("password", "");
                mEditor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_main_clear_list : {
                QuantumUtils.notificationAlerts.clear();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
