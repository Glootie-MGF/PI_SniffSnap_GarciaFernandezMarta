# 🐾 SniffSnap by Marta García Fernández 🐾

¡Bienvenido a SniffSnap! Una aplicación Android desarrollada con Kotlin que te permite explorar y descubrir el maravilloso mundo de las razas de perros. 🐕✨
La aplicación consume datos de una API externa para mostrar detalles como el nombre, la imagen, la esperanza de vida, el temperamento y más sobre cada raza de perro.

## 🌟 Características Principales

- **Exploración de Razas de Perros**: Descubre una amplia variedad de razas de perros, con información detallada y fotos. 📚🐶
- **Reconocimiento de Raza**: ¿Has visto un perro y te preguntas de qué raza es? ¡Utiliza nuestra función de reconocimiento de imagen para descubrirlo! 📸🔍
- **Favoritos**: Guarda tus razas favoritas para acceder a ellas rápidamente en cualquier momento. ❤️🐕
- **Autenticación de Usuarios**: Regístrate e inicia sesión para personalizar tu experiencia y guardar tus razas favoritas. 🔐👤

## 🛠 Tecnologías y Herramientas Utilizadas

- **Kotlin**: Lenguaje de programación principal.
- **Android Studio**: IDE para el desarrollo de la aplicación.
- **Retrofit & Moshi**: Para realizar llamadas a la API y manejar el JSON.
- **Coil & Glide**: Bibliotecas para la carga de imágenes desde Internet.
- **Firebase**: Autenticación de usuarios, base de datos en tiempo real y almacenamiento de imágenes.
- **CameraX**: Para implementar la funcionalidad de reconocimiento de raza mediante la cámara.
- **Jetpack Compose**: Para construir la UI de manera moderna y declarativa.
- **TensorFlow Lite**: Para el reconocimiento de imágenes y clasificación de razas de perros.

## 🏗 Estructura del Proyecto

El proyecto sigue la arquitectura MVVM (Modelo-Vista-ViewModel) para una separación clara de la lógica de negocio y la interfaz de usuario.

- **Modelo**: Representa los datos (`Dog`, `User`) y la lógica de negocio. `Dog` para representar la información de los perros y `User` para los datos de los usuarios.
- **Vista**: Compuesta por actividades y fragmentos, junto con Composables en algunas partes, que muestran la interfaz de usuario. `DogListActivity` muestra la lista de perros y `DogDetailActivity` muestra los detalles de un perro seleccionado.
- **ViewModel**: Gestiona el estado de la vista y la lógica de negocio, exponiendo datos a la vista. `DogListViewModel` y `AuthViewModel` son ejemplos de ViewModel en este proyecto.

## 🚀 Cómo Empezar

Para ejecutar el proyecto en tu entorno local, sigue estos pasos:

1. Clonar el repositorio:
   git clone https://github.com/glootie-mgf/pi_sniffsnap_garciafernandezmarta.git
2. Abrir el proyecto en Android Studio.
3. Configurar Firebase añadiendo tu archivo `google-services.json` en la carpeta `app`.
4. Ejecutar la aplicación en un emulador o dispositivo Android para una mejor experiencia.

## 🤝 Contribuciones

El proyecto siempre admite mejoras, las contribuciones son bienvenidas. Si deseas contribuir, por favor, abre un issue o una pull request.

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.