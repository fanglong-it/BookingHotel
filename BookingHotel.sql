
CREATE database BookingHotel
go

use BookingHotel
go


CREATE table tblRole(
	RoleId bit primary key,
	RoleName varchar(50)
)


CREATE table tblAccount(
	userId varchar(50) primary key,
	Password varchar(100),
	RoleId bit foreign key references tblRole(RoleId),
	Status nvarchar(20)

)

CREATE table tblUser(
	userId varchar(50) foreign key references tblAccount(userId),
	Name nvarchar(50),
	Address Nvarchar(200),
	PhoneNumber varchar(12)
)


CREATE table tblHotel(
	hotelId int identity(0,1) primary key,
	hotelName nvarchar(50),
	hotelAddress nvarchar(200),
	Status bit
)

CREATE table tblTypeRoom(
	typeId varchar(10) primary key,
	typeName varchar(50)
)


CREATE table tblRoom(
	hotelId int foreign key references tblHotel(hotelId),
	roomNo int identity (0,1) primary key,
	roomName varchar(50),
	availableDate date,
	quantity int,
	typeId varchar(10) foreign key references tblTypeRoom(typeId),
	roomPrice float
)


CREATE table tblFeedBack(
	roomNo int foreign key references tblRoom(roomNo),
	userId varchar(50) foreign key references tblAccount(userId),
	value int,
)

CREATE table tblOrder(
	orderId varchar(50) primary key,
	userId varchar(50) foreign key references tblAccount(userId),
	orderDate date,
	total float,
	status bit
)


CREATE table tblOrderDetails(
	orderId varchar(50) foreign key references tblOrder(orderId),
	roomNo int foreign key references tblRoom(roomNo),
	hotelId int foreign key references tblHotel(hotelId),
	orderQuantity int,
	night int,
	checkIn nvarchar(50),
	checkOut nvarchar(50)
)


CREATE table tblDiscount(
	discountCode varchar(50) primary key,
	discountValue int,
	existDate date
) 













