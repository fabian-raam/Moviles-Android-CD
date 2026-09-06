# Lab 03 - Registro de Producto
Fabián Ramírez

## Descripción
Aplicación desarrollada con Jetpack Compose que permite ingresar el nombre, 
precio y cantidad de un producto. Al presionar el botón, muestra un resumen con 
los datos y el importe total.

## Capturas
<img width="462" height="936" alt="image" src="https://github.com/user-attachments/assets/2e2e8fcd-9448-4882-a12d-24d03730f427" />
<img width="490" height="872" alt="image" src="https://github.com/user-attachments/assets/485f2805-decc-495c-b23b-2f25df5a6791" />

## ¿Qué pasaría si declaramos las variables de los campos sin remember?

Sin remember, cuando ocurra una recomposición, el valor de las variables 
se volvería a inicializar. Por eso, los datos ingresados en los campos podrían 
perderse en lugar de conservarse durante la recomposición.
