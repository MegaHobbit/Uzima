CREATE TABLE IF NOT EXISTS appointment
(

    --from auditable
    id                    BIGINT      NOT NULL,
    created_on            TIMESTAMP,
    created_by            VARCHAR(255),
    modified_on           TIMESTAMP,
    modified_by           VARCHAR(255),

    --from appointment
    appointment_date_time TIMESTAMP   NOT NULL,
    status                VARCHAR(20) NOT NULL,
    reason                VARCHAR(255),
    notes                 VARCHAR(255),
    appointment_type      VARCHAR(255),
    deleted_flag          BOOLEAN,
    reminder_sent_at      TIMESTAMP,


--foreign key columns
    doctor_id             BIGINT      NOT NULL,
    patient_id            BIGINT      NOT NULL,
    service_point_id      BIGINT      NOT NULL,

    CONSTRAINT fk_appointment_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id),
    CONSTRAINT fk_appointment_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient (id),
    CONSTRAINT fk_appointment_service_point
        FOREIGN KEY (service_point_id)
            REFERENCES service_point (id)
);

ALTER TABLE ONLY appointment
    ADD CONSTRAINT appointment_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.appointment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE appointment_seq OWNED BY appointment.id;

ALTER TABLE appointment
    ALTER COLUMN id
        SET DEFAULT nextval('appointment_seq');
