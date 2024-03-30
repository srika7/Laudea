package com.example.laudea;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laudea.Adapter.DashboardAdapter;
import com.example.laudea.Domain.DashboardDomain;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCourceList;
    private RecyclerView recyclerViewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_list);

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<DashboardDomain> items = new ArrayList<>();
        items.add(new DashboardDomain("Time Tables", "tt"));
        items.add(new DashboardDomain("Attendance", "ic_2"));
        items.add(new DashboardDomain("CA Marks", "em"));
        items.add(new DashboardDomain("Courses", "ic_4"));
        items.add(new DashboardDomain("Exam Seating", "es"));
        items.add(new DashboardDomain("Elective Enrollment", "ic_5"));
        items.add(new DashboardDomain("Feedback", "fb"));
        items.add(new DashboardDomain("Exam Results", "er"));
        items.add(new DashboardDomain("Fees", "fees"));
        items.add(new DashboardDomain("Circular", "c"));

        recyclerViewCourse = findViewById(R.id.view);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterCourceList = new DashboardAdapter(items);
        recyclerViewCourse.setAdapter(adapterCourceList);
        ((DashboardAdapter) adapterCourceList).setOnItemClickListener(new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                if (position == 9) { // Assuming "Circular" is the last item in the list
                    // Start new activity here
                    startActivity(new Intent(DashboardActivity.this, CircularActivity.class));
                }
                if (position == 8) { // Assuming "Circular" is the last item in the list
                    // Start new activity here
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                }
            }
        });
    }
}