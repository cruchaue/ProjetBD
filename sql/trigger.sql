/*Select COLUMN_NAME from USER_TAB_COLUMNS where TABLE_NAME='RESERVATION';*/
/*==============================================================*/
/* TRIGGER : Dates locations                                    */
/*==============================================================*/
CREATE OR REPLACE TRIGGER DatesLocations
BEFORE INSERT OR UPDATE ON LOCATION
FOR EACH ROW
BEGIN
	IF :new.DATE_HEURE_DEBUT > :new.DATE_HEURE_FIN THEN
		raise_application_error(-20100,'La date de début de location doit être infèrieur à la date de fin');
	END IF;
END;
/
/*==============================================================*/
/* TEST						                                    */
/*==============================================================*/
INSERT INTO LOCATION VALUES (001,0,0,TO_DATE('22/01/2015','dd/mm/yyyy'),TO_DATE('21/01/2015','dd/mm/yyyy'));



/*==============================================================*/
/* TRIGGER : Dates véhicules                                    */
/*==============================================================*/
CREATE OR REPLACE TRIGGER DatesVehicule
BEFORE INSERT OR UPDATE ON VEHICULE
FOR EACH ROW
BEGIN
	IF :new.DATE_DEBUT_SERVICE > sysdate THEN
		raise_application_error(-20100,'La date de mise en service du véhicule doit être inférieure à la date actuelle');
	END IF;
END;
/
/*==============================================================*/
/* TEST						                                    */
/*==============================================================*/
INSERT INTO VEHICULE VALUES (001,'Renault trafic', TO_DATE('22/06/2015','dd/mm/yyyy'),10);



/*==============================================================*/
/* TRIGGER : Dates naissances                                   */
/*==============================================================*/
CREATE OR REPLACE TRIGGER DatesClients
BEFORE INSERT OR UPDATE ON CLIENT
FOR EACH ROW
BEGIN
	IF :new.DATE_NAISSANCE > sysdate THEN
		raise_application_error(-20100,'La date de naissance doit etre inferieure a la date actuelle');
	END IF;
END;
/
/*==============================================================*/
/* TEST	 : Dates naissances clients                             */
/*==============================================================*/
INSERT INTO CLIENT VALUES (6,1511948948,1478,'oui','GALLET','Jordan',TO_DATE('01/02/2020','dd/mm/yyyy'),'homme','Chambéry','49 rue de gipsy king',73000,TO_DATE('12/06/2014','dd/mm/yyyy'),TO_DATE('12/01/2014','dd/mm/yyyy'));



/*==============================================================*/
/* TRIGGER : Dates abonnements                                  */
/*==============================================================*/
CREATE OR REPLACE TRIGGER DatesAbonnements
BEFORE INSERT OR UPDATE ON CLIENT
FOR EACH ROW
BEGIN
	IF :new.DATE_DEBUT_ABO > :new.DATE_FIN_ABO THEN
		raise_application_error(-20100,'La date de debut d abonnement doit etre inferieure a la date fin');
	END IF;
END;
/
/*==============================================================*/
/* TEST	 							                            */
/*==============================================================*/
INSERT INTO CLIENT VALUES (80,1511948948,1478,'oui','GALLET','Jordan',TO_DATE('01/02/1992','dd/mm/yyyy'),'homme','Chambéry','49 rue de gipsy king',73000,TO_DATE('12/01/2015','dd/mm/yyyy'),TO_DATE('12/01/2014','dd/mm/yyyy'));




/*==============================================================*/
/* TRIGGER : Dates de reservations                              */
/*==============================================================*/
CREATE OR REPLACE TRIGGER DatesReservations
BEFORE INSERT OR UPDATE ON RESERVATION
FOR EACH ROW
BEGIN
	IF :new.DATE_RESERVATION < sysdate THEN
		raise_application_error(-20100,'La date de reservation doit etre superieur a la date actuelle');
	END IF;
END;
/
/*==============================================================*/
/* TEST	 							                            */
/*==============================================================*/
INSERT INTO RESERVATION VALUES (0,0,0,TO_DATE('22/01/2015','dd/mm/yyyy'));



/*==============================================================*/
/* TRIGGER : Interdire la location multiple                     */
/*==============================================================*/
CREATE OR REPLACE TRIGGER InterdireLocationsMulti
BEFORE INSERT ON LOCATION
FOR EACH ROW
DECLARE
	nbLocation INTEGER;
BEGIN 
	SELECT count(ID_LOCATION) into nbLocation
	FROM LOCATION
	WHERE DATE_HEURE_FIN = TO_DATE('01/01/01 00:00:00','dd/mm/yy HH24:MI:SS')
	AND ID_CLIENT = :new.ID_CLIENT;
	
	IF nbLocation > 0 THEN
		raise_application_error(-20100,'Location multiple de velo interdite');
	END IF;	
END;
/
/*==============================================================*/
/* TEST	 							                            */
/*==============================================================*/
INSERT INTO LOCATION VALUES (10,0,0, TO_DATE('23-01-2015 12:50','dd-mm-yyyy HH24:MI'),'');


CREATE OR REPLACE TRIGGER IdClient_auto
BEFORE INSERT ON CLIENT
FOR EACH ROW
BEGIN
  SELECT idClient_seq.NEXTVAL
  INTO   :new.ID_CLIENT
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER idLocation_auto
BEFORE INSERT ON LOCATION
FOR EACH ROW
BEGIN
  SELECT idLocation_seq.NEXTVAL
  INTO   :new.ID_LOCATION
  FROM   dual;
END;
/



AND l1.DATE_HEURE_FIN != to_date('01/01/01 00:00:00','dd/mm/yy HH24:MI:SS');


CREATE OR REPLACE TRIGGER gen_amende
AFTER update on LOCATION 
DECLARE
	dateDebut DATE;
	heures int;
BEGIN 
	SELECT DATE_HEURE_DEBUT into dateDebut
	FROM LOCATION l1, LOCATION l2
	WHERE l1.ID_LOCATION = l2.ID_LOCATION
	AND l1.DATE_HEURE_FIN!=l2.DATE_HEURE_FIN;
	dateDebut := (sysdate - dateDebut)*24;
	if dateDebut > 12 then
		raise_application_error(-20100,'Vous avez une amende');
	end if;
END; 
/
update LOCATION set DATE_HEURE_DEBUT = to_date('21/01/2015','dd/mm/yyyy') where ID_LOCATION=153;