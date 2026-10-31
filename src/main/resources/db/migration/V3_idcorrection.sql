CREATE SEQUENCE IF NOT EXISTS producto_id_seq
OWNED BY producto.id;

ALTER TABLE producto
    ALTER COLUMN id SET DEFAULT nextval('producto_id_seq');

SELECT setval(
               'producto_id_seq',
               COALESCE((SELECT MAX(id) FROM producto), 0) + 1,
               false
       );