# Proyecto Taller 3

## Enlace al Repositorio

[Repositorio en GitHub](https://github.com/jmartter/Taller_3.git)

Este proyecto es una aplicación de Android, la aplicación está compuesta por tres pantallas diferentes: `PantallaInicio`, `ActividadPrincipal` y `PantallaConfiguracion`.

## Ubicación del Código

El código de este proyecto se encuentra en la siguiente ruta:

`/Taller_2/src/main/java/com/example/taller_1/estructura/`

## Componentes Principales

### `PantallaInicio.kt`

1. **Clase `PantallaInicio`**:
   - Es una subclase de `ComponentActivity`.
   - En el método `onCreate`, se configura la pantalla de inicio usando Jetpack Compose.
   - Se pasa un color seleccionado a la pantalla de inicio.

2. **Función `PantallaInicioScreen`**:
   - Es una función composable que define la UI de la pantalla de inicio.
   - Usa `getGreetingMessage` para obtener un mensaje de saludo basado en la hora del día.
   - Usa `Image` para mostrar una imagen de banner.
   - Usa `Button` para navegar a la actividad principal.

3. **Función `PantallaInicioScreenPreview`**:
   - Es una función composable que proporciona una vista previa de la pantalla de inicio en el editor.

### `ActividadPrincipal.kt`

1. **Clase `ActividadPrincipal`**:
   - Es una subclase de `ComponentActivity`.
   - En el método `onCreate`, se configura la pantalla principal usando Jetpack Compose.
   - Se pasa un color seleccionado a la pantalla principal.

2. **Función `ActividadPrincipalScreen`**:
   - Es una función composable que define la UI de la pantalla principal.
   - Usa `remember` para mantener el estado del nombre, el saludo y otros estados.
   - Usa `TextField` para ingresar el nombre.
   - Usa `Button` para guardar el nombre en `SharedPreferences` y en SQLite, y actualizar el saludo.
   - Usa `Button` para cargar nombres y colores desde SQLite.
   - Usa `Button` para navegar a la pantalla de configuración.
   - Usa `LazyColumn` para mostrar una lista de nombres y colores guardados.
   - Usa `AlertDialog` para confirmar la eliminación o carga de un nombre y color.

3. **Función `ActividadPrincipalScreenPreview`**:
   - Es una función composable que proporciona una vista previa de la pantalla principal en el editor.

### `PantallaConfiguracion.kt`

1. **Clase `PantallaConfiguracion`**:
   - Es una subclase de `ComponentActivity`.
   - En el método `onCreate`, se configura la pantalla de configuración usando Jetpack Compose.
   - Se pasa un color seleccionado a la pantalla de configuración.

2. **Función `PantallaConfiguracionScreen`**:
   - Es una función composable que define la UI de la pantalla de configuración.
   - Usa `remember` para mantener el estado del color seleccionado.
   - Recupera el nombre guardado de `SharedPreferences`.
   - Muestra el nombre guardado en un `Text`.
   - Usa `LazyVerticalGrid` para mostrar una cuadrícula de colores seleccionables.
   - Usa `Button` para navegar de vuelta a la pantalla de inicio.

3. **Función `ColorCircle`**:
   - Es una función composable que define un círculo de color clicable.
   - Usa `Box` para crear un círculo con un borde y un color de fondo.
   - Guarda o actualiza el color seleccionado en SQLite.

4. **Función `PantallaConfiguracionScreenPreview`**:
   - Es una función composable que proporciona una vista previa de la pantalla de configuración en el editor.

### `DatabaseHelper.kt`

1. **Clase `DatabaseHelper`**:
   - Es una subclase de `SQLiteOpenHelper`.
   - Maneja la creación y actualización de la base de datos SQLite.
   - Proporciona funciones para guardar, eliminar, actualizar y obtener nombres y colores.
