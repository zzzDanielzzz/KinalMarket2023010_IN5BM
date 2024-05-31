drop database if exists DBKinalMarket;
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
    primary key PK_CodigoProducto(codigoProducto),
    constraint FK_Productos_Provedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor)
        ON DELETE CASCADE,
    constraint FK_Productos_TipoProducto foreign key TipoProducto(codigoTipoProducto)
		references TipoProducto(codigoTipoProducto)  
        ON DELETE CASCADE
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_CodigoTelefonoProveedor (codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor)
        ON DELETE CASCADE
);

create table EmailProveedor(
	codigoEmailProveedor int,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_CodigoEmailProveedor (codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key Proveedores(codigoProveedor)
		references Proveedores(codigoProveedor)
        ON DELETE CASCADE
);

create table Empleados(
	codigoEmpleado int,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_CodigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_CargoEmpleado foreign key CargoEmpleado(codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
        ON DELETE CASCADE
);

create table Factura(
	numeroFactura int,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_NumeroFactura (numeroFactura),
    constraint FK_Factura_Clientes foreign key Clientes(codigoCliente)
		references Clientes(codigoCliente)
        ON DELETE CASCADE,
    constraint FK_Factura_Empleados foreign key Empleados(codigoEmpleado)
		references Empleados(codigoEmpleado)
        ON DELETE CASCADE
);

create table DetalleFactura(
	codigoDetalleFactura int,
    precioUnitario decimal(10, 2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_CodigoDetalleFactura (codigoDetalleFactura),
    constraint FK_DetalleFactura_Facturas foreign key Factura(numeroFactura)
		references Factura(numeroFactura)
        ON DELETE CASCADE,
	constraint FK_DetalleFactura_Productos foreign key Productos(codigoProducto)
		references Productos(codigoProducto)
        ON DELETE CASCADE
);

create table DetalleCompra(
	codigoDetalleCompra int,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_CodigoDetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Productos foreign key Productos(codigoProducto)
		references Productos(codigoProducto)
        ON DELETE CASCADE,
    constraint FK_DetalleCompra_Compras foreign key Compras(numeroDocumento)    
		references Compras(numeroDocumento)
        ON DELETE CASCADE
);
delimiter $$

create procedure sp_agregarCliente(in codCli int, in nitCli varchar(10), in nombreCli varchar(50), in apellidosCli varchar(50),in direccionCli varchar(150), in telefonoCli varchar(45), in correoCli varchar(45)
)
begin
	insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
    values (codCli, nitCli, nombreCli, apellidosCli, direccionCli, telefonoCli, correoCli);
end$$

delimiter ;

call sp_agregarCliente('1','78542585', 'Daniel', 'Sacol', 'San jose Pinula', '33815217', 'danieledusc@gmail.com'); 

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

create procedure sp_buscarCliente(in codCli varchar(10))
begin
   select
    c.codigoCliente,
    c.NITCliente,
	c.apellidosCliente,
    c.nombreCliente,
    c.direccionCliente,
    c.telefonoCliente,
    c.correoCliente
    from clientes c where codigoCliente = codCli;
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
 
create procedure sp_agregarProducto(
    in codProd VARCHAR(15),
    in descripProd VARCHAR(15),
    in precioUnit DECIMAL(10,2),
    in precioDocen DECIMAL(10,2),
    in precioMay DECIMAL(10,2),
    in imagProd VARCHAR(45),
    in exist INT,
    in codTipoProd INT,
    in codProv INT
)
begin
    insert into Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor)
    values(codProd, descripProd, precioUnit, precioDocen, precioMay, imagProd, exist, codTipoProd, codProv);
end$$
DELIMITER ;
 
CALL sp_agregarProducto('1', 'Arroz', 5.99, 68.99, 129.99, 'arroz.jpg', 100, 1, 1);

delimiter $$

create procedure sp_buscarProductoPorCodigo(in codProd varchar(15))
begin
    select p.codigoProducto, p.descripcionProducto, p.precioUnitario, p.precioDocena, p.precioMayor, 
           p.imagenProducto, p.existencia, p.codigoProveedor, p.codigoTipoProducto
    from Productos p 
    where p.codigoProducto = codProd;
end$$

delimiter ;
 
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
 
delimiter $$
create procedure sp_actualizarproducto(
    in codprod varchar(15),
    in descripprod varchar(15),
    in preciounit decimal(10,2),
    in preciodocen decimal(10,2),
    in preciomay decimal(10,2),
    in imagprod varchar(45),
    in exist int,
    in codtipoprod int,
    in codprov int
)
begin
    update productos
    set descripcionproducto = descripprod,
        preciounitario = preciounit,
        preciodocena = preciodocen,
        preciomayor = preciomay,
        imagenproducto = imagprod,
        existencia = exist,
        codigotipoproducto = codtipoprod,
        codigoproveedor = codprov
    where codigoproducto = codprod;
end$$
delimiter ;
 

 
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

-- ----------------------------------------------------- Empleados ------------------------------------------------------------
delimiter $$

create procedure sp_agregarEmpleado(
    in codEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in codCargoEmp int
)
begin
    insert into Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    values (codEmp, nombres, apellidos, sueldo, direccion, turno, codCargoEmp);
end$$

delimiter ;
call sp_agregarEmpleado(1, 'John', 'Doe', 3000.00, '1234 Elm Street', 'Day', 1);


delimiter $$

create procedure sp_listarEmpleados()
begin 
    select e.codigoEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.direccion, e.turno, e.codigoCargoEmpleado 
    from Empleados e;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarEmpleado(in codEmpleado int)
begin
    select e.codigoEmpleado, e.nombresEmpleado, e.apellidosEmpleado, e.sueldo, e.direccion, e.turno, e.codigoCargoEmpleado 
    from Empleados e 
    where e.codigoEmpleado = codEmpleado;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarEmpleado(
    in codEmp int,
    in nombres varchar(50),
    in apellidos varchar(50),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in codCargoEmp int
)
begin
    update Empleados 
    set nombresEmpleado = nombres, apellidosEmpleado = apellidos, sueldo = sueldo, direccion = direccion, turno = turno, codigoCargoEmpleado = codCargoEmp 
    where codigoEmpleado = codEmp;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarEmpleado(in codEmp int)
begin
    delete from Empleados 
    where codigoEmpleado = codEmp;
end$$

delimiter ;

-- ------------------------------------------- Factura ------------------------------------------------------------------------
delimiter $$

create procedure sp_agregarFactura(
    in numFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in codCli int,
    in codEmp int
)
begin
    insert into Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
    values (numFac, estadoFac, totalFac, fechaFac, codCli, codEmp);
end$$

delimiter ;

CALL sp_agregarFactura(12345, 'Pagada', 500.00, '2024-05-19', 1, 1);

delimiter $$

create procedure sp_listarFacturas()
begin 
    select f.numeroFactura, f.estado, f.totalFactura, f.fechaFactura, f.codigoCliente, f.codigoEmpleado 
    from Factura f;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarFactura(in numFac int)
begin
    select f.numeroFactura, f.estado, f.totalFactura, f.fechaFactura, f.codigoCliente, f.codigoEmpleado 
    from Factura f 
    where f.numeroFactura = numFac;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarFactura(
    in numFac int,
    in estadoFac varchar(50),
    in totalFac decimal(10,2),
    in fechaFac varchar(45),
    in codCli int,
    in codEmp int
)
begin
    update Factura 
    set estado = estadoFac, totalFactura = totalFac, fechaFactura = fechaFac, codigoCliente = codCli, codigoEmpleado = codEmp 
    where numeroFactura = numFac;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarFactura(in numFac int)
begin
    delete from Factura 
    where numeroFactura = numFac;
end$$

delimiter ;

-- ------------------------------------------------ Detalle Factura-----------------------------------------------------
delimiter $$

create procedure sp_agregarDetalleFactura(
    in codDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in numFac int,
    in codProd varchar(15)
)
begin
    insert into DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    values (codDetFac, precioUnit, cant, numFac, codProd);
end$$

delimiter ;

CALL sp_agregarDetalleFactura(1, 10.50, 2, 12345, '1');

delimiter $$

create procedure sp_listarDetalleFacturas()
begin 
    select d.codigoDetalleFactura, d.precioUnitario, d.cantidad, d.numeroFactura, d.codigoProducto
    from DetalleFactura d;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarDetalleFactura(in codDetFac int)
begin
    select d.codigoDetalleFactura, d.precioUnitario, d.cantidad, d.numeroFactura, d.codigoProducto
    from DetalleFactura d 
    where d.codigoDetalleFactura = codDetFac;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarDetalleFactura(
    in codDetFac int,
    in precioUnit decimal(10,2),
    in cant int,
    in numFac int,
    in codProd varchar(15)
)
begin
    update DetalleFactura 
    set precioUnitario = precioUnit, cantidad = cant, numeroFactura = numFac, codigoProducto = codProd 
    where codigoDetalleFactura = codDetFac;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarDetalleFactura(in codDetFac int)
begin
    delete from DetalleFactura 
    where codigoDetalleFactura = codDetFac;
end$$

delimiter ;

-- ------------------------------------------------- Detalle Compra ---------------------------------------------------
delimiter $$

create procedure sp_agregarDetalleCompra(
    in codDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in codProd varchar(15),
    in numDoc int
)
begin
    insert into DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    values (codDetComp, costoUnit, cant, codProd, numDoc);
end$$

delimiter ;

CALL sp_agregarDetalleCompra(1, 15.75, 3, '1', 123456);

delimiter $$

create procedure sp_listarDetalleCompras()
begin 
    select d.codigoDetalleCompra, d.costoUnitario, d.cantidad, d.codigoProducto, d.numeroDocumento
    from DetalleCompra d;
end$$

delimiter ;

delimiter $$

create procedure sp_buscarDetalleCompraPorCodigo(in codDetComp int)
begin
    select d.codigoDetalleCompra, d.costoUnitario, d.cantidad, d.codigoProducto, d.numeroDocumento
    from DetalleCompra d 
    where d.codigoDetalleCompra = codDetComp;
end$$

delimiter ;

delimiter $$

create procedure sp_actualizarDetalleCompra(
    in codDetComp int,
    in costoUnit decimal(10,2),
    in cant int,
    in codProd varchar(15),
    in numDoc int
)
begin
    update DetalleCompra 
    set costoUnitario = costoUnit, cantidad = cant, codigoProducto = codProd, numeroDocumento = numDoc 
    where codigoDetalleCompra = codDetComp;
end$$

delimiter ;

delimiter $$

create procedure sp_eliminarDetalleCompra(in codDetComp int)
begin
    delete from DetalleCompra 
    where codigoDetalleCompra = codDetComp;
end$$

delimiter ;

delimiter $$



-- ---------------------------------------------------Triggers -------------------------------------------------------------------
delimiter $$
create trigger tr_detallefactura_after_inser after insert on detallefactura
for each row
begin
    update factura
    set totalfactura = totalfactura + new.preciounitario * new.cantidad
    where numerofactura = new.numerofactura;
end
$$
delimiter ;


delimiter $$
create trigger tr_detallefactura_after_update after update on detallefactura
for each row
begin
    update factura
    set totalfactura = totalfactura + (new.preciounitario * new.cantidad) - (old.preciounitario * old.cantidad)
    where numerofactura = new.numerofactura;
end $$
delimiter ;


delimiter $$
create trigger tr_detallefactura_after_delete after delete on detallefactura
for each row
begin
    update factura
    set totalfactura = totalfactura - old.preciounitario * old.cantidad
    where numerofactura = old.numerofactura;
end $$
delimiter ;


delimiter $$
create trigger tr_detallecompra_after_insert after insert on detallecompra
for each row
begin
    update compras
    set totaldocumento = totaldocumento + new.costounitario * new.cantidad
    where numerodocumento = new.numerodocumento;
end
$$
delimiter ;


delimiter $$
create trigger tr_detallecompra_after_update after update on detallecompra
for each row
begin
    update compras
    set totaldocumento = totaldocumento + (new.costounitario * new.cantidad) - (old.costounitario * old.cantidad)
    where numerodocumento = new.numerodocumento;
end $$
delimiter ;


delimiter $$
create trigger tr_detallecompra_after_delete after delete on detallecompra
for each row
begin
    update compras
    set totaldocumento = totaldocumento - old.costounitario * old.cantidad
    where numerodocumento = old.numerodocumento;
end $$
delimiter ;

delimiter $$

create trigger tr_detallefactura_productos_after_insert after insert on detallefactura
for each row
begin
    update productos
    set existencia = existencia - new.cantidad
    where codigoproducto = new.codigoproducto;
end $$
delimiter ;

delimiter $$

create trigger tr_detallefactura_productos_after_delete after delete on detallefactura
for each row
begin
    update productos
    set existencia = existencia + old.cantidad
    where codigoproducto = old.codigoproducto;
end $$

delimiter ;

delimiter $$
create trigger tr_detallefactura_productos_after_update after update on detallefactura
for each row
begin
    declare resta int;
    set resta = new.cantidad - old.cantidad;
    
    update productos
    set existencia = existencia - resta
    where codigoproducto = new.codigoproducto;
end;
$$

delimiter ;

delimiter $$

create trigger tr_detallecompras_after_insert after insert on detallecompra
for each row
begin
    update productos
    set existencia = existencia + new.cantidad
    where codigoproducto = new.codigoproducto;
end $$
delimiter ;

delimiter $$

create trigger tr_detallecompras_after_delete after delete on detallecompra
for each row
begin
    update productos
    set existencia = existencia - old.cantidad
    where codigoproducto = old.codigoproducto;
end $$

delimiter ;

delimiter $$
create trigger tr_detallecompras_after_update after update on detallecompra
for each row
begin
    declare resta int;
    set resta = new.cantidad - old.cantidad;
    
    update productos
    set existencia = existencia - resta
    where codigoproducto = new.codigoproducto;
end;
$$

delimiter ;

DELIMITER $$
 
CREATE TRIGGER AfterInsertDetalleCompra
AFTER INSERT ON DetalleCompra
FOR EACH ROW
BEGIN
    DECLARE precioProveedor DECIMAL(10,2);
    DECLARE precioDocena DECIMAL(10,2);
    DECLARE precioMayor DECIMAL(10,2);
 
    -- Calcular precios
    SET precioProveedor = NEW.costoUnitario * 1.40;
    SET precioDocena = precioProveedor * 1.35;
    SET precioMayor = precioProveedor * 1.25;
 
    -- Actualizar productos con los precios calculados
    UPDATE Productos
    SET precioUnitario = precioProveedor,
        precioDocena = precioDocena,
        precioMayor = precioMayor
    WHERE codigoProducto = NEW.codigoProducto;
    
    
END $$
 
DELIMITER ;

