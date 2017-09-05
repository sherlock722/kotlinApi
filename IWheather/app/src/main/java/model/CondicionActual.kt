package model

/**
 * Created by javier on 5/9/17.
 */
data class CondicionActual (var wheatherId : Int = 0,
                            var condicion : String? = null,
                            var descripcion : String? = null,
                            var icono : String? = null,
                            var presion : Float = 0.toFloat(),
                            var humedad : Float = 0.toFloat(),
                            var maxTemp : Float = 0.toFloat(),
                            var minTemp : Float = 0.toFloat(),
                            var temperatura : Double = 0.toDouble())