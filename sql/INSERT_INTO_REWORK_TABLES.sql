USE [SCPRD]
GO
/****** Object:  StoredProcedure [wmwhse1].[INSERT_INTO_REWORK_TABLES]    Script Date: 19.11.2021 20:46:24 ******/
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


CREATE PROCEDURE [dbo].[INSERT_INTO_REWORK_TABLES] 
	-- Add the parameters for the stored procedure here
AS
BEGIN

	-------------------------------------------
	DECLARE @WMS_10 nvarchar(50)  = 'INFOR_WMS_10'
	-------------------------------------------
	DECLARE 
		@NOMER nvarchar(90), @COLUMN_NAME nvarchar(90)
	--- EXCEL_FILE_WMS10 ----------------------------------------------------------------------------
	IF OBJECT_ID('tempdb..#REWORK_EXCEL_FILE_WMS10') IS NOT NULL DROP TABLE #REWORK_EXCEL_FILE_WMS10;  
	SELECT * INTO #REWORK_EXCEL_FILE_WMS10 FROM [SCPRD].[dbo].[REWORK_EXCEL_FILE_WMS10]
	---
	WHILE(EXISTS(SELECT TOP 1 1 FROM #REWORK_EXCEL_FILE_WMS10))
	BEGIN
	 IF OBJECT_ID('tempdb..#COLUMN_NAME_for_WMS10') IS NOT NULL DROP TABLE #COLUMN_NAME_for_WMS10;
	   SELECT 
			COLUMN_NAME
		INTO #COLUMN_NAME_for_WMS10
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME = 'REWORK_EXCEL_FILE_WMS10' AND COLUMN_NAME NOT IN ('№ п/п', 'НОМЕР', 'ССЫЛКА НА WIKI', 'ОПИСАНИЕ')
		-----
			SET @NOMER = (SELECT TOP 1 НОМЕР FROM #REWORK_EXCEL_FILE_WMS10)
			--SELECT 'NOMER= ' + @NOMER;

				INSERT INTO [dbo].[REWORK]([WMS],[REWORKNUMBER],[WIKILINK],[DESCRIPTION])
				SELECT
					@WMS_10 AS [WMS],
					НОМЕР AS [REWORKNUMBER],
					[ССЫЛКА НА WIKI] AS [WIKILINK],
					ОПИСАНИЕ AS [DESCRIPTION]
				FROM #REWORK_EXCEL_FILE_WMS10
				WHERE  НОМЕР = @NOMER; 

						 WHILE(EXISTS(SELECT TOP 1 1 FROM #COLUMN_NAME_for_WMS10))
						 BEGIN
							SELECT TOP 1 @COLUMN_NAME = COLUMN_NAME FROM #COLUMN_NAME_for_WMS10
							--SELECT @COLUMN_NAME AS COLUMN_NAME
							EXEC('
								INSERT INTO [dbo].[REWORKDETAIL]([WMS],[REWORKNUMBER],[PROJECT],[STATUS])
								SELECT 
									'''+@WMS_10+''' AS [WMS],
									НОМЕР	        AS [REWORKNUMBER],
									'''+@COLUMN_NAME+''' AS [PROJECT],
									['+@COLUMN_NAME+']      AS [STATUS]
								FROM dbo.REWORK_EXCEL_FILE_WMS10 WHERE ['+@COLUMN_NAME+'] IS NOT NULL AND НОМЕР = '''+@NOMER+'''')

							EXEC('DELETE FROM #COLUMN_NAME_for_WMS10 WHERE COLUMN_NAME = '''+@COLUMN_NAME+'''')
						 END;
						 ------
		DELETE FROM #REWORK_EXCEL_FILE_WMS10 WHERE НОМЕР = @NOMER 
	END
	------------------------------------------------------------------------------------------------
	-- EXCEL_FILE_WMS11 ----------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------
	-------------------------------------------
	DECLARE @WMS_11_0_3 nvarchar(50)  = 'INFOR_SCE_11_0_3'
	-------------------------------------------
	IF OBJECT_ID('tempdb..#REWORK_EXCEL_FILE_WMS11') IS NOT NULL DROP TABLE #REWORK_EXCEL_FILE_WMS11;  
	SELECT * INTO #REWORK_EXCEL_FILE_WMS11 FROM [SCPRD].[dbo].[REWORK_EXCEL_FILE_WMS11]
	---
	WHILE(EXISTS(SELECT TOP 1 1 FROM #REWORK_EXCEL_FILE_WMS11))
	BEGIN
	   IF OBJECT_ID('tempdb..#COLUMN_NAME_for_WMS11') IS NOT NULL DROP TABLE #COLUMN_NAME_for_WMS11;
	   SELECT TOP 1
			COLUMN_NAME
		INTO #COLUMN_NAME_for_WMS11
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME = 'REWORK_EXCEL_FILE_WMS11' AND COLUMN_NAME NOT IN ('№ п/п', 'НОМЕР', 'ССЫЛКА НА WIKI', 'ОПИСАНИЕ')
		-----
			SET @NOMER = (SELECT TOP 1 НОМЕР FROM #REWORK_EXCEL_FILE_WMS11)
			--SELECT 'NOMER= ' + @NOMER;

				INSERT INTO [dbo].[REWORK]([WMS],[REWORKNUMBER],[WIKILINK],[DESCRIPTION])
				SELECT
					@WMS_11_0_3 AS [WMS],
					НОМЕР AS [REWORKNUMBER],
					[ССЫЛКА НА WIKI] AS [WIKILINK],
					ОПИСАНИЕ AS [DESCRIPTION]
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
								FROM dbo.REWORK_EXCEL_FILE_WMS11 WHERE ['+@COLUMN_NAME+'] IS NOT NULL AND НОМЕР = '''+@NOMER+'''')

							EXEC('DELETE FROM #COLUMN_NAME_for_WMS11 WHERE COLUMN_NAME = '''+@COLUMN_NAME+'''')
						 END;
						 ------
		DELETE FROM #REWORK_EXCEL_FILE_WMS11 WHERE НОМЕР = @NOMER 
	END
END;

