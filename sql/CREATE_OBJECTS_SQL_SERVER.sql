---START MS SQL-----------------
USE [master]
GO

CREATE DATABASE [REWORKAPP]
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
	[ADDWHO] [nvarchar](30) NULL,
	[EDITWHO] [nvarchar](30) NULL,
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
	[ADDWHO] [nvarchar](30) NULL,
	[EDITWHO] [nvarchar](30) NULL,
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
	[РПХ] [nvarchar](255) NULL,
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
---END MS SQL-----------------
---START postgresql-----------------

-- DROP TABLE IF EXISTS public.REWORK;
-- DROP TABLE IF EXISTS public.REWORK_EXCEL_FILE_WMS10
CREATE TABLE IF NOT EXISTS public.REWORK
(
    SERIALKEY integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
	WMS character varying(50) NOT NULL,
	REWORKNUMBER character varying(50) NOT NULL,
	RESOURCE character varying(500) NULL,
	WIKILINK character varying(500) NULL,
	DESCRIPTION character varying NULL,
	ADDDATE timestamp NOT NULL DEFAULT timezone('utc', now()),
	ADDWHO character varying(30) NULL DEFAULT current_user,
	EDITWHO character varying(30) NULL DEFAULT current_user,
	EDITDATE timestamp  NOT NULL DEFAULT timezone('utc', now()),
	CONSTRAINT PK_REWORK PRIMARY KEY(WMS,REWORKNUMBER)
)

CREATE TABLE IF NOT EXISTS public.REWORKDETAIL
(
    SERIALKEY integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
	WMS character varying(50) NOT NULL,
	REWORKNUMBER character varying(50) NOT NULL,
	PROJECT character varying(80) NOT NULL,
	STATUS character varying(30) NULL,
	ADDDATE timestamp  NOT NULL DEFAULT timezone('utc', now()),
	ADDWHO character varying(30) NULL DEFAULT current_user,
	EDITWHO character varying(30) NULL DEFAULT current_user,
	EDITDATE timestamp  NOT NULL DEFAULT timezone('utc', now()),
	CONSTRAINT PK_REWORKDETAIL PRIMARY KEY(WMS,REWORKNUMBER,PROJECT),
	CONSTRAINT FK_REWORKDETAIL FOREIGN KEY(WMS,REWORKNUMBER) REFERENCES public.REWORK(WMS,REWORKNUMBER)
)

CREATE TABLE IF NOT EXISTS public.REWORK_EXCEL_FILE_WMS10
(
	НОМЕР character varying(255) NULL,
	РЕСУРС character varying(500) NULL,
	"ССЫЛКА НА WIKI" character varying(255) NULL,
	ОПИСАНИЕ character varying(255) NULL,
	ЛОГИКОН character varying(255) NULL,
	ПРОДУКТЫ character varying(255) NULL,
	"ВОЛГА-СЕРВИС" character varying(255) NULL,
	НТП character varying(255) NULL,
	ОЗСМ character varying(255) NULL,
	МЗСМ character varying(255) NULL,
	РПХ character varying(255) NULL,
	СИГМА character varying(255) NULL,
	БАРС character varying(255) NULL,
	"СК ГПН-СМ" character varying(255) NULL,
	ALG character varying(255) NULL,
	Гараж_Tools character varying(255) NULL,
	ГалаЦентр character varying(255) NULL,
	"Виктория Балтия" character varying(255) NULL,
	"Сибирский Гурман" character varying(255) NULL,
	"Лукоил ПНОС" character varying(255) NULL,
	Боярд character varying(255) NULL
)
CREATE TABLE IF NOT EXISTS public.REWORK_EXCEL_FILE_WMS11
(
	НОМЕР character varying(255) NULL,
	РЕСУРС character varying(500) NULL,
	"ССЫЛКА НА WIKI" character varying(255) NULL,
	ОПИСАНИЕ character varying(255) NULL,
	ЛОГИКОН character varying(255) NULL,
	МОНЕТКА character varying(255) NULL

)

---END postgresql-----------------


