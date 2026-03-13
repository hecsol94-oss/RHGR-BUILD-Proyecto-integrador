----PROYECTO INTEGRADOR 1ª DAW - EDNA MODA WORKSHOP----
Este repositorio contiene el desarrollo del Proyecto Integrador para el ciclo de Desarrollo de Aplicaciones Web (DAW). El objetivo es crear una aplicación de gestión de citas para el taller de la famosa diseñadora de superhéroes, Edna Moda.

----DESCRIPCIÓN DEL PROYECTO----
La aplicación permite la gestión integral (CRUD) de clientes, trajes, empleados y citas en los diferentes talleres del estudio. El sistema está diseñado para manejar la compleja logística de héroes y villanos, asegurando que el flujo de trabajo en el taller sea eficiente y seguro.

----TECNOLOGIAS UTILIZADAS----
1. Lenguaje: Java (JDK actualizado).

2. IDE: Eclipse.

3. Base de Datos: MySQL (Modelo Relacional).

4. Modelado: Diagramas E/R y UML.

5. Control de Versiones: Git & GitHub.

6. Metodología: SCRUM.

----ANÁLISIS Y DISEÑO (Sprint #1)----
1. En esta fase inicial, se ha definido la arquitectura de datos para cumplir con las reglas de negocio:

2. Jerarquía de Usuarios: Acceso diferenciado para Aprendices, Oficiales y Maestros.

3. Gestión de Citas: Relación 1:N con los responsables y N:M con los aprendices (máximo 2 por cita).

4. Bonus de Seguridad: Implementación de lógica para garantizar un intervalo de 1 hora entre citas de héroes y villanos.

----ESTRUCTURA DEL REPOSITORIO----
1. /src: Código fuente de la aplicación (siguiendo el patrón MVC).

2. /doc: Documentación del proyecto, incluyendo el Diagrama Entidad/Relación.

3. /sql: Scripts de creación de la base de datos y carga de datos iniciales.

----PLANIFICACÓN (Sprints)----
1. Sprint 1: Análisis de requisitos, diseño del modelo E/R y configuración de herramientas colaborativas.

2. Sprint 2: Diseño de interfaces (Vista), generación del modelo relacional y creación de la BBDD.

3. Sprint 3: Desarrollo de la lógica de control y clases del modelo.

4. Sprint 4: Pruebas unitarias (JUnit), documentación JavaDoc y manual de usuario.
