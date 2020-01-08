use my_finances_db
go

drop table if exists movimiento;
drop table if exists cotizacion;
drop table if exists producto;
drop table if exists meta;
drop table if exists categoria;
drop table if exists fuente;
drop table if exists rol;
drop table if exists usuario;
select 'Tabla usuario eliminada';


create table usuario(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	email		VARCHAR(50)		NOT NULL UNIQUE,
	nombre		VARCHAR(100)	NOT NULL,
	password	VARCHAR(100)	NOT NULL,
	pregunta1	VARCHAR(100)	NULL,
	pregunta2	VARCHAR(100)	NULL,
	respuesta1	VARCHAR(100)	NULL,
	respuesta2	VARCHAR(100)	NULL,
	foto		VARCHAR(100)	NULL
);

select 'Tabla usuario creada';
go