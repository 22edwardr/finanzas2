use my_finances_db
go

drop table if exists movimiento;
drop table if exists categoria;
select 'Tabla categoria eliminada';


create table categoria(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	nomeclatura VARCHAR(5)		NOT NULL,
	tipo		VARCHAR(1)		NOT NULL,
	nombre		VARCHAR(100)	NULL,
	descripcion	VARCHAR(100)	NULL,
	color		VARCHAR(8)		NULL,
	FOREIGN KEY categoria_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE usuario_nomeclatura_u(usuario_id,nomeclatura),
	CONSTRAINT tipo_categoria_ck CHECK (tipo in('D','C'))
);

select 'Tabla categoria creada';
go