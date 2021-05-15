
 -- Este archivo es leido de forma predeterminada por spring, todos los datos de prueba 
 -- deben ser escritos aqui para que puedan ser utilizados durante la ejecucion del proyecto
 -- tomar en cuenta que si esta habilitada la configuracion de create-drop en el archivo properties
 -- estos datos seran eliminados al ejecutar nuevamente el proyecto y se crearan nuevamente.
 -- si se desea crear una tabla debe ser en una sola linea ya que spring toma las lineas como operaciones separadas

INSERT INTO `regiones` (`id`,`nombre`) VALUES (1,'Centroamérica'),(2,'Norteamérica'),(3,'Sudamerica'),(4,'Asia'),(5,'Europa');
INSERT INTO `clientes` (`nombre`,`apellido`,`email`,`created_at`,`region_id`) VALUES ('Franklin','Flores','franklin1@gmail.com','2021-11-02',1),('Franklin225','Flores22','franklin8812@gmail.com','2021-12-02',2),('Fra58nklin2','Flo88res2','franklin812@gmail.com','2021-12-02',3),('Frankl88in2','F22lores2','frankl22in12@gmail.com','2021-12-02',4),('Franklin','Flores','frankl55in1@gmail.com','2021-11-02',1),('Franklin4225','Flores22','franklin82812@gmail.com','2021-12-02',1),('Fra58nklin2','Flo88res2','franklin8312@gmail.com','2021-12-02',2),('Frankl88in2','F22lores2','frankl22in124@gmail.com','2021-12-02',4);
