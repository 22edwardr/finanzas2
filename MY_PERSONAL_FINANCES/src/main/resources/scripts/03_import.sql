use my_finances_db;

delete from usuario where id in (1,2);

insert into usuario(id, email, nombre, password, pregunta1, pregunta2, respuesta1, respuesta2, enabled, fecha_creacion) values (1,'a@hotmail.com','Miguel Martinez','$2a$10$C33v390RRes2c9Jpu6GD9.lgr3PMQr.UKD.4UJpQZKUoQkhSjWvHe','Cual es mi animal favorito','En que año naci','Tigre','1956',1, NOW());
insert into usuario(id, email, nombre, password, pregunta1, pregunta2, respuesta1, respuesta2, enabled, fecha_creacion) values (2,'b@gmail.com','Edward Robayo','$2a$10$bBTKl2wMnXDomTV1aYvDYOCLHrua.jnXvsaaZRVmzf5iMUOR5eVAK','Cual es mi color favorito','Nombre de mejor amigo','Azul','Ninguno',1, NOW());

insert into rol(usuario_id,rol) values (1,'ROLE_USUARIO');
insert into rol(usuario_id,rol) values (2,'ROLE_USUARIO');
insert into rol(usuario_id,rol) values (2,'ROLE_ADMINISTRADOR');

insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (1,1,'VIV','C',1,'Vivienda','Lo del arriendo','#345678');
insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (2,1,'ALI','C',1,'Alimentacion','Mercado a la lata','#002255');
insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (3,1,'SUE','D',1,'Sueldo','Mi trabajito','#669977');
insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (4,2,'HOS','C',0,'Hospedaje',NULL,'#345678');
insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (5,2,'ALI','C',1,'Alimentacion',NULL,'#002255');
insert into categoria(id,usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color) values (6,2,'TRA','D',1,'Trabajo',NULL,'#998822');

insert into fuente(id,usuario_id,nomeclatura,estado,nombre,color) values (1,1,'CAH',1,'Cuenta de ahorros','#345678');
insert into fuente(id,usuario_id,nomeclatura,estado,nombre,color) values (2,2,'CAM',1,'Caja menor','#345678');
insert into fuente(id,usuario_id,nomeclatura,estado,nombre,color) values (3,2,'CDT',1,NULL,'#002255');
insert into fuente(id,usuario_id,nomeclatura,estado,nombre,color) values (4,2,'CAH',1,'Cuenta de ahorros','#998822');

insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,2,9500,'Ejecutivo','2019-01-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,1,550000,'Arriendo','2019-01-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,3,990500,'Pago de nomina','2019-01-31');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,2,9500,'Ejecutivo','2019-02-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,1,550000,'Arriendo','2019-02-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,3,990500,'Pago de nomina','2019-02-27');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,2,9500,'Ejecutivo','2020-01-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,1,550000,'Arriendo','2020-01-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,3,990500,'Pago de nomina','2020-01-31');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,2,9500,'Ejecutivo','2020-02-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,1,550000,'Arriendo','2020-02-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (1,1,3,990500,'Pago de nomina','2020-02-27');

insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,5,9500,'Ejecutivo','2019-01-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,4,550000,'Arriendo','2019-01-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,6,990500,'Pago de nomina','2019-01-31');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,5,9500,'Ejecutivo','2019-02-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,4,550000,'Arriendo','2019-02-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,6,990500,'Pago de nomina','2019-02-27');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,5,9500,'Ejecutivo','2020-01-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,4,550000,'Arriendo','2020-01-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,6,990500,'Pago de nomina','2020-01-31');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,5,9500,'Ejecutivo','2020-02-06');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,4,550000,'Arriendo','2020-02-15');
insert into movimiento(usuario_id,fuente_id,categoria_id,valor,descripcion,fecha) values (2,4,6,990500,'Pago de nomina','2020-02-27');

insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (1,'Enero 2020','A',300000,'Para el viaje','2020-01-01','2020-01-31'); 
insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (1,'Febrero 2020','A',300000,'Para el viaje','2020-02-01','2020-02-28'); 
insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (1,'2020','A',9000000,'Gastos poquitos','2020-01-01','2020-12-31'); 
insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (2,'Enero 2020','A',450000,'Para el viaje','2020-01-01','2020-01-31'); 
insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (2,'Febrero 2020','A',450000,'Para el viaje','2020-02-01','2020-02-28'); 
insert into meta(usuario_id,nombre,tipo,valor,descripcion,fecha_inicial,fecha_final) values (2,'2020','A',7000000,'Gastos poquitos','2020-01-01','2020-12-31');

insert into producto(id,usuario_id,nombre,unidad_medicion) values (1,1,'Empanada','C');
insert into producto(id,usuario_id,nombre,unidad_medicion) values (2,1,'Leche','V');
insert into producto(id,usuario_id,nombre,unidad_medicion) values (3,2,'Arroz','P');
insert into producto(id,usuario_id,nombre,unidad_medicion) values (4,2,'Xbox','T');

insert into cotizacion(producto_id,nombre,valor) values (1,'De la esquina', 1000);
insert into cotizacion(producto_id,nombre,valor) values (1,'Exito', 1500);
insert into cotizacion(producto_id,nombre,valor) values (1,'Oxxo', 2000);
insert into cotizacion(producto_id,nombre,valor) values (1,'Ara Parmalat', 3500);
insert into cotizacion(producto_id,nombre,valor) values (1,'Exito Colanta', 3000);
insert into cotizacion(producto_id,nombre,valor) values (1,'Exito Alqueria', 3200);
insert into cotizacion(producto_id,nombre,valor) values (2,'Diana', 4726.2);
insert into cotizacion(producto_id,nombre,valor) values (2,'Huila', 5102.61);
insert into cotizacion(producto_id,nombre,valor) values (2,'Rozudo', 1235.13);
insert into cotizacion(producto_id,nombre,valor) values (2,'Game hour', 3500);
insert into cotizacion(producto_id,nombre,valor) values (2,'De la esquina', 2600);
insert into cotizacion(producto_id,nombre,valor) values (2,'Centro mayor', 5200);