package com.example.cardsvspeople;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity
{
	EditText editTextUserName,editTextPassword,editTextConfirmPassword;
	Button btnCreateAccount;

//	LoginDataBaseAdapter loginDataBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		// get Instance  of Database Adapter
//		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
//		loginDataBaseAdapter=loginDataBaseAdapter.open();

		// Get Refferences of Views
		
}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		//loginDataBaseAdapter.close();
	}
}