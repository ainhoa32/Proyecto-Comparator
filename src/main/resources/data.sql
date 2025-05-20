INSERT IGNORE INTO usuarios (nombre, contrasena) VALUES
('agustin', '$2a$10$DCxy6CvvetDKc.vCA5wxQOJWhPquC0IC6ZBv2bMIwqzu.Wmx7QFn2'),
('ana', '$2a$10$gNrybyKCMt.2Nsmlxh9/zO65ArED7DqD0LTsZs8qJUF6cUXHYtJrO'),
('juanito', '$2a$10$sQ9lYOdbl2XIZV6SiocaV.bB9E.R0SpMl6qQtv61VNty/n.mJD5e2'),
('kevin', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe'),
('ainhoa', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe');

INSERT IGNORE INTO productos (
    nombre, precio, precioGranel, tamanoUnidad, unidadMedida,
    indice, urlImagen, prioridad, supermercado, busqueda_id
)
VALUES
    ('Arroz Integral', 20.50, 18.00, 1.00, 'kg', 1, 'https://ejemplo.com/imagen1.jpg', 1, 'Supermercado A', 101),
    ('Aceite de Oliva', 55.00, 50.00, 0.75, 'L', 2, 'https://ejemplo.com/imagen2.jpg', 2, 'Supermercado B', 102),
    ('Leche Deslactosada', 18.90, 17.00, 1.00, 'L', 3, 'https://ejemplo.com/imagen3.jpg', 0, 'Supermercado C', 103),
    ('Frijoles Negros', 22.30, 20.00, 0.90, 'kg', 4, 'https://ejemplo.com/imagen4.jpg', 1, 'Supermercado A', 104),
    ('Pan Integral', 25.00, 0.00, 0.50, 'kg', 5, 'https://ejemplo.com/imagen5.jpg', 3, 'Supermercado D', 105);

INSERT IGNORE INTO favoritos (usuario_id, nombre)
VALUES
    (1, 'Aceite de Oliva'),
    (1, 'Leche Deslactosada'),
    (1, 'Pan Integral'),
    (1, 'Arroz Integral'),
    (1, 'Frijoles Negros'),

    (2, 'Miel Natural'),
    (2, 'Queso Fresco'),
    (2, 'Harina de Trigo'),
    (2, 'Tomates Cherry'),
    (2, 'Espinacas'),

    (3, 'Avena'),
    (3, 'Yogur Natural'),
    (3, 'Zanahorias'),
    (3, 'Papas'),
    (3, 'Cebolla'),

    (4, 'Manzanas'),
    (4, 'Naranjas'),
    (4, 'Lechuga'),
    (4, 'Pepino'),
    (4, 'Calabacín'),

    (5, 'Pollo'),
    (5, 'Pescado'),
    (5, 'Carne de Res'),
    (5, 'Huevos'),
    (5, 'Jamón');

INSERT IGNORE INTO busquedas (nombre_busqueda, producto_id)
VALUES
    ('Buscar arroz integral', 1),
    ('Buscar aceite de oliva', 2),
    ('Buscar leche deslactosada', 3),
    ('Buscar frijoles negros', 4),
    ('Buscar pan integral', 5);

select * from favoritos WHERE nombre like 'chocolate';

DELETE FROM favoritos WHERE nombre like 'chocolate';
