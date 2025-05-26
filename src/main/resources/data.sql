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

