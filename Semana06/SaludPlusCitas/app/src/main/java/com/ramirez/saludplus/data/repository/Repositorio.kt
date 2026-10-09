package com.ramirez.saludplus.data.repository

import com.ramirez.saludplus.data.model.Cita
import com.ramirez.saludplus.data.model.Especialidad
import com.ramirez.saludplus.data.model.Medico
import com.ramirez.saludplus.data.model.Usuario

object Repositorio{

    val usuarios = mutableListOf<Usuario>()

    // Usuario actualmente logueado en la sesión
    var usuarioActual: Usuario? = null

    // ID de la especialidad seleccionada para agendar cita
    var especialidadSeleccionadaId: Int = 1

    val especialidades = listOf<Especialidad>(
        Especialidad(1,"Medicina General"),
        Especialidad(2,"Pediatria"),
        Especialidad(3,"Ginecologia"),
        Especialidad(4,"Cardiologia"),
        Especialidad(5,"Dermatologia"),
        Especialidad(6,"Traumatologia"),
        Especialidad(7,"Oftalmologia")
    )

    val medicos = listOf<Medico>(
        Medico(1,"Ana Torres",1),
        Medico(2,"Renzo Perez",1),
        Medico(3,"Fabian Ramirez",2),
        Medico(4,"Pedro Alvarez",3),
        Medico(5,"Lucas Martinez",3),
        Medico(6,"Juan Oyala",4),
        Medico(7,"Sofia Guevara",4),
        Medico(8,"Jordy Gamboa",5),
        Medico(9,"Leon Paredes",5),
        Medico(10,"Renzo Trauco",6),
        Medico(11,"Kiara Lopez",6),
        Medico(12,"Noemi Renez",7),
        Medico(13,"Paola Bala",7),
    )

    val citas = mutableListOf<Cita>()



    //Registrar usuario
    fun registrarUsuario( usuario: Usuario): Boolean{
        if (usuarios.any { it.correo == usuario.correo}){
            return false
        }
        usuarios.add(usuario)
        usuarioActual = usuario // Guardar sesión activa
        return true
    }


    //Obtener medicos por especialidades
    fun getMedicosPorEspecialidad(especialidadId : Int) : List<Medico> {
        return medicos.filter { it.especialidadId == especialidadId }
    }

    //Agregar una cita
    fun agregarCita(cita: Cita){
        citas.add(cita)
    }

    //Ver mis citas
    fun getCitasPorUsuario(usuarioId: Int) : List<Cita>{
        return citas.filter { it.usuarioId == usuarioId }
    }


    //Busca un usuario con esos datos o devuelve null
    fun login(correo : String, contrasena : String) : Usuario? {
        val user = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        if (user != null) {
            usuarioActual = user // Guardar sesión activa
        }
        return user
    }

}
