package model

/**
 * Created by javier on 5/9/17.
 */
data class Lugar (var long : Float = 0.toFloat(),
                  var lat : Float = 0.toFloat(),
                  var amanecer : Long = 0,
                  var puestaSol : Long = 0,
                  var pais : String? = null,
                  var ciudad : String? = null,
                  var ultimaActualizacion : Long = 0)