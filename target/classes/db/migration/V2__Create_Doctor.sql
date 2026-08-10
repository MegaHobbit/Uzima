CREATE TABLE IF NOT EXISTS doctor
(
    --from auditable
    id               BIGINT NOT NULL,
    created_on       TIMESTAMP,
    created_by       VARCHAR(255),
    modified_on      TIMESTAMP,
    modified_by      VARCHAR(255),

    --from doctor
    first_name       VARCHAR(50),
    last_name        VARCHAR(256),
    doctor_number    VARCHAR(50),
    phone_number     VARCHAR(50),
    deleted_flag     BOOLEAN,

    --foreign key column
    service_point_id BIGINT NOT NULL,

    CONSTRAINT fk_service_point
        FOREIGN KEY (service_point_id)
            REFERENCES service_point (id)

);

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.doctor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.doctor_seq OWNED BY doctor.id;

ALTER TABLE public.doctor
    ALTER COLUMN id SET DEFAULT nextval('public.doctor_seq');

