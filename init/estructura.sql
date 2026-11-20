

BEGIN;

CREATE SCHEMA IF NOT EXISTS restaurante;

CREATE TABLE IF NOT EXISTS restaurante.adicionales
(
    adicional_id serial NOT NULL,
    nombre character varying(80) COLLATE pg_catalog."default" NOT NULL,
    precio numeric(10, 2) NOT NULL DEFAULT 0,
    aplica_a character varying(60) COLLATE pg_catalog."default",
    CONSTRAINT adicionales_pkey PRIMARY KEY (adicional_id)
);

CREATE TABLE IF NOT EXISTS restaurante.clientes
(
    cliente_id serial NOT NULL,
    identificacion character varying(20) COLLATE pg_catalog."default" NOT NULL,
    nombre character varying(120) COLLATE pg_catalog."default" NOT NULL,
    direccion character varying(200) COLLATE pg_catalog."default",
    telefono character varying(20) COLLATE pg_catalog."default",
    email character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT clientes_pkey PRIMARY KEY (cliente_id),
    CONSTRAINT clientes_identificacion_key UNIQUE (identificacion)
);

CREATE TABLE IF NOT EXISTS restaurante.empleados
(
    empleado_id serial NOT NULL,
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    usuario character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(100) COLLATE pg_catalog."default" NOT NULL,
    rol character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT empleados_pkey PRIMARY KEY (empleado_id),
    CONSTRAINT empleados_usuario_key UNIQUE (usuario)
);

CREATE TABLE IF NOT EXISTS restaurante.mesas
(
    mesa_id serial NOT NULL,
    numero integer NOT NULL,
    estado character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'libre',
    CONSTRAINT mesas_pkey PRIMARY KEY (mesa_id),
    CONSTRAINT mesas_numero_key UNIQUE (numero),
    CONSTRAINT mesas_estado_check CHECK (estado IN ('libre', 'ocupada', 'reservada', 'mantenimiento'))
);

CREATE TABLE IF NOT EXISTS restaurante.productos
(
    producto_id serial NOT NULL,
    nombre character varying(120) COLLATE pg_catalog."default" NOT NULL,
    codigo character varying(32),
    precio_base numeric(10, 2) NOT NULL,
    categoria character varying(60) COLLATE pg_catalog."default" NOT NULL,
    estacion character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT productos_pkey PRIMARY KEY (producto_id)
);

CREATE TABLE IF NOT EXISTS restaurante.comandas
(
    comanda_id serial NOT NULL,
    mesa_id integer NOT NULL,
    empleado_id integer NOT NULL,
    estado character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'abierta',
    total numeric(10, 2) NOT NULL DEFAULT 0,
    fecha timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT comandas_pkey PRIMARY KEY (comanda_id),
    CONSTRAINT comandas_estado_check CHECK (estado IN ('pendiente', 'lista', 'entregada', 'pagada', 'cancelada'))
);

CREATE TABLE IF NOT EXISTS restaurante.comprobantes
(
    comprobante_id serial NOT NULL,
    comanda_id integer NOT NULL,
    cliente_id integer,
    tipo_comprobante character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'ticket_interno',
    numero character varying(30) COLLATE pg_catalog."default" NOT NULL,
    subtotal numeric(10, 2) NOT NULL,
    descuento numeric(10, 2) NOT NULL DEFAULT 0,
    iva numeric(10, 2) NOT NULL DEFAULT 0,
    propina numeric(10, 2) NOT NULL DEFAULT 0,
    total numeric(10, 2) NOT NULL,
    fecha_emision timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT comprobantes_pkey PRIMARY KEY (comprobante_id),
    CONSTRAINT comprobantes_comanda_id_key UNIQUE (comanda_id),
    CONSTRAINT comprobantes_numero_key UNIQUE (numero),
    CONSTRAINT comprobantes_tipo_check CHECK (tipo_comprobante IN ('ticket_interno', 'factura', 'nota_venta'))
);

CREATE TABLE IF NOT EXISTS restaurante.detalle_comanda
(
    detalle_comanda_id serial NOT NULL,
    comanda_id integer NOT NULL,
    producto_id integer NOT NULL,
    cantidad integer NOT NULL DEFAULT 1,
    subtotal numeric(10, 2) NOT NULL,
    estado character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'pendiente',
    notas text COLLATE pg_catalog."default",
    CONSTRAINT detalle_comanda_pkey PRIMARY KEY (detalle_comanda_id),
    CONSTRAINT detalle_comanda_estado_check CHECK (estado IN ('pendiente', 'en_preparacion', 'listo', 'servido', 'cancelado'))
);

CREATE TABLE IF NOT EXISTS restaurante.detalle_adicional
(
    detalle_adicional_id serial NOT NULL,
    detalle_comanda_id integer NOT NULL,
    adicional_id integer NOT NULL,
    cantidad integer NOT NULL DEFAULT 1,
    precio_aplicado numeric(10, 2) NOT NULL,
    CONSTRAINT detalle_adicional_pkey PRIMARY KEY (detalle_adicional_id)
);

CREATE TABLE IF NOT EXISTS restaurante.pagos
(
    pago_id serial NOT NULL,
    comprobante_id integer NOT NULL,
    metodo character varying(30) COLLATE pg_catalog."default" NOT NULL,
    monto numeric(10, 2) NOT NULL,
    comision numeric(10, 2) NOT NULL DEFAULT 0,
    CONSTRAINT pagos_pkey PRIMARY KEY (pago_id),
    CONSTRAINT pagos_comprobante_id_key UNIQUE (comprobante_id)
);

-- Foreign keys

ALTER TABLE IF EXISTS restaurante.comandas
    ADD CONSTRAINT comandas_empleado_id_fkey FOREIGN KEY (empleado_id)
    REFERENCES restaurante.empleados (empleado_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS idx_comandas_empleado
    ON restaurante.comandas(empleado_id);

ALTER TABLE IF EXISTS restaurante.comandas
    ADD CONSTRAINT comandas_mesa_id_fkey FOREIGN KEY (mesa_id)
    REFERENCES restaurante.mesas (mesa_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS idx_comandas_mesa
    ON restaurante.comandas(mesa_id);

ALTER TABLE IF EXISTS restaurante.comprobantes
    ADD CONSTRAINT comprobantes_cliente_id_fkey FOREIGN KEY (cliente_id)
    REFERENCES restaurante.clientes (cliente_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS restaurante.comprobantes
    ADD CONSTRAINT comprobantes_comanda_id_fkey FOREIGN KEY (comanda_id)
    REFERENCES restaurante.comandas (comanda_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
-- (Índice duplicado eliminado: comprobantes_comanda_id_key ya crea un índice único)

ALTER TABLE IF EXISTS restaurante.detalle_adicional
    ADD CONSTRAINT detalle_adicional_adicional_id_fkey FOREIGN KEY (adicional_id)
    REFERENCES restaurante.adicionales (adicional_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS restaurante.detalle_adicional
    ADD CONSTRAINT detalle_adicional_detalle_comanda_id_fkey FOREIGN KEY (detalle_comanda_id)
    REFERENCES restaurante.detalle_comanda (detalle_comanda_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_detalle_adic_item
    ON restaurante.detalle_adicional(detalle_comanda_id);

ALTER TABLE IF EXISTS restaurante.detalle_comanda
    ADD CONSTRAINT detalle_comanda_comanda_id_fkey FOREIGN KEY (comanda_id)
    REFERENCES restaurante.comandas (comanda_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_detalle_comanda
    ON restaurante.detalle_comanda(comanda_id);

ALTER TABLE IF EXISTS restaurante.detalle_comanda
    ADD CONSTRAINT detalle_comanda_producto_id_fkey FOREIGN KEY (producto_id)
    REFERENCES restaurante.productos (producto_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS restaurante.pagos
    ADD CONSTRAINT pagos_comprobante_id_fkey FOREIGN KEY (comprobante_id)
    REFERENCES restaurante.comprobantes (comprobante_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS idx_pagos_comprobante
    ON restaurante.pagos(comprobante_id);

INSERT INTO restaurante.empleados (nombre, usuario, password_hash, rol)
VALUES ('admin sistema', 'admin', 'admin123', 'administrador');

END;