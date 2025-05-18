INSERT IGNORE INTO Usuarios (nombre, contrasena) VALUES
('agustin', '$2a$10$DCxy6CvvetDKc.vCA5wxQOJWhPquC0IC6ZBv2bMIwqzu.Wmx7QFn2'),
('ana', '$2a$10$gNrybyKCMt.2Nsmlxh9/zO65ArED7DqD0LTsZs8qJUF6cUXHYtJrO'),
('juanito', '$2a$10$sQ9lYOdbl2XIZV6SiocaV.bB9E.R0SpMl6qQtv61VNty/n.mJD5e2'),
('kevin', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe');




select * from favoritos;

INSERT IGNORE INTO Productos (
    nombre, precio, precioGranel, tamanoUnidad, unidadMedida,
    indice, urlImagen, prioridad, supermercado, busqueda_id
)
VALUES
    ('Arroz Integral', 20.50, 18.00, 1.00, 'kg', 1, 'https://ejemplo.com/imagen1.jpg', 1, 'Supermercado A', 101),
    ('Aceite de Oliva', 55.00, 50.00, 0.75, 'L', 2, 'https://ejemplo.com/imagen2.jpg', 2, 'Supermercado B', 102),
    ('Leche Deslactosada', 18.90, 17.00, 1.00, 'L', 3, 'https://ejemplo.com/imagen3.jpg', 0, 'Supermercado C', 103),
    ('Frijoles Negros', 22.30, 20.00, 0.90, 'kg', 4, 'https://ejemplo.com/imagen4.jpg', 1, 'Supermercado A', 104),
    ('Pan Integral', 25.00, 0.00, 0.50, 'kg', 5, 'https://ejemplo.com/imagen5.jpg', 3, 'Supermercado D', 105);

INSERT IGNORE INTO Favoritos (usuario_id, producto_id)
VALUES
    (1, 2),
    (2, 5),
    (3, 1),
    (4, 3),
    (1, 4);

INSERT IGNORE INTO Busquedas (nombre_busqueda, producto_id)
VALUES
    ('Buscar arroz integral', 1),
    ('Buscar aceite de oliva', 2),
    ('Buscar leche deslactosada', 3),
    ('Buscar frijoles negros', 4),
    ('Buscar pan integral', 5);

SELECT * FROM Productos;

