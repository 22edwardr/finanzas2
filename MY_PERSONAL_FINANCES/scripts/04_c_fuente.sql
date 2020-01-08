use my_finances_db
go

drop table if exists movimiento;
drop table if exists fuente;
select 'Tabla fuente eliminada';


create table fuente(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	nomeclatura VARCHAR(5)		NOT NULL,
	nombre		VARCHAR(100)	NULL,
	color		VARCHAR(8)		NULL,
	FOREIGN KEY fuente_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	UNIQUE usuario_nomeclatura_u(usuario_id,nomeclatura)
);

select 'Tabla fuente creada';
go