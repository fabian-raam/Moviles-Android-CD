package com.ramirez.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.ramirez.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {

    // Usa el contexto de la aplicacion para no retener una Activity.
    private val cliente = LocationServices.getFusedLocationProviderClient(
        context.applicationContext
    )

    // La pantalla solicitara el permiso antes de buscar. Si falta, se devuelve el error.
    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val ubicacion = cliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancelacion.token
            ).await()

            if (ubicacion == null) {
                Result.failure(IllegalStateException("Activa el GPS e intenta de nuevo"))
            } else {
                Result.success(
                    Posicion(
                        latitud = ubicacion.latitude,
                        longitud = ubicacion.longitude
                    )
                )
            }
        } catch (e: CancellationException) {
            // La cancelacion de una corrutina debe propagarse, no convertirse en un error de UI.
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            cancelacion.cancel()
        }
    }
}
