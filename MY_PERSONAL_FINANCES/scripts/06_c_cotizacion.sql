use my_finances_db
go

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
go