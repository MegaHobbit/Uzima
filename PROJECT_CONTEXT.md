# Development Context

This document was compiled from local repository inspection, Git history, PostgreSQL metadata, and configuration files. No source code, database objects, repository contents, or GitHub resources were modified.

## 1. MAPP

### Repository
- GitHub repository: https://github.com/MegaHobbit/Mapp
- Local project paths:
  - C:\Users\JetCore Computers\Desktop\Test Cloning\Mapp (current working copy; branch: develop)
  - C:\Users\JetCore Computers\Desktop\MyApp\mapp (older local copy; branch: master)
- Current branch:
  - Test Cloning\Mapp -> develop
  - MyApp\mapp -> master
- Repository structure (verified from local tree):
  - .mvn/, mvnw, mvnw.cmd, pom.xml
  - src/main/java/com/example/mapp/
    - Controller/
    - Enums/
    - Models/
    - Repositories/
    - Service/
    - dto/
  - src/main/resources/application.properties
  - src/test/java/com/example/mapp/MappApplicationTests.java
- Relevant recent commits (from local git log on Test Cloning\Mapp):
  - 5ba8e51 Added Exception Handlers, Mapper, Controller and service tests for the Patient Entity
  - ed6858e Added controllers for patient service
  - c6cd432 Added migrations for 5 tables
  - 89fd62e created migrations for tables doctor, patient, person and service point
  - 580df38 Addedpatient service and visit service and relsted files
- Verification note: the current checkout does not contain the migration files or the exception-handler/mapper/test classes named in the recent history, so the history appears ahead of or divergent from the working tree.

### Local Environment
- Verified local repo root: C:\Users\JetCore Computers\Desktop\Test Cloning\Mapp
- Build tool: Maven (pom.xml)
- Runtime: local Spring Boot app configured on port 8300 (from application.properties)

### Technology Stack
- Java: 26 (pom.xml)
- Spring Boot: 4.0.6 (pom.xml)
- Build tool: Maven
- Database: PostgreSQL 18.4 (verified via psql against the local server)
- Database name used by app: mapp
- Frontend/mobile: none discovered in the repository
- Significant dependencies:
  - spring-boot-starter-data-jdbc
  - spring-boot-starter-data-jpa
  - spring-boot-starter-webmvc
  - org.postgresql:postgresql
  - Lombok

### Architecture
- Package structure:
  - com.example.mapp.Controller
  - com.example.mapp.Enums
  - com.example.mapp.Models
  - com.example.mapp.Repositories
  - com.example.mapp.Service
  - com.example.mapp.dto
- Controllers:
  - ProductController: POST /api/product, GET /api/product
- Services:
  - ProductService: creates products and maps entities to DTOs
- Repositories:
  - ProductRepository: extends JpaRepository<Product, String>
- Entities:
  - BaseEntity (mapped superclass)
  - Person, Doctors, Patient, ServicePoint, Department, Employees, Customer, Product, Pharmacy, Lab, Radiology
- DTOs:
  - ProductRequest, ProductResponse
- Mappers:
  - none as dedicated mapper classes; mapping is done inside ProductService
- Configuration:
  - MappApplication is the Spring Boot entry point
  - application.properties configures PostgreSQL URL/username/password, JPA, Hibernate, and server port
- Exception handling:
  - none discovered; services throw runtime exceptions directly or rely on framework defaults
- Validation:
  - none discovered in code
- Security:
  - none discovered
- Scheduled/background tasks:
  - none discovered

### Domain Model
- BaseEntity (mapped superclass)
  - Purpose: common audit/identity fields for persistence
  - Fields: id, createdOn, modifiedOn, createdBy
  - Relationships: inherited by multiple entities
  - Enums: none
  - Inheritance: base for many entities
  - Audit fields: yes (created/modified)
  - Soft deletion: no
  - Business rules: none implemented
- Person (entity; extends BaseEntity)
  - Purpose: shared person profile for medical/staff roles
  - Fields: firstName, lastName, surName, dateOfBirth, gender, contact, email, residence
  - Relationships: parent class for Doctors/Patient/Employees
  - Enums: Gender
  - Inheritance: yes
  - Audit fields: inherited from BaseEntity
  - Soft deletion: no
  - Business rules: none implemented
- Doctors (entity; extends Person)
  - Purpose: clinician record
  - Fields: doctorRegNumber, doctorAccNumber, doctorStatus
  - Relationships: none in current code
  - Enums: DoctorStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Patient (entity; extends Person)
  - Purpose: patient record
  - Fields: visitDate, visitNumber, visitStatus, visitType
  - Relationships: none in current code
  - Enums: VisitStatus, VisitType
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- ServicePoint (entity; extends BaseEntity)
  - Purpose: service-point/department-like location
  - Fields: servicePointId, servicePointName, description, status
  - Relationships: none in current code
  - Enums: ServicePointStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Department (entity; extends BaseEntity)
  - Purpose: department catalog
  - Fields: depId, depName, depDescription
  - Relationships: none in current code
  - Enums: none
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Employees (entity; extends Person)
  - Purpose: employee/personnel record
  - Fields: employeeNumber, accountNumber
  - Relationships: none in current code
  - Enums: none
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Customer (entity)
  - Purpose: simple customer/contact record
  - Fields: id, name, email, gender
  - Relationships: none
  - Enums: none
  - Inheritance: no
  - Audit fields: none
  - Soft deletion: no
- Product (entity; extends BaseEntity)
  - Purpose: inventory/product record
  - Fields: name, description, category, amount, quantity, buyingPrice, sellingPrice, status, supplier
  - Relationships: none
  - Enums: ProductStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Pharmacy (entity; extends BaseEntity)
  - Purpose: pharmacy record
  - Fields: status, quantity
  - Relationships: none
  - Enums: PharmStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Lab (entity; extends BaseEntity)
  - Purpose: lab record
  - Fields: laboratoryNumber, labName, status
  - Enums: LabStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Radiology (entity; extends BaseEntity)
  - Purpose: radiology service record
  - Fields: radiologyNumber, price, description, category, amount, radStatus
  - Enums: RadStatus
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: no
- Enums present:
  - DoctorStatus, Gender, LabStatus, PharmStatus, ProductStatus, RadStatus, ServicePointStatus, VisitStatus, VisitType

### Database
- Database: mapp
- PostgreSQL version: 18.4
- Tables present in public schema:
  - appointment
  - doctor
  - flyway_schema_history
  - patient
  - person
  - service_point
- Columns (salient):
  - appointment: id, created_on, created_by, modified_on, modified_by, appointment_number, appointment_date_time, appointment_status, reason_for_appointment, duration, notes
  - doctor: id, created_on, created_by, modified_on, modified_by, first_name, middle_name, last_name, date_of_birth, phone_number, email, address, gender, marital_status, preferred_language, occupation, national_id, birth_certificate_number, doctor_reg_number, doctor_acc_number, doctor_status, service_point_id
  - patient: id, created_on, created_by, modified_on, modified_by, first_name, middle_name, last_name, date_of_birth, phone_number, email, address, gender, marital_status, preferred_language, occupation, national_id, birth_certificate_number, patient_number, registration_date, blood_group, insurance_provider, insurance_number, emergency_contact, patient_status, doctor_id, service_point_id
  - person: id, created_on, created_by, modified_on, modified_by, first_name, middle_name, last_name, date_of_birth, phone_number, email, address, gender, marital_status, preferred_language, occupation, national_id, birth_certificate_number
  - service_point: id, created_on, created_by, modified_on, modified_by, service_point_name, description, service_point_status
- Primary keys:
  - appointment: id
  - doctor: id
  - patient: id
  - person: id
  - service_point: id
- Foreign keys:
  - doctor.service_point_id -> service_point.id
  - patient.doctor_id -> doctor.id
  - patient.service_point_id -> service_point.id
- Indexes:
  - one PK index per table; no additional non-PK indexes discovered
- Constraints:
  - PKs and FKs only; no check constraints discovered
- Sequences/identity generation:
  - appointment_seq
  - doctor_seq
  - patient_seq
  - person_seq
  - service_point_seq
- Enums/types:
  - columns are stored as character varying/character and timestamps; no dedicated PostgreSQL enum types discovered
- Flyway migration versions (from flyway_schema_history):
  - 1: Create Person
  - 2: Create ServicePoint
  - 3: Create Doctor
  - 4: Create Patient
  - 5: Create Appointment
- Schema relationships:
  - service_point -> doctor -> patient (plus patient -> service_point)
  - appointment stands alone

### Code/Database Consistency
- Verified mismatches:
  - The live database has tables for appointment/doctor/patient/person/service_point, but the current Java model contains many other entity classes (Product, Pharmacy, Lab, Radiology, Department, Customer, Employees) that are not reflected in the current schema.
  - The repo currently contains no Flyway migration SQL files in the working tree, yet the database has a flyway_schema_history table with applied migrations.
  - ProductRepository uses JpaRepository<Product, String> while the Product entity declares a Long id.
  - Person and Product both redeclare an id field even though they already inherit one from BaseEntity; this is likely an entity modeling mistake.
  - The current code appears to be a product-inventory scaffold rather than a faithful implementation of the database model that is actually present.
- Verified alignments:
  - The database tables do contain the expected basic healthcare entities (doctor/patient/service_point), and the code includes corresponding entity classes with similar names.

### Current Development State
- Completed:
  - Spring Boot app scaffold for MAPP exists and starts with a minimal datasource/JPA configuration.
  - A basic Product CRUD flow exists through ProductController/ProductService/ProductRepository.
  - The database has a live schema and Flyway history.
- Partially implemented:
  - Healthcare-oriented entities exist in code, but their relationships and lifecycle behavior are not fully implemented.
  - Recent commit history suggests patient-service/controller/mapper/test work existed at some point, but those artifacts are not present in the current checkout.
- Likely next steps:
  - Reconcile the code model with the actual database schema.
  - Add or restore Flyway SQL files to the repository so migrations are versioned and visible to future developers.
  - Implement the intended domain objects (doctor/patient/service_point/appointment) rather than the current product-focused scaffold.
- Known TODOs:
  - none explicitly commented in the code
- Technical debt:
  - model drift between code and database
  - no validation/security/exception-handling architecture
  - no clear mapping layer or dedicated exception handling
  - inconsistent naming/packaging conventions (Controller/Models/Repositories vs lowercase package names in other projects)

### Important Resources
- GitHub repository: https://github.com/MegaHobbit/Mapp
- Local app base URL (configured): http://localhost:8300
- No OpenAPI/Swagger configuration was found in the MAPP repository.

## 2. Uzima

### Repository
- GitHub repository: https://github.com/MegaHobbit/Uzima
- Local project path: C:\Users\JetCore Computers\Desktop\Uzima
- Current branch: develop
- Repository structure (verified from local tree):
  - .mvn/, mvnw, mvnw.cmd, pom.xml
  - src/main/java/com/uzima/
    - controllers/
    - dtos/
    - enums/
    - models/
    - repository/
    - services/
    - swaggerconfig/
  - src/main/resources/application.properties
  - src/main/resources/db/migration/V1__Create_ServicePoint.sql
  - src/main/resources/db/migration/V2__Create_Doctor.sql
  - src/main/resources/db/migration/V3__Create_Patient.sql
  - src/test/com/uzima/Uzima/UzimaApplicationTests.java
- Relevant recent commits:
  - 4010893 This is the updated Uzima Merge branch 'develop' of https://github.com/MegaHobbit/Uzima
  - b372c4d files
  - 0483786 Added DTOs
  - c785dfa Created a microservice to get patient details from doctor and servicepoint
  - ca6e51a created migrations for tables doctor, patient, service-point
  - c237278 feat(service-point): implement soft delete and filter endpoints

### Local Environment
- Verified local repo root: C:\Users\JetCore Computers\Desktop\Uzima
- Build tool: Maven (pom.xml)
- Runtime: local Spring Boot app configured on port 8600 (application.properties)

### Technology Stack
- Java: 25 (pom.xml)
- Spring Boot: 3.5.14 (pom.xml)
- Build tool: Maven
- Database: PostgreSQL 18.4 (verified via psql)
- Database name used by app: uzima_hospital
- Frontend/mobile: none discovered in the repository
- Significant dependencies:
  - spring-boot-starter-data-jpa
  - spring-boot-starter-web
  - org.postgresql:postgresql
  - Lombok
  - springdoc-openapi-starter-webmvc-ui
  - flyway-core
  - flyway-database-postgresql

### Architecture
- Package structure:
  - com.uzima.controllers
  - com.uzima.dtos
  - com.uzima.enums
  - com.uzima.models
  - com.uzima.repository
  - com.uzima.services
  - com.uzima.swaggerconfig
- Controllers:
  - ServicePointController
  - DoctorController
  - PatientController
- Services:
  - ServicePointService
  - DoctorService
  - PatientService
  - SchedulerService
- Repositories:
  - ServicePointRepository
  - DoctorRepository
  - PatientRepository
- Entities:
  - Auditable (mapped superclass)
  - ServicePoint
  - Doctor
  - Patient
- DTOs:
  - ServicePointData
  - DoctorData
  - DoctorRequest
  - PatientRequest
  - PatientData
  - PatientDetails
- Mappers:
  - none as dedicated mapper classes; DTO conversion is done inside DTO classes and services
- Configuration:
  - OpenApiConfig sets Swagger/OpenAPI metadata and server URLs
  - application.properties configures datasource, Hibernate, Flyway, and Swagger paths
- Exception handling:
  - none as a dedicated global handler; services throw RuntimeException directly
- Validation:
  - none discovered (no jakarta.validation annotations)
- Security:
  - none discovered
- Scheduled/background tasks:
  - SchedulerService exists, but it is not wired with @Scheduled and is not used by the current app flow

### Domain Model
- Auditable (mapped superclass)
  - Purpose: shared persistence identity and audit fields
  - Fields: id, createdOn, createdBy, modifiedOn, modifiedBy
  - Relationships: inherited by ServicePoint/Doctor/Patient
  - Enums: none
  - Inheritance: yes
  - Audit fields: yes
  - Soft deletion: no (implemented per entity via deletedFlag)
  - Business rules: none implemented beyond persistence metadata
- ServicePoint (entity)
  - Purpose: service point catalog
  - Fields: pointName, description, pointStatus, deletedFlag
  - Relationships: one-to-many to Doctor
  - Enums: PointStatus
  - Inheritance: yes (extends Auditable)
  - Audit fields: inherited
  - Soft deletion: yes, via deletedFlag
  - Business rules: create/update/get/delete operations are implemented in service layer
- Doctor (entity)
  - Purpose: doctor record
  - Fields: doctorNumber, firstName, lastName, phoneNumber, deletedFlag
  - Relationships: many-to-one to ServicePoint; one-to-many to Patient
  - Enums: none
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: yes, via deletedFlag
  - Business rules: each doctor belongs to a service point and can be soft-deleted
- Patient (entity)
  - Purpose: patient record
  - Fields: patientNumber, firstName, lastName, email, phoneNumber, gender, deletedFlag
  - Relationships: many-to-one to Doctor
  - Enums: Gender
  - Inheritance: yes
  - Audit fields: inherited
  - Soft deletion: yes, via deletedFlag
  - Business rules: each patient belongs to a doctor and can be soft-deleted
- Enums present:
  - Gender, PointName, PointStatus

### Database
- Database: uzima_hospital
- PostgreSQL version: 18.4
- Tables present in public schema:
  - doctor
  - flyway_schema_history
  - patient
  - service_point
- Columns (salient):
  - doctor: id, created_on, created_by, modified_on, modified_by, first_name, last_name, doctor_number, phone_number, deleted_flag, service_point_id
  - patient: id, created_on, created_by, modified_on, modified_by, first_name, last_name, email, phone_number, gender, deleted_flag, patient_number, doctor_id
  - service_point: id, created_on, created_by, modified_on, modified_by, point_name, description, point_status, deleted_flag
- Primary keys:
  - doctor: id
  - patient: id
  - service_point: id
- Foreign keys:
  - doctor.service_point_id -> service_point.id
  - patient.doctor_id -> doctor.id
- Indexes:
  - PK indexes only; no additional indexes discovered
- Constraints:
  - PKs and FKs; NOT NULL on service_point.point_name, service_point.point_status, doctor.service_point_id, patient.doctor_id
- Sequences/identity generation:
  - doctor_seq
  - patient_seq
  - service_point_seq
- Enums/types:
  - point_status and gender are stored as character varying; no dedicated PostgreSQL enum types
- Flyway migration versions (from flyway_schema_history):
  - 1: Create ServicePoint
  - 2: Create Doctor
  - 3: Create Patient
- Schema relationships:
  - service_point -> doctor -> patient

### Code/Database Consistency
- Verified alignment:
  - The entity model and the database schema are closely aligned for ServicePoint, Doctor, and Patient.
  - The Flyway migration files in src/main/resources/db/migration match the live database schema and the Flyway history.
  - The entity field names and table names are mostly consistent with the database columns, including soft-delete flags and foreign keys.
- Minor discrepancies / issues:
  - DoctorController and PatientController use @RequestParam("/id") in some endpoints, which is likely incorrect for Spring MVC and may prevent parameter binding.
  - PatientRepository contains an odd custom method signature List<Patient> id(Long id); it does not follow Spring Data naming conventions and is not clearly used.
  - The code uses RuntimeException directly rather than a dedicated exception-handling strategy.

### Current Development State
- Completed:
  - A working Spring Boot API skeleton exists for service points, doctors, and patients.
  - Flyway migrations are present and applied to the database.
  - DTOs and service-layer CRUD behaviors are implemented.
- Partially implemented:
  - Soft delete is implemented for service points/doctors/patients, but filtering and lifecycle semantics are still minimal.
  - OpenAPI/Swagger documentation is configured, but it is not yet a major development focus in the code.
- Likely next steps:
  - Add proper validation and exception handling.
  - Harden controller parameter binding and API semantics.
  - Add security if the project is moving toward production readiness.
- Known TODOs:
  - none explicitly documented in code
- Technical debt:
  - no security layer
  - no validation annotations
  - no dedicated exception handling
  - controller parameter quirks and minimal repository query methods

### Important Resources
- GitHub repository: https://github.com/MegaHobbit/Uzima
- Swagger UI (configured): http://localhost:8600/swagger-ui.html
- OpenAPI docs (configured): http://localhost:8600/api-docs
- Local app base URL (configured): http://localhost:8600
- OpenAPI config source: src/main/java/com/uzima/swaggerconfig/OpenApiConfig.java

## 3. MAPP vs Uzima

### Key Differences
- MAPP is a more exploratory/early-stage codebase with many domain entities and a product-inventory feel; Uzima is a narrower hospital-domain API focused on service points, doctors, and patients.
- MAPP currently uses Spring Boot 4.0.6 / Java 26; Uzima uses Spring Boot 3.5.14 / Java 25.
- MAPP’s current repository state does not appear to be fully aligned with its live database schema; Uzima is much more consistent between code, migrations, and database.
- MAPP uses Hibernate auto-update semantics in application.properties; Uzima uses Flyway migrations with ddl-auto=validate.
- MAPP has no Swagger/OpenAPI configuration in the repo; Uzima explicitly configures OpenAPI and Swagger UI.

### Shared Patterns
- Both projects are Spring Boot + JPA + PostgreSQL APIs.
- Both use Lombok and basic controller/service/repository layering.
- Both use simple audit metadata fields (createdOn/modifiedOn/createdBy/modifiedBy) via a mapped superclass.
- Both expose simple CRUD-style REST endpoints and use DTOs for API payloads.

### Architectural Evolution
- Uzima appears to have evolved into a more disciplined, migration-driven persistence model.
- MAPP looks closer to a prototyping or experimentation layer, with many domain classes but limited implementation consistency.
- The names and package structures in MAPP are less consistent than Uzima, suggesting it may have been developed in a more ad-hoc manner.

## 4. Development Conventions
- Observed in MAPP:
  - package names are mixed-case (Controller, Models, Repositories, Service, dto, Enums)
  - Lombok is used heavily
  - DTOs are simple data holders
  - no explicit validation/security/exception-handling conventions are present
- Observed in Uzima:
  - package names are lowercase and grouped by responsibility (controllers, services, repository, models, dtos, enums)
  - Lombok is used heavily
  - DTO conversion is handled in DTO classes and service classes
  - no explicit validation/security/exception-handling conventions are present
- Common conventions across both:
  - Spring Boot application entry points in the root package
  - entity classes use JPA annotations and Lombok
  - repositories extend Spring Data JPA interfaces
  - service classes are simple CRUD orchestrators

## 5. Known Issues and Technical Debt
- MAPP:
  - major code/schema drift
  - no Flyway migration files in the current working tree despite Flyway history in the database
  - duplicated/inconsistent ID handling in entity inheritance
  - repository/entity generic mismatch (String id vs Long id)
  - no validation/security/exception handling
- Uzima:
  - no validation framework or exception-handler strategy
  - no security layer
  - controller parameter binding issues in some endpoints
  - no indexes beyond PKs
  - scheduler service is not actually scheduled

## 6. Important URLs
- MAPP GitHub: https://github.com/MegaHobbit/Mapp
- Uzima GitHub: https://github.com/MegaHobbit/Uzima
- Uzima Swagger UI (configured): http://localhost:8600/swagger-ui.html
- Uzima OpenAPI docs (configured): http://localhost:8600/api-docs
- MAPP local base URL (configured): http://localhost:8300
- Uzima OpenAPI config server entry (configured): https://api.uzima.com

## 7. Current Databases
- mapp -> MAPP
  - PostgreSQL 18.4
  - Public tables: appointment, doctor, flyway_schema_history, patient, person, service_point
- uzima_hospital -> Uzima
  - PostgreSQL 18.4
  - Public tables: doctor, flyway_schema_history, patient, service_point
- postgres -> PostgreSQL system database
  - Used for server-level metadata and authentication context

## 8. Verification Notes
- Verified from repository files and Git history:
  - MAPP repo remote URL from git remote
  - Uzima repo remote URL from git remote
  - pom.xml contents for Java/Spring Boot versions
  - application.properties for datasource and Flyway settings
  - Java source files for controllers/services/entities/DTOs
- Verified from database metadata:
  - PostgreSQL version 18.4
  - table/column/PK/FK/index/sequence metadata for mapp and uzima_hospital
  - Flyway history tables for both databases
- Inferred from code and repository state:
  - MAPP’s current working tree appears to be a partial or earlier scaffold compared with the recent commit history
  - Uzima is more migration-driven and aligned with its database than MAPP
- Unknown / not directly verified:
  - whether MAPP has a live frontend or mobile client outside the current repository
  - whether the MAPP product/inventory domain is the intended end-state or just an experimental prototype

ADDENDUM — VERIFICATION
- MAPP repo URL — VERIFIED (git remote: Test Cloning\Mapp: origin https://github.com/MegaHobbit/Mapp)
- MAPP local path/branch — VERIFIED (filesystem/git)
- MAPP DB schema (tables/sequences/Flyway) — VERIFIED (psql: information_schema, \d+)
- MAPP Flyway SQL in repo — UNKNOWN/MISSING (filesystem: no migration files found)
- Uzima code ↔ migrations ↔ DB — VERIFIED (src/db/migration + psql)
- PostgreSQL version 18.4 — VERIFIED (psql: SELECT version())
- Swagger endpoints for Uzima — INFERRED (application.properties + OpenApiConfig)
- Any external GitHub web fetches — FAILED/UNKNOWN (web fetch blocked)

Notes: DB access was via local psql; no source files/DB were modified. The information in this addendum is included to clearly label which facts were directly verified, which were inferred, and which remain unknown.
