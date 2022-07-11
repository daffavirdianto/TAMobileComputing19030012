package com.example.tamobilecomputing19030012;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button LogInButton;
    EditText Username, Password;
    String UserHolder, PasswordHolder;
    boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogInButton = (Button) findViewById(R.id.buttonlogin);

        Username = (EditText) findViewById(R.id.edtusername);
        Password = (EditText) findViewById(R.id.edtpassword);
        sqLiteHelper = new SQLiteHelper(this);
        //method OnClick Login
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Method Cek Email Password
                CheckEditTextStatus();
                // Method Login Akun
                LoginFunction();
            }
        });
    }

    // Method login
    @SuppressLint("Range")
    public void LoginFunction(){
        if(EditTextEmptyHolder) {

            // SQLite database permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Email Query
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " +
                    SQLiteHelper.Table_Column_3_User + "=?", new String[]{UserHolder}, null, null, null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    // Password Email
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_Password));
                    cursor.close();
                }
            }

            // Method Cek Email Passsword
            CheckFinalResult();
        }
        else {
            // Messages Pop Up
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please Enter UserName or Password").setNegativeButton("OK",null).create().show();
        }
    }

    // Method Cek Email Dan Password
    public void CheckEditTextStatus(){
        // Getting value from All EditText and storing into

        String Variables;
        UserHolder = Username.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(UserHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }
    // Cek Email Password Yang Sudah Ada Di DataBase SQLite
    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra(UserEmail, UserHolder);
            startActivity(intent);
        }
        else {
            //Messages Pop Up
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("UserName or Password is Wrong, Please Try Again").setNegativeButton("OK",null).create().show();
        }
        TempPassword = "NOT_FOUND" ;
    }
}