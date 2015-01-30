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



SELECT l1.DATE_HEURE_DEBUT 
	FROM LOCATION l1, LOCATION l2
	WHERE l1.ID_LOCATION = l2.ID_LOCATION

to_date('12/01/2015','dd/mm/yyyy') - to_date('01/01/01 00:00:00','dd/mm/yy HH24:MI:SS');
AND l1.DATE_HEURE_FIN != to_date('01/01/01 00:00:00','dd/mm/yy HH24:MI:SS');


AND l1.DATE_HEURE_FIN!=l2.DATE_HEURE_FIN
CREATE OR REPLACE TRIGGER gen_amende
AFTER update on LOCATION 
DECLARE
	dateDebut DATE;
	diff float;
BEGIN
	dateDebut:=0;
	SELECT l1.DATE_HEURE_DEBUT into dateDebut
	FROM LOCATION l1, LOCATION l2
	WHERE l1.ID_LOCATION = l2.ID_LOCATION 
	AND l1.DATE_HEURE_FIN!=l2.DATE_HEURE_FIN;
	diff:= (sysdate - dateDebut)*24;
	if diff > 12 then
		raise_application_error(-20100,'Vous avez une amende');
	end if;
END; 
/
update LOCATION set DATE_HEURE_DEBUT = to_date('21/01/2015','dd/mm/yyyy') where ID_LOCATION=153;







/*Select COLUMN_NAME from USER_TAB_COLUMNS where TABLE_NAME='RESERVATION';*/


/*==============================================================*/
/* TRIGGER : Plusieurs amendes  (à faire sur Reservation et location */
/*==============================================================*/

/*==============================================================*/
/* TRIGGER : Dates de reservations Res                      */
/*==============================================================*/
CREATE OR REPLACE TRIGGER PlusieursAmendesRes
BEFORE INSERT ON RESERVATION
FOR EACH ROW
DECLARE
nbAmendes integer;
nbAmendesPlusUnMois integer;
BEGIN
	SELECT count(*) INTO nbAmendesPlusUnMois FROM Amende NATURAL JOIN Location Where (id_client = :new.id_client AND (reglement = 'non') AND (MONTHS_BETWEEN(sysdate,date_amende) > 0));  
	SELECT count(*) INTO nbAmendes FROM Amende NATURAL JOIN Location Where id_client = :new.id_client AND reglement = 'non';
	IF (nbAmendes >= 2) THEN
		raise_application_error(-20100,'Deux amendes non régularisées');
	END IF;
	
	IF (nbAmendesPlusUnMois > 0) THEN
		raise_application_error(-20100,'Une amende de plus dun mois non régularisée');
	END IF;
END;
/


/*==============================================================*/
/* TEST	 							                            */
/*==============================================================*/
INSERT INTO RESERVATION VALUES (0,0,0,TO_DATE('22/01/2015','dd/mm/yyyy'));


/*==============================================================*/
/* TRIGGER : Dates de reservations                              */
/*==============================================================*/
CREATE OR REPLACE TRIGGER PlusieursAmendesLocation
BEFORE INSERT ON LOCATION
FOR EACH ROW
DECLARE
nbAmendes integer;
nbAmendesPlusUnMois integer;
BEGIN
	SELECT count(*) INTO nbAmendesPlusUnMois FROM Amende NATURAL JOIN Location Where (id_client = :new.id_client AND (reglement = 'non') AND (MONTHS_BETWEEN(sysdate,date_amende) > 0));  
	SELECT count(*) INTO nbAmendes FROM Amende NATURAL JOIN Location Where id_client = :new.id_client AND reglement = 'non';
	IF (nbAmendes >= 2) THEN
		raise_application_error(-20100,'Deux amendes non régularisées');
	END IF;
	
	IF (nbAmendesPlusUnMois > 0) THEN
		raise_application_error(-20100,'Une amende de plus dun mois non régularisée');
	END IF;
END;
/

/*==============================================================*/
/* TEST LOCATION						                                    */
/*==============================================================*/
INSERT INTO LOCATION VALUES (001,0,0,TO_DATE('10-12-2014 08:30','dd-mm-yyyy HH24:MI'),TO_DATE('10-12-2014 15:30','dd-mm-yyyy HH24:MI'));
-- amende > 1 mois
INSERT INTO AMENDE VALUES (001,001,TO_DATE('10-12-2014 18:30','dd-mm-yyyy HH24:MI'),1000,'non');
INSERT INTO LOCATION VALUES (002,0,0,TO_DATE('27-01-2015 08:30','dd-mm-yyyy HH24:MI'),TO_DATE('27-01-2015 15:30','dd-mm-yyyy HH24:MI'));



-- 2 amendes < 1 mois

-- pour 2 amendes
INSERT INTO LOCATION VALUES (003,0,0,TO_DATE('27-01-2015 08:30','dd-mm-yyyy HH24:MI'),TO_DATE('27-01-2015 15:30','dd-mm-yyyy HH24:MI'));
INSERT INTO AMENDE VALUES (002,003,TO_DATE('27-01-2014 15:30','dd-mm-yyyy HH24:MI'),1000,'non');
INSERT INTO AMENDE VALUES (003,003,TO_DATE('27-01-2014 15:31','dd-mm-yyyy HH24:MI'),1000,'non');
INSERT INTO LOCATION VALUES (004,0,0,TO_DATE('27-01-2015 08:30','dd-mm-yyyy HH24:MI'),TO_DATE('27-01-2015 15:30','dd-mm-yyyy HH24:MI'));







/*==============================================================*/
/* TRIGGER : Vérification code généré pour non abonné : unique                         */
/*==============================================================*/
SET SERVEROUTPUT ON;
CREATE OR REPLACE TRIGGER VerificationCodeGenere
AFTER INSERT OR UPDATE ON CLIENT
DECLARE  
nbCode integer;
BEGIN
	SELECT count(*) INTO nbCode FROM Client c1,Client c2 Where c1.code_secret = c2.code_secret;
	IF( nbCode > 0 ) THEN
		dbms_output.put_line('code ' || nbCode);
		raise_application_error(-20100,'Ce code secret est déjà utilisé');
	END IF;
END;
/

/*==============================================================*/
/* TEST : Mise à jour code secret non abonné                     */
/*==============================================================*/

INSERT INTO CLIENT VALUES (99,NULL,NULL,'non',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
UPDATE CLIENT SET code_secret = 10 WHERE id_client = 99;
INSERT INTO CLIENT VALUES (100,NULL,NULL,'non',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
UPDATE CLIENT SET code_secret = 10 WHERE id_client = 100;

select id_client from client where code_secret = 10;
SELECT count(id_client) FROM Client Where abonne = 'non'AND code_secret = 10;


/*==============================================================*/
/* TRIGGER : Location vélo                   */
/*==============================================================*/

CREATE OR REPLACE TRIGGER LocationVelo
BEFORE UPDATE ON Velo
FOR EACH ROW

BEGIN

IF(:OLD.position = 'voiture' AND :NEW.position = 'location') THEN
			raise_application_error(-20100,'Un véhicule dans une camionnette ne peut être loué');
END IF;
/* on ne peut pas louer un vélo HS */

if(:NEW.etat = 'HS' AND :OLD.position = 'bornette' AND :NEW.position = 'location') THEN
			raise_application_error(-20100,'Un vélo HS ne peut être loué');

END IF;


IF(:OLD.position = 'location' AND :NEW.position = 'voiture') THEN
			raise_application_error(-20100,'Un vélo loué ne peut aller dans la camionnette');
END IF;

END;
/

/*==============================================================*/
/* TEST : Location d'un vélo				  s                  */
/*==============================================================*/
-- Test camionnette location 
-- avant
INSERT INTO VELO VALUES (99,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','location');
-- après (
UPDATE VELO SET position = 'voiture' WHERE id_velo = 99;

-- Test location camionnette 
-- avant
INSERT INTO VELO VALUES (100,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','voiture');
-- après (
UPDATE VELO SET position = 'location' WHERE id_velo = 100;

-- Test hs
-- avant
INSERT INTO VELO VALUES (101,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'hs','voiture');
-- après (
UPDATE VELO SET position = 'location' WHERE id_velo = 101;


----------------- OK
/*==============================================================*/
/* TRIGGER : Location vélo            NE FONCTIONNE PAS       */ 
/*==============================================================*/

CREATE OR REPLACE TRIGGER RenduVelo
BEFORE UPDATE ON Location
FOR EACH ROW
BEGIN

IF(:OLD.DATE_HEURE_FIN = NULL AND :NEW.DATE_HEURE_FIN != NULL) THEN
	UPDATE VELO SET position = 'bornette' WHERE id_velo = :NEW.id_velo;
END IF;

END;
/

/*==============================================================*/
/* Test : Location vélo                   */
/*==============================================================*/
INSERT INTO VELO VALUES (103,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','location');
INSERT INTO LOCATION VALUES (100,0,102,TO_DATE('27-01-2015 08:30','dd-mm-yyyy HH24:MI'),NULL);
UPDATE LOCATION SET DATE_HEURE_FIN = sysdate WHERE id_location = 100;





/* ---- Vérifier pour 2 véhicules s’ils ne sont pas programmés sur la même station pour un même jour
**/

CREATE OR REPLACE TRIGGER actionIdentiqueVehiculeDiff
AFTER INSERT ON ACTION
DECLARE
nbVehicule integer;
BEGIN
SELECT count(*) into nbVehicule FROM ACTION a1 JOIN ACTION a2 ON a1.lieu_arrive = a2.lieu_arrive NATURAL JOIN Routine
WHERE (date_routine = date_routine  
	AND a1.num_routine != a2.num_routine);
IF ( nbVehicule > 0 ) THEN
			raise_application_error(-20100,'Pas deux véhicules le même jour à une même station');
END IF;
END;
/

INSERT INTO ROUTINE VALUES (99,0,0,sysdate);
INSERT INTO ACTION VALUES (1000,99,'Station 1','Déplacer 5 vélos de la station 1 à la station 2','null',' ',1,5,'Station 2');
 
INSERT INTO ROUTINE VALUES (100,0,1,sysdate);
INSERT INTO ACTION VALUES (1001,100,'Station 1','Déplacer 5 vélos de la station 1 à la station 2','null',' ',1,5,'Station 2');
DELETE ACTION WHERE num_action = 1001;


/*==== -- Insertion d'un vélo dans une bornette ==== */

CREATE OR REPLACE TRIGGER VerificationVeloBornette
BEFORE INSERT ON VeloBornette
FOR EACH ROW
DECLARE
etatV varchar2(10);
etatB varchar2(10);
numStation integer;
BEGIN

SELECT etat into etatV FROM Velo WHERE id_velo = :NEW.id_velo;
SELECT num_station into numStation from Bornette where num_borne = :NEW.num_borne; 
SELECT etat into etatB FROM Bornette where num_borne = :NEW.num_borne;

if(etatB = 'hs') THEN
			raise_application_error(-20100,'Un vélo ne peut être remis sur une bornette hs');
END IF; 

if(etatV = 'hs' AND numStation != 0) THEN
			raise_application_error(-20100,'Un vélo hs ne peut être remis en station');

END IF;

END;
/

/* ==== TEST insertion d'une liaison entre un vélo et une bornette (velo hs)  ===  */

INSERT INTO VELO VALUES (199,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'hs','voiture');
INSERT INTO BORNETTE VALUES(199,1,'ok');
INSERT INTO VELOBORNETTE VALUES(199,199,sysdate);

/* ==== TEST insertion d'une liaison entre un vélo et une bornette (bornette hs)  ===  */
INSERT INTO VELO VALUES (200,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','voiture');
INSERT INTO BORNETTE VALUES(200,1,'hs');
INSERT INTO VELOBORNETTE VALUES(200,200,sysdate);


/*==============================================================*/
/* TRIGGER : Vérification disponibilité réservation  : pas réserver vélo si tous les vélos sont réserves à + ou - 30 min           */
/*==============================================================*/


CREATE OR REPLACE TRIGGER VerifDispoResa
BEFORE INSERT OR UPDATE ON RESERVATION
FOR EACH ROW
DECLARE
    nbResa integer;
    nbBornettes integer;
	nbVeloDispo integer;
BEGIN
    SELECT COUNT(NUM_RESERVATION) into nbResa from RESERVATION WHERE DATE_RESERVATION > (:new.DATE_RESERVATION - interval '30' minute) and DATE_RESERVATION < :new.DATE_RESERVATION + interval '30' minute AND num_station = :NEW.num_station;
    SELECT count(num_borne) into nbBornettes from Bornette where num_station=:new.NUM_STATION AND etat = 'ok';
	
	select count(id_velo) into nbVeloDispo from Velo NATURAL JOIN VeloBornette NATURAL JOIN Bornette where position = 'bornette' AND num_station = :NEW.num_station;
	
		
	IF(nbVeloDispo < 1) THEN 
		    raise_application_error(-20100, 'Location impossible : Aucun vélo disponible');
	END IF;   

	IF (nbresa >= nbBornettes) THEN
    raise_application_error(-20100, 'Le nombre de reservation maximum a été atteint, crénaux indisponibles');
    END IF;   
END;
/

INSERT INTO STATION VALUES (99,'Test de reservation', 'Place st-test','44000','Nantes','Vnull');
INSERT INTO VELO VALUES (245,'modele1',TO_DATE('21/01/2015','dd/mm/yyyy'),'ok','bornette');
INSERT INTO BORNETTE VALUES(245,99,'ok');
INSERT INTO VELOBORNETTE VALUES(245,245,sysdate);

INSERT INTO RESERVATION VALUES (13,99,0,sysdate);
INSERT INTO RESERVATION VALUES (14,99,1,TO_DATE('28-01-2015 16:50','dd-mm-yyyy HH24:MI'));
INSERT INTO RESERVATION VALUES (15,99,2,TO_DATE('28-01-2015 19:20','dd-mm-yyyy HH24:MI'));

/*==============================================================*/
/* TRIGGER : Location possible par rapport au nombre de bornettes libres et de réservations                        */
/*==============================================================*/
CREATE OR REPLACE TRIGGER LocationPossibleResa
BEFORE INSERT ON LOCATION
FOR EACH ROW
DECLARE
nbResa integer;
numB integer;
numS integer;
nbBornettes integer;
nbVeloDispo integer;

BEGIN
	
	select num_borne into numB from (select num_borne,heure_date from velobornette where id_velo = :NEW.id_velo order by heure_date desc) where rownum = 1;

	select num_station into numS from Bornette where num_borne = numB;

    SELECT COUNT(NUM_RESERVATION) into nbResa from RESERVATION WHERE num_station = numS AND DATE_RESERVATION > (:new.date_heure_debut - interval '30' minute) and DATE_RESERVATION < :new.date_heure_debut + interval '30' minute;

    SELECT count(num_borne) into nbBornettes from Bornette where num_station=numS AND etat = 'ok';
	
	select count(id_velo) into nbVeloDispo from Velo NATURAL JOIN VeloBornette NATURAL JOIN Bornette where position = 'bornette' AND num_station = numS;
	
        -- FAIRE requete nb de bornettes dispo (+ test dans le if)
		
	IF(nbVeloDispo < 1) THEN 
		    raise_application_error(-20100, 'Location impossible : Aucun vélo disponible');
	END IF;
	
	IF (nbresa >= nbBornettes) THEN
    raise_application_error(-20100, 'Location impossible : le nombre de reservation maximum a été atteint, crénaux indisponibles');
    END IF; 
END;
/


-- test : faire une réservation sur une station (la 99) qui a une seule place de libre
INSERT INTO RESERVATION VALUES (13,99,0,sysdate);
-- on veut louer le vélo n°245 de la station n°99 qui a déjà une réservation
INSERT INTO LOCATION VALUES (idlocation_seq.nextval,0,245,sysdate,NULL);
UPDATE VELO SET position = 'location' WHERE id_velo = 245;
--- => le trigger est déclenché 


CREATE SEQUENCE idReservation_seq;

CREATE OR REPLACE TRIGGER idReservation_auto
BEFORE INSERT ON Reservation
FOR EACH ROW
BEGIN
  SELECT idReservation_seq.nextval
  INTO   :new.num_reservation
  FROM   dual;
END;
/




