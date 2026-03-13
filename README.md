----Proyecto Integrador 1º DAW - Edna Moda Workshop
Este repositorio contiene el desarrollo del Proyecto Integrador para el ciclo de Desarrollo de Aplicaciones Web (DAW). El objetivo es crear una aplicación de gestión de citas para el taller de la famosa diseñadora de superhéroes, Edna Moda.

----Descripción del Proyecto
La aplicación permite la gestión integral (CRUD) de clientes, trajes, empleados y citas en los diferentes talleres del estudio. El sistema está diseñado para manejar la compleja logística de héroes y villanos, asegurando que el flujo de trabajo en el taller sea eficiente y seguro.

----Tecnologias Utilizadas
Lenguaje: Java (JDK actualizado).

IDE: Eclipse.

Base de Datos: MySQL (Modelo Relacional).

Modelado: Diagramas E/R y UML.

Control de Versiones: Git & GitHub.

Metodología: SCRUM.

----Análisis y Diseño (Sprint #1)
En esta fase inicial, se ha definido la arquitectura de datos para cumplir con las reglas de negocio:

Jerarquía de Usuarios: Acceso diferenciado para Aprendices, Oficiales y Maestros.

Gestión de Citas: Relación 1:N con los responsables y N:M con los aprendices (máximo 2 por cita).

Bonus de Seguridad: Implementación de lógica para garantizar un intervalo de 1 hora entre citas de héroes y villanos.

----Estructura del Repositorio
/src: Código fuente de la aplicación (siguiendo el patrón MVC).

/doc: Documentación del proyecto, incluyendo el Diagrama Entidad/Relación.

/sql: Scripts de creación de la base de datos y carga de datos iniciales.

----Planificación (Sprints)
Sprint 1: Análisis de requisitos, diseño del modelo E/R y configuración de herramientas colaborativas.

Sprint 2: Diseño de interfaces (Vista), generación del modelo relacional y creación de la BBDD.

Sprint 3: Desarrollo de la lógica de control y clases del modelo.

Sprint 4: Pruebas unitarias (JUnit), documentación JavaDoc y manual de usuario.
