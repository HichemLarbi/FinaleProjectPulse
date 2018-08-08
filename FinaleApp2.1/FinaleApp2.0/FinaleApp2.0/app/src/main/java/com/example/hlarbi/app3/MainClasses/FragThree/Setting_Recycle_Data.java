package com.example.hlarbi.app3.MainClasses.FragThree;
/*Here is a simple class that is used in the RecycleViewAdapter class
* Infact, it is composed of String and drawable ressources files. They are displayed in the RecycleView
* */
import com.example.hlarbi.app3.R;
public class Setting_Recycle_Data {
    public static String[] title_setting = new String[]{
            "Profile",
            "Survey",
            "Fitbit Synchronasation",
            "Reset Password",
            "About PulseYou",
            "Log Out"
    };
    public static int[] images_icon_setting = new int[]{
            R.drawable.userinfo,
            R.drawable.test,
            R.drawable.fitbitsynchro,
            R.drawable.resetpassword,
            R.drawable.about,
            R.drawable.logout
    };

}
