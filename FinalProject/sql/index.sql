DROP INDEX IF EXISTS hotelIndex; 
DROP INDEX IF EXISTS staffIndex;
DROP INDEX IF EXISTS roomIndex;
DROP INDEX IF EXISTS customerIndex;
DROP INDEX IF EXISTS maintenanceIndex; 
DROP INDEX IF EXISTS bookingIndex;
DROP INDEX IF EXISTS repairIndex;
DROP INDEX IF EXISTS requestIndex; 
DROP INDEX IF EXISTS assignedIndex; 



CREATE INDEX hotelIndex
ON Hotel
USING BTREE
(hotelID);

CREATE INDEX staffIndex
ON Staff
USING BTREE
(SSN);

CREATE INDEX roomIndex
ON Room
USING BTREE
(roomNo);

CREATE INDEX customerIndex
ON Customer
USING BTREE
(customerID);

CREATE INDEX maintenanceIndex
ON MaintenanceCompany
USING BTREE
(cmpID);

CREATE INDEX bookingIndex
ON Booking
USING BTREE
(bookingDate);

CREATE INDEX repairIndex
ON Repair
USING BTREE
(repairDate);

CREATE INDEX requestIndex
ON Request
USING BTREE
(reqID);

CREATE INDEX assignedIndex
ON Assigned
USING BTREE
(asgID);
