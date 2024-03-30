package com.example.laudea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CircularActivity extends AppCompatActivity {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;
    TextView startingDate;
    TextView endingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.search);
//        startingDate = findViewById(R.id.startingDate);
//        endingDate = findViewById(R.id.endingDate);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CircularActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(CircularActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        adapter = new MyAdapter(CircularActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               searchList(newText);
                return true;
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CircularActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
//        MaterialButton button = findViewById(R.id.rangePicker);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(
//                        MaterialDatePicker.todayInUtcMilliseconds(),
//                        MaterialDatePicker.todayInUtcMilliseconds()
//                )).build();
//
//                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
//                    @Override
//                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
//                        Date startDate = new Date(selection.first);
//                        Date endDate = new Date(selection.second);
//
//                        // Filter data based on the selected date range
//                        ArrayList<DataClass> filteredList = new ArrayList<>();
//                        for (DataClass data : dataList) {
//                            Date dataDate = data.getUploadDate();
//                            if (dataDate != null && dataDate.after(startDate) && dataDate.before(endDate)) {
//                                filteredList.add(data);
//                            }
//                        }
//
//                        // Update RecyclerView with filtered data
//                        adapter.setData(filteredList);
//
//                        // Update TextViews to show selected date range
//                        String date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(startDate);
//                        String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(endDate);
//                        startingDate.setText(MessageFormat.format("Selected Starting Date: {0}", date1));
//                        endingDate.setText(MessageFormat.format("Selected Ending Date: {0}", date2));
//                    }
//                });
//
//
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//            }
//        });
    }

    // Method to filter the RecyclerView based on search query
    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase()) || text.isEmpty()) {
                searchList.add(dataClass);
            }


        }
        adapter.searchDataList(searchList);
    }
    public void searchListByDate(String date) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getDataLang().equals(date) || date.isEmpty()) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

}
