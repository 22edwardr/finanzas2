use my_finances_db
go

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
go