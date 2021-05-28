
 -- Este archivo es leido de forma predeterminada por spring, todos los datos de prueba 
 -- deben ser escritos aqui para que puedan ser utilizados durante la ejecucion del proyecto
 -- tomar en cuenta que si esta habilitada la configuracion de create-drop en el archivo properties
 -- estos datos seran eliminados al ejecutar nuevamente el proyecto y se crearan nuevamente.
 -- si se desea crear una tabla debe ser en una sola linea ya que spring toma las lineas como operaciones separadas

INSERT INTO `regiones` (`id`,`nombre`) VALUES (1,'Centroamérica'),(2,'Norteamérica'),(3,'Sudamérica'),(4,'Asia'),(5,'Europa');
INSERT INTO `clientes` (`nombre`,`apellido`,`email`,`created_at`,`region_id`) VALUES ('Franklin','Flores','franklin1@gmail.com','2021-11-02',1),('Franklin225','Flores22','franklin8812@gmail.com','2021-12-02',2),('Fra58nklin2','Flo88res2','franklin812@gmail.com','2021-12-02',3),('Frankl88in2','F22lores2','frankl22in12@gmail.com','2021-12-02',4),('Franklin','Flores','frankl55in1@gmail.com','2021-11-02',1),('Franklin4225','Flores22','franklin82812@gmail.com','2021-12-02',1),('Fra58nklin2','Flo88res2','franklin8312@gmail.com','2021-12-02',2),('Frankl88in2','F22lores2','frankl22in124@gmail.com','2021-12-02',4);
INSERT INTO `usuarios` (username,password,`nombre`,`apellido`,`email`,enabled) VALUES ('franklin','$2a$10$qQm/GfiGxAgNOe2tKVA8PO2iudc9/Qnc3fGVftSDUEXXMZwJGxhWi','Franklin','Flores','franklingranados142@gamil.com',1), ('meybel','$2a$10$xFjdubKtKgpnO5q.EV.yt.n0gJj0PQ48VgDw5kYe.lHGPTsp.atRm','Meybel','Avila','meymercy@gamil.com',1), ('alberto','$2a$10$qNIWG7Qdm52j2deiWBYXce14qnqdHgq4DzsYkdbbhr0.xsND1BECG','Alberto','granados','franklinflores@gamil.com',1); 
-- Los roles deben llevar un prefijo ROLE_ para ser valido y ser escrito en mayusculas
INSERT INTO `roles`(name) VALUES('ROLE_ADMIN'),('ROLE_SALES');
INSERT INTO `usuarios_roles`(user_id,role_id) VALUES (1,1),(2,1),(2,2),(3,2);
INSERT INTO `productos`(nombre,precio,created_at) VALUES ('MOUSE LOGITECH','30.99',NOW()),('CAMARA TCON LOGITECH','300.95',NOW()),('AUDIFONOS MAXEL PRO','20.99',NOW());
INSERT INTO `facturas` (`created_at`, `descripcion`, `observacion`, `cliente_id`) VALUES (NOW(), 'venta al por menor/mayor', 'mucha mercaderia', '1'),(NOW(), 'venta al por menor', NULL, '2');
INSERT INTO `facturas_items` (`cantidad`, `producto_id`, `factura_id`) VALUES (22,1,1),(33,2,1),(5,3,2);