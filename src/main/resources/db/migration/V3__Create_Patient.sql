CREATE TABLE IF NOT EXISTS patient
(

    --from auditable
    id             BIGINT NOT NULL,
    created_on     TIMESTAMP,
    created_by     VARCHAR(255),
    modified_on    TIMESTAMP,
    modified_by    VARCHAR(255),

    --from patient
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    email          VARCHAR(50),
    phone_number   VARCHAR(50),
    gender         VARCHAR(20),
    deleted_flag     BOOLEAN,
    patient_number VARCHAR(50),

    --foreign key columns
    doctor_id      BIGINT,

    CONSTRAINT fk_doctor_id
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id)
);

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.patient_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE patient_seq OWNED BY patient.id;

ALTER TABLE public.patient
    ALTER COLUMN id
        SET DEFAULT nextval('patient_seq');
