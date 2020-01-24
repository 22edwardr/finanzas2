#02_c_usuario.sql

use my_finances_db;

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
	id					BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	email				VARCHAR(50)		NOT NULL UNIQUE,
	nombre				VARCHAR(100)	NOT NULL,
	password			VARCHAR(100)	NOT NULL,
    enabled    			BIT(1)          NOT NULL,
	pregunta1			VARCHAR(100)	NULL,
	pregunta2			VARCHAR(100)	NULL,
	respuesta1			VARCHAR(100)	NULL,
	respuesta2			VARCHAR(100)	NULL,
	foto				VARCHAR(100)	NULL,
	token_confirmacion	VARCHAR(10)		NULL,
	fecha_creacion		DATETIME		NOT NULL
);

select 'Tabla usuario creada';

#03_c_rol.sql

use my_finances_db;

drop table if exists rol;
select 'Tabla rol eliminada';


create table rol(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	rol			VARCHAR(100)	NOT NULL,
	FOREIGN KEY rol_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

select 'Tabla rol creada';

#04_c_fuente.sql

use my_finances_db;

drop table if exists movimiento;
drop table if exists fuente;
select 'Tabla fuente eliminada';


create table fuente(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	nomeclatura VARCHAR(5)		NOT NULL,
	estado		BIT(1)			NOT NULL,
	nombre		VARCHAR(100)	NULL,
	color		VARCHAR(7)		NULL,
	FOREIGN KEY fuente_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE usuario_nomeclatura_u(usuario_id,nomeclatura)
);

select 'Tabla fuente creada';

#05_c_categoria.sql

use my_finances_db;

drop table if exists movimiento;
drop table if exists categoria;
select 'Tabla categoria eliminada';


create table categoria(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	nomeclatura VARCHAR(5)		NOT NULL,
	tipo		VARCHAR(1)		NOT NULL,
	estado		BIT(1)			NOT NULL,
	nombre		VARCHAR(100)	NULL,
	descripcion	VARCHAR(100)	NULL,
	color		VARCHAR(7)		NULL,
	FOREIGN KEY categoria_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE usuario_nomeclatura_u(usuario_id,nomeclatura),
	CONSTRAINT tipo_categoria_ck CHECK (tipo in('D','C'))
);

select 'Tabla categoria creada';

#06_c_meta.sql

use my_finances_db;

drop table if exists meta;
select 'Tabla meta eliminada';


create table meta(
	id				BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id		BIGINT			NOT NULL,
	nombre 			VARCHAR(100)	NOT NULL,
	tipo			VARCHAR(1)		NOT NULL,
	valor			NUMERIC(18,2)	NOT NULL,
	fecha_inicial	DATE			NOT NULL,
	fecha_final		DATE			NOT NULL,
	descripcion		VARCHAR(100)	NULL,
	FOREIGN KEY meta_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT tipo_meta CHECK (tipo in ('A','G')),
	CONSTRAINT fecha_meta CHECK (fecha_final >= fecha_inicial)
);

select 'Tabla meta creada';

#07_c_movimiento.sql

use my_finances_db;

drop table  if exists movimiento;
select 'Tabla movimiento eliminada';


create table movimiento(
	id				BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id		BIGINT			NOT NULL,
	fuente_id		BIGINT			NOT NULL,
	categoria_id	BIGINT			NOT NULL,
	valor			NUMERIC(18,2)	NOT NULL,
	fecha			DATE			NOT NULL,
	descripcion		VARCHAR(100)	NULL,
	FOREIGN KEY movimiento_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY movimiento_fuente_fk(fuente_id) REFERENCES fuente(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY movimiento_categoria_fk(categoria_id) REFERENCES categoria(id) ON DELETE CASCADE ON UPDATE CASCADE
);

select 'Tabla movimiento creada';

#08_c_producto.sql

use my_finances_db;

drop table if exists cotizacion;
drop table if exists producto;
select 'Tabla producto eliminada';


create table producto(
	id				BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id		BIGINT			NOT NULL,
	nombre 			VARCHAR(100)	NOT NULL,
	unidad_medicion	VARCHAR(1)		NOT NULL,
	FOREIGN KEY producto_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT unidad_medicion CHECK (unidad_medicion in ('P','C','T','V'))
);

select 'Tabla producto creada';

#09_c_cotizacion.sql

use my_finances_db;

drop table if exists cotizacion;
select 'Tabla cotizacion eliminada';


create table cotizacion(
	id				BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	producto_id		BIGINT			NOT NULL,
	nombre 			VARCHAR(100)	NOT NULL,
	valor			NUMERIC(22,6)	NOT NULL,
	FOREIGN KEY cotizacion_producto_fk(producto_id) REFERENCES producto(id) ON DELETE CASCADE ON UPDATE CASCADE
);

select 'Tabla cotizacion creada';
