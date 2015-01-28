/*==============================================================*/
/* Table : CLIENT                                               */
/*==============================================================*/


INSERT INTO CLIENT VALUES (0,1511948948,1478,'oui','GALLET','Jordan',TO_DATE('01/02/1992','dd/mm/yyyy'),'homme','Chambéry','49 rue de gipsy king',73000,TO_DATE('12/01/2014','dd/mm/yyyy'),TO_DATE('12/01/2014','dd/mm/yyyy'));
INSERT INTO CLIENT VALUES (1,1511948948,84844,'oui','CRUCHAUDET','Emerick',TO_DATE('30/05/1991','dd/mm/yyyy'),'homme','Dijon','10 avenue de happn',73000,TO_DATE('12/01/2014','dd/mm/yyyy'),TO_DATE('12/01/2014','dd/mm/yyyy'));
INSERT INTO CLIENT VALUES (2,1511948948,0,'oui','CHAIEB','Mohamed',TO_DATE('29/07/1989','dd/mm/yyyy'),'homme','Voreppe','2 rue des colonnes',73000,TO_DATE('12/01/2014','dd/mm/yyyy'),TO_DATE('12/01/2014','dd/mm/yyyy'));
INSERT INTO CLIENT VALUES (3,1511948948,1,'non',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO CLIENT VALUES (4,1511948948,2,'non',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO CLIENT VALUES (idClient_seq.nextval,1511948948,2,'non',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*==============================================================*/
/* Table : SUPERVISEUR                                          ok*/
/*==============================================================*/

INSERT INTO SUPERVISEUR VALUES (0,"Pot defleur","Harry","homme",TO_DATE('01/02/1992','dd/mm/yyyy'),"14 avenue des spinashs",69003,"Lyon 3ème");


INSERT INTO LOCATION VALUES (idLocation_seq.nextval,7,6,sysdate,sysdate);

/*==============================================================*/
/* Table : STATION                                              */
/*==============================================================*/


INSERT INTO STATION VALUES (0,'Centre de réparation', 'rue du centre de réparation','44000','Nantes','Vnull');
INSERT INTO STATION VALUES (1,'Château des ducs de Bretagne ', '4 place Marc Elder','44000','Nantes','Vnull');
INSERT INTO STATION VALUES (2,'Gare de Nantes','27 Boulevard Stalingrad','44000','Nantes','Vnull');
INSERT INTO STATION VALUES (3,'Mémorial de l abolition de l esclavage', 'Quai de la Fosse','44200','Nantes','Vnull');
INSERT INTO STATION VALUES (4,'Cathédrale St-Pierre', 'Place st-pierre','44000','Nantes','Vnull');

/*==============================================================*/
/* Table : VELO                                                 */
/*==============================================================*/


INSERT INTO VELO VALUES (0,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (1,'modele2',TO_DATE('01/07/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (2,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (3,'modele3',TO_DATE('18/12/2014','dd/mm/yyyy'),'hs','bornette');
INSERT INTO VELO VALUES (4,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (5,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (6,'modele3',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (7,'modele3',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (8,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (9,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (10,'modele2',TO_DATE('18/12/2014','dd/mm/yyyy'),'ok','bornette');
INSERT INTO VELO VALUES (11,'modele1',TO_DATE('18/12/2014','dd/mm/yyyy'),'hs','bornette');



-- Bornettes station 1
INSERT INTO BORNETTE VALUES (0,1,'ok');
INSERT INTO BORNETTE VALUES (1,1,'ok');
INSERT INTO BORNETTE VALUES (2,1,'ok');
INSERT INTO BORNETTE VALUES (3,1,'ok');
INSERT INTO BORNETTE VALUES (4,1,'ok');
INSERT INTO BORNETTE VALUES (5,1,'ok');
INSERT INTO BORNETTE VALUES (6,1,'ok');
INSERT INTO BORNETTE VALUES (7,1,'ok');
-- Bornettes station 2
INSERT INTO BORNETTE VALUES (8,2,'ok');
INSERT INTO BORNETTE VALUES (9,2,'ok');
INSERT INTO BORNETTE VALUES (10,2,'ok');
INSERT INTO BORNETTE VALUES (11,2,'ok');
INSERT INTO BORNETTE VALUES (12,2,'ok');
INSERT INTO BORNETTE VALUES (13,2,'ok');
INSERT INTO BORNETTE VALUES (14,2,'ok');
INSERT INTO BORNETTE VALUES (15,2,'ok');
-- Bornettes station 3
INSERT INTO BORNETTE VALUES (16,3,'ok');
INSERT INTO BORNETTE VALUES (17,3,'ok');
INSERT INTO BORNETTE VALUES (18,3,'ok');
INSERT INTO BORNETTE VALUES (19,3,'ok');
INSERT INTO BORNETTE VALUES (20,3,'ok');
INSERT INTO BORNETTE VALUES (21,3,'ok');
INSERT INTO BORNETTE VALUES (22,3,'ok');
INSERT INTO BORNETTE VALUES (23,3,'ok');
-- Bornettes station 4
INSERT INTO BORNETTE VALUES (24,4,'ok');
INSERT INTO BORNETTE VALUES (25,4,'ok');
INSERT INTO BORNETTE VALUES (26,4,'ok');
INSERT INTO BORNETTE VALUES (27,4,'ok');
INSERT INTO BORNETTE VALUES (28,4,'ok');
INSERT INTO BORNETTE VALUES (29,4,'ok');
INSERT INTO BORNETTE VALUES (30,4,'ok');
INSERT INTO BORNETTE VALUES (31,4,'ok');




/*==============================================================*/
/* Table : "DATE"
/*==============================================================*/
INSERT INTO "DATE" VALUES (TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));


/*==============================================================*/
/* Table : VELOBORNETTE                                         */
/*==============================================================*/

INSERT INTO VELOBORNETTE VALUES (0,0, TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (1,1,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (2,2,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (3,3,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (4,4,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (5,5,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (6,6,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (7,7,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (8,8,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (9,9,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (10,10,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));
INSERT INTO VELOBORNETTE VALUES (11,11,TO_DATE('21-01-2015 08:30','dd-mm-yyyy HH24:MI'));



/*==============================================================*/
/* Table : PLAGEHORAIRE                                         ok*/
/*==============================================================*/


INSERT INTO PLAGEHORAIRE VALUES (1,0,TO_DATE('08:00','HH24:MI'),TO_DATE('14:59','HH24:MI'),'Vnul');
INSERT INTO PLAGEHORAIRE VALUES (1,1,TO_DATE('15:00','HH24:MI'),TO_DATE('17:00','HH24:MI'),'Vmoins');
INSERT INTO PLAGEHORAIRE VALUES (1,2,TO_DATE('17:00','HH24:MI'),TO_DATE('07:59','HH24:MI'),'Vplus');

INSERT INTO PLAGEHORAIRE VALUES (2,0,TO_DATE('08:00','HH24:MI'),TO_DATE('14:59','HH24:MI'),'Vnul');
INSERT INTO PLAGEHORAIRE VALUES (2,1,TO_DATE('15:00','HH24:MI'),TO_DATE('17:00','HH24:MI'),'Vplus');
INSERT INTO PLAGEHORAIRE VALUES (2,2,TO_DATE('17:00','HH24:MI'),TO_DATE('07:59','HH24:MI'),'Vmoins');

INSERT INTO PLAGEHORAIRE VALUES (3,0,TO_DATE('08:00','HH24:MI'),TO_DATE('14:59','HH24:MI'),'Vplus');
INSERT INTO PLAGEHORAIRE VALUES (3,1,TO_DATE('15:00','HH24:MI'),TO_DATE('17:00','HH24:MI'),'Vmoins');
INSERT INTO PLAGEHORAIRE VALUES (3,2,TO_DATE('17:00','HH24:MI'),TO_DATE('07:59','HH24:MI'),'Vnul');

INSERT INTO PLAGEHORAIRE VALUES (4,0,TO_DATE('08:00','HH24:MI'),TO_DATE('14:59','HH24:MI'),'Vmoins');
INSERT INTO PLAGEHORAIRE VALUES (4,1,TO_DATE('15:00','HH24:MI'),TO_DATE('17:00','HH24:MI'),'Vplus');
INSERT INTO PLAGEHORAIRE VALUES (4,2,TO_DATE('17:00','HH24:MI'),TO_DATE('07:59','HH24:MI'),'Vnul');

/*==============================================================*/
/* Table : VEHICULE                                            ok */
/*==============================================================*/
create table VEHICULE 
(
   IMMATRICULATION      INTEGER              not null,
   MODELE               VARCHAR2(100),
   DATE_DEBUT_SERVICE   DATE,
   constraint PK_VEHICULE primary key (IMMATRICULATION)
);

INSERT INTO VEHICULE VALUES (0,"Renault Express",TO_DATE('19-09-2013','dd-mm-yyyy');
INSERT INTO VEHICULE VALUES (1,"Renault Express",TO_DATE('21-01-2015','dd-mm-yyyy');
INSERT INTO VEHICULE VALUES (2,"Renault Express",TO_DATE('22-01-2015','dd-mm-yyyy');
INSERT INTO VEHICULE VALUES (3,"Renault Express",TO_DATE('23-01-2015','dd-mm-yyyy');



/*==============================================================*/
/* Table : ROUTINE                                              ok*/
/*==============================================================*/
create table ROUTINE 
(
   NUM_ROUTINE          INTEGER              not null,
   NUM_SUPERVISEUR      INTEGER              not null,
   IMMATRICULATION      INTEGER              not null,
   "DATE"               DATE,
   constraint PK_ROUTINE primary key (NUM_ROUTINE)
);


INSERT INTO ROUTINE (0,0,0,TO_DATE('22-01-2015','dd-mm-yyyy'));


/*==============================================================*/
/* Table : ACTION                                               ok*/
/*==============================================================*/
create table ACTION(
   NUM_ACTION           INTEGER              not null,
   NUM_ROUTINE          INTEGER              not null,
   LIEU                 VARCHAR2(100),
   COMMENTAIRE          VARCHAR2(100),
   ETAT                 VARCHAR2(10) CHECK( ETAT IN ('valide', 'invalide','null') ),
   NOTIFICATION         VARCHAR2(100),
   PRIORITE				INTEGER,
   constraint PK_ACTION primary key (NUM_ACTION));
   
   
INSERT INTO ACTION VALUES (0,0,"Station 1","Aller à la Station 1",'null',' ',1);
INSERT INTO ACTION VALUES (1,0,"Station 1","Contrôler et réparer bornette",'null',' ',2);
INSERT INTO ACTION VALUES (2,0,"Station 1","Déplacer 5 vélos de la station 1 à la station 2",'null',' ',3);
