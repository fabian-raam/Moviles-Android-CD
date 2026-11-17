package com.ramirez.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.ramirez.campusgps.model.Posicion
import com.ramirez.campusgps.model.UbicacionNoDisponibleException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {

    // Usa el contexto de la aplicacion para no retener una Activity.
    private val contexto = context.applicationContext
    private val cliente by lazy {
        LocationServices.getFusedLocationProviderClient(contexto)
    }

    override fun playServicesDisponible(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS
    }

    // La pantalla solicitara el permiso antes de buscar. Si falta, se devuelve el error.
    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val administrador = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            // Evita devolver una posicion en cache cuando la ubicacion esta desactivada.
            if (!LocationManagerCompat.isLocationEnabled(administrador)) {
                return Result.failure(UbicacionNoDisponibleException())
            }

            val ubicacion = cliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancelacion.token
            ).await()

            if (ubicacion == null) {
                Result.failure(UbicacionNoDisponibleException())
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
