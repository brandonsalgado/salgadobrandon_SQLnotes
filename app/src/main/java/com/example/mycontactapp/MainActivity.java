package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.mycontactapp.DatabaseHelper.COLUMN_NAME_CONTACT;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;
    boolean editingContact = false;
    String contactEditID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);


        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MaiActivity: instantiated the DatabaseHelper");

    }


    public void addData(View view)
    {
        boolean isInserted = false;
        boolean isEdited = false;
        if (!editingContact) {
            isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());
        }
        else
        {
            isEdited =  myDb.editData(contactEditID, editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());
            editingContact = false;
        }

        if (isInserted)
        {
            Toast.makeText(MainActivity.this, "Success contact inserted", Toast.LENGTH_LONG).show();

        }
        else if (isEdited)
        {
            Toast.makeText(MainActivity.this, "Success contact edited", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Failed contact not inserted", Toast.LENGTH_LONG).show();

        }
    }

    public void viewData(View v)
    {
        Cursor res = myDb.getALlData();
        Log.d("MyContactApp", "MainActivity: collecting data");

        if (res.getCount() == 0)
        {
            showMessage("Error", "No data found in db");
            return;
        }

        StringBuffer buf = new StringBuffer();
        while(res.moveToNext())
        {
            //append res to buffer use string buffer and cursor
            buf.append("ID: " + res.getString(0) + "\n");
            buf.append("Name: " + res.getString(1) + "\n");
            buf.append("Phone Number: " + res.getString(2) + "\n");
            buf.append("Address: " + res.getString(3) + "\n" + "\n");


        }
        showMessage("Contacts", buf.toString());
    }

    public void showMessage(String title, String  msg)
    {
        final String msg1 = msg;
        Log.d("MyContactApp", "MainActivity: showing data");
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(msg);
        if (!title.equals("Error")) {
            build.setNeutralButton("Edit Top Contact", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(MainActivity.this, "Edit Contact Then click add Contact", Toast.LENGTH_LONG).show();
                    editingContact = true;
                    contactEditID = //Integer.parseInt(
                            msg1.substring(4, 5);//)
                }
            });
        }
        build.show();

    }

    public void searchContacts(View v)
    {
        Cursor res = myDb.getALlData();
        Log.d("MyContactApp", "MainActivity: collecting data");

        if (res.getCount() == 0)
        {
            showMessage("Error", "No data found in db");
            return;
        }
        Log.d("MyContactApp", "Main Activity: name: " + editName.getText().toString());

        String input = editName.getText().toString() + editAddress.getText().toString() + editPhone.getText().toString();
        if (input.equals(""))
        {
            showMessage("Error", "No search input");
            return;
        }

        StringBuffer buf = new StringBuffer();
        while(res.moveToNext())
        {
            //append res to buffer use string buffer and cursor
            if ((res.getString(1).equals(editName.getText().toString()) || editName.getText().toString().equals("")) && (res.getString(2).equals(editPhone.getText().toString()) || editPhone.getText().toString().equals("")) && (res.getString(3).equals(editAddress.getText().toString()) || editAddress.getText().toString().equals((""))))
            {
                //if () {
                    buf.append("ID: " + res.getString(0) + "\n");
                    buf.append("Name: " + res.getString(1) + "\n");
                    buf.append("Phone Number: " + res.getString(2) + "\n");
                    buf.append("Address: " + res.getString(3) + "\n" + "\n");

            }



        }
        if (buf.toString().equals(""))
        {
            buf.append("No contacts with specified details found");
        }
        showMessage("Contacts", buf.toString());


    }

    public void editContacts(View v)
    {

    }

}