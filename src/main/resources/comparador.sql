CREATE TABLE IF NOT EXISTS Usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) UNIQUE NOT NULL,
contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Cesta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_prods VARCHAR(255),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
);

CREATE TABLE IF NOT EXISTS Productos (
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
busqueda_id INT,
CONSTRAINT fk_busqueda FOREIGN KEY (busqueda_id) REFERENCES Busquedas(id)
);


CREATE TABLE IF NOT EXISTS Busquedas (
    id INT AUTO_INCREMENT PRIMARY KEY,
	nombre_busqueda VARCHAR(255) NOT NULL,
    fecha_busqueda DATETIME DEFAULT CURRENT_TIMESTAMP,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);


CREATE TABLE IF NOT EXISTS ListasPredeterminadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Lista_Productos (
    lista_id INT NOT NULL,
    producto_id INT NOT NULL,
    PRIMARY KEY (lista_id, producto_id),
    FOREIGN KEY (lista_id) REFERENCES ListasPredeterminadas(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

CREATE TABLE IF NOT EXISTS Favoritos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    producto_id INT NOT NULL,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

INSERT INTO Usuarios (nombre, contrasena) VALUES
('agustin', '$2a$10$DCxy6CvvetDKc.vCA5wxQOJWhPquC0IC6ZBv2bMIwqzu.Wmx7QFn2'),
('ana', '$2a$10$gNrybyKCMt.2Nsmlxh9/zO65ArED7DqD0LTsZs8qJUF6cUXHYtJrO'),
('juanito', '$2a$10$sQ9lYOdbl2XIZV6SiocaV.bB9E.R0SpMl6qQtv61VNty/n.mJD5e2'),
('kevin', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe');
