package model

/**
 * Created by javier on 5/9/17.
 */
data class Clima (var lugar : Lugar? = null,
                  var icon : String? = null,
                  var condicionActual : CondicionActual = CondicionActual(),
                  var temperatura: Temperatura = Temperatura(),
                  var viento: Viento = Viento(),
                  var nieve: Nieve = Nieve(),
                  var nubes: Nubes = Nubes())