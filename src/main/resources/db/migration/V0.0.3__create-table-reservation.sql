create table if not exists reservation (
    id integer primary key GENERATED ALWAYS AS IDENTITY,
    date_debut timestamp,
    date_fin timestamp,
    type_reunion varchar(6),
    nb_web_cam_externes integer,
    nb_tableau_externes integer,
    nb_pieuvre_externes integer,
    nb_ecran_externes integer,
    id_salle integer,
    CONSTRAINT fk_salle FOREIGN KEY(id_salle) REFERENCES salle(id)
);