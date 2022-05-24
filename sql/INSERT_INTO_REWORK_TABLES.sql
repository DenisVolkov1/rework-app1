USE [REWORKBASE]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Volokov,Denis>
-- Create date: <29.08.2021>
-- Description:	INSERT INTO [dbo].[REWORKNUMBER] ,INSERT INTO [dbo].[REWORKPROJECT] from [dbo].[REWORK_EXCEL_FILE_WMS10] and [dbo].[REWORK_EXCEL_FILE_WMS11]
-- Перенос данных из обменнных таблиц REWORK_EXCEL_FILE_WMS10 и REWORK_EXCEL_FILE_WMS11 соответственно.
-- ============================================= 


ALTER PROCEDURE [dbo].[INSERT_INTO_REWORK_TABLES] 
	-- Add the parameters for the stored procedure here
AS
BEGIN

	-------------------------------------------
	DECLARE @WMS_10 nvarchar(50)       = 'INFOR_WMS_10',
	        @prefix_link nvarchar(300) = '../AppData/Roaming/Microsoft/Excel'
	-------------------------------------------
	DECLARE 
		@NOMER nvarchar(90), @COLUMN_NAME nvarchar(90)
	--- EXCEL_FILE_WMS10 ----------------------------------------------------------------------------
	IF OBJECT_ID('tempdb..#REWORK_EXCEL_FILE_WMS10') IS NOT NULL DROP TABLE #REWORK_EXCEL_FILE_WMS10;  
	SELECT * INTO #REWORK_EXCEL_FILE_WMS10 FROM [REWORKBASE].[dbo].[REWORK_EXCEL_FILE_WMS10] WHERE НОМЕР IS NOT NULL 
	---
	WHILE(EXISTS(SELECT TOP 1 1 FROM #REWORK_EXCEL_FILE_WMS10))
	BEGIN
	 IF OBJECT_ID('tempdb..#COLUMN_NAME_for_WMS10') IS NOT NULL DROP TABLE #COLUMN_NAME_for_WMS10;
	   SELECT 
			COLUMN_NAME
		INTO #COLUMN_NAME_for_WMS10
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME = 'REWORK_EXCEL_FILE_WMS10' AND COLUMN_NAME NOT IN ('№ п/п', 'НОМЕР','РЕСУРС', 'ССЫЛКА НА WIKI', 'ОПИСАНИЕ')
		-----
			SET @NOMER = (SELECT TOP 1 НОМЕР FROM #REWORK_EXCEL_FILE_WMS10)
			--SELECT 'NOMER= ' + @NOMER;

				INSERT INTO [REWORKBASE].[dbo].[REWORK]([WMS],[REWORKNUMBER],[RESOURCE],[WIKILINK],[DESCRIPTION])
				SELECT
					@WMS_10 AS [WMS],
					[НОМЕР] AS [REWORKNUMBER],
					[РЕСУРС] AS [RESOURCE],
					[ССЫЛКА НА WIKI] AS [WIKILINK],
					[ОПИСАНИЕ] AS [DESCRIPTION]
				FROM #REWORK_EXCEL_FILE_WMS10
				WHERE  НОМЕР = @NOMER; 

						 WHILE(EXISTS(SELECT TOP 1 1 FROM #COLUMN_NAME_for_WMS10))
						 BEGIN
							SELECT TOP 1 @COLUMN_NAME = COLUMN_NAME FROM #COLUMN_NAME_for_WMS10
							--SELECT @COLUMN_NAME AS COLUMN_NAME
							EXEC('
								INSERT INTO REWORKBASE.[dbo].[REWORKDETAIL]([WMS],[REWORKNUMBER],[PROJECT],[STATUS])
								SELECT 
									'''+@WMS_10+''' AS [WMS],
									НОМЕР	        AS [REWORKNUMBER],
									'''+@COLUMN_NAME+''' AS [PROJECT],
									['+@COLUMN_NAME+']      AS [STATUS]
								FROM REWORKBASE.dbo.REWORK_EXCEL_FILE_WMS10 WHERE ['+@COLUMN_NAME+'] IS NOT NULL AND НОМЕР = '''+@NOMER+'''')

							EXEC('DELETE FROM #COLUMN_NAME_for_WMS10 WHERE COLUMN_NAME = '''+@COLUMN_NAME+'''')
						 END;
						 ------
		DELETE FROM #REWORK_EXCEL_FILE_WMS10 WHERE НОМЕР = @NOMER 
	END
	----Апдейтим приставку для пути файлового ресурса  UPDATE_SCE10
	UPDATE  REWORKBASE.dbo.REWORK 
		SET RESOURCE = REPLACE(RESOURCE,@prefix_link,'\\10.1.8.66\файловый обмен\_MISHA\UPDATE_SCE10')
	WHERE RESOURCE IS NOT NULL AND WMS = @WMS_10


	------------------------------------------------------------------------------------------------
	-- EXCEL_FILE_WMS11 ----------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------
	-------------------------------------------
	DECLARE @WMS_11_0_3 nvarchar(50)  = 'INFOR_SCE_11_0_3'
	-------------------------------------------
	IF OBJECT_ID('tempdb..#REWORK_EXCEL_FILE_WMS11') IS NOT NULL DROP TABLE #REWORK_EXCEL_FILE_WMS11;  
	SELECT * INTO #REWORK_EXCEL_FILE_WMS11 FROM [REWORKBASE].[dbo].[REWORK_EXCEL_FILE_WMS11]  WHERE НОМЕР IS NOT NULL 
	---
	WHILE(EXISTS(SELECT TOP 1 1 FROM #REWORK_EXCEL_FILE_WMS11))
	BEGIN
	   IF OBJECT_ID('tempdb..#COLUMN_NAME_for_WMS11') IS NOT NULL DROP TABLE #COLUMN_NAME_for_WMS11;
	   SELECT
			COLUMN_NAME
		INTO #COLUMN_NAME_for_WMS11
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME = 'REWORK_EXCEL_FILE_WMS11' AND COLUMN_NAME NOT IN ('№ п/п', 'НОМЕР','РЕСУРС', 'ССЫЛКА НА WIKI', 'ОПИСАНИЕ')
		-----
			SET @NOMER = (SELECT TOP 1 НОМЕР FROM #REWORK_EXCEL_FILE_WMS11)
			--SELECT 'NOMER= ' + @NOMER;

				INSERT INTO REWORKBASE.[dbo].[REWORK]([WMS],[REWORKNUMBER],[RESOURCE],[WIKILINK],[DESCRIPTION])
				SELECT
					@WMS_11_0_3 AS [WMS],
					[НОМЕР] AS [REWORKNUMBER],
					[РЕСУРС] AS [RESOURCE],
					[ССЫЛКА НА WIKI] AS [WIKILINK],
					[ОПИСАНИЕ] AS [DESCRIPTION]
				FROM #REWORK_EXCEL_FILE_WMS11
				WHERE  НОМЕР = @NOMER; 

				    WHILE(EXISTS(SELECT TOP 1 1 FROM #COLUMN_NAME_for_WMS11))
				    BEGIN
					SELECT TOP 1 @COLUMN_NAME = COLUMN_NAME FROM #COLUMN_NAME_for_WMS11
					--SELECT @COLUMN_NAME AS COLUMN_NAME
					EXEC('
						INSERT INTO [dbo].[REWORKDETAIL]([WMS],[REWORKNUMBER],[PROJECT],[STATUS])
						SELECT 
							'''+@WMS_11_0_3+''' AS [PROJECT],
							НОМЕР	        AS [REWORKNUMBER],
							'''+@COLUMN_NAME+''' AS [PROJECT],
							['+@COLUMN_NAME+']      AS [STATUS]
						FROM REWORKBASE.dbo.REWORK_EXCEL_FILE_WMS11 WHERE ['+@COLUMN_NAME+'] IS NOT NULL AND НОМЕР = '''+@NOMER+'''')

					EXEC('DELETE FROM #COLUMN_NAME_for_WMS11 WHERE COLUMN_NAME = '''+@COLUMN_NAME+'''')
				    END;
				    ------
		DELETE FROM #REWORK_EXCEL_FILE_WMS11 WHERE НОМЕР = @NOMER 
	END
	----Апдейтим приставку для пути файлового ресурса  UPDATE_SCE11
	UPDATE  REWORKBASE.dbo.REWORK 
		SET RESOURCE = REPLACE(RESOURCE,@prefix_link,'\\10.1.8.66\файловый обмен\_MISHA\UPDATE_SCE11')
	WHERE RESOURCE IS NOT NULL AND WMS = @WMS_11_0_3
END;
---END : mssql SQL-------------------------------------------------------------------
---START : postgres SQL-------------------------------------------------------------------
-- PROCEDURE: public.insert_into_rework_tables()
-- DROP PROCEDURE IF EXISTS public.insert_into_rework_tables();
CREATE OR REPLACE PROCEDURE public.insert_into_rework_tables(
	)
LANGUAGE 'plpgsql'
AS $BODY$
DECLARE
	 WMS_10 CONSTANT  varchar  := 'INFOR_WMS_10';
	 WMS_11_0_3 CONSTANT varchar  := 'INFOR_SCE_11_0_3';
	 prefix_link CONSTANT varchar := '../AppData/Roaming/Microsoft/Excel';
	 NOMER varchar; 
	 COLUMN_NAME_ varchar;
BEGIN
DROP TABLE IF EXISTS temp_REWORK_EXCEL_FILE_WMS10;
CREATE TEMP TABLE IF NOT EXISTS temp_REWORK_EXCEL_FILE_WMS10 
AS SELECT * FROM public.REWORK_EXCEL_FILE_WMS10 WHERE НОМЕР IS NOT NULL;

	WHILE EXISTS(SELECT 1 FROM temp_REWORK_EXCEL_FILE_WMS10 LIMIT 1) LOOP
		   DROP TABLE IF EXISTS temp_COLUMN_NAME_for_WMS10;
		   CREATE TEMP TABLE IF NOT EXISTS temp_COLUMN_NAME_for_WMS10 
		   AS
		   SELECT 
				COLUMN_NAME
			FROM INFORMATION_SCHEMA.COLUMNS
			WHERE TABLE_NAME = 'rework_excel_file_wms10' AND COLUMN_NAME NOT IN ('НОМЕР','РЕСУРС', 'ССЫЛКА НА WIKI', 'ОПИСАНИЕ');
			
			SELECT НОМЕР INTO NOMER FROM temp_REWORK_EXCEL_FILE_WMS10 LIMIT 1;			
				INSERT INTO public.REWORK(WMS,REWORKNUMBER,RESOURCE,WIKILINK,DESCRIPTION)
				SELECT
					WMS_10 AS WMS,
					"НОМЕР" AS REWORKNUMBER,
					"РЕСУРС" AS RESOURCE,
					"ССЫЛКА НА WIKI" AS WIKILINK,
					"ОПИСАНИЕ" AS DESCRIPTION
				FROM temp_REWORK_EXCEL_FILE_WMS10
				WHERE НОМЕР = NOMER;
				
				WHILE EXISTS(SELECT 1 FROM temp_COLUMN_NAME_for_WMS10 LIMIT 1) LOOP
					  SELECT COLUMN_NAME INTO COLUMN_NAME_ FROM temp_COLUMN_NAME_for_WMS10 LIMIT 1;
					  EXECUTE format(
						  		'INSERT INTO public.REWORKDETAIL(WMS,REWORKNUMBER,PROJECT,STATUS)
								SELECT 
									%s AS WMS,
									НОМЕР AS REWORKNUMBER,
									%s AS PROJECT,
									%s AS STATUS
							   FROM public.REWORK_EXCEL_FILE_WMS10
							   WHERE %s IS NOT NULL AND НОМЕР = %s',
						  quote_literal(WMS_10),
						  quote_literal(COLUMN_NAME_),
						  quote_ident(COLUMN_NAME_),
						  quote_ident(COLUMN_NAME_),
						  quote_literal(NOMER));
					  EXECUTE format('DELETE FROM temp_COLUMN_NAME_for_WMS10 WHERE COLUMN_NAME = %s',quote_literal(COLUMN_NAME_));	   
				END LOOP;
			DELETE FROM temp_REWORK_EXCEL_FILE_WMS10 WHERE НОМЕР = NOMER; 		  
	END LOOP;
	----Апдейтим приставку для пути файлового ресурса  UPDATE_SCE10
	UPDATE public.REWORK 
	SET RESOURCE = concat('\\10.1.8.66\файловый обмен\_MISHA\UPDATE_SCE10\',RESOURCE)
	WHERE RESOURCE IS NOT NULL AND WMS = WMS_10 AND LEFT(resource, 7) != 'http://';
	----Апдейтим 'Уст.'
	UPDATE public.REWORKDETAIL SET STATUS='Тест'  where status='?' ;
	UPDATE public.REWORKDETAIL SET STATUS='Уст.'  where status='X' ;
	UPDATE public.REWORKDETAIL SET STATUS='Нов.'  where status='Х' ;
	UPDATE public.REWORKDETAIL SET STATUS='Тест'  where status='N/A' ;
END;
$BODY$;
ALTER PROCEDURE public.insert_into_rework_tables()
    OWNER TO postgres;

COMMENT ON PROCEDURE public.insert_into_rework_tables()
    IS '
-- =============================================
-- Author:		<Volokov,Denis>
-- Create date: <03.05.2022>
-- Description:	INSERT INTO [REWORKNUMBER] ,INSERT INTO [REWORKPROJECT] from [REWORK_EXCEL_FILE_WMS10] and [REWORK_EXCEL_FILE_WMS11]
-- Перенос данных из обменнных таблиц REWORK_EXCEL_FILE_WMS10 и REWORK_EXCEL_FILE_WMS11 соответственно.
-- ============================================= ';
---END : postgres SQL-------------------------------------------------------------------
