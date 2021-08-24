USE HotelReservation;

-- 1.
-- Write a query that returns a list of reservations that end in July 2023, 
-- including the name of the guest, the room number(s), and the reservation dates.

SELECT
	FirstName,
    LastName,
    StartDate,
    EndDate
FROM Reservation r
INNER JOIN Guest g ON g.GuestID = r.GuestID
WHERE r.EndDate BETWEEN '2023-07-01' AND '2023-08-01';

-- Results:
-- 'Ben','Rickel','2023-06-28','2023-07-02'
-- 'Walter','Holaway','2023-07-13','2023-07-14'
-- 'Wilfred','Vise','2023-07-18','2023-07-21'
-- 'Bettyann','Seery','2023-07-28','2023-07-29'




-- 2.
-- Write a query that returns a list of all reservations for rooms with a jacuzzi, 
-- displaying the guest's name, the room number, and the dates of the reservation.

SELECT
	g.FirstName,
    g.LastName,
    ro.RoomID,
    r.StartDate,
    r.EndDate
FROM Reservation r
INNER JOIN Room ro ON r.RoomID = ro.RoomID
INNER JOIN RoomAmenity ra ON ro.RoomID = ra.RoomID
INNER JOIN Amenity a ON a.AmenityID = ra.AmenityID
INNER JOIN Guest g ON g.GuestID = r.GuestID
WHERE a.AmenityType = 'Jacuzzi'
GROUP BY g.FirstName, g.LastName, ro.RoomID, r.StartDate, r.EndDate;

-- Results:
-- 'Karie','Yang','201','2023-03-06','2023-03-07'
-- 'Bettyann','Seery','203','2023-02-05','2023-02-10'
-- 'Karie','Yang','203','2023-09-13','2023-09-15'
-- 'Ben','Rickel','205','2023-06-28','2023-07-02'
-- 'Wilfred','Vise','207','2023-04-23','2023-04-24'
-- 'Walter','Holaway','301','2023-04-09','2023-04-13'
-- 'Mack','Simmer','301','2023-11-22','2023-11-25'
-- 'Bettyann','Seery','303','2023-07-28','2023-07-29'
-- 'Duane','Cullison','305','2023-02-22','2023-02-24'
-- 'Bettyann','Seery','305','2023-08-30','2023-09-01'
-- 'Ben','Rickel','307','2023-03-17','2023-03-20'



-- 3.
-- Write a query that returns all the rooms reserved for a specific guest, including the guest's name, the room(s) reserved, 
-- the starting date of the reservation, and how many people were included in the reservation. 
-- (Choose a guest's name from the existing data.)

SELECT
	r.RoomID,
    g.FirstName,
    g.LastName,
    r.StartDate,
    r.Adults,
    r.Children,
    SUM(r.Adults + r.Children) TotalGuests
FROM Reservation r
INNER JOIN Guest g ON g.GuestID = r.GuestID
WHERE g.FirstName = 'Maritza' AND g.LastName = 'Tilton'
GROUP BY r.RoomID, g.FirstName, g.LastName, r.StartDate, r.Adults, r.Children;

-- Results:
-- '401','Maritza','Tilton','2023-05-30','2','4','6'
-- '302','Maritza','Tilton','2023-12-24','2','0','2'



-- 4.
-- Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation. 
-- The results should include all rooms, whether or not there is a reservation associated with the room.


SELECT
	r.RoomID,
    IFNULL(re.ReservationID, 'N/A') ReservationID,
    IFNULL(re.TotalRoomCost, 'N/A') TotalRoomCost
FROM Room r
LEFT OUTER JOIN Reservation re ON r.RoomID = re.RoomID
LEFT OUTER JOIN RoomType rt ON rt.RoomTypeID = r.RoomTypeID
GROUP BY r.RoomID, re.ReservationID, rt.BasePrice
ORDER BY r.RoomID ASC;

-- Results:
-- '201','4','199.99'
-- '202','7','174.99'
-- '203','2','199.99'
-- '203','21','199.99'
-- '204','16','174.99'
-- '205','15','174.99'
-- '206','12','149.99'
-- '206','23','149.99'
-- '207','10','174.99'
-- '208','13','149.99'
-- '208','20','149.99'
-- '301','9','199.99'
-- '301','24','199.99'
-- '302','6','174.99'
-- '302','25','174.99'
-- '303','18','199.99'
-- '304','14','174.99'
-- '305','3','199.99'
-- '305','19','199.99'
-- '306','N/A','149.99'
-- '307','5','174.99'
-- '308','1','149.99'
-- '401','11','399.99'
-- '401','17','399.99'
-- '401','22','399.99'
-- '402','N/A','399.99'



-- 5.
-- Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.

SELECT
	r.RoomID,
    g.FirstName,
    g.LastName,
    r.StartDate,
    r.EndDate,
    r.Adults,
    r.Children,
	r.Adults + r.Children TotalGuests
FROM Reservation r
INNER JOIN Guest g ON g.GuestID = r.GuestID
WHERE (r.StartDate BETWEEN '2023-04-01' AND '2023-05-01'
OR r.EndDate BETWEEN '2023-04-01' AND '2023-05-01')
AND (r.Adults + r.Children) >= 3;

-- ?? there are no reservations that meet these constraints
--


-- 6.
-- Write a query that returns a list of all guest names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations and then by the guest's name.


SELECT
	g.LastName,
	g.FirstName,
    COUNT(r.ReservationID) NumberOfReservations
FROM Guest g
INNER JOIN Reservation r ON g.GuestID = r.GuestID
INNER JOIN Room ro ON r.RoomID = ro.RoomID
GROUP BY g.GuestID, g.LastName, g.FirstName
ORDER BY COUNT(r.ReservationID) DESC, g.LastName ASC, g.FirstName ASC;

-- Results:
-- 'Cullison','Duane','2'
-- 'Holaway','Walter','2'
-- 'Lipton','Aurore','2'
-- 'Luechtefeld','Zachery','1'
-- 'Rickel','Ben','2'
-- 'Seery','Bettyann','3'
-- 'Simmer','Mack','4'
-- 'Tilton','Maritza','2'
-- 'Tison','Joleen','2'
-- 'Vise','Wilfred','2'
-- 'Yang','Karie','2'



-- 7.
-- Write a query that displays the name, address, and phone number of a guest based on their phone number. 
-- (Choose a phone number from the existing data.)

SELECT
	g.FirstName,
    g.LastName,
    g.Phone,
    a.StreetNumber,
    a.StreetName,
    z.City,
    z.State,
    z.ZipID
FROM Guest g
INNER JOIN Address a ON g.AddressID = a.AddressID
INNER JOIN Zip z ON a.ZipID = z.ZipID
WHERE g.Phone = '(291) 553-0508';

-- Results:
-- 'Mack','Simmer','(291) 553-0508','379','Old Shore St.','Council Bluffs','IA','51501'




-- Data Table queries for fun!

-- Room data table
SELECT 
	r.RoomID,
    r.ADAAccessible,
    rt.RoomType,
    rt.StandardOccupancy,
    rt.MaximumOccupancy,
    rt.ExtraGuestCharge,
    rt.BasePrice
FROM Room r
INNER JOIN RoomType rt ON rt.RoomTypeID = r.RoomTypeID
ORDER BY r.RoomID ASC;


	