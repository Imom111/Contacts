package edu.utl.dsm502.contacts.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import edu.utl.dsm502.contacts.model.Contact;

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_ID_CONTACT = "SHARED_PREFERENCES_ID_CONTACT";
    private static final String SHARED_PREFERENCES_NAME_CONTACT = "SHARED_PREFERENCES_NAME_CONTACT";
    private static final String SHARED_PREFERENCES_NUMBER_CONTACT = "SHARED_PREFERENCES_NUMBER_CONTACT";
    private static final String SHARED_PREFERENCES_EMAIL_CONTACT = "SHARED_PREFERENCES_EMAIL_CONTACT";
    private static final String SHARED_PREFERENCES_ADDRESS_CONTACT = "SHARED_PREFERENCES_ADDRESS_CONTACT";
    private static final String SHARED_PREFERENCES_PHOTOGRAPHY_CONTACT = "SHARED_PREFERENCES_PHOTOGRAPHY_CONTACT";

    public static SharedPrefManager instance;
    public static Context context;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveContact(Contact c){
        editor.putInt(SHARED_PREFERENCES_ID_CONTACT, c.getId());
        editor.putString(SHARED_PREFERENCES_NAME_CONTACT, c.getName());
        editor.putString(SHARED_PREFERENCES_NUMBER_CONTACT, c.getNumber());
        editor.putString(SHARED_PREFERENCES_EMAIL_CONTACT, c.geteMail());
        editor.putString(SHARED_PREFERENCES_ADDRESS_CONTACT, c.getAddress());
        editor.putString(SHARED_PREFERENCES_PHOTOGRAPHY_CONTACT, c.getPhotography());
        editor.apply();
    }

    public Contact getContact(){
        Contact c = new Contact();
        c.setId(sharedPreferences.getInt(SHARED_PREFERENCES_ID_CONTACT,-1));
        c.setName(sharedPreferences.getString(SHARED_PREFERENCES_NAME_CONTACT,null));
        c.setNumber(sharedPreferences.getString(SHARED_PREFERENCES_NUMBER_CONTACT,null));
        c.seteMail(sharedPreferences.getString(SHARED_PREFERENCES_EMAIL_CONTACT,null));
        c.setAddress(sharedPreferences.getString(SHARED_PREFERENCES_ADDRESS_CONTACT,null));
        c.setPhotography(sharedPreferences.getString(SHARED_PREFERENCES_PHOTOGRAPHY_CONTACT,null));
        return c;
    }
}
