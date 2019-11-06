SELECT s.sname, COUNT(c.pid) FROM Suppliers s, Catalog c WHERE c.sid=s.sid GROUP BY s.sname; 

SELECT s.sname, COUNT(c.pid) as pCount 
FROM Suppliers s, Catalog c 
WHERE s.sid=c.sid 
GROUP BY s.sname
HAVING COUNT(c.pid) > 2;

SELECT s.sname, count(*) as numParts
FROM Suppliers s, Catalog c
WHERE s.sid = c.sid
and s.sid in
(SELECT c.sid FROM Catalog c
    EXCEPT
    (SELECT c.sid FROM Catalog c WHERE c.pid IN
        (SELECT p.pid FROM Parts p WHERE p.color != 'Green')))
GROUP BY s.sname;


SELECT s.sname, MAX(c.cost) as mostExpensivePart
FROM Suppliers s, Parts p, Catalog c
WHERE s.sid = c.sid and c.sid IN 
(
SELECT s.sid FROM Suppliers s, Parts p, Catalog c WHERE s.sid = c.sid AND p.pid = c.pid AND p.color = 'Red'
INTERSECT
(
SELECT s.sid FROM Suppliers s, Parts p, Catalog c WHERE s.sid = c.sid AND p.pid = c.pid AND p.color = 'Green'))
GROUP BY s.sname;


