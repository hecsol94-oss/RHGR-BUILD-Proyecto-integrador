
DROP TABLE IF EXISTS Citas;
DROP TABLE IF EXISTS Traje;
DROP TABLE IF EXISTS Empleados;
DROP TABLE IF EXISTS Taller;
DROP TABLE IF EXISTS Cliente;

CREATE TABLE Cliente(
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
    contrasena VARCHAR(30)
);

CREATE TABLE Taller (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sala VARCHAR(30),
    tipo_sala VARCHAR(30)
);

CREATE TABLE Traje (
    id_traje INT AUTO_INCREMENT PRIMARY KEY,
    nombre_traje VARCHAR(30), 
    estado VARCHAR(30),
    id_cliente INT,
    CONSTRAINT fk_traje_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora_inicio TIME,
    duracion INT,
    id_cliente INT,
    id_sala INT,
    CONSTRAINT fk_citas_cliente  FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    CONSTRAINT fk_citas_taller FOREIGN KEY (id_sala) REFERENCES Taller(id_sala)
);
