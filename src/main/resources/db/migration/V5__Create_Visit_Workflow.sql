
CREATE TABLE IF NOT EXISTS visit
(

    --from auditable
    id               BIGINT       NOT NULL,
    created_on       TIMESTAMP,
    created_by       VARCHAR(255),
    modified_on      TIMESTAMP,
    modified_by      VARCHAR(255),

    --from visit
    visit_number     VARCHAR(50)  NOT NULL,
    visit_date_time  TIMESTAMP    NOT NULL,
    visit_type       VARCHAR(50)  NOT NULL,
    visit_status     VARCHAR(50)  NOT NULL,
    chief_complaint  VARCHAR(500),
    notes            VARCHAR(1000),

    deleted_flag     BOOLEAN      NOT NULL DEFAULT FALSE,

    --foreign key columns
    patient_id       BIGINT       NOT NULL,
    doctor_id        BIGINT       NOT NULL,
    service_point_id BIGINT       NOT NULL,
    appointment_id   BIGINT,

    CONSTRAINT fk_visit_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient (id),
    CONSTRAINT fk_visit_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id),
    CONSTRAINT fk_visit_service_point
        FOREIGN KEY (service_point_id)
            REFERENCES service_point (id),
    CONSTRAINT fk_visit_appointment
        FOREIGN KEY (appointment_id)
            REFERENCES appointment (id)
);

ALTER TABLE ONLY visit
    ADD CONSTRAINT visit_pk PRIMARY KEY (id);

ALTER TABLE ONLY visit
    ADD CONSTRAINT visit_number_uk UNIQUE (visit_number);

ALTER TABLE ONLY visit
    ADD CONSTRAINT visit_appointment_uk UNIQUE (appointment_id);

CREATE SEQUENCE IF NOT EXISTS public.visit_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.visit_seq OWNED BY visit.id;

ALTER TABLE public.visit
    ALTER COLUMN id SET DEFAULT nextval('public.visit_seq');

CREATE SEQUENCE IF NOT EXISTS public.visit_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE INDEX IF NOT EXISTS idx_visit_patient_id ON visit (patient_id);
CREATE INDEX IF NOT EXISTS idx_visit_doctor_id ON visit (doctor_id);
CREATE INDEX IF NOT EXISTS idx_visit_appointment_id ON visit (appointment_id);
CREATE INDEX IF NOT EXISTS idx_visit_status ON visit (visit_status);
CREATE INDEX IF NOT EXISTS idx_visit_date_time ON visit (visit_date_time);

CREATE TABLE IF NOT EXISTS consultation
(

    --from auditable
    id                         BIGINT      NOT NULL,
    created_on                 TIMESTAMP,
    created_by                 VARCHAR(255),
    modified_on                TIMESTAMP,
    modified_by                VARCHAR(255),

    --from consultation
    consultation_date_time     TIMESTAMP   NOT NULL,
    chief_complaint            VARCHAR(500),
    history_of_present_illness VARCHAR(1000),
    examination_findings       VARCHAR(1000),
    assessment                 VARCHAR(1000),
    diagnosis                  VARCHAR(500),
    treatment_plan             VARCHAR(1000),
    notes                      VARCHAR(1000),
    deleted_flag               BOOLEAN     NOT NULL DEFAULT FALSE,

    --foreign key columns
    visit_id                   BIGINT      NOT NULL,
    doctor_id                  BIGINT      NOT NULL,

    CONSTRAINT fk_consultation_visit
        FOREIGN KEY (visit_id)
            REFERENCES visit (id),
    CONSTRAINT fk_consultation_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id)
);

ALTER TABLE ONLY consultation
    ADD CONSTRAINT consultation_pk PRIMARY KEY (id);

ALTER TABLE ONLY consultation
    ADD CONSTRAINT consultation_visit_uk UNIQUE (visit_id);

CREATE SEQUENCE IF NOT EXISTS public.consultation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.consultation_seq OWNED BY consultation.id;

ALTER TABLE public.consultation
    ALTER COLUMN id SET DEFAULT nextval('public.consultation_seq');

CREATE INDEX IF NOT EXISTS idx_consultation_doctor_id ON consultation (doctor_id);

CREATE TABLE IF NOT EXISTS medication
(

    --from auditable
    id              BIGINT       NOT NULL,
    created_on      TIMESTAMP,
    created_by      VARCHAR(255),
    modified_on     TIMESTAMP,
    modified_by     VARCHAR(255),

    --from medication
    medication_name VARCHAR(100) NOT NULL,
    generic_name    VARCHAR(100),
    dosage_form     VARCHAR(50),
    strength        VARCHAR(50),
    description     VARCHAR(500),
    active_flag     BOOLEAN      NOT NULL DEFAULT TRUE,
    deleted_flag    BOOLEAN      NOT NULL DEFAULT FALSE
);

ALTER TABLE ONLY medication
    ADD CONSTRAINT medication_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.medication_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.medication_seq OWNED BY medication.id;

ALTER TABLE public.medication
    ALTER COLUMN id SET DEFAULT nextval('public.medication_seq');

CREATE INDEX IF NOT EXISTS idx_medication_active_flag ON medication (active_flag);

CREATE TABLE IF NOT EXISTS prescription
(

    --from auditable
    id                  BIGINT      NOT NULL,
    created_on          TIMESTAMP,
    created_by          VARCHAR(255),
    modified_on         TIMESTAMP,
    modified_by         VARCHAR(255),

    --from prescription
    prescription_number VARCHAR(50) NOT NULL,
    prescription_date   TIMESTAMP   NOT NULL,
    status              VARCHAR(50) NOT NULL,
    instructions        VARCHAR(1000),
    notes               VARCHAR(1000),
    deleted_flag        BOOLEAN     NOT NULL DEFAULT FALSE,

    --foreign key columns
    visit_id            BIGINT      NOT NULL,
    patient_id          BIGINT      NOT NULL,
    doctor_id           BIGINT      NOT NULL,

    CONSTRAINT fk_prescription_visit
        FOREIGN KEY (visit_id)
            REFERENCES visit (id),
    CONSTRAINT fk_prescription_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient (id),
    CONSTRAINT fk_prescription_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id)
);

ALTER TABLE ONLY prescription
    ADD CONSTRAINT prescription_pk PRIMARY KEY (id);

ALTER TABLE ONLY prescription
    ADD CONSTRAINT prescription_number_uk UNIQUE (prescription_number);

CREATE SEQUENCE IF NOT EXISTS public.prescription_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.prescription_seq OWNED BY prescription.id;

ALTER TABLE public.prescription
    ALTER COLUMN id SET DEFAULT nextval('public.prescription_seq');

CREATE SEQUENCE IF NOT EXISTS public.prescription_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE INDEX IF NOT EXISTS idx_prescription_visit_id ON prescription (visit_id);
CREATE INDEX IF NOT EXISTS idx_prescription_patient_id ON prescription (patient_id);
CREATE INDEX IF NOT EXISTS idx_prescription_doctor_id ON prescription (doctor_id);
CREATE INDEX IF NOT EXISTS idx_prescription_status ON prescription (status);

CREATE TABLE IF NOT EXISTS prescription_item
(

    --from auditable
    id              BIGINT         NOT NULL,
    created_on      TIMESTAMP,
    created_by      VARCHAR(255),
    modified_on     TIMESTAMP,
    modified_by     VARCHAR(255),

    --from prescription item
    dosage          VARCHAR(100)   NOT NULL,
    frequency       VARCHAR(100)   NOT NULL,
    duration        VARCHAR(100)   NOT NULL,
    quantity        INTEGER        NOT NULL,
    route           VARCHAR(50),
    instructions    VARCHAR(500),
    deleted_flag    BOOLEAN        NOT NULL DEFAULT FALSE,

    --foreign key columns
    prescription_id BIGINT         NOT NULL,
    medication_id   BIGINT         NOT NULL,

    CONSTRAINT fk_prescription_item_prescription
        FOREIGN KEY (prescription_id)
            REFERENCES prescription (id),
    CONSTRAINT fk_prescription_item_medication
        FOREIGN KEY (medication_id)
            REFERENCES medication (id)
);

ALTER TABLE ONLY prescription_item
    ADD CONSTRAINT prescription_item_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.prescription_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.prescription_item_seq OWNED BY prescription_item.id;

ALTER TABLE public.prescription_item
    ALTER COLUMN id SET DEFAULT nextval('public.prescription_item_seq');

CREATE INDEX IF NOT EXISTS idx_prescription_item_prescription_id ON prescription_item (prescription_id);
CREATE INDEX IF NOT EXISTS idx_prescription_item_medication_id ON prescription_item (medication_id);

CREATE TABLE IF NOT EXISTS billing
(

    --from auditable
    id            BIGINT         NOT NULL,
    created_on    TIMESTAMP,
    created_by    VARCHAR(255),
    modified_on   TIMESTAMP,
    modified_by   VARCHAR(255),

    --from billing
    bill_number   VARCHAR(50)    NOT NULL,
    billing_date  TIMESTAMP      NOT NULL,
    status        VARCHAR(50)    NOT NULL,
    total_amount  NUMERIC(12, 2) NOT NULL,
    notes         VARCHAR(1000),
    deleted_flag  BOOLEAN        NOT NULL DEFAULT FALSE,

    --foreign key columns
    visit_id      BIGINT         NOT NULL,
    patient_id    BIGINT         NOT NULL,

    CONSTRAINT fk_billing_visit
        FOREIGN KEY (visit_id)
            REFERENCES visit (id),
    CONSTRAINT fk_billing_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient (id)
);

ALTER TABLE ONLY billing
    ADD CONSTRAINT billing_pk PRIMARY KEY (id);

ALTER TABLE ONLY billing
    ADD CONSTRAINT bill_number_uk UNIQUE (bill_number);

CREATE SEQUENCE IF NOT EXISTS public.billing_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.billing_seq OWNED BY billing.id;

ALTER TABLE public.billing
    ALTER COLUMN id SET DEFAULT nextval('public.billing_seq');

CREATE SEQUENCE IF NOT EXISTS public.bill_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE INDEX IF NOT EXISTS idx_billing_visit_id ON billing (visit_id);
CREATE INDEX IF NOT EXISTS idx_billing_patient_id ON billing (patient_id);
CREATE INDEX IF NOT EXISTS idx_billing_status ON billing (status);

CREATE TABLE IF NOT EXISTS billing_item
(

    --from auditable
    id          BIGINT         NOT NULL,
    created_on  TIMESTAMP,
    created_by  VARCHAR(255),
    modified_on TIMESTAMP,
    modified_by VARCHAR(255),

    --from billing item
    description VARCHAR(255)   NOT NULL,
    quantity    NUMERIC(10, 2) NOT NULL,
    unit_price  NUMERIC(12, 2) NOT NULL,
    amount      NUMERIC(12, 2) NOT NULL,
    deleted_flag BOOLEAN       NOT NULL DEFAULT FALSE,

    --foreign key columns
    billing_id  BIGINT         NOT NULL,

    CONSTRAINT fk_billing_item_billing
        FOREIGN KEY (billing_id)
            REFERENCES billing (id)
);

ALTER TABLE ONLY billing_item
    ADD CONSTRAINT billing_item_pk PRIMARY KEY (id);

CREATE SEQUENCE IF NOT EXISTS public.billing_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.billing_item_seq OWNED BY billing_item.id;

ALTER TABLE public.billing_item
    ALTER COLUMN id SET DEFAULT nextval('public.billing_item_seq');

CREATE INDEX IF NOT EXISTS idx_billing_item_billing_id ON billing_item (billing_id);

CREATE TABLE IF NOT EXISTS clinical_report
(

    --from auditable
    id             BIGINT       NOT NULL,
    created_on     TIMESTAMP,
    created_by     VARCHAR(255),
    modified_on    TIMESTAMP,
    modified_by    VARCHAR(255),

    --from clinical report
    report_number  VARCHAR(50)  NOT NULL,
    report_type    VARCHAR(50)  NOT NULL,
    report_date    TIMESTAMP    NOT NULL,
    title          VARCHAR(255) NOT NULL,
    findings       VARCHAR(2000),
    conclusion     VARCHAR(1000),
    recommendations VARCHAR(1000),
    notes          VARCHAR(1000),
    deleted_flag   BOOLEAN      NOT NULL DEFAULT FALSE,

    --foreign key columns
    visit_id       BIGINT       NOT NULL,
    patient_id     BIGINT       NOT NULL,
    doctor_id      BIGINT       NOT NULL,

    CONSTRAINT fk_clinical_report_visit
        FOREIGN KEY (visit_id)
            REFERENCES visit (id),
    CONSTRAINT fk_clinical_report_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient (id),
    CONSTRAINT fk_clinical_report_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (id)
);

ALTER TABLE ONLY clinical_report
    ADD CONSTRAINT clinical_report_pk PRIMARY KEY (id);

ALTER TABLE ONLY clinical_report
    ADD CONSTRAINT report_number_uk UNIQUE (report_number);

CREATE SEQUENCE IF NOT EXISTS public.clinical_report_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.clinical_report_seq OWNED BY clinical_report.id;

ALTER TABLE public.clinical_report
    ALTER COLUMN id SET DEFAULT nextval('public.clinical_report_seq');

CREATE SEQUENCE IF NOT EXISTS public.report_number_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE INDEX IF NOT EXISTS idx_clinical_report_visit_id ON clinical_report (visit_id);
CREATE INDEX IF NOT EXISTS idx_clinical_report_patient_id ON clinical_report (patient_id);
CREATE INDEX IF NOT EXISTS idx_clinical_report_doctor_id ON clinical_report (doctor_id);
CREATE INDEX IF NOT EXISTS idx_clinical_report_type ON clinical_report (report_type);
