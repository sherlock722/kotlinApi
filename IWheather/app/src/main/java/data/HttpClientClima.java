package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import utils.Utils;

/**
 * Created by javier on 5/9/17.
 */

public class HttpClientClima {

    public String getWheatherData (String lugar){

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        //Al tratar con conexiones a internet se deben utilizar try - catch
        try{

            //Iniciar la conexión
            connection = (HttpURLConnection) (new URL (Utils.INSTANCE.getBASE_URL() + lugar)).openConnection();

            //Utilizo el método GET para obtener datos de la URL
            connection.setRequestMethod("GET");

            //setDoInput se utiliza para obtener respuesta
            connection.setDoInput(true);
            connection.connect();

            //Para recuperar el "chorro de bytes" que llegan del servidor y traducirlos
            //a un lenguaje que entienda el programa, utilizo la clase StringBuffer
            //Esta clase ya es legible

            //StringBuffer stringBuffer = new StringBuffer();

            //Android Studio nos recomienda utilizar la clase StringBuilder
            StringBuilder stringBuffer = new StringBuilder();

            //El "chorro" de bytes se recupera en un InputStream
            inputStream = connection.getInputStream();

            // Se almacena en un BufferedReader lo que llega en el inputStream.
            //Es el encargado de leer todos el "chorro" que le va llegando en el inputStream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line = null;

            //Se recorre el bufferedReader y se lleva al stringBuffer para que se entienda.
            while ((line = bufferedReader.readLine()) != null){
                //Se ordena con \r\n
                //stringBuffer.append(line + "\r\n");

                //A partir del StringBuider se utilizan los métodos append
                stringBuffer.append(line).append("\r\n");

            }

            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();


        }catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

}
