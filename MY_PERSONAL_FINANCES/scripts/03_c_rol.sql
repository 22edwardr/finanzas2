use my_finances_db
go

drop table if exists rol;
select 'Tabla rol eliminada';


create table rol(
	id			BIGINT 			PRIMARY KEY AUTO_INCREMENT,
	usuario_id	BIGINT			NOT NULL,
	rol			VARCHAR(100)	NOT NULL,
	FOREIGN KEY rol_usuario_fk(usuario_id) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

select 'Tabla rol creada';
go