USE [SCPRD]
GO
/****** Object:  StoredProcedure [wmwhse1].[INSERT_INTO_REWORK_TABLES]    Script Date: 30.11.2021 22:42:23 ******/
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
	SELECT * INTO #REWORK_EXCEL_FILE_WMS10 FROM [SCPRD].[dbo].[REWORK_EXCEL_FILE_WMS10] WHERE НОМЕР IS NOT NULL 
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

				INSERT INTO [dbo].[REWORK]([WMS],[REWORKNUMBER],[RESOURCE],[WIKILINK],[DESCRIPTION])
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
	----Апдейтим приставку для пути файлового ресурса  UPDATE_SCE10
	UPDATE  dbo.REWORK 
		SET RESOURCE = REPLACE(RESOURCE,@prefix_link,'\\10.1.8.66\файловый обмен\_MISHA\UPDATE_SCE10')
	WHERE RESOURCE IS NOT NULL AND WMS = @WMS_10


	------------------------------------------------------------------------------------------------
	-- EXCEL_FILE_WMS11 ----------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------
	-------------------------------------------
	DECLARE @WMS_11_0_3 nvarchar(50)  = 'INFOR_SCE_11_0_3'
	-------------------------------------------
	IF OBJECT_ID('tempdb..#REWORK_EXCEL_FILE_WMS11') IS NOT NULL DROP TABLE #REWORK_EXCEL_FILE_WMS11;  
	SELECT * INTO #REWORK_EXCEL_FILE_WMS11 FROM [SCPRD].[dbo].[REWORK_EXCEL_FILE_WMS11]  WHERE НОМЕР IS NOT NULL 
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

				INSERT INTO [dbo].[REWORK]([WMS],[REWORKNUMBER],[RESOURCE],[WIKILINK],[DESCRIPTION])
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
								FROM dbo.REWORK_EXCEL_FILE_WMS11 WHERE ['+@COLUMN_NAME+'] IS NOT NULL AND НОМЕР = '''+@NOMER+'''')

							EXEC('DELETE FROM #COLUMN_NAME_for_WMS11 WHERE COLUMN_NAME = '''+@COLUMN_NAME+'''')
						 END;
						 ------
		DELETE FROM #REWORK_EXCEL_FILE_WMS11 WHERE НОМЕР = @NOMER 
	END
	----Апдейтим приставку для пути файлового ресурса  UPDATE_SCE11
	UPDATE  dbo.REWORK 
		SET RESOURCE = REPLACE(RESOURCE,@prefix_link,'\\10.1.8.66\файловый обмен\_MISHA\UPDATE_SCE11')
	WHERE RESOURCE IS NOT NULL AND WMS = @WMS_11_0_3


END;

