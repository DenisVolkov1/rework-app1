-- CREATE DATABASE [REWORKBASE] 
USE [REWORKBASE]
GO
/****** Create tables  ******/
--DROP TABLE [dbo].[REWORK] 
CREATE TABLE [dbo].[REWORK](
	[REWORKNUMBER] [int] IDENTITY(1,1) NOT NULL,
	[DESCRIPTION] [nvarchar](max) NOT NULL,
	[TASK] [nvarchar](150) NULL,
	[TASKMONETKA] [nvarchar](150) NULL,
	ISDELETED bit DEFAULT 0 NOT NULL,
	[ADDDATE] [datetime] NOT NULL,
	[ADDWHO] [nvarchar](20) NULL,
	[EDITWHO] [nvarchar](20) NULL,
	[EDITDATE] [datetime] NOT NULL,
	CONSTRAINT [PK_REWORK] PRIMARY KEY CLUSTERED ([REWORKNUMBER])
)

ALTER TABLE [dbo].[REWORK] ADD  CONSTRAINT [DF__REWORK__ADDDA__]  DEFAULT (getdate()) FOR [ADDDATE]
GO

ALTER TABLE [dbo].[REWORK] ADD  CONSTRAINT [DF__REWORK__ADDWH__]  DEFAULT (user_name()) FOR [ADDWHO]
GO

ALTER TABLE [dbo].[REWORK] ADD  CONSTRAINT [DF__REWORK__EDITW__]  DEFAULT (user_name()) FOR [EDITWHO]
GO

ALTER TABLE [dbo].[REWORK] ADD  CONSTRAINT [DF__REWORK__EDITD__]  DEFAULT (getdate()) FOR [EDITDATE]
GO

--DROP TABLE [dbo].[REWORKDETAIL] 
CREATE TABLE [dbo].[REWORKDETAIL](
	[SERIALKEY] [int] IDENTITY(1,1) NOT NULL,
	[REWORKNUMBER] [int] NOT NULL,
	[SERVER] [nvarchar](30) NOT NULL,
	[STATUS] [nvarchar](30) NOT NULL,
	[ADDDATE] [datetime] NULL,
	[ADDWHO] [nvarchar](20) NULL,
	[EDITWHO] [nvarchar](20) NULL,
	[EDITDATE] [datetime] NULL,
 CONSTRAINT [PK_REWORKDETAIL] PRIMARY KEY CLUSTERED ([REWORKNUMBER],[SERVER])
 )

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKDETAIL__ADDWH__]  DEFAULT (user_name()) FOR [ADDWHO]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKDETAIL__EDITW__]  DEFAULT (user_name()) FOR [EDITWHO]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKDETAIL__EDITD__]  DEFAULT (getdate()) FOR [EDITDATE]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKDETAIL__STATUS__]  DEFAULT ('') FOR [STATUS]
GO

ALTER TABLE [dbo].[REWORKDETAIL]  WITH CHECK ADD  CONSTRAINT [FK_REWORKDETAIL_REWORK] FOREIGN KEY([REWORKNUMBER])
REFERENCES [dbo].[REWORK] ([REWORKNUMBER])
GO

ALTER TABLE [dbo].[REWORKDETAIL] CHECK CONSTRAINT [FK_REWORKDETAIL_REWORK]
GO


USE [REWORKBASE]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Volokov Denis>
-- Create date: <31.01.2023>
-- Description:
-- Создание backup базы данных.
-- =============================================

CREATE PROCEDURE [dbo].[CREATE_BACKUP_PROC](
	@path_MSSQLBackup nvarchar(512)
)
AS
BEGIN
	
	--update enterprise.CONFIGURATION set currentvalue='2' where component = 'WMApp' and parm = 'TransactionIsolationLevel'
	DECLARE @name NVARCHAR(256) -- database name  
	DECLARE @path NVARCHAR(512) -- path for backup files  
	DECLARE @fileName NVARCHAR(512) -- filename for backup  
	DECLARE @fileDate NVARCHAR(40) -- used for file name
 
	-- specify database backup directory
	SET @path = @path_MSSQLBackup
 
	-- specify filename format
	SELECT @fileDate = CONVERT(NVARCHAR(20),GETDATE(),112) 
	print('-----START BACKUPING-----') 
	DECLARE db_cursor CURSOR READ_ONLY FOR  
	SELECT name 
	FROM master.sys.databases 
	WHERE name IN ('REWORKBASE')  -- exclude these databases
	AND state = 0 -- database is online
	AND is_in_standby = 0 -- database is not read only for log shipping
 
	OPEN db_cursor   
	FETCH NEXT FROM db_cursor INTO @name   
 
	WHILE @@FETCH_STATUS = 0   
	BEGIN   
		SET @fileName = @path + @name + '_' + @fileDate + '.bak'  
		print('-----START('+@name+')-----')
		BACKUP DATABASE @name TO DISK = @fileName WITH COMPRESSION
		print('-----END-----')
		FETCH NEXT FROM db_cursor INTO @name   
	END
	CLOSE db_cursor   
	DEALLOCATE db_cursor
END;
