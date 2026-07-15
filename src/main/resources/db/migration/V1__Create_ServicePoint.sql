CREATE TABLE IF NOT EXISTS service_point
(
    --from auditable
    id           BIGINT NOT NULL,
    created_on   TIMESTAMP,
    created_by   VARCHAR(255),
    modified_on  TIMESTAMP,
    modified_by  VARCHAR(255),

    --from service_point
    point_name   VARCHAR(50),
    description  VARCHAR(256),
    point_status VARCHAR(20),
    deleted_flag BOOLEAN

);

ALTER TABLE ONLY service_point
    ADD CONSTRAINT service_point_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.service_point_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.service_point_seq OWNED BY service_point.id;

ALTER TABLE public.service_point
    ALTER COLUMN id SET DEFAULT nextval('public.service_point_seq');

