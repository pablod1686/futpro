package utils.com.futpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import JBCrypt.BCrypt;
import profile.SessionActivity;
import profile.UserData;


/**
 * Created by jpablo09 on 12/20/2017.
 */

public class NewUserActivity extends AppCompatActivity {


    public ArrayList<UserData> copy =  new ArrayList<>();
    private String myJSON;

    private static final String TAG_RESULTS ="result";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "em";
    private static final String TAG_PW = "pw";

    private EditText email, password, confirmation;
    private String setEmail;
    private String setPassword;


    private Boolean CheckEditTextEmpty;
    private Boolean IsConfirmed;


    private static final String EXTRA_USEREMAIL = "userEmail";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);

        //userName =  findViewById(R.id.userNameEntry);
        email =  findViewById(R.id.emailEntry);
        password =  findViewById(R.id.passwordEntry);
        confirmation = findViewById(R.id.confirmationEntry);




        getData();
        //Log.d("PW", "USER Data :" + copy.get(1).getEmail());

        final TextView insert = findViewById(R.id.insert_new_user);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    appendNewUserData();



                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        TextView signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewUserActivity.this, SessionActivity.class);
                startActivity(intent);

            }
        });







    }


    public void appendNewUserData() throws ParseException {




        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        final ArrayList<HashMap<String, String>> json = new ArrayList<>();

        final HashMap<String, String> map = new HashMap<>();

        if(SubmitData()) {




                    //map.put("id", setUserID);
                    map.put("email", setEmail);
                    map.put("password", hashed(setPassword));


                    json.add(map);
                    Gson gson = new GsonBuilder().create();
                    String list = gson.toJson(json);


                    params.put("gamerData", list.replaceAll("\\[", "").replaceAll("\\]", ""));
                    client.post("http://betlogic.co/FUTPRO/insert_newuser_data.php", params, new AsyncHttpResponseHandler());

                    Intent intent = new Intent(NewUserActivity.this, PlayerCardRegistration.class);
                    intent.putExtra(EXTRA_USEREMAIL, setEmail);
                    startActivity(intent);


                    //params.put("singleBet", list);
                    //client.post("http://betlogic.co/FUTPRO/insert_multiple_gamer_data.php", params, new AsyncHttpResponseHandler());


        }

        //Log.d("Tag", "JSON list: " + params);
    }

    public boolean SubmitData() throws ParseException {



        //setUserName = userName.getText().toString();
        setEmail = email.getText().toString();
        setPassword = password.getText().toString();
        String setConfirmation = confirmation.getText().toString();




            CheckEditTextIsEmptyOrNot(setEmail, setPassword, setConfirmation);


            if (CheckEditTextEmpty) {


                if(emailIsValid(setEmail)) {


                    if(emailExists(setEmail)) {

                        isMatching(setPassword, setConfirmation);

                        if (IsConfirmed) {

                            Toast.makeText(NewUserActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                            ClearEditTextAfterDoneTask();

                            return true;

                        } else {


                            Toast.makeText(NewUserActivity.this, "Passwords do not match, please try again!", Toast.LENGTH_LONG).show();
                            ClearEditTextAfterDoneTask();

                            return false;
                        }
                    }else{

                        Toast.makeText(NewUserActivity.this, "Email Already Exist!", Toast.LENGTH_LONG).show();
                        ClearEditTextAfterDoneTask();

                        return false;

                    }


                }else{

                    Toast.makeText(NewUserActivity.this, "Invalid email format!", Toast.LENGTH_LONG).show();
                    ClearEditTextAfterDoneTask();

                    return false;

                }

            } else {

                Toast.makeText(NewUserActivity.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                ClearEditTextAfterDoneTask();
                return false;

            }

    }

    public void CheckEditTextIsEmptyOrNot(String email, String password, String confirm){

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)){

            CheckEditTextEmpty = false ;

        }
        else {

            CheckEditTextEmpty = true ;
        }
    }

    public void isMatching(String password, String confirm){


        if(password.equals(confirm)){

            IsConfirmed = true;

        }else{

            IsConfirmed = false;

        }



    }

    public void ClearEditTextAfterDoneTask(){

        //userName.getText().clear();
        email.getText().clear();
        password.getText().clear();
        confirmation.getText().clear();


    }

    public boolean emailIsValid(String email){

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();


    }

    public boolean emailExists(String email){

        if(copy.size() > 0) {

            for (int i = 0; i < copy.size(); i++) {


                if (Objects.equals(copy.get(i).getEmail(), email))
                    return false;

            }

        }

        return true;

    }

    public String hashed(String password){

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        // gensalt's log_rounds parameter determines the complexity
        // the work factor is 2**log_rounds, and the default is 10
        //String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        return  hashed;

    }



    private void syncedMatchList() throws ParseException {


        String ID;
        String email;
        String password;
        JSONArray user;


        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            user = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("MYSQL", "gamer Data :" + gamer);
            for(int i=0;i< user.length();i++){

                JSONObject c = user.getJSONObject(i);
                ID = c.getString(TAG_ID);
                email = c.getString(TAG_EMAIL);
                password = c.getString(TAG_PW);

                copy.add(new UserData(ID, email, password));


                //Log.d("PW", "USER Data :" + copy.get(i).getEmail());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getData(){
        @SuppressLint("StaticFieldLeak")
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://betlogic.co/FUTPRO/get_user_data.php");


                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){


                myJSON=result;

                try {

                    syncedMatchList();


                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }

    private void testHashDecryption(){



        String decrypt = "black1487";
        String pw = copy.get(0).getPassword();
        //Log.d("PW", "USER Data :" + pw);


        if (BCrypt.checkpw(decrypt, pw))
            Log.d("Decrypt", "yes: " + pw);
            //System.out.println("It matches");
        else
            Log.d("Decrypt", "no: " + pw);
        //System.out.println("It does not match");


    }

}
