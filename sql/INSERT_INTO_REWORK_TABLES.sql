USE [REWORKBASE]
GO
/****** Object:  StoredProcedure [dbo].[INSERT_INTO_REWORK_TABLES]    Script Date: 15.01.2023 0:11:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Volokov Denis>
-- Create date: <14.01.2023>
-- Description:
-- Перенос данных из обменнных таблиц M22 соответственно.
-- =============================================

ALTER PROCEDURE [dbo].[INSERT_INTO_REWORK_TABLES]
AS
BEGIN
	DELETE FROM dbo.M22 WHERE [Название доработки/исправления, ссылкой на Redmine] IS NULL;
	-------------------------------------------
	;WITH cde AS (

	SELECT 
		[Название доработки/исправления, ссылкой на Redmine] AS [1],
		[Название доработки/исправления, ссылкой на Redmine]+'_'+CAST(ROW_NUMBER() OVER (PARTITION BY [Название доработки/исправления, ссылкой на Redmine] ORDER BY [Название доработки/исправления, ссылкой на Redmine]) AS nvarchar) AS [2]
	  FROM [REWORKBASE].[dbo].[M22] AS u
	  WHERE [Название доработки/исправления, ссылкой на Redmine] IN(SELECT [Название доработки/исправления, ссылкой на Redmine]
																	FROM [REWORKBASE].[dbo].[M22]
																	GROUP BY [Название доработки/исправления, ссылкой на Redmine]
																	HAVING 
																	COUNT([Название доработки/исправления, ссылкой на Redmine])>1)

	)
	UPDATE cde SET [1] = [2];
	--- M22 ----------------------------------------------------------------------------
	--[REWORK]
	INSERT INTO [dbo].[REWORK] 
		 ([DESCRIPTION],[TASK],[TASKMONETKA],[ADDDATE],[EDITDATE])
	SELECT [Название доработки/исправления, ссылкой на Redmine],[Задача],[Задача Монетки], IIF([DEV Дата] IS NULL,IIF([BER-RC Дата] IS NULL,GETDATE(),[BER-RC Дата]), [DEV Дата]) , IIF([DEV Дата] IS NULL,IIF([BER-RC Дата] IS NULL,GETDATE(),[BER-RC Дата]), [DEV Дата])
	FROM [dbo].[M22];
	----
	--[REWORKDETAIL]
	----
	INSERT INTO [dbo].[REWORKDETAIL] 
		 ([REWORKNUMBER] ,[SERVER] ,[STATUS] ,[ADDDATE] ,[EDITDATE])
	SELECT r.[REWORKNUMBER],'Дев сервер'  ,[DEV]      ,[DEV Дата],[DEV Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION
	UNION ALL
	SELECT r.[REWORKNUMBER],'ЕКБ ТЭЦ'  ,[BER-RC]      ,[BER-RC Дата],[BER-RC Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION
	UNION ALL
	SELECT r.[REWORKNUMBER],'ЕКБ РЦ Берёзовский'  ,[BER]      ,[BER Дата],[BER Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION
	UNION ALL
	SELECT r.[REWORKNUMBER],'Новосибирск'  ,[NSK]      ,[NSK Дата],[NSK Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION
	UNION ALL
	SELECT r.[REWORKNUMBER],'Нефтеюганск'  ,[NFU]      ,[NFU Дата],[NFU Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION
	UNION ALL
	SELECT r.[REWORKNUMBER],'Уфа'  ,[UFA]      ,[UFA Дата],[UFA Дата]
	FROM dbo.REWORK as r
		JOIN dbo.M22 as m
			ON m.[Название доработки/исправления, ссылкой на Redmine] = r.DESCRIPTION


	UPDATE dbo.REWORK SET TASK = REPLACE(REPLACE(TASK,' ',''),'#','') WHERE TASK LIKE '%#%';

	UPDATE dbo.REWORK SET TASK = REPLACE(REPLACE(TASK,' ',''),'/',',') WHERE TASK LIKE '%/%';
	
	UPDATE dbo.REWORK SET TASK = '11081,11353' WHERE TASK = '11081, 11353'
	UPDATE dbo.REWORK SET TASK = '12883,8223' WHERE TASK = '12883 8223'

	UPDATE dbo.REWORKDETAIL SET STATUS = 'V' where STATUS like '%V%';

	UPDATE dbo.REWORKDETAIL 
		SET STATUS= (CASE  
						WHEN STATUS IS NULL THEN 'NEW'
						WHEN STATUS = 'V'   THEN 'OK' 
					END)
END;

