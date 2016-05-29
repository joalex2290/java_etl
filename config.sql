-- Table: public.tiempo

-- DROP TABLE public.tiempo;

CREATE TABLE public.tiempo
(
  id integer NOT NULL,
  franja character(15),
  hora_inicio time without time zone,
  hora_fin time without time zone,
  jornada character(10),
  cant_horas_franja integer
)
WITH (
  OIDS=FALSE
);

INSERT INTO public.tiempo(id, franja, hora_inicio, hora_fin, jornada, cant_horas_franja)
    VALUES (1,'4:00-8:00','4:00:00', '5:00:00', 'Mañana', 4),
           (2,'4:00-8:00','5:00:00', '6:00:00', 'Mañana', 4),
           (3,'4:00-8:00','6:00:00', '7:00:00', 'Mañana', 4),
           (4,'4:00-8:00','7:00:00', '8:00:00', 'Mañana', 4),
           (5,'8:00-12:00','8:00:00', '9:00:00', 'Mañana', 4),
           (6,'8:00-12:00','9:00:00', '10:00:00', 'Mañana', 4),
           (7,'8:00-12:00','10:00:00', '11:00:00', 'Mañana', 4),
           (8,'8:00-12:00','11:00:00', '12:00:00', 'Mañana', 4),
           (9,'12:00-16:00','12:00:00', '14:00:00', 'Tarde', 4),
           (10,'12:00-16:00','14:00:00', '14:00:00', 'Tarde', 4),
           (11,'12:00-16:00','14:00:00', '15:00:00', 'Tarde', 4),
           (12,'12:00-16:00','15:00:00', '16:00:00', 'Tarde', 4),
           (13,'16:00-20:00','16:00:00', '17:00:00', 'Tarde', 4),
           (14,'16:00-20:00','17:00:00', '18:00:00', 'Tarde', 4),
           (15,'16:00-20:00','18:00:00', '19:00:00', 'Noche', 4),
           (16,'16:00-20:00','19:00:00', '20:00:00', 'Tarde', 4),
           (17,'20:00-23:00','20:00:00', '21:00:00', 'Noche', 3),
           (18,'20:00-23:00','21:00:00', '22:00:00', 'Noche', 3),
           (19,'20:00-23:00','22:00:00', '23:00:00', 'Noche', 3),
           (20,'23:00-4:00','23:00:00', '4:00:00', 'Madrugada', 5)
