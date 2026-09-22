## Mejora con IA

### Prompt que usé

Tengo una aplicación Android desarrollada con Kotlin y Jetpack Compose.
Quiero que modifiques únicamente el composable PantallaRegisrtro de mi MainActivity.kt.
Agrega estas dos funcionalidades:
1. Validación de campos vacíos: cuando el usuario presione "AGREGAR PRODUCTO", verifica que nombre, precio y cantidad
2. tengan datos. Si alguno está vacío, NO debe mostrarse la Card del producto y debe aparecer un mensaje de error en rojo
3. indicando que debe completar todos los campos. Si todos tienen datos, debe funcionar como actualmente y mostrar la Card con el resumen.
4. Botón "LIMPIAR": agrega un botón que, al presionarlo, vacíe nombre, precio y cantidad, oculte la Card del resumen y quite
5. cualquier mensaje de error.
Importante: no cambies el diseño actual, los textos existentes, colores, espaciados, estructura general ni nombres de mis variables.
 No modifiques MainActivity, el Scaffold, el tema ni otros archivos. Mantén mi código lo más simple posible porque estoy aprendiendo Jetpack Compose.
Devuélveme el código completo actualizado de PantallaRegisrtro y los imports nuevos que sean necesarios.

### Qué generó la IA

La IA agregó la validación de campos vacíos. Si falta algún dato, se muestra un mensaje de error en rojo y no aparece la Card del producto.
También agregó un botón LIMPIAR que vacía los tres campos, oculta el resumen y elimina el mensaje de error.

### Qué acepté, añadí o corregí y por qué
Acepté la validación de campos vacíos y el botón LIMPIAR porque funcionaban correctamente.
Además, añadí:

- El nombre solo puede contener letras.
- El precio debe ser un número válido.
- La cantidad debe ser un número entero.
- El precio y la cantidad no pueden ser negativos ni iguales a 0.

