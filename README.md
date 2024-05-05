# SniffSnap by Marta García Fernández

Este proyecto es una aplicación Android desarrollada con Kotlin que permite a los usuarios explorar información sobre diferentes razas de perros. La aplicación consume datos de una API externa para mostrar detalles como el nombre, la imagen, la esperanza de vida, el temperamento y más sobre cada raza de perro.

## Características

- **Listado de Perros**: Muestra una lista de perros con su imagen y nombre. Utiliza un `RecyclerView` para una gestión eficiente de la lista.
- **Detalle de Perro**: Al seleccionar un perro de la lista, se muestra una pantalla con más detalles sobre el perro seleccionado, incluyendo su temperamento, esperanza de vida, y más.
- **Autenticación de Usuarios**: La aplicación incluye funcionalidades de autenticación, permitiendo a los usuarios registrarse e iniciar sesión.

## Tecnologías y Herramientas Utilizadas

- **Kotlin**: Lenguaje de programación principal.
- **Android Studio**: Entorno de desarrollo integrado (IDE) para el desarrollo de la aplicación.
- **Retrofit**: Biblioteca utilizada para realizar llamadas a la API.
- **Moshi**: Biblioteca utilizada para la serialización/deserialización de JSON.
- **Coil**: Biblioteca para la carga de imágenes desde Internet.
- **Firebase Authentication**: Servicio utilizado para la autenticación de usuarios.
- **Firebase Realtime Database**: Base de datos utilizada para almacenar información de usuarios.
- **Firebase Storage**: Servicio utilizado para almacenar imágenes.

## Estructura del Proyecto

El proyecto sigue la arquitectura MVVM (Modelo-Vista-ViewModel) para separar la lógica de la interfaz de usuario de la lógica de negocio.

- **Modelo**: Representa los datos y la lógica de negocio. En este proyecto, los modelos incluyen `Dog` para representar la información de los perros y `User` para los datos de los usuarios.
- **Vista**: Compuesta por actividades y fragmentos que muestran la interfaz de usuario. `DogListActivity` muestra la lista de perros y `DogDetailActivity` muestra los detalles de un perro seleccionado.
- **ViewModel**: Gestiona el estado de la vista y la lógica de negocio, exponiendo datos a la vista. `DogListViewModel` y `AuthViewModel` son ejemplos de ViewModel en este proyecto.

## Cómo Empezar

Para ejecutar el proyecto, necesitarás:

1. Clonar el repositorio:
git clone https://github.com/glootie-mgf/pi_sniffsnap_garciafernandezmarta.git

2. Abrir el proyecto en Android Studio.
3. Configurar Firebase añadiendo tu archivo `google-services.json` en la carpeta `app`. (Modificado - No implementado)
4. Ejecutar la aplicación en un emulador o dispositivo Android.

## Contribuciones

Aunque el proyecto aún no está completo, las contribuciones son bienvenidas. Si deseas contribuir, por favor, abre un issue o una pull request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
