use my_finances_db
go

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
go