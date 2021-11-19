USE [SCPRD]
GO


CREATE DATABASE [SCPRD]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SCPRD', FILENAME = N'C:\backup\SCPRD.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'SCPRD_log', FILENAME = N'C:\backup\SCPRD_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SCPRD].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [SCPRD] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [SCPRD] SET ANSI_NULLS OFF 
GO
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORK](
	[SERIALKEY] [int] IDENTITY(1,1) NOT NULL,
	[WMS] [nvarchar](50) NOT NULL,
	[REWORKNUMBER] [nvarchar](50) NOT NULL,
	[WIKILINK] [nvarchar](255) NULL,
	[DESCRIPTION] [nvarchar](max) NULL,
	[ADDDATE] [datetime] NOT NULL,
	[ADDWHO] [nvarchar](18) NULL,
	[EDITWHO] [nvarchar](18) NULL,
	[EDITDATE] [datetime] NOT NULL,
 CONSTRAINT [PK__REWORKNU__E0C27C7F2525AE9B] PRIMARY KEY CLUSTERED 
(
	[WMS] ASC,
	[REWORKNUMBER] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [RE_SERIALKEY_UNIQUE] UNIQUE NONCLUSTERED 
(
	[SERIALKEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[REWORK] ADD  DEFAULT (getutcdate()) FOR [ADDDATE]
GO

ALTER TABLE [dbo].[REWORK] ADD  DEFAULT (user_name()) FOR [ADDWHO]
GO

ALTER TABLE [dbo].[REWORK] ADD  DEFAULT (user_name()) FOR [EDITWHO]
GO

ALTER TABLE [dbo].[REWORK] ADD  DEFAULT (getutcdate()) FOR [EDITDATE]
GO
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORKDETAIL](
	[SERIALKEY] [int] IDENTITY(1,1) NOT NULL,
	[WMS] [nvarchar](50) NOT NULL,
	[REWORKNUMBER] [nvarchar](50) NOT NULL,
	[PROJECT] [nvarchar](80) NOT NULL,
	[STATUS] [nvarchar](30) NULL,
	[ADDDATE] [datetime] NOT NULL,
	[ADDWHO] [nvarchar](18) NULL,
	[EDITWHO] [nvarchar](18) NULL,
	[EDITDATE] [datetime] NOT NULL,
 CONSTRAINT [PK_REWORKDETAIL] PRIMARY KEY CLUSTERED 
(
	[WMS] ASC,
	[REWORKNUMBER] ASC,
	[PROJECT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [PR_SERIALKEY_UNIQUE] UNIQUE NONCLUSTERED 
(
	[SERIALKEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKPRO__ADDDA__58D1301D]  DEFAULT (getutcdate()) FOR [ADDDATE]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKPRO__ADDWH__59C55456]  DEFAULT (user_name()) FOR [ADDWHO]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKPRO__EDITW__5AB9788F]  DEFAULT (user_name()) FOR [EDITWHO]
GO

ALTER TABLE [dbo].[REWORKDETAIL] ADD  CONSTRAINT [DF__REWORKPRO__EDITD__5BAD9CC8]  DEFAULT (getutcdate()) FOR [EDITDATE]
GO

ALTER TABLE [dbo].[REWORKDETAIL]  WITH CHECK ADD  CONSTRAINT [FK_REWORKDETAIL_REWORK] FOREIGN KEY([WMS], [REWORKNUMBER])
REFERENCES [dbo].[REWORK] ([WMS], [REWORKNUMBER])
GO
ALTER TABLE [dbo].[REWORKDETAIL] CHECK CONSTRAINT [FK_REWORKDETAIL_REWORK]
GO
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORK_EXCEL_FILE_WMS10](
	[π Ô/Ô] [float] NULL,
	[ÕŒÃ≈–] [nvarchar](255) NULL,
	[——€À ¿ Õ¿ WIKI] [nvarchar](255) NULL,
	[Œœ»—¿Õ»≈] [nvarchar](255) NULL,
	[ÀŒ√» ŒÕ] [nvarchar](255) NULL,
	[œ–Œƒ” “€] [nvarchar](255) NULL,
	[¬ŒÀ√¿-—≈–¬»—] [nvarchar](255) NULL,
	[Õ“œ] [nvarchar](255) NULL,
	[Œ«—Ã] [nvarchar](255) NULL,
	[Ã«—Ã] [nvarchar](255) NULL,
	[—»√Ã¿] [nvarchar](255) NULL,
	[¡¿–—] [nvarchar](255) NULL,
	[—  √œÕ-—Ã] [nvarchar](255) NULL,
	[ALG] [nvarchar](255) NULL,
	[√‡‡Ê_Tools] [nvarchar](255) NULL,
	[√‡Î‡÷ÂÌÚ] [nvarchar](255) NULL,
	[¬ËÍÚÓËˇ ¡‡ÎÚËˇ] [nvarchar](255) NULL,
	[—Ë·ËÒÍËÈ √ÛÏ‡Ì] [nvarchar](255) NULL,
	[ÀÛÍÓËÎ œÕŒ—] [nvarchar](255) NULL,
	[¡Óˇ‰] [nvarchar](255) NULL
) ON [PRIMARY]
GO
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORK_EXCEL_FILE_WMS11](
	[ÕŒÃ≈–] [nvarchar](255) NULL,
	[——€À ¿ Õ¿ WIKI] [nvarchar](255) NULL,
	[Œœ»—¿Õ»≈] [nvarchar](255) NULL,
	[ÀŒ√» ŒÕ] [nvarchar](255) NULL,
	[ÃŒÕ≈“ ¿] [nvarchar](255) NULL
) ON [PRIMARY]
GO



