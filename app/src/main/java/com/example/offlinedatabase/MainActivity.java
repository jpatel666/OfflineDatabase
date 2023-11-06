package com.example.offlinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.offlinedatabase.Adapter.ContackbookAdapter;
import com.example.offlinedatabase.Database.SqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addContact;

    RecyclerView recyclerView;
    ArrayList<Integer> idlist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> contactlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContact = findViewById(R.id.addContact);

        fetchAllData();
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,InsertContactActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void fetchAllData(){  //fetchAllData Method Create Self

        SqliteHelper sqliteHelper = new SqliteHelper(MainActivity.this);

        Cursor cursor = sqliteHelper.getAllData();  //getAllData Method Create Self

        while (cursor.moveToNext()){

            int userid = cursor.getInt(0);
            String name = cursor.getString(1);
            String contact = cursor.getString(2);

            idlist.add(userid);
            namelist.add(name);
            contactlist.add(contact);
        }

        recyclerView = findViewById(R.id.recyclerView);

        ContackbookAdapter contackbookAdapter = new ContackbookAdapter(MainActivity.this,idlist,namelist,contactlist);
        recyclerView.setAdapter(contackbookAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}





