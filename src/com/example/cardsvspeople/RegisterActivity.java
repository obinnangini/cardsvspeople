package com.example.cardsvspeople;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.cardsvspeople.AsyncTasks.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
	
	private EditText userNameArea,passWordArea,nickNameArea;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_register);
 
        TextView regButton = (TextView) findViewById(R.id.btnRegister);
 
        // Listening to Login Screen link
        regButton.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
            	register();
            	
           
            }
        });
    }
    
    public void register()
    {
    	userNameArea = (EditText)findViewById(R.id.reg_username);
    	nickNameArea = (EditText)findViewById(R.id.reg_nickname);
    	passWordArea = (EditText)findViewById(R.id.reg_password);
    	
    	String userName = userNameArea.getText().toString();
    	String nickName = nickNameArea.getText().toString();
    	String passWord = passWordArea.getText().toString();
    	
    	 AsyncTasks.RegisterUser task = new RegisterUser();
    	 task.execute(userName,nickName,passWord);
           
           Intent intentRegisterSuccess= new Intent(this,MenuActivity.class);
           intentRegisterSuccess.putExtra("gamename", nickName);
           intentRegisterSuccess.putExtra("username", userName);
           startActivity(intentRegisterSuccess);
    	
           
    }
    
    public static String executePost(String targetURL, String urlParameters)
    {
      URL url;
      HttpURLConnection connection = null;  
      try {
        //Create connection
        url = new URL(targetURL);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", 
             "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length", "" + 
                 Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");  

        connection.setUseCaches (false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        
        //Send request
        DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
        wr.writeBytes (urlParameters);
        wr.flush ();
        wr.close ();

        //Get Response    
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer(); 
        while((line = rd.readLine()) != null) {
          response.append(line);
          response.append('\r');
        }
        rd.close();
        return response.toString();

      } catch (Exception e) {

        e.printStackTrace();
        return null;

      } finally {

        if(connection != null) {
          connection.disconnect(); 
        }
      }
    }
    
}