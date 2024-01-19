package com.example.offlinedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.offlinedatabase.Database.SqliteHelper;

public class    InsertContactActivity extends AppCompatActivity {

    EditText etName,etContact;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_contact);

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        btnSave = findViewById(R.id.btnSave);

        if(getIntent().getExtras()!=null){

            String name = getIntent().getStringExtra("name");
            String contact = getIntent().getStringExtra("contact");

            etName.setText(name);
            etContact.setText(contact);
            btnSave.setText("Update");
            btnSave.setEnabled(true);
            btnSave.setBackgroundColor(getColor(R.color.buttoncolor));
        }else {
            btnSave.setText("Save");
            btnSave.setEnabled(false);
        }

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().isEmpty() && !etContact.getText().toString().isEmpty()){

                    btnSave.setEnabled(true);
                    btnSave.setBackgroundColor(getColor(R.color.buttoncolor));
                }else {

                    btnSave.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().isEmpty() && !etName.getText().toString().isEmpty()){

                    btnSave.setEnabled(true);
                    btnSave.setBackgroundColor(getColor(R.color.buttoncolor));
                }else {

                    btnSave.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SqliteHelper sqliteHelper = new SqliteHelper(InsertContactActivity.this);  //Alt+Enter

                if(getIntent().getExtras()!=null){

                    String newname = etName.getText().toString();
                    String newcontact = etContact.getText().toString();

                    int userid = getIntent().getIntExtra("userid",0);

                    sqliteHelper.updateData(userid,newname,newcontact);  //updateData Method Create Self  //Alt+Enter

                    Toast.makeText(InsertContactActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                }else {

                    String name = etName.getText().toString();
                    String contact = etContact.getText().toString();

                    sqliteHelper.insertData(name,contact);  //insertData Method Create Self  //Alt+Enter

                    Toast.makeText(InsertContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

                onBackPressed();

            }
        });
    }

    @Override
    public void onBackPressed() {  //onBack...(onBackPressed) Method Create Self
        super.onBackPressed();
        Intent intent = new Intent(InsertContactActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}