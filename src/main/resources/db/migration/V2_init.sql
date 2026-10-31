-- V1 ya crea "producto". Renombra la tabla anterior solo si existe con el nombre incorrecto.
DO $$
BEGIN
    IF to_regclass('public.prodecto') IS NOT NULL AND to_regclass('public.producto') IS NULL THEN
        ALTER TABLE public.prodecto RENAME TO producto;
    END IF;
END $$;
