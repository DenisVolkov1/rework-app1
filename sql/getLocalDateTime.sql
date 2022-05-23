USE [REWORKBASE]
GO
/****** Object:  UserDefinedFunction [dbo].[getLocalDateTime]    Script Date: 19.11.2021 20:47:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<VO,,DENIS>
-- Create date: <09/11/2021>
-- Description:	<Возвращает местное время>
-- Используется в запросах для перевода на локальное время сервера SQL.
-- =============================================
CREATE FUNCTION [dbo].[getLocalDateTime]
(
	@DateTimeUTC datetime 
)
RETURNS datetime
AS
BEGIN
DECLARE @RESULT datetime
--Different time between locl time ad UTS time.
DECLARE
	@Hour int -- difference in hour 
	SELECT @Hour = DATEDIFF(HOUR,GETUTCDATE(),GETDATE()) -- get value and set in @Hour
	-- Add the T-SQL statements to compute the return value here
	SELECT @RESULT = DATEADD(HOUR,@Hour,@DateTimeUTC);
	-- Return the result of the function
	RETURN @RESULT
END

------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------------
-- FUNCTION: public.getLocalDateTime(timestamp without time zone)

-- DROP FUNCTION IF EXISTS public."getLocalDateTime"(timestamp without time zone);

CREATE OR REPLACE FUNCTION public.getLocalDateTime(DateTimeUTC timestamp)
    RETURNS timestamp
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$

begin 
 	DateTimeUTC := (now()-timezone('utc', now()) + DateTimeUTC);
	return DateTimeUTC;
end;	
$BODY$;

ALTER FUNCTION public.getLocalDateTime(DateTimeUTC timestamp)
    OWNER TO postgres;

COMMENT ON FUNCTION public.getLocalDateTime(DateTimeUTC timestamp)
    IS '-- =============================================
-- Author:		<VO,,DENIS>
-- Create date: <09/11/2021>
-- Description:	<Возвращает местное время>
-- Используется в запросах для перевода на локальное время сервера SQL.';
