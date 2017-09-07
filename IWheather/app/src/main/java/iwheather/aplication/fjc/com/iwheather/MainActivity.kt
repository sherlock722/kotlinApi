package iwheather.aplication.fjc.com.iwheather

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import data.HttpClientClima
import data.JSONParseClima
import kotlinx.android.synthetic.main.activity_main.*
import model.Clima
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import utils.Utils
import java.io.IOException
import java.io.InputStream
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


    //Se crea una clase privada para llamada asincrona al API y obtener imagenes
    //El inner se pone para poder acceder a la imageView que está fuera
    private inner class DescargarImagenAsync : AsyncTask <String, Void, Bitmap>(){

        override fun doInBackground(vararg p0: String?): Bitmap {
            //Se llama a la función que descarga la imagen
            return descargarImagen(p0[0] as String)
        }


        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            //Cargar la imagen en nuestro ImageView
            imageViewCity.setImageBitmap(result)
        }

        //Esta funcion va a descargar la imagen del API
        fun descargarImagen (codigo : String) : Bitmap{

            val cliente = DefaultHttpClient()

            //Aquí se monta la URL completa para descargar la imagen
            val getRequest = HttpGet(Utils.ICON_URL + codigo + ".png")

            try {

                val response = cliente.execute(getRequest)

                //Si se produce algún error se maneja con la siguiente línea
                val statusCodigo =  response.statusLine.statusCode

                //Se comprueba el valor de statusCodigo
                if (statusCodigo != HttpStatus.SC_OK){

                    //Se produce un error y se para la descarga
                    Log.e("DescargaImagen", "Error :" + statusCodigo)
                    return null!!
                }

                //Almacenamos los datos si llegan
                val entity = response.entity

                //Nos está llegando un Stream (los datos de la imagen)
                if (entity != null){

                    val inputStream : InputStream?
                    inputStream = entity.content

                    //Para decodificar este Stream

                    val bitmap : Bitmap = BitmapFactory.decodeStream(inputStream)
                    return bitmap

                }



            }catch (e: IOException){

                e.printStackTrace()
            }

            return null!!

        }
    }


    //Se crea una clase privada para la llamada asincrona al API y obtener sus datoa
    //El inner se pone para poder acceder a la varibla global clima
    private inner class ClimaTask : AsyncTask<String, Void, Clima>() {

        override fun doInBackground(vararg p0: String?): Clima {

            //Se instacia nuestra clase HttpClientClima
            var datos = HttpClientClima().getWheatherData(p0[0])
            clima = JSONParseClima.getWheather(datos)!!

            //Se recupera la imagen
            clima.icon=clima.condicionActual.icono
            DescargarImagenAsync().execute(clima.icon)

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