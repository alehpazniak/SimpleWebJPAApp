CREATE TABLE IF NOT EXISTS public.employees (
    employee_id SERIAL PRIMARY KEY,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	department_id numeric NOT NULL,
	job_title varchar NOT NULL,
	gender varchar NOT NULL,
	date_of_birth date NOT NULL
);