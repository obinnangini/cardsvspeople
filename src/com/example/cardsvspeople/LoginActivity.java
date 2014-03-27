package com.example.cardsvspeople;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

   private EditText  username=null;
   private EditText  password=null;
   private TextView attempts;
   private Button login;
   int counter = 3;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      username = (EditText)findViewById(R.id.editText1);
      password = (EditText)findViewById(R.id.editText2);
      attempts = (TextView)findViewById(R.id.textView5);
      attempts.setText(Integer.toString(counter));
      login = (Button)findViewById(R.id.buttonLogin);
   }

   public void login(View view){
      if(username.getText().toString().equals("admin") && 
      password.getText().toString().equals("admin")){
      Toast.makeText(getApplicationContext(), "Hello there...", 
      Toast.LENGTH_SHORT).show();
      Intent intentLogin= new Intent(this,MenuActivity.class);
      startActivity(intentLogin);
   }	
   else{
      Toast.makeText(getApplicationContext(), "Wrong Credentials",
      Toast.LENGTH_SHORT).show();
      attempts.setBackgroundColor(Color.RED);	
      counter--;
      attempts.setText(Integer.toString(counter));
      if(counter==0){
         login.setEnabled(false);
      }

   }

}
   
   public void register(View view){

	      Intent intentRegister= new Intent(this,RegisterActivity.class);
	      startActivity(intentRegister);
	}
   
   public void cheat(View view){
	   	  Intent intentLogin= new Intent(this,MenuActivity.class);
	      startActivity(intentLogin);
	}
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}