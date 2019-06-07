package np.com.smartpolicestation;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    private Context context;
    private SharedPreferences sharedPreferences;

    public Shared(Context context){
        this.context = context;
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("LiveData", Context.MODE_PRIVATE);
        }
    }

    public void setBackground(boolean value){
        sharedPreferences.edit().putBoolean("background", value).apply();
    }

    public boolean getBackground(){
        return sharedPreferences.getBoolean("background", false);
    }
}
