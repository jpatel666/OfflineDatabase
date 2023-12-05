package com.example.offlinedatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<Integer> idlist = new ArrayList<>();
    ArrayList<String> namelist = new ArrayList<>();
    ArrayList<String> contactlist = new ArrayList<>();

    //Database:-Two Types Of Database
    //1.Offline Database 2.Online Database
    //API Calling And Web Service, Firebase Is On Server...Online Database
    //SQLite, SharedPreference Is On Device...Offline Database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                if(searchText.isEmpty())
                {
                    ContackbookAdapter contackbookAdapter = new ContackbookAdapter(MainActivity.this,idlist,namelist,contactlist);
                    recyclerView.setAdapter(contackbookAdapter);
                }
                else
                {
                    ArrayList<Integer> searchidlist = new ArrayList<>();
                    ArrayList<String> searchnamelist = new ArrayList<>();
                    ArrayList<String> searchcontactlist = new ArrayList<>();

                    for(int i=0;i<namelist.size();i++)
                    {
                        // Ajay = ajay == AJAy = ajAy = ajay
                        if(namelist.get(i).trim().toLowerCase().contains(searchText.trim().toLowerCase()) || contactlist.get(i).trim().contains(searchText.trim()))
                        {
                            searchidlist.add(idlist.get(i));
                            searchnamelist.add(namelist.get(i));
                            searchcontactlist.add(contactlist.get(i));
                        }
                    }

                    ContackbookAdapter contackbookAdapter = new ContackbookAdapter(MainActivity.this,searchidlist,searchnamelist,searchcontactlist);
                    recyclerView.setAdapter(contackbookAdapter);

                }

                return false;
            }
        });

    }
}





