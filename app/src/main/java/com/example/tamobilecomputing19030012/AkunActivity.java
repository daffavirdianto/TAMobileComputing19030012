package com.example.tamobilecomputing19030012;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AkunActivity extends AppCompatActivity {

    EditText NISN, User, Password, Nama ;
    Button Register;
    String NISNHolder, NamaHolder, UserHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        Register = (Button)findViewById(R.id.buttonRegister);

        NISN = (EditText)findViewById(R.id.nisn);
        User = (EditText)findViewById(R.id.user);
        Password = (EditText)findViewById(R.id.password);
        Nama = (EditText)findViewById(R.id.nama);

        sqLiteHelper = new SQLiteHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Method Create Db Sqlite
                SQLiteDataBaseBuild();
                // Method Db Sqlite
                SQLiteTableBuild();
                // Method Cek Status Email
                CheckEditTextStatus();
                // Method cek email yang sudah ada di db Sqlite
                CheckingEmailAlreadyExistsOrNot();
                // Method Proses insert email password
                EmptyEditTextAfterDataInsert();
            }
        });
    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" +
                SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                SQLiteHelper.Table_Column_1_NISN + " VARCHAR, " + SQLiteHelper.Table_Column_2_Nama + " VARCHAR, " +
                SQLiteHelper.Table_Column_3_User + " VARCHAR, " + SQLiteHelper.Table_Column_4_Password + " VARCHAR);");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true) {
            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ SQLiteHelper.TABLE_NAME+" (nisn, nama, user,password) VALUES ('"+NISNHolder+"', '"+NamaHolder+"','"+UserHolder+"', '"+PasswordHolder+"');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            AlertDialog.Builder builder = new AlertDialog.Builder(AkunActivity.this);
            builder.setMessage("User Registered Successfully").setNegativeButton("OK",null).create().show();
        }
        else {
            // Messages Pop Up
            AlertDialog.Builder builder = new AlertDialog.Builder(AkunActivity.this);
            builder.setMessage("Please Fill All The Required Fields").setNegativeButton("OK",null).create().show();
        }
    }

    // Method Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){
        NISN.getText().clear();
        Nama.getText().clear();
        User.getText().clear();
        Password.getText().clear();
    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){
        NISNHolder = NISN.getText().toString();
        NamaHolder = Nama.getText().toString();
        UserHolder = User.getText().toString();
        PasswordHolder = Password.getText().toString();
        if(TextUtils.isEmpty(NISNHolder)|| TextUtils.isEmpty(NamaHolder) || TextUtils.isEmpty(UserHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }

    // Method Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " +
                SQLiteHelper.Table_Column_3_User + "=?", new String[]{UserHolder}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();

                // If Email is already exists then Result variablevalue set as EmailFound.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data intoSQLitedatabase.
        CheckFinalResult();
    }
    // Checking result
    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email is exists then toast msg will display.
            Toast.makeText(AkunActivity.this,"Email Already Exists", Toast.LENGTH_LONG).show();
        }
        else {
            // If email already dose n't exists then userregistration details will entered to SQLitedatabase.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }


}