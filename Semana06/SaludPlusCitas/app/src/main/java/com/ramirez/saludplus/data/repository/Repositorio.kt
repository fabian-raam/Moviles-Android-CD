package com.ramirez.saludplus.data.repository

import com.ramirez.saludplus.data.model.Cita
import com.ramirez.saludplus.data.model.Especialidad
import com.ramirez.saludplus.data.model.Medico
import com.ramirez.saludplus.data.model.Usuario

object Repositorio{

    val usuarios = mutableListOf<Usuario>()

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
        //usuarios.any es una funciona que pregunta por algun objeto
        //de ese tipo, asi q recorre toda la lista, el it hace referencia
        //al objeto donde estemos en ese momento, asi verifica con todos
        if (usuarios.any { it.correo == usuario.correo}){
            return false
        }
        usuarios.add(usuario)
        return true
    }


    //Obtener medicos por especialidades
    fun getMedicosPorEspecialidad(especialidadId : Int) : List<Medico> {
        //it. hace referencia al objeto actual q se esta tratando
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
        //find busca el primer resultado que coincida
        return usuarios.find { it.correo == correo && it.contrasena == contrasena }
    }

}