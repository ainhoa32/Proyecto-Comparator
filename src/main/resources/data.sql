INSERT IGNORE INTO usuarios (nombre, contrasena, esAdmin) VALUES
('agustin@ejemplo.com', '$2a$10$DCxy6CvvetDKc.vCA5wxQOJWhPquC0IC6ZBv2bMIwqzu.Wmx7QFn2', 0),
('pedro@ejemplo.com', '$2a$10$gNrybyKCMt.2Nsmlxh9/zO65ArED7DqD0LTsZs8qJUF6cUXHYtJrO',0),
('javier@ejemplo.com', '$2a$10$sQ9lYOdbl2XIZV6SiocaV.bB9E.R0SpMl6qQtv61VNty/n.mJD5e2',0),
('kevin@ejemplo.com', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe',1),
('ainhoa@ejemplo.com', '$2a$10$RSKzsbNyH7xy.1w4VWbGPewBmMO4iNjKpjTSkWDL0fcBhD7T8Oowe',1);

INSERT IGNORE INTO listaspredeterminadas (id, nombre, esVisible) VALUES
(1, 'Despensa Básica',0),
(2, 'Productos de Limpieza',0),
(3, 'Cuidado Personal',0),
(4, 'Snacks Favoritos',0),
(5, 'Productos de Belleza',0);


INSERT ignore INTO productos (nombre, precio, precioGranel, tamanoUnidad, unidadMedida, supermercado, urlImagen, indice) VALUES
                                                                                                                      ('Arroz', 22.50, 20.00, 1.00, 'kg', 'Bodega Aurrera', 'https://example.com/arroz.jpg', 1),
                                                                                                                      ('Frijoles', 28.00, 25.00, 1.00, 'kg', 'Bodega Aurrera', 'https://example.com/frijoles.jpg', 2),
                                                                                                                      ('Aceite vegetal', 40.00, 38.00, 1.00, 'L', 'Walmart', 'https://example.com/aceite.jpg', 3),
                                                                                                                      ('Sal', 10.00, 9.50, 1.00, 'kg', 'Walmart', 'https://example.com/sal.jpg', 4),
                                                                                                                      ('Azúcar', 25.00, 23.00, 1.00, 'kg', 'Bodega Aurrera', 'https://example.com/azucar.jpg', 5);

INSERT ignore INTO productos (nombre, precio, precioGranel, tamanoUnidad, unidadMedida, supermercado, urlImagen, indice) VALUES
                                                                                                                      ('Detergente líquido', 55.00, 50.00, 2.00, 'L', 'Soriana', 'https://example.com/detergente.jpg', 6),
                                                                                                                      ('Suavizante', 35.00, 32.00, 2.00, 'L', 'Walmart', 'https://example.com/suavizante.jpg', 7),
                                                                                                                      ('Limpiador multiusos', 30.00, 28.00, 1.00, 'L', 'Soriana', 'https://example.com/limpiador.jpg', 8),
                                                                                                                      ('Cloro', 20.00, 18.00, 1.00, 'L', 'Bodega Aurrera', 'https://example.com/cloro.jpg', 9),
                                                                                                                      ('Esponjas', 18.00, 17.00, 1.00, 'pza', 'Walmart', 'https://example.com/esponjas.jpg', 10);

INSERT ignore INTO productos (nombre, precio, precioGranel, tamanoUnidad, unidadMedida, supermercado, urlImagen, indice) VALUES
                                                                                                                      ('Shampoo', 60.00, 55.00, 0.75, 'L', 'Walmart', 'https://example.com/shampoo.jpg', 11),
                                                                                                                      ('Pasta dental', 22.00, 20.00, 0.15, 'kg', 'Soriana', 'https://example.com/pasta.jpg', 12),
                                                                                                                      ('Jabón corporal', 15.00, 14.00, 1.00, 'pza', 'Walmart', 'https://example.com/jabon.jpg', 13),
                                                                                                                      ('Desodorante', 35.00, 33.00, 0.20, 'kg', 'Bodega Aurrera', 'https://example.com/desodorante.jpg', 14),
                                                                                                                      ('Cepillo de dientes', 20.00, 18.00, 1.00, 'pza', 'Soriana', 'https://example.com/cepillo.jpg', 15);

INSERT ignore INTO productos (nombre, precio, precioGranel, tamanoUnidad, unidadMedida, supermercado, urlImagen, indice) VALUES
                                                                                                                      ('Papas fritas', 17.00, 16.00, 0.15, 'kg', 'Walmart', 'https://example.com/papas.jpg', 16),
                                                                                                                      ('Chocolate', 25.00, 24.00, 0.10, 'kg', 'Soriana', 'https://example.com/chocolate.jpg', 17),
                                                                                                                      ('Galletas', 30.00, 28.00, 0.25, 'kg', 'Walmart', 'https://example.com/galletas.jpg', 18),
                                                                                                                      ('Palomitas para microondas', 12.00, 11.50, 1.00, 'pza', 'Bodega Aurrera', 'https://example.com/palomitas.jpg', 19),
                                                                                                                      ('Refresco', 18.00, 17.00, 2.00, 'L', 'Soriana', 'https://example.com/refresco.jpg', 20);

INSERT ignore INTO productos (nombre, precio, precioGranel, tamanoUnidad, unidadMedida, supermercado, urlImagen, indice) VALUES
                                                                                                                      ('Base de maquillaje', 120.00, 110.00, 0.03, 'L', 'Walmart', 'https://example.com/base.jpg', 21),
                                                                                                                      ('Delineador', 50.00, 48.00, 0.01, 'kg', 'Soriana', 'https://example.com/delineador.jpg', 22),
                                                                                                                      ('Labial', 60.00, 58.00, 0.02, 'kg', 'Bodega Aurrera', 'https://example.com/labial.jpg', 23),
                                                                                                                      ('Desmaquillante', 45.00, 42.00, 0.25, 'L', 'Walmart', 'https://example.com/desmaquillante.jpg', 24),
                                                                                                                      ('Brochas de maquillaje', 70.00, 68.00, 1.00, 'set', 'Soriana', 'https://example.com/brochas.jpg', 25);

INSERT ignore INTO lista_Productos (lista_id, producto_id) VALUES
                                                        (1, 1),
                                                        (1, 2),
                                                        (1, 3),
                                                        (1, 4),
                                                        (1, 5);

INSERT ignore INTO lista_Productos (lista_id, producto_id) VALUES
                                                        (2, 6),
                                                        (2, 7),
                                                        (2, 8),
                                                        (2, 9),
                                                        (2, 10);

INSERT ignore INTO lista_Productos (lista_id, producto_id) VALUES
                                                        (3, 11),
                                                        (3, 12),
                                                        (3, 13),
                                                        (3, 14),
                                                        (3, 15);

INSERT ignore INTO lista_Productos (lista_id, producto_id) VALUES
                                                        (4, 16),
                                                        (4, 17),
                                                        (4, 18),
                                                        (4, 19),
                                                        (4, 20);

INSERT ignore INTO lista_Productos (lista_id, producto_id) VALUES
                                                        (5, 21),
                                                        (5, 22),
                                                        (5, 23),
                                                        (5, 24),
                                                        (5, 25);

