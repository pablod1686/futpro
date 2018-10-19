package profile;

/**
 * Created by jpablo09 on 9/20/2018.
 */

public class UserData {

    private String ID;
    private String email;
    private String password;

    public  UserData(){


    }


    public UserData( String email) {


        this.email = email;


    }


    public UserData( String id, String email) {


        this.email = email;
        this.ID = id;


    }

    public UserData(String id, String email,  String  password) {

        this.ID = id;
        this.email = email;
        this.password = password;

    }


    public String getID(){

        return ID;

    }

    public String getEmail(){

        return email;

    }

    public String getPassword(){

        return password;

    }


}
