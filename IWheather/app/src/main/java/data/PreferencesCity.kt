package data

import android.app.Activity
import android.content.SharedPreferences

/**
 * Created by javier on 7/9/17.
 */
//Clase para las preferncias del usuario
class PreferencesCity (activity: Activity) {

    var pref : SharedPreferences

    //Se inicializa la variable pref
    init {
        //De esta manera se va quedar el archivo de modo privado
        pref = activity.getPreferences(Activity.MODE_PRIVATE)
    }


    var ciudad : String
    get() = pref.getString("ciudad", "Madrid")
    set(ciudad) = pref.edit().putString("ciudad",ciudad).apply()

}