package data

import model.Clima
import model.Lugar
import org.json.JSONException
import org.json.JSONObject
import utils.Utils

/**
 * Created by javier on 5/9/17.
 */
object JSONParseClima {

    fun getWheather (data : String) : Clima?{

        val clima = Clima()

        try{

            //Se crea el objeto JSON a partir de cada data recogido
            val jsonObject = JSONObject(data)

            //Se crea la variable lugar que es la que se va a recuperar su información
            val lugar = Lugar()

            var coordObj = Utils.getObject("coord",jsonObject);
            lugar.lat = Utils.getFloat("lat",coordObj)
            lugar.long = Utils.getFloat("lon",coordObj)

            val sysObjet = Utils.getObject("sys",jsonObject)
            lugar.pais = Utils.getString("country",sysObjet)
            lugar.amanecer = Utils.getLong("sunrise",sysObjet)
            lugar.puestaSol = Utils.getLong("sunset",sysObjet)
            lugar.ultimaActualizacion = Utils.getLong("dt",jsonObject)
            lugar.ciudad = Utils.getString("name",jsonObject)

            clima.lugar=lugar

            val mainObject = Utils.getObject("main",jsonObject)
            clima.condicionActual.humedad = Utils.getFloat("humidity",mainObject)
            clima.condicionActual.temperatura = Utils.getDouble("temp",mainObject)
            clima.condicionActual.presion = Utils.getFloat("pressure",mainObject)
            clima.condicionActual.maxTemp = Utils.getFloat("temp_max",mainObject)
            clima.condicionActual.minTemp = Utils.getFloat("temp_min",mainObject)

            //Para los arrays
            val jsonArray = jsonObject.getJSONArray("weather")
            val jsonWheather = jsonArray.getJSONObject(0)
            clima.condicionActual.wheatherId = Utils.getInt("id",jsonWheather)
            clima.condicionActual.descripcion = Utils.getString("description",jsonWheather)
            clima.condicionActual.condicion = Utils.getString("main",jsonWheather)
            clima.condicionActual.icono = Utils.getString("icon",jsonWheather)

            val vientoObj = Utils.getObject("wind",jsonObject)
            clima.viento.velocidad = Utils.getFloat("speed",vientoObj)
            //clima.viento.cent = Utils.getFloat("deg",vientoObj)


            val nubeObj = Utils.getObject("clouds",jsonObject)
            clima.nubes.precipitacion = Utils.getInt("all",nubeObj)

            return clima


        }catch (e : JSONException){
            e.printStackTrace()

        }

        return null

    }


}



