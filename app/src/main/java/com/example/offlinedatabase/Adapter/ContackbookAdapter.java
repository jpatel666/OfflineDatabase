package com.example.offlinedatabase.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlinedatabase.Database.SqliteHelper;
import com.example.offlinedatabase.InsertContactActivity;
import com.example.offlinedatabase.MainActivity;
import com.example.offlinedatabase.R;
import com.example.offlinedatabase.UpdateContactActivity;

import java.util.ArrayList;

public class ContackbookAdapter extends RecyclerView.Adapter<ContackbookAdapter.MyClass> {

    MainActivity mainActivity;
    ArrayList<Integer> idlist;
    ArrayList<String> namelist;
    ArrayList<String> contactlist;
    ArrayList<Integer> colorlist;

    public ContackbookAdapter(MainActivity mainActivity, ArrayList<Integer> idlist, ArrayList<String> namelist, ArrayList<String> contactlist) {
        this.mainActivity = mainActivity;
        this.idlist = idlist;
        this.namelist = namelist;
        this.contactlist = contactlist; //13
        colorlist = new ArrayList<>();  //4

        for(int i=0;i<contactlist.size();i=i+4){
                colorlist.add(mainActivity.getColor(R.color.orange));  //0
                colorlist.add(mainActivity.getColor(R.color.green));   //1
                colorlist.add(mainActivity.getColor(R.color.skyblue)); //2
                colorlist.add(mainActivity.getColor(R.color.yellow));  //3
        }
    }

    @NonNull
    @Override
    public ContackbookAdapter.MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mainActivity).inflate(R.layout.contact_view_design,parent,false);
        MyClass myClass = new MyClass(view);
        return myClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ContackbookAdapter.MyClass holder, int position) {

        holder.tvName.setText(namelist.get(position));
        holder.tvContact.setText(contactlist.get(position));
        holder.tvUserlogo.setText(namelist.get(position));

        holder.tvUserlogo.setBackgroundColor(colorlist.get(position));

        holder.ivOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mainActivity,holder.ivOption);

                mainActivity.getMenuInflater().inflate(R.menu.option_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.update){

                            Intent intent = new Intent(mainActivity, InsertContactActivity.class);
                            intent.putExtra("userid",idlist.get(position));
                            intent.putExtra("name",namelist.get(position));
                            intent.putExtra("contact",contactlist.get(position));
                            mainActivity.startActivity(intent);
                            mainActivity.finish();

                        } else if (item.getItemId()==R.id.delete) {

                            SqliteHelper sqliteHelper = new SqliteHelper(mainActivity);
                            int userid = idlist.get(position);
                            sqliteHelper.deleteData(userid);  //deleteData Method Create Self //Alt+Enter

                            contactlist.remove(position);
                            idlist.remove(position);
                            namelist.remove(position);
                            colorlist.remove(position);

                            notifyDataSetChanged();
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return idlist.size();
    }

    public class MyClass extends RecyclerView.ViewHolder {

        TextView tvName,tvContact,tvUserlogo;
        ImageView ivOption;

        public MyClass(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvUserlogo = itemView.findViewById(R.id.tvUserlogo);
            ivOption = itemView.findViewById(R.id.ivOption);
        }
    }
}
