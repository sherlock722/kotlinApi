package iwheather.aplication.fjc.com.iwheather

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import data.HttpClientClima
import data.JSONParseClima
import kotlinx.android.synthetic.main.activity_main.*
import model.Clima
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var clima = Clima()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        renderClimaDatos("Madrid")

    }

    //Se crea una función que rederice los datos y complete la URL del API
    fun renderClimaDatos(ciudad: String) {

        val climaTask = ClimaTask()
        climaTask.execute(*arrayOf(ciudad + "&APPID=" + "d18f1ef9223eea7745e51ad2fd50afc7" + "&units=metric"))

    }


    //Se crea una clase privada para la llamada asincrona del API
    //El inner se pone para poder acceder a la varibla global clima
    private inner class ClimaTask : AsyncTask<String, Void, Clima>() {

        override fun doInBackground(vararg p0: String?): Clima {

            //Se instacia nuestra clase HttpClientClima
            var datos = HttpClientClima().getWheatherData(p0[0])
            clima = JSONParseClima.getWheather(datos)!!

            return clima

        }

        //Se pasan los datos recogidos del API a los elementos de muestra vista
        override fun onPostExecute(result: Clima?) {
            super.onPostExecute(result)

            //Se crean formatos para las fechas,las temperaturas y el formato decimal para formatear los datos
            //que llegan del API
            val formatoFecha = java.text.DateFormat.getTimeInstance()
            val amanecer = formatoFecha.format(Date(clima.lugar!!.amanecer))
            val puesta = formatoFecha.format(Date(clima.lugar!!.puestaSol))
            val actualizar = formatoFecha.format(Date(clima.lugar!!.ultimaActualizacion))

            val formatoDecimal = DecimalFormat("#.#")
            val formatoTemp = formatoDecimal.format(clima.condicionActual.temperatura)


            //Pasamos a nuestros EditText los valores del API
            textViewCity.text = clima.lugar!!.ciudad + "," + clima.lugar!!.pais
            textViewTemp.text = "" + formatoTemp + "C"
            textViewHumedity.text = "Humedad:" + clima.condicionActual.humedad + "%"
            textViewPress.text = "Presion:" + clima.condicionActual.presion
            textViewWing.text = "Viento:" + clima.viento.velocidad + "mps"
            textViewSunset.text = "Puesta de Sol:" + puesta
            textViewSunrise.text="Amanecer:" +  amanecer
            textViewUpdate.text = "Ultima Actualización:" + actualizar
            textViewCloud.text = "Nube:" + clima.condicionActual.condicion + "(" + clima.condicionActual.descripcion + ")"


        }

    }

}