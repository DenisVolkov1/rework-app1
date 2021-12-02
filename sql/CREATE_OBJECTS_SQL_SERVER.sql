USE [master]
GO

CREATE DATABASE [SCPRD]
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORK](
	[SERIALKEY] [int] IDENTITY(1,1) NOT NULL,
	[WMS] [nvarchar](50) NOT NULL,
	[REWORKNUMBER] [nvarchar](50) NOT NULL,
	[RESOURCE] [nvarchar](500) NULL,
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
	[№ п/п] [float] NULL,
	[НОМЕР] [nvarchar](255) NULL,
	[РЕСУРС] [nvarchar](500) NULL,
	[ССЫЛКА НА WIKI] [nvarchar](255) NULL,
	[ОПИСАНИЕ] [nvarchar](255) NULL,
	[ЛОГИКОН] [nvarchar](255) NULL,
	[ПРОДУКТЫ] [nvarchar](255) NULL,
	[ВОЛГА-СЕРВИС] [nvarchar](255) NULL,
	[НТП] [nvarchar](255) NULL,
	[ОЗСМ] [nvarchar](255) NULL,
	[МЗСМ] [nvarchar](255) NULL,
	[СИГМА] [nvarchar](255) NULL,
	[БАРС] [nvarchar](255) NULL,
	[СК ГПН-СМ] [nvarchar](255) NULL,
	[ALG] [nvarchar](255) NULL,
	[Гараж_Tools] [nvarchar](255) NULL,
	[ГалаЦентр] [nvarchar](255) NULL,
	[Виктория Балтия] [nvarchar](255) NULL,
	[Сибирский Гурман] [nvarchar](255) NULL,
	[Лукоил ПНОС] [nvarchar](255) NULL,
	[Боярд] [nvarchar](255) NULL
) ON [PRIMARY]
GO

-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
CREATE TABLE [dbo].[REWORK_EXCEL_FILE_WMS11](
	[НОМЕР] [nvarchar](255) NULL,
	[РЕСУРС] [nvarchar](500) NULL,
	[ССЫЛКА НА WIKI] [nvarchar](255) NULL,
	[ОПИСАНИЕ] [nvarchar](255) NULL,
	[ЛОГИКОН] [nvarchar](255) NULL,
	[МОНЕТКА] [nvarchar](255) NULL
) ON [PRIMARY]
GO



