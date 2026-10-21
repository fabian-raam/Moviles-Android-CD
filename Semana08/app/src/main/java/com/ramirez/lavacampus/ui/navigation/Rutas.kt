package com.ramirez.lavacampus.ui.navigation

sealed class Rutas(val ruta: String) {
    object Splash : Rutas("splash")
    object Login : Rutas("login")
    object EstudianteInicio : Rutas("estudiante_inicio")
    object EstudianteNuevoPedido : Rutas("estudiante_nuevo_pedido")
    object EstudianteDetallePedido : Rutas("estudiante_detalle_pedido/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "estudiante_detalle_pedido/$pedidoId"
    }
    object EstudianteMisPedidos : Rutas("estudiante_mis_pedidos")
    object EstudianteTarifas : Rutas("estudiante_tarifas")
    object EstudiantePerfil : Rutas("estudiante_perfil")

    object AdminPanel : Rutas("admin_panel")
    object AdminBandeja : Rutas("admin_bandeja")
    object AdminGestionPedido : Rutas("admin_gestion_pedido/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "admin_gestion_pedido/$pedidoId"
    }
    object AdminServicios : Rutas("admin_servicios")
    object AdminFormularioServicio : Rutas("admin_formulario_servicio/{servicioId}") {
        fun crearRuta(servicioId: Int) = "admin_formulario_servicio/$servicioId"
    }
    object AdminClientes : Rutas("admin_clientes")
    object AdminAjustes : Rutas("admin_ajustes")
}