use my_finances_db
go

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
go