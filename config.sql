--script: Crear bodega de datos
--Las “surrogate key” se identifican como id_nombreTabla
--Las llaves naturales de cada dimensión se identifican con la palabra id.

--Dimension Fecha

CREATE SEQUENCE fecha_id_seq;
CREATE TABLE FECHA (
id_fecha INTEGER DEFAULT NEXTVAL('fecha_id_seq') PRIMARY KEY,
fecha date,
year double precision,
month double precision,
monthname text,
day double precision,
dayofyear double precision,
weekdayname text,
calendarweek double precision,
formatteddate text,
quartal text,
yearquartal text,
yearmonth text,
yearcalendarweek text,
weekend text,
Colombiaholiday text,
holidayname text,
cwstart date,
cwend date,
monthstart date,
monthend timestamp
);

insert into FECHA
(fecha, year, month, monthname, day, dayofyear, weekdayname, calendarweek, 
formatteddate, quartal, yearquartal, yearmonth, yearcalendarweek, weekend, 
Colombiaholiday, holidayname, cwstart, cwend, monthstart, monthend)
SELECT
	datum AS fecha,
	EXTRACT(YEAR FROM datum) AS YEAR,
	EXTRACT(MONTH FROM datum) AS MONTH,
	-- Localized month name
	to_char(datum, 'TMMonth') AS MonthName,
	EXTRACT(DAY FROM datum) AS DAY,
	EXTRACT(doy FROM datum) AS DayOfYear,
	-- Localized weekday
	to_char(datum, 'TMDay') AS WeekdayName,
	-- ISO calendar week
	EXTRACT(week FROM datum) AS CalendarWeek,
	to_char(datum, 'dd. mm. yyyy') AS FormattedDate,
	'Q' || to_char(datum, 'Q') AS Quartal,
	to_char(datum, 'yyyy/"Q"Q') AS YearQuartal,
	to_char(datum, 'yyyy/mm') AS YearMonth,
	-- ISO calendar year and week
	to_char(datum, 'iyyy/IW') AS YearCalendarWeek,
	-- Weekend
	CASE WHEN EXTRACT(isodow FROM datum) IN (6, 7) THEN 'Weekend' ELSE 'Weekday' END AS Weekend,
	-- Fixed holidays 
        -- for Colombia
        'No Holiday' AS Colombiaholiday,
	-- Some periods of the year, adjust for your organisation and country
	'NONE' AS Period,
	-- ISO start and end of the week of this date
	datum + (1 - EXTRACT(isodow FROM datum))::INTEGER AS CWStart,
	datum + (7 - EXTRACT(isodow FROM datum))::INTEGER AS CWEnd,
	-- Start and end of the month of this date
	datum + (1 - EXTRACT(DAY FROM datum))::INTEGER AS MonthStart,
	(datum + (1 - EXTRACT(DAY FROM datum))::INTEGER + '1 month'::INTERVAL)::DATE - '1 day'::INTERVAL AS MonthEnd
FROM (
	-- There are 3 leap years in this range, so calculate 365 * 10 + 3 records
	SELECT '2010-01-01'::DATE + SEQUENCE.DAY AS datum
	FROM generate_series(0,4017) AS SEQUENCE(DAY)
	GROUP BY SEQUENCE.DAY
     ) DQ
ORDER BY 1;


--Dimension Tiempo

CREATE SEQUENCE tiempo_id_seq;
CREATE TABLE TIEMPO(
  id_tiempo INTEGER DEFAULT NEXTVAL('tiempo_id_seq') PRIMARY KEY,
  franja character(15)  NOT NULL,
  hora_inicio time without time zone  NOT NULL,
  hora_fin time without time zone  NOT NULL,
  jornada character(10)  NOT NULL
);

INSERT INTO tiempo (franja, hora_inicio, hora_fin, jornada)
VALUES ('0-1','00:00:00', '01:00:00', 'MAÑANA'),
('1-2','01:00:00', '02:00:00', 'MAÑANA'),
('2-3','02:00:00', '03:00:00', 'MAÑANA'),
('3-4','03:00:00', '04:00:00', 'MAÑANA'),
('4-5','04:00:00', '05:00:00', 'MAÑANA'),
('5-6','05:00:00', '06:00:00', 'MAÑANA'),
('6-7','06:00:00', '07:00:00', 'MAÑANA'),
('7-8','07:00:00', '08:00:00', 'MAÑANA'),
('8-9','08:00:00', '09:00:00', 'MAÑANA'),
('9-10','09:00:00', '10:00:00', 'MAÑANA'),
('10-11','10:00:00', '11:00:00', 'MAÑANA'),
('11-12','11:00:00', '12:00:00', 'MAÑANA'),
('12-13','12:00:00', '13:00:00', 'TARDE'),
('13-14','13:00:00', '14:00:00', 'TARDE'),
('14-15','14:00:00', '15:00:00', 'TARDE'),
('15-16','15:00:00', '16:00:00', 'TARDE'),
('16-17','16:00:00', '17:00:00', 'TARDE'),
('17-18','17:00:00', '18:00:00', 'TARDE'),
('18-19','18:00:00', '19:00:00', 'TARDE'),
('19-20','19:00:00', '20:00:00', 'NOCHE'),
('20-21','20:00:00', '21:00:00', 'NOCHE'),
('21-22','21:00:00', '22:00:00', 'NOCHE'),
('22-23','22:00:00', '23:00:00', 'NOCHE'),
('23-0','23:00:00', '00:00:00', 'NOCHE');


--Dimension parada
CREATE SEQUENCE parada_id_seq;
CREATE TABLE PARADA(
id_parada INTEGER DEFAULT NEXTVAL('parada_id_seq') PRIMARY KEY,
id character(50)  NOT NULL UNIQUE,
nombre character(50)  NOT NULL,
tipo character(20)  NOT NULL CHECK (tipo IN ('RUTA','ESTACION'))
);


--Tabla de hechos demanda de pasajeros
CREATE TABLE DEMANDA(
id_fecha INTEGER,
id_franja INTEGER,
id_origen INTEGER,
id_destino INTEGER,
cantidad_pasajeros INTEGER,

PRIMARY KEY (id_fecha, id_franja, id_origen, id_destino),

FOREIGN KEY (id_fecha)
REFERENCES FECHA(id_fecha),

FOREIGN KEY (id_franja)
REFERENCES TIEMPO(id_tiempo),

FOREIGN KEY (id_origen)
REFERENCES PARADA(id_parada),

FOREIGN KEY (id_destino)
REFERENCES PARADA(id_parada)
);
