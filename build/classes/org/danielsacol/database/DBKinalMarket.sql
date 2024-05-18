set global time_zone = '-6:00';
create database DBKinalMarket;
use DBKinalMarket;

create table Clientes (
	codigoCliente int not null,
	NITCliente varchar (10),
	nombreCliente varchar (50),
	apellidosCliente varchar (50),
	direccionCliente varchar(150),
	telefonoCliente varchar (45),
	correoCliente varchar (45),
	primary key PK_CodigoCliente(codigoCliente)
	);
    
 create table Proveedores (
    codigoProveedor int not null,
    NITProveedor varchar(10),
    nombresProveedor varchar(50),
    apellidosProveedor varchar(50),
    direccionProveedor varchar(150),
    razonSocial varchar(100),
    contactoPrincipal varchar(100),
    paginaWeb varchar(100),
    primary key PK_CodigoProveedor(codigoProveedor)
);   
    

create table CargoEmpleado (
	codigoCargoEmpleado int,
    nombreCargo varchar (45),
    descripcionCargo varchar (45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table TipoProducto(
	codigoTipoProducto int not null,
    descripcion varchar (45),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60)  ,
    totalDocumento decimal(10,2),
    primary key PK_NumeroDocumento(numeroDocumento)
);

create table Productos(
	codigoProducto varchar(15),
    descripcionProducto VARCHAR(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int,
    codigoProveedor int,
    codigoTipoProducto int,
    constraint FK_Productos_Provedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor),
    constraint FK_Productos_TipoProducto foreign key TipoProducto(codigoTipoProducto)
		references TipoProducto(codigoTipoProducto)    
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    constraint FK_TelefonoProveedor_Proveedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	codigoEmailProveedor int,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    constraint FK_EmailProveedor_Proveedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table Empleados(
	codigoEmpleado int,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    constraint FK_Empleados_CargoEmpleado foreign key CargoEmpleado(codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);

delimiter $$

create procedure sp_agregarCliente(in codCli int, in nitCli varchar(10), in nombreCli varchar(50), in apellidosCli varchar(50),in direccionCli varchar(150), in telefonoCli varchar(45), in correoCli varchar(45)
)
begin
	insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
    values (codCli, nitCli, nombreCli, apellidosCli, direccionCli, telefonoCli, correoCli);
end$$

delimiter ;

call sp_agregarCliente('2','78542585', 'Daniel', 'Sacol', 'San jose Pinula', '33815217', 'danieledusc@gmail.com'); 

delimiter $$
create procedure sp_listarCliente()
begin 
	select
    c.codigoCliente,
    c.NITCliente,
	c.apellidosCliente,
    c.nombreCliente,
    c.direccionCliente,
    c.telefonoCliente,
    c.correoCliente
    from clientes c;
    end $$
delimiter ;

call sp_listarCliente();

delimiter $$

create procedure sp_buscarCliente(in nitCli varchar(10))
begin
   select
    c.codigoCliente,
    c.NITCliente,
	c.apellidosCliente,
    c.nombreCliente,
    c.direccionCliente,
    c.telefonoCliente,
    c.correoCliente
    from clientes c where NITCliente = nitCli;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarCliente(
    in codCli int, in nitCli varchar(10), in nombreCli varchar(50),
    in apellidosCli varchar(50), in direccionCli varchar(150), in telefonoCli varchar(45),
    in correoCli varchar(45)
)
begin
    update Clientes
    set
		NITCliente = nitcli,
        nombreCliente = nombrecli,
        apellidosCliente = apellidoscli,
        direccionCliente = direccioncli,
        telefonoCliente = telefonocli,
        correoCliente = correocli
    where
        codigoCliente = codCli;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarCliente(in codCli int)
begin
    delete from Clientes where codigoCliente = codCli;
end$$

delimiter ;

call sp_eliminarCliente(2);


-- PROVEEDORES
delimiter $$
create procedure sp_agregarProveedor(in codProv INT, in nitProv VARCHAR(10), in nombresProv VARCHAR(50), in apellidosProv VARCHAR(50),
    in direccionProv VARCHAR(150), in razonSocial VARCHAR(100), in contactoPrincipal VARCHAR(100), in paginaWeb VARCHAR(100)
)
begin
    insert into Proveedores (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (codProv, nitProv, nombresProv, apellidosProv, direccionProv, razonSocial, contactoPrincipal, paginaWeb);
END$$
DELIMITER ;

CALL sp_agregarProveedor(1, '1234567890', 'Proveedor Ejemplo', 'Apellidos Ejemplo', 'Dirección Ejemplo', 'Razón Social Ejemplo', 'Contacto Ejemplo', 'www.proveedorejemplo.com');
delimiter $$
create procedure sp_listarProveedor()
begin 
	select
    p.codigoProveedor,
    p.NITProveedor,
	p.nombresProveedor,
    p.apellidosProveedor,
    p.direccionProveedor,
    p.razonSocial,
    p.contactoPrincipal,
    p.paginaWeb
    from Proveedores p;
end $$
delimiter ;

call sp_listarProveedor(); 
delimiter $$
create procedure sp_buscarProveedor(in codigoProv int)
begin
   select
    p.codigoProveedor,
    p.NITProveedor,
    p.nombresProveedor,
    p.apellidosProveedor,
    p.direccionProveedor,
    p.razonSocial,
    p.contactoPrincipal,
    p.paginaWeb
    from Proveedores p where codigoProveedor = codigoProv;
end$$
delimiter ;

delimiter $$
create procedure sp_actualizarProveedor(
    in codProv int, in nitProv varchar(10), in nombresProv varchar(50),
    in apellidosProv varchar(50), in direccionProv varchar(150), 
    in razonSocialProv varchar(100), in contactoPrincipalProv varchar(100),
    in paginaWebProv varchar(100)
)
begin
    update Proveedores
    set
        NITProveedor = nitProv,
        nombresProveedor = nombresProv,
        apellidosProveedor = apellidosProv,
        direccionProveedor = direccionProv,
        razonSocial = razonSocialProv,
        contactoPrincipal = contactoPrincipalProv,
        paginaWeb = paginaWebProv
    where
        codigoProveedor = codProv;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarProveedor(in codProv int)
begin
    delete from Proveedores where codigoProveedor = codProv;
end$$
delimiter ;


-- CARGO EMPLEADO

delimiter $$

create procedure sp_agregarCargoEmpleado(in codCargo int, in nomCargo varchar(45), in descripCargo varchar(45)
)
begin
    insert into CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo)
    values (codCargo, nomCargo, descripCargo);
end$$

delimiter ;

call sp_agregarCargoEmpleado(1, 'Limpieza', 'Limpiar');

delimiter $$
create procedure sp_listarCargoEmpleado()
begin
    select CE.codigoCargoEmpleado, CE.nombreCargo, CE.descripcionCargo  from CargoEmpleado CE;
end$$

delimiter ;

delimiter $$
create procedure sp_buscarCargoEmpleado(in codCargo int)
begin
    select CE.codigoCargoEmpleado, CE.nombreCargo, CE.descripcionCargo  from CargoEmpleado CE where codigoCargoEmpleado = codCargo;
end$$

delimiter ;
delimiter $$
create procedure sp_actualizarCargoEmpleado(
    in codCargo int, in nombCargo varchar(45), in descripCargo varchar(45)
)
begin
    update CargoEmpleado
    set
        nombreCargo = nombCargo,
        descripcionCargo = descripCargo
    where
        codigoCargoEmpleado = codCargo;
end$$

delimiter ;
delimiter $$
create procedure sp_eliminarCargoEmpleado(in codCargo int)
begin
    delete from CargoEmpleado where codigoCargoEmpleado = codCargo;
end$$

delimiter ;

-- COMPRAS
delimiter $$

create procedure sp_agregarCompra(in numDoc int, in fechaDocument date, in descrip varchar(60), in totalDocument decimal(10,2))
begin
    insert into Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    values (numDoc, fechaDocument, descrip, totalDocument);
end$$

delimiter ;

CALL sp_agregarCompra(123456, '2024-05-04', 'Compra de suministros', 500.00);
delimiter $$
create procedure sp_listarCompras()
begin
    select C.numeroDocumento, C.fechaDocumento, C.descripcion, C.totalDocumento from Compras C;
end$$

delimiter ;

call sp_listarCompras();
delimiter $$
create procedure sp_buscarCompra(in numDoc int)
begin
    select C.numeroDocumento, C.fechaDocumento, C.descripcion, C.totalDocumento from Compras C where numeroDocumento = numDoc;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarCompra(in numDoc int, in fechaDocument date, in descrip varchar(60), in totalDocument decimal(10,2))
begin
    update Compras
    set
        fechaDocumento = fechaDocument,
        descripcion = descrip,
        totalDocumento = totalDocument
    where
        numeroDocumento = numDoc;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarCompra(in numDoc int)
begin
    delete from Compras where numeroDocumento = numDoc;
end$$

delimiter ;

-- TIPO DE PRODUCTO
delimiter $$
create procedure sp_agregarTipoProducto(in codTipoProducto int ,in descr varchar(45))
begin
	insert into TipoProducto (codigoTipoProducto, descripcion)
    values(codTipoProducto, descr);
end $$
delimiter ;

call sp_agregarTipoProducto(1, 'Tipo Ejemplo');

delimiter $$
create procedure sp_listarTipoProducto()
begin
	select
	TP.codigoTipoProducto,
    TP.descripcion
    from TipoProducto TP ;
end $$
delimiter ;

call sp_listarTipoProducto();

delimiter $$
create procedure sp_buscarTipoProducto(in codTipoProducto int)
begin
	select
	TP.codigoTipoProducto,
    TP.descripcion
    from TipoProducto TP
    where codigoTipoProducto=codTipoProducto;
end $$
delimiter ;


delimiter $$
create procedure sp_eliminarTipoProducto(in codTipoProducto int)
begin
	delete from TipoProducto 
    where TipoProducto.codigoTipoProducto=codTipoProducto;
end $$
delimiter ;


delimiter $$
create procedure sp_actualizarTipoProducto(in codTipoProducto int,in descr varchar(45))
begin
	update TipoProducto 
	set 
		TipoProducto.descripcion=descr
    where
		TipoProducto.codigoTipoProducto=codTipoProducto;
end $$
delimiter ;

DELIMITER $$
 
CREATE PROCEDURE sp_agregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_imagenProducto VARCHAR(45),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_imagenProducto, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;
 
CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 'arroz.jpg', 100, 1, 1);

 
Delimiter $$
create procedure sp_listarProductos()
begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.imagenProducto,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
end$$
Delimiter ;
 
call sp_listarProductos();
 
DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaImagenProducto VARCHAR(45),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        imagenProducto = p_nuevaImagenProducto,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;
 

 
Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$
 
DELIMITER ;
 

-- ------------------------------------------ TELEFONO PROVEEDOR------------------------------------------------------

delimiter $$

create procedure sp_agregarTelefonoProveedor(in codTelPro int, in numPrin varchar(8), in numSec varchar(8), in obs varchar(45), in codPro int)
begin
    insert into TelefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (codTelPro, numPrin, numSec, obs, codPro);
end$$

delimiter ;

call sp_agregarTelefonoProveedor(1, '5165', '5165', 'sdfg', 1);

delimiter $$

create procedure sp_listarTelefonoProveedor()
begin 
    select t.codigoTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.codigoProveedor from TelefonoProveedor t;
end$$

delimiter ;

call sp_listarTelefonoProveedor();
delimiter $$

create procedure sp_buscarTelefonoProveedor(in codPro int)
begin
    select t.codigoTelefonoProveedor, t.numeroPrincipal, t.numeroSecundario, t.observaciones, t.codigoProveedor from TelefonoProveedor t where t.codigoProveedor = codPro;
end$$

delimiter ;
delimiter $$

create procedure sp_actualizarTelefonoProveedor(in codTelPro int, in numPrin varchar(8), in numSec varchar(8), in obs varchar(45), in codPro int)
begin
    update TelefonoProveedor set numeroPrincipal = numPrin, numeroSecundario = numSec, observaciones = obs, codigoProveedor = codPro where codigoTelefonoProveedor = codTelPro;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarTelefonoProveedor(in codTelPro int)
begin
    delete from TelefonoProveedor where codigoTelefonoProveedor = codTelPro;
end$$

delimiter ;

-- --------------------------------------- -----Email Proveedor -------------------------------------------------------------------------------
delimiter $$

create procedure sp_agregarEmailProveedor(
    in codEmailPro int,
    in email varchar(50),
    in descr varchar(100),
    in codPro int
)
begin
    insert into EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values (codEmailPro, email, descr, codPro);
end$$

delimiter ;

delimiter $$

create procedure sp_listarEmailProveedor()
begin 
    select e.codigoEmailProveedor, e.emailProveedor, e.descripcion, e.codigoProveedor 
    from EmailProveedor e;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarEmailProveedor(in codPro int)
begin
    select e.codigoEmailProveedor, e.emailProveedor, e.descripcion, e.codigoProveedor 
    from EmailProveedor e 
    where e.codigoProveedor = codPro;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarEmailProveedor(
    in codEmailPro int,
    in email varchar(50),
    in descr varchar(100),
    in codPro int
)
begin
    update EmailProveedor 
    set emailProveedor = email, descripcion = descr, codigoProveedor = codPro 
    where codigoEmailProveedor = codEmailPro;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarEmailProveedor(in codEmailPro int)
begin
    delete from EmailProveedor 
    where codigoEmailProveedor = codEmailPro;
end$$

delimiter ;