-- FUNCTION: public.getlocaldatetime(timestamp without time zone)

-- DROP FUNCTION IF EXISTS public.getNumberRework(suffix varchar);

CREATE OR REPLACE FUNCTION public.getNumberRework(suffix varchar)
    RETURNS varchar
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare
	ret text;
begin 

DROP TABLE IF EXISTS allNumbers;
CREATE TEMP TABLE allNumbers (
	NUM varchar NOT NULL
);
INSERT INTO allNumbers 
SELECT
  CASE
  	WHEN i BETWEEN 0 AND 9    THEN '000'||i
	WHEN i BETWEEN 10 AND 99   THEN '00'||i
	WHEN i BETWEEN 100 AND 999  THEN '0'||i
	WHEN i BETWEEN 1000 AND 9999 THEN i::text 
  END AS NUM 
FROM
  generate_series(1,9999) i;
suffix := upper(suffix);

DROP TABLE IF EXISTS temp_return_value;
CREATE TEMP TABLE IF NOT EXISTS temp_return_value 
AS
select NUM from allNumbers
EXCEPT		
select replace(reworknumber,suffix,'') AS NUM
from public.rework 
where reworknumber SIMILAR TO suffix||'[0-9][0-9][0-9][0-9]' escape '?';

select suffix||NUM into ret from temp_return_value order by NUM limit 1;
	return ret;
end;	
$BODY$;

ALTER FUNCTION public.getNumberRework(suffix varchar)
    OWNER TO postgres;

COMMENT ON FUNCTION public.getNumberRework(suffix varchar)
    IS '-- =============================================
-- Author:		<VO,,DENIS>
-- Create date: <09/05/2022>
-- Description:	<Возвращает последовательный номер "доработки">
-- Если на веб-форме указано что присвоить номер автоматически, то номер формата: суффикс в верхнем регистре + "[0-9][0-9][0-9][0-9]" ';
