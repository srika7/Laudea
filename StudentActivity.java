package com.example.laudea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class StudentActivity extends AppCompatActivity {

    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<StudentClass> StudentClassList;
    StudentAdapter adapter;
    SearchView searchView;
    TextView datePickerActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.recyclerView);

        searchView = findViewById(R.id.search);
        datePickerActions = findViewById(R.id.date);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(StudentActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        StudentClassList = new ArrayList<>();

        adapter = new StudentAdapter(StudentActivity.this, StudentClassList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentClassList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    StudentClass studentClass = itemSnapshot.getValue(StudentClass.class);

                    studentClass.setKey(itemSnapshot.getKey());

                    StudentClassList.add(studentClass);
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

      

        datePickerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public void searchList(String text) {
        ArrayList<StudentClass> searchList = new ArrayList<>();
        for (StudentClass studentClass : StudentClassList) {
            if (studentClass.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(studentClass);
            }
        }
        adapter.searchDataList(searchList);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                StudentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date selection
                        // Here you can filter the images based on the selected date
                        // and update the RecyclerView accordingly
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Intent intent = new Intent(StudentActivity.this, UploadActivity.class);
                        intent.putExtra("selected_date", selectedDate);
                        startActivity(intent);
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }
}
