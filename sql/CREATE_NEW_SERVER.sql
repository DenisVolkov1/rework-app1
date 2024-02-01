-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<volkov>
-- Create date: <01/02/2024>
-- Description:	<>
-- =============================================
ALTER PROCEDURE dbo.CREATE_NEW_SERVER 
	-- Add the parameters for the stored procedure here
	@PROJECT nvarchar(90) = '',
	@NEW_SERVER nvarchar(90) = '',
	@COPY_AS_SERVER nvarchar(90) = ''
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	--------------
	-- Проверки START--
	--------------
	IF(@PROJECT IS NULL OR @NEW_SERVER IS NULL) THROW 51000, 'NULL не допустим для передачи как параметр!', 1; 

	IF(@PROJECT ='' OR @NEW_SERVER ='') THROW 51000, 'Заполните все обязятельные поля!', 1;

	IF NOT EXISTS (SELECT * FROM dbo.PROJECT WHERE NAME=@PROJECT) THROW 51000, 'Нет такого имени проекта!', 1;

	IF EXISTS (SELECT * FROM dbo.REWORKDETAIL WHERE [SERVER]=@NEW_SERVER) THROW 51000, 'Уже есть такой!', 1;

	IF(@COPY_AS_SERVER != '' AND @COPY_AS_SERVER IS NOT NULL) IF NOT EXISTS (SELECT * FROM dbo.REWORKDETAIL WHERE [SERVER]=@COPY_AS_SERVER) THROW 51000, 'Нет такого имени сервера!', 1;
	--------------
	-- Проверки END--
	--------------

	INSERT INTO [REWORKBASE].[dbo].[REWORKDETAIL] 
						(REWORKNUMBER,                [SERVER],[STATUS], EDITDATE)
	SELECT DISTINCT   rd.REWORKNUMBER, @NEW_SERVER AS [SERVER],
		IIF(@COPY_AS_SERVER != '' AND @COPY_AS_SERVER IS NOT NULL, rd.[STATUS], '') AS [STATUS],
		IIF(@COPY_AS_SERVER != '' AND @COPY_AS_SERVER IS NOT NULL, rd.EDITDATE, NULL) AS EDITDATE
	  FROM [REWORKBASE].[dbo].[REWORK] AS r
		JOIN [REWORKBASE].[dbo].[REWORKDETAIL] AS rd
			ON r.REWORKNUMBER = rd.REWORKNUMBER
	  WHERE 
		PROJECT = @PROJECT AND 
		[SERVER] = IIF(@COPY_AS_SERVER != '' AND @COPY_AS_SERVER IS NOT NULL, @COPY_AS_SERVER, [SERVER]);

END
GO
