package utils

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by javier on 5/9/17.
 */
object Utils {


    //Se crean distintos métodos para tratar los valores de distintos tipos leídos de un JSON

    @Throws (JSONException::class)
    fun getObject (tagName: String, jsonObject: JSONObject ) : JSONObject{

        val jObj = jsonObject.getJSONObject(tagName)
        return jObj

    }

    @Throws (JSONException::class)
    fun getString (tagName: String, jsonObject: JSONObject ) : String{

        return jsonObject.getString(tagName)

    }

    @Throws (JSONException::class)
    fun getFloat (tagName: String, jsonObject: JSONObject ) : Float{

        return jsonObject.getDouble(tagName).toFloat()

    }

    @Throws (JSONException::class)
    fun getDouble (tagName: String, jsonObject: JSONObject ) : Double{

        return jsonObject.getDouble(tagName)

    }

    @Throws (JSONException::class)
    fun getInt (tagName: String, jsonObject: JSONObject ) : Int{

        return jsonObject.getInt(tagName)

    }

    @Throws (JSONException::class)
    fun getLong (tagName: String, jsonObject: JSONObject ) : Long{

        return jsonObject.getLong(tagName)

    }

}