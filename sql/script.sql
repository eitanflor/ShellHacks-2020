USE [master]
GO
/****** Object:  Database [AutoNation]    Script Date: 9/27/2020 12:26:20 AM ******/
CREATE DATABASE [AutoNation]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'AutoNation', FILENAME = N'/var/opt/mssql/data/AutoNation.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'AutoNation_log', FILENAME = N'/var/opt/mssql/data/AutoNation_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [AutoNation] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [AutoNation].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [AutoNation] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [AutoNation] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [AutoNation] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [AutoNation] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [AutoNation] SET ARITHABORT OFF 
GO
ALTER DATABASE [AutoNation] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [AutoNation] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [AutoNation] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [AutoNation] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [AutoNation] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [AutoNation] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [AutoNation] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [AutoNation] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [AutoNation] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [AutoNation] SET  ENABLE_BROKER 
GO
ALTER DATABASE [AutoNation] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [AutoNation] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [AutoNation] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [AutoNation] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [AutoNation] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [AutoNation] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [AutoNation] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [AutoNation] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [AutoNation] SET  MULTI_USER 
GO
ALTER DATABASE [AutoNation] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [AutoNation] SET DB_CHAINING OFF 
GO
ALTER DATABASE [AutoNation] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [AutoNation] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [AutoNation] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [AutoNation] SET QUERY_STORE = OFF
GO
USE [AutoNation]
GO
/****** Object:  Table [dbo].[appointment]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[appointment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[employee_created] [int] NOT NULL,
	[car_id] [int] NOT NULL,
	[customer_id] [int] NOT NULL,
	[employee_id] [int] NULL,
	[start_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[price_expected] [decimal](10, 2) NULL,
	[price_full] [decimal](10, 2) NULL,
	[discount] [decimal](10, 2) NULL,
	[price_final] [decimal](10, 2) NULL,
	[canceled] [bit] NOT NULL,
	[cancellation_reason] [varchar](max) NULL,
	[notes] [varchar](max) NULL,
 CONSTRAINT [PK_appointment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[car]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[car](
	[id] [int] NOT NULL,
	[vin] [varchar](128) NOT NULL,
	[license_plate] [varchar](50) NOT NULL,
	[make] [varchar](50) NULL,
	[model] [varchar](50) NULL,
	[year] [varchar](50) NOT NULL,
	[color] [varchar](50) NULL,
	[customer_id] [int] NOT NULL,
	[state] [varchar](50) NULL,
 CONSTRAINT [PK_car] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](128) NOT NULL,
	[last_name] [varchar](128) NOT NULL,
	[date_of_birth] [varchar](128) NOT NULL,
	[sex] [varchar](128) NOT NULL,
	[address] [varchar](128) NULL,
	[contact_mobile] [varchar](128) NULL,
	[contact_email] [varchar](128) NULL,
	[date_registered] [varchar](128) NULL,
 CONSTRAINT [customer_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[employee]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[employee](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](64) NOT NULL,
	[last_name] [varchar](64) NOT NULL,
	[employee_type] [varchar](64) NOT NULL,
 CONSTRAINT [employee_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[service]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[service](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[service_name] [varchar](128) NOT NULL,
	[duration] [int] NOT NULL,
	[price] [decimal](10, 2) NOT NULL,
 CONSTRAINT [service_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[service_provided]    Script Date: 9/27/2020 12:26:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[service_provided](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[appointment_id] [int] NOT NULL,
	[service_id] [int] NOT NULL,
 CONSTRAINT [service_provided_pk] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[appointment]  WITH CHECK ADD  CONSTRAINT [FK_appointment_car] FOREIGN KEY([car_id])
REFERENCES [dbo].[car] ([id])
GO
ALTER TABLE [dbo].[appointment] CHECK CONSTRAINT [FK_appointment_car]
GO
ALTER TABLE [dbo].[appointment]  WITH CHECK ADD  CONSTRAINT [FK_appointment_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[appointment] CHECK CONSTRAINT [FK_appointment_customer]
GO
ALTER TABLE [dbo].[appointment]  WITH CHECK ADD  CONSTRAINT [FK_appointment_employee] FOREIGN KEY([employee_created])
REFERENCES [dbo].[employee] ([id])
GO
ALTER TABLE [dbo].[appointment] CHECK CONSTRAINT [FK_appointment_employee]
GO
ALTER TABLE [dbo].[appointment]  WITH CHECK ADD  CONSTRAINT [FK_appointment_employee1] FOREIGN KEY([employee_id])
REFERENCES [dbo].[employee] ([id])
GO
ALTER TABLE [dbo].[appointment] CHECK CONSTRAINT [FK_appointment_employee1]
GO
ALTER TABLE [dbo].[car]  WITH CHECK ADD  CONSTRAINT [FK_car_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[car] CHECK CONSTRAINT [FK_car_customer]
GO
ALTER TABLE [dbo].[service_provided]  WITH CHECK ADD  CONSTRAINT [FK_service_provided_appointment] FOREIGN KEY([appointment_id])
REFERENCES [dbo].[appointment] ([id])
GO
ALTER TABLE [dbo].[service_provided] CHECK CONSTRAINT [FK_service_provided_appointment]
GO
ALTER TABLE [dbo].[service_provided]  WITH CHECK ADD  CONSTRAINT [FK_service_provided_service] FOREIGN KEY([service_id])
REFERENCES [dbo].[service] ([id])
GO
ALTER TABLE [dbo].[service_provided] CHECK CONSTRAINT [FK_service_provided_service]
GO
USE [master]
GO
ALTER DATABASE [AutoNation] SET  READ_WRITE 
GO
