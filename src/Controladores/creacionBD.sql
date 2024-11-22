-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-11-22 14:49:52.433

-- tables
-- Table: catalogo
CREATE TABLE catalogo (
    codigo serial PRIMARY KEY, -- Llave primaria
    nombre varchar(100), -- Se permite NULL
    precio real, -- Se permite NULL
    marca varchar(100), -- Se permite NULL
    ruta varchar(200), -- Se permite NULL
    alto real, -- Se permite NULL
    ancho real, -- Se permite NULL
    largo real, -- Se permite NULL
    tamano real, -- Se permite NULL
    idtipo int, -- Se permite NULL
    idsubtipo int -- Se permite NULL
);

-- Table: detalle_pedido
CREATE TABLE detalle_pedido (
    cdetallepedido serial PRIMARY KEY, -- Llave primaria
    idpedido int, -- Se permite NULL
    codigo int -- Se permite NULL
);

-- Table: pedido
CREATE TABLE pedido (
    idpedido serial PRIMARY KEY, -- Llave primaria
    fecha date, -- Se permite NULL
    total real, -- Se permite NULL
    usuario int -- Se permite NULL
);

-- Table: subtipo
CREATE TABLE subtipo (
    idsubtipo serial PRIMARY KEY, -- Llave primaria
    subtipo varchar(50) -- Se permite NULL
);

-- Table: tipo
CREATE TABLE tipo (
    idtipo serial PRIMARY KEY, -- Llave primaria
    tipo varchar(50) -- Se permite NULL
);

-- Table: usuario
CREATE TABLE usuario (
    usuario int PRIMARY KEY, -- Llave primaria
    contrasena varchar(100) -- Se permite NULL
);

-- foreign keys
-- Reference: catalogo_Table_3 (table: catalogo)
ALTER TABLE catalogo ADD CONSTRAINT catalogo_tipo
    FOREIGN KEY (idtipo)
    REFERENCES tipo (idtipo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: catalogo_subtipo (table: catalogo)
ALTER TABLE catalogo ADD CONSTRAINT catalogo_subtipo
    FOREIGN KEY (idsubtipo)
    REFERENCES subtipo (idsubtipo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: detalle_pedido_catalogo (table: detalle_pedido)
ALTER TABLE detalle_pedido ADD CONSTRAINT detalle_pedido_catalogo
    FOREIGN KEY (codigo)
    REFERENCES catalogo (codigo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: detalle_pedido_pedido (table: detalle_pedido)
ALTER TABLE detalle_pedido ADD CONSTRAINT detalle_pedido_pedido
    FOREIGN KEY (idpedido)
    REFERENCES pedido (idpedido)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: pedido_usuario (table: pedido)
ALTER TABLE pedido ADD CONSTRAINT pedido_usuario
    FOREIGN KEY (usuario)
    REFERENCES usuario (usuario)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.
