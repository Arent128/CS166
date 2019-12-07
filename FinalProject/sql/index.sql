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
