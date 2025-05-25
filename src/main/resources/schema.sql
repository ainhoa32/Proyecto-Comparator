create database if not exists comparador_de_precios;
use comparador_de_precios;
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    esAdmin int default 0 not null
    );

CREATE TABLE IF NOT EXISTS listasPredeterminadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    precioGranel DECIMAL(10,2) NOT NULL,
    tamanoUnidad DECIMAL(10,2),
    unidadMedida VARCHAR(10),
    indice INT,
    urlImagen TEXT,
    prioridad INT DEFAULT 0,
    supermercado VARCHAR(100),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    busqueda_id INT
    );

CREATE TABLE IF NOT EXISTS busquedas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_busqueda VARCHAR(255) NOT NULL,
    fecha_busqueda DATETIME DEFAULT CURRENT_TIMESTAMP,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
    );


CREATE TABLE IF NOT EXISTS lista_Productos (
    lista_id INT NOT NULL,
    producto_id INT NOT NULL,
    PRIMARY KEY (lista_id, producto_id),
    FOREIGN KEY (lista_id) REFERENCES listasPredeterminadas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
    );

CREATE TABLE IF NOT EXISTS cesta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
    );

CREATE TABLE IF NOT EXISTS favoritos (
     id INT AUTO_INCREMENT PRIMARY KEY,
     usuario_id INT NOT NULL,
     nombre varchar(255) not null,
     FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
    );

CREATE TABLE if not exists cesta_productos (
    cesta_id INT NOT NULL,
    producto_id INT NOT NULL,
    PRIMARY KEY (cesta_id, producto_id),
    FOREIGN KEY (cesta_id) REFERENCES cesta(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);
