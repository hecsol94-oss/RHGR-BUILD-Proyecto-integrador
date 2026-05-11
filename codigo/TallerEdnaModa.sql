USE tallerednamoda;

DROP TABLE IF EXISTS Cita_Aprendiz;
DROP TABLE IF EXISTS Citas;
DROP TABLE IF EXISTS Traje;
DROP TABLE IF EXISTS Taller;
DROP TABLE IF EXISTS Empleados;
DROP TABLE IF EXISTS Cliente;

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_personaje VARCHAR(30),
    tipo_heroe VARCHAR(30),
    superpoder VARCHAR(30),
    colores VARCHAR(50)
);

CREATE TABLE Empleados ( 
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    categoria VARCHAR(30),
    nombre VARCHAR(30),
    apellido VARCHAR(30),
    apodo VARCHAR(30),
    usuario VARCHAR(30),
    contraseña VARCHAR(30)
);

CREATE TABLE Taller (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sala VARCHAR(30),
    tipo_sala VARCHAR(30)
);

CREATE TABLE Traje (
    id_traje INT AUTO_INCREMENT PRIMARY KEY,
    nombre_traje VARCHAR(50), 
    estado VARCHAR(30),
    id_cliente INT,
    CONSTRAINT fk_traje_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente) ON DELETE CASCADE
);

CREATE TABLE Citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora_inicio TIME,
    duracion INT DEFAULT 1,
    id_empleado INT,
    id_cliente INT,
    id_sala INT,
    id_traje INT,
    CONSTRAINT fk_citas_cliente  FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente) ON DELETE CASCADE,
    CONSTRAINT fk_citas_taller FOREIGN KEY (id_sala) REFERENCES Taller (id_sala) ON DELETE CASCADE,
    CONSTRAINT fk_citas_empleado FOREIGN KEY (id_empleado) REFERENCES Empleados (id_empleado),
    CONSTRAINT fk_citas_traje FOREIGN KEY (id_traje) REFERENCES Traje (id_traje) ON DELETE CASCADE
);

CREATE TABLE Cita_Aprendiz (
    id_aprendiz INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT,
    id_empleado INT,
    CONSTRAINT fk_aprendiz_citas FOREIGN KEY (id_cita) REFERENCES Citas
    (id_cita) ON DELETE CASCADE,
    CONSTRAINT fk_aprendiz_empleado FOREIGN KEY (id_empleado) REFERENCES Empleados (id_empleado)
);

INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Mr. Increíble', 'superhéroe', 'superfuerza', 'rojo y azúl');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Elastigirl', 'superheroína', 'elasticidad', 'rojo y blanco');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Dash', 'superhéroe', 'supervelocidad', 'rojo y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Violeta', 'superheroína', 'invisible y campos fuerza', 'rojo y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Síndrome', 'supervillano', 'inventos', 'blanco y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Jack-Jack', 'superhéroe', 'multipoderes', 'rojo y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Frozono', 'superhéroe', 'hielo', 'blanco y azúl claro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('The Underminer', 'supervillano', 'excavar', 'marrón y amarillo');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Bomb Voyage', 'supervillano', 'bombas atómicas', 'blanco y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Rapta-Pantallas', 'supervillana', 'hipnosis', 'negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Vacío', 'superheroína', 'portales', 'verde y azúl');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Helectrix', 'superhéroe', 'electricidad', 'azúl oscuro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Krujido', 'superhéroe', 'telequinesia', 'azúl oscuro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Reflujo', 'superhéroe', 'lava', 'naranja');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Bloque', 'superheroína', 'superfuerza', 'marrón y negro');
INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Silbido', 'superhéroe', 'chillido agudo', 'negro');

INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Lucía', 'Martínez', 'Aguja', 'lucia', 'Lucia2026');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Carlos', 'Ruiz', 'Tijeras', 'carlos', 'Tijeras123');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Ana', 'Torres', 'SastreX', 'ana', 'SastreX2026');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Javier', 'Gómez', 'MaestroModa', 'javier', 'MaestroModa!');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'María', 'Delgado', 'Costurilla', 'maria', 'Costurilla22');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Pablo', 'Herrera', 'HiloFino', 'pablo', 'HiloFino33');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Sofía', 'Navarro', 'PatrónX', 'sofia', 'PatronX44');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Diego', 'Fernández', 'CorteMaestro', 'diego', 'CorteMaestro55');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Elena', 'Rivas', 'DamaAguja', 'elena', 'DamaAguja66');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Tomás', 'Villalba', 'GranSastre', 'tomas', 'GranSastre77');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Laura', 'Sánchez', 'Dedal', 'laura', 'Dedal2026');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Miguel', 'Ortega', 'Puntada', 'miguel', 'Puntada123');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Carmen', 'Vega', 'HiloRojo', 'carmen', 'HiloRojo456');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Álvaro', 'Castro', 'Costurero', 'alvaro', 'Costurero789');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Raquel', 'Molina', 'PatrónPro', 'raquel', 'PatronPro321');
INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Fernando', 'Ibáñez', 'AltaCostura', 'fernando', 'AltaCostura999');

INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Milán', 'diseño');
INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('París', 'diseño');
INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Madrid', 'costura');
INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Nueva York', 'costura');
INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Berlín', 'pruebas');

INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje principal de Mr. Increíble', 'pruebas', 1);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje reforzado para misiones pesadas', 'costura', 1);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje elástico de alta resistencia', 'pruebas', 2);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de infiltración flexible', 'diseño', 2);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje aerodinámico de velocidad', 'costura', 3);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje antifricción para carreras', 'diseño', 3);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de invisibilidad optimizado', 'pruebas', 4);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de campos de fuerza', 'costura', 4);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Armadura tecnológica de Síndrome', 'diseño', 5);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de vuelo mejorado', 'costura', 5);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje ignífugo para Jack-Jack', 'pruebas', 6);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje multirresistente para poderes variables', 'diseño', 6);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje térmico de hielo', 'costura', 7);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje anti-condensación', 'diseño', 7);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de excavación subterránea', 'diseño', 8);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje blindado anti-derrumbes', 'costura', 8);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje explosivo reforzado', 'pruebas', 9);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje ignífugo anti-detonación', 'diseño', 9);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de hipnosis visual', 'costura', 10);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de camuflaje digital', 'diseño', 10);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje estabilizador de portales', 'pruebas', 11);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje anti-distorsión espacial', 'costura', 11);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje conductor de electricidad', 'costura', 12);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje aislante de alto voltaje', 'diseño', 12);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de concentración telequinética', 'diseño', 13);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje reforzado para control mental', 'costura', 13);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje resistente a lava', 'pruebas', 14);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje térmico de contención', 'diseño', 14);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de superfuerza reforzado', 'pruebas', 15);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de impacto pesado', 'costura', 15);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de protección auditiva', 'costura', 16);
INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje amplificador de ondas sonoras', 'diseño', 16);

INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '09:00', 1, 1, 3, 4, 1);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '10:00', 1, 2, 1, 3, 3);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '11:00', 1, 3, 3, 4, 5);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '12:00', 1, 4, 5, 7, 7);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '15:00', 1, 5, 1, 8, 9);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '16:00', 1, 8, 4, 8, 15);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '17:00', 1, 9, 4, 8, 17);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '09:00', 1, 6, 5, 3, 11);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '10:00', 1, 7, 3, 7, 13);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '11:00', 1, 11, 5, 7, 21);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '12:00', 1, 12, 3, 9, 24);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '15:00', 1, 10, 1, 8, 19);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '16:00', 1, 16, 4, 8, 31);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '09:00', 1, 13, 3, 7, 26);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '10:00', 1, 14, 5, 7, 27);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '11:00', 1, 15, 4, 10, 30);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '15:00', 1, 5, 1, 8, 10);
INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '16:00', 1, 8, 4, 8, 16);

INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (1, 1);
INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (2, 1);
INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (3, 2);
INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (5, 1);
INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (6, 2);
INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (10, 1);