--Las “surrogate key” se identifican como id_nombreTabla
--Las llaves naturales de cada dimensión se identifican con la palabra id.

--DIMENSION TIEMPO
CREATE TABLE tiempo (
	id_tiempo SERIAL PRIMARY KEY,
	franja TEXT,
	hora_inicio TIME WITHOUT TIME ZONE,
	hora_fin TIME WITHOUT TIME ZONE,
	jornada TEXT
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

--DIMENSION FECHA
CREATE TABLE fecha (
	id_fecha SERIAL PRIMARY KEY,
	fecha DATE,
	año DOUBLE PRECISION,
	mes DOUBLE PRECISION,
	nombre_mes TEXT,
	dia DOUBLE PRECISION,
	dia_año DOUBLE PRECISION,
	nombre_dia TEXT,
	semana_calendario DOUBLE PRECISION,
	fecha_formateada TEXT,
	trimestre TEXT,
	año_trimestre TEXT,
	año_mes TEXT,
	año_semana TEXT,
	es_dia_semana TEXT,
	festivo_colombia TEXT,
	festivo_usa TEXT,
	festivo_australia TEXT,
	festivo_canada TEXT,
	inicio_semana DATE,
	fin_semana DATE,
	inicio_mes DATE,
	fin_mes DATE
);

INSERT INTO fecha
(fecha, año, mes, nombre_mes, dia, dia_año, nombre_dia, semana_calendario, 
	fecha_formateada, trimestre, año_trimestre, año_mes, año_semana, es_dia_semana, 
	festivo_colombia, festivo_usa, festivo_australia, festivo_canada, inicio_semana, fin_semana, inicio_mes, fin_mes)
SELECT
datum AS DATE,
EXTRACT(YEAR FROM datum) AS AÑO,
EXTRACT(MONTH FROM datum) AS MES,
to_char(datum, 'TMMonth') AS NOMBRE_MES,
EXTRACT(DAY FROM datum) AS DIA,
EXTRACT(doy FROM datum) AS DIA_AÑO,
to_char(datum, 'TMDay') AS NOMBRE_DIA,
EXTRACT(week FROM datum) AS SEMANA_CALENDARIO,
to_char(datum, 'yymmdd') AS FECHA_FORMATEADA,
'T' || to_char(datum, 'Q') AS TRIMESTRE,
to_char(datum, 'yyyy-"T"Q') AS AÑO_TRIMESTRE,
to_char(datum, 'yyyy-mm') AS AÑO_MES,
to_char(datum, 'iyyy-IW') AS AÑO_SEMANA,
CASE WHEN EXTRACT(isodow FROM datum) IN (6, 7) THEN 'NO' ELSE 'SI' END AS ES_DIA_SEMANA,
CASE WHEN to_char(datum, 'MMDDYYYY') IN ('01012014', '01062014', '03242014', '04172014', '04182014', '05012014',
	'06022014', '06232014','06302014','08072014', '08182014', '10132014','11032014','11172014','12082014','12252014',
	'01012015', '01122015','03232015','04022015', '04032015', '05012015','05182015','06082015','06152015','06292015',
	'07202015','08072015', '08172015', '10122015','11022015','11162015','12082015','12252015',
	'01012016', '01112016','03212016','03242016', '03252016', '05092016','05302016','06062016','07042016','07202016',
	'08152016', '10172016','11072016','11142016','12082016')
THEN 'SI' ELSE 'NO' END
AS FESTIVO_COLOMBIA,
CASE WHEN to_char(datum, 'MMDD') IN ('0101', '0704', '1225', '1226')
THEN 'SI' ELSE 'NO' END
AS FESTIVO_USA,
CASE WHEN to_char(datum, 'MMDD') IN 
('0101', '0106', '0501', '0815', '1101', '1208', '1225', '1226') 
THEN 'SI' ELSE 'NO' END 
AS FESTIVO_AUSTRALIA,
CASE WHEN to_char(datum, 'MMDD') IN ('0101', '0701', '1225', '1226')
THEN 'SI' ELSE 'NO' END 
AS FESTIVO_CANADA,
datum + (1 - EXTRACT(isodow FROM datum))::INTEGER AS INICIO_SEMANA,
datum + (7 - EXTRACT(isodow FROM datum))::INTEGER AS FIN_SEMANA,
datum + (1 - EXTRACT(DAY FROM datum))::INTEGER AS INICIO_MES,
(datum + (1 - EXTRACT(DAY FROM datum))::INTEGER + '1 month'::INTERVAL)::DATE - '1 day'::INTERVAL AS FIN_MES
FROM (
	SELECT '2014-01-01'::DATE + SEQUENCE.DAY AS datum
	FROM generate_series(0,1095) AS SEQUENCE(DAY)
	GROUP BY SEQUENCE.DAY
	) DQ
ORDER BY 1;

--DIMENSION PARADA
CREATE TABLE parada (
	id_parada  SERIAL PRIMARY KEY,
	id TEXT  NOT NULL UNIQUE,
	nombre TEXT  NOT NULL,
	tipo TEXT  NOT NULL
);

--HECHO DEMANDA
CREATE TABLE demanda (
	id_demanda SERIAL PRIMARY KEY,
	id_fecha INTEGER REFERENCES fecha(id_fecha),
	id_franja INTEGER REFERENCES tiempo(id_tiempo),
	id_origen INTEGER REFERENCES parada(id_parada),
	id_destino INTEGER REFERENCES parada(id_parada),
	cantidad_pasajeros INTEGER		
);