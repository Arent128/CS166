/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/* Includes for the project below */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   public int errorChecker(String query) throws SQLException { 
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
	    }
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i) {
         }
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }

   public String getSelectString(String query) throws SQLException  { 
      // creates a statement object
      String result = "";
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
	    }
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            result = rs.getString(i);
      }//end while
      stmt.close ();
      return result;
   }

   public int executeQueryLimit (String query, int count) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
         if(count == rowCount)   {
            break;
         }
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery


   public boolean verifyDate(String date)   {
       DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
       format.setLenient(false);
       try   {
           format.parse(date);
           return true;
       }
       catch (ParseException e)   {
           System.out.println("Error! The given string is an invaild date format.\nThe format of the string should follow month/day/year");
           return false;
       }
   }

   public boolean verifyNumber(String input)    {
       char c = '1';
       for(int i = 0; i < input.length(); i++)   {
           c = input.charAt(i);
           if(!(Character.isDigit(c)))   {
               return false;
           }
       }
       return true;
   }
   public boolean isEmpty(String input)   {
       if(input.length() == 0)   {
           return true;
       }
       return false;
   }

   public int verifyText(String input)   {
       char c = '0';
       if(input.length() > 30)   {
           return 2; 
       }
       for(int i = 0; i < input.length(); i++)   {
           if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))   {
               continue;
           }
           else   {
               return 1;
           }
       }
       return 0;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  // Given repair details add repair in the DB
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      try   {
        String userIn_1 = "";
        String userIn_2 = "";
        String userIn_3 = "";
        int errorResult = 0;
        String errorCheck = "";

        do {
            System.out.println("Please enter a staff SSN to assign for cleaning");
            try   {
                userIn_1 = in.readLine();
                if(esql.isEmpty(userIn_1))   {
                    throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                }
                if(esql.verifyNumber(userIn_1) == false)   {
                    throw new Exception(String.format("Error! A Staff SSN cannot contain letters or special characters"));
                }
                errorCheck = String.format("SELECT s.SSN FROM Staff s WHERE s.SSN = %s and s.role = 'HouseCleaning';", userIn_1);
                errorResult = esql.errorChecker(errorCheck);
                if(errorResult == 0)   {
                    throw new Exception(String.format("Error %s is not a vaild employee/this employee's role is not in house cleaning", userIn_1));
                }
                break;
            }
            catch (Exception e)   {
               System.out.println(e.getMessage());
               continue;
            }

        }while(true);

        do {
            try   {
                System.out.println("Please enter a HotelID to select a hotel");
                userIn_2 = in.readLine();
                if(esql.isEmpty(userIn_2))   {
                    throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                }
                if(esql.verifyNumber(userIn_2) == false)   {
                    throw new Exception(String.format("Error! A hotelID cannot contain letters or special characters"));
                }
                errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s;", userIn_2);
                errorResult = esql.errorChecker(errorCheck);
                if(errorResult == 0)   {
                    throw new Exception(String.format("Error %s is not a vaild HotelID in the Hotel table!", userIn_2));
                }
                break;
            }
            catch (Exception e)   {
                System.out.println(e.getMessage());
                continue;
            }
        }while(true);
        do   {
            try   {
                System.out.println("Please enter the room number to be cleaned");
                userIn_3 = in.readLine();
                if(esql.isEmpty(userIn_3))   {
                    throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                }
                errorCheck = String.format("SELECT r.roomNo FROM Room r, Hotel h WHERE h.hotelID = %s and r.roomNo = %s;", userIn_2, userIn_3);
                errorResult = esql.errorChecker(errorCheck);
                if(esql.verifyNumber(userIn_3) == false)   {
                    throw new Exception(String.format("Error! A room number cannot contain letters or special characters"));
                }
                if(errorResult == 0)   {
                    throw new Exception(String.format("Error %s is not a vaild room in given HotelID %s", userIn_3, userIn_2));
                }
                break;
            }
            catch(Exception e)   {
                continue;
            }
        }while(true);
        int assignVal = (Integer.parseInt(esql.getSelectString("SELECT MAX(asgID) FROM Assigned;")) + 1);

        String insertStatement = String.format("INSERT INTO Assigned(asgID, staffID, hotelID, roomNo) VALUES(%s, %s, %s, %s)", Integer.toString(assignVal), userIn_1, userIn_2, userIn_3);
        esql.executeUpdate(insertStatement);
        System.out.println(String.format("StaffID %s was assigned to clean room number %s at hotelID %s. The value of the asgID for the job was %s", userIn_1, userIn_3, userIn_2, Integer.toString(assignVal)));

      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      try {
          String userIn_1 = "", userIn_2 = "", userIn_3 = "", userIn_4 = "", userIn_5 = "", issue = "";
          String errorCheck = "";
          int errorResult = 0;

          do   {
              try   {
                  System.out.println("Please enter a hotelID");
                  userIn_1 = in.readLine();
                  if(esql.isEmpty(userIn_1))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s;", userIn_1);
                  if(esql.verifyNumber(userIn_1) == false)   {
                      throw new Exception(String.format("Error a hotelID must not contain special characters or letters"));
                  }
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error %s is not a vaild HotelID in the Hotel table!", userIn_1));
                  }
                  break;

              }
              catch(Exception e)   {
                  System.out.println(e.getMessage());
              }

          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a Staff SSN");
                  userIn_2 = in.readLine();
                  if(esql.isEmpty(userIn_2))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_2) == false)   {
                      throw new Exception(String.format("Error a Staff SSN must not contain special characters or letters"));
                  }
                  errorCheck = String.format("SELECT s.SSN FROM Staff s WHERE s.SSN = %s and s.role = 'Manager';", userIn_2);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error %s is not a vaild employee/this employee's role is not a Manager", userIn_2));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a room number");
                  userIn_3 = in.readLine();
                  if(esql.isEmpty(userIn_3))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_3) == false)   {
                      throw new Exception(String.format("Error a room number must not contain special characters or letters"));
                  }
                  errorCheck = String.format("SELECT r.roomNo FROM Room r, Hotel h WHERE h.hotelID = %s and r.roomNo = %s;", userIn_1, userIn_3);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error %s is not a vaild room in given HotelID %s", userIn_3, userIn_1));
                  }
                  break;

              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a repairID");
                  userIn_4 = in.readLine();
                  if(esql.isEmpty(userIn_4))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_4) == false)   {
                      throw new Exception(String.format("Error a repairID number must not contain special characters or letters"));
                  } 
                  errorCheck = String.format("SELECT rep.rID FROM Repair rep WHERE rep.rID = %s;", userIn_4);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error %s is not a vaild repairID", userIn_4));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a repair date");
                  userIn_5 = in.readLine();
                  if(esql.isEmpty(userIn_5))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(userIn_5) == false)   {
                      throw new Exception("");
                  }
                  errorCheck = String.format("SELECT rep.repairDate FROM Repair rep WHERE rep.rID = %s and rep.repairDate = '%s';", userIn_4, userIn_5);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult > 0)   {
                      throw new Exception(String.format("Error repairID %s is already scheduled for %s", userIn_4, userIn_5));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

        int requestVal = (Integer.parseInt(esql.getSelectString("SELECT MAX(reqID) FROM Request")) + 1);
        do   {
            try   {
                System.out.println("Briefly describe the issue to repair (30 characters or less)");
                issue = in.readLine();
                  if(esql.isEmpty(issue))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                int textResult = esql.verifyText(issue);
                if(issue.length() > 30)   {
                    throw new Exception(String.format("Error! The description you inserted exceeeds the character limit.\nPlease use atmost 30 characters"));
                }
                else   {
                    break;
                }
            }
            catch (Exception e)   {
                System.out.println(e.getMessage());
                continue;
            }
        }while(true);

        String repairRequest = String.format("INSERT INTO Request(reqID, managerID, repairID, requestDate, description) VALUES (%s, %s, %s, '%s'::date, '%s')", Integer.toString(requestVal), userIn_2, userIn_4, userIn_5, issue);
        esql.executeUpdate(repairRequest);
        System.out.println(String.format("Request for roomNo %s at HotelID %s was created by managerID %s. The repairID %s is scheduled for %s and has been assigned a requestID of %s", userIn_3, userIn_1, userIn_2, userIn_4, userIn_5, Integer.toString(requestVal)));

      }
      catch (Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      try {
          String userIn_1 = "";
          String errorCheck = "";
          int errorResult = 0;
          
          do   {
              try   {
                  System.out.println("Insert a hotelID to get a count of available rooms");
                  userIn_1 = in.readLine();
                  if(esql.isEmpty(userIn_1))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_1) == false)   {
                      throw new Exception(String.format("Error a hotelID number must not contain special characters or letters"));
                  } 
                  errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s", userIn_1);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! %s is not a vaild hotelID", userIn_1));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          String availableRoom = String.format("SELECT notBooked.roomNo, notBooked.hotelID FROM (SELECT r.roomNo, r.hotelID FROM Room r EXCEPT (SELECT b.roomNo, b.hotelID FROM Booking b)) as notBooked WHERE notBooked.hotelID = %s", userIn_1);
          int rowCount = esql.executeQuery(availableRoom);
          System.out.println("total rows: " + rowCount);
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      try {
          /*NOTE THAT THIS QUERY AS REQUESTED ABOVE JUST GETS THE TOTAL COUNT OF BOOKED
            ROOMS THAT EXIST ON THIS LIST. THEREFORE THE SAME ROOM CAN APPEAR TWICE GIVEN
            IT WAS BOOKED ON A DIFFERENT DATE*/
          String userIn_1 = "";
          String errorCheck = "";
          int errorResult = 0;
          do   {
              try   {
                  System.out.println("Insert a hotelID to get a count of booked rooms");
                  userIn_1 = in.readLine();
                  if(esql.isEmpty(userIn_1))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_1) == false)   {
                      throw new Exception(String.format("Error a hotelID number must not contain special characters or letters"));
                  } 
                  errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s", userIn_1);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! %s is not a vaild hotelID", userIn_1));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);
          String bookedRoom = String.format("SELECT b.roomNo FROM Booking b WHERE b.hotelID = %s", userIn_1);
          int rowCount = esql.executeQuery(bookedRoom);
          System.out.println("total rows: " + rowCount);
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      // Your code goes here.
      //
      /*
       * Given the description above I will list all the available given in a week for a given room. Although the name of the function of the name
       * would lead me to believe I should display a list of rooms that have been booked for a week.
       */
      try {
          String userIn_1 = "", userIn_2 = "";
          String errorCheck = "";
          int errorResult = 0;
          do   {
              try   {
                  System.out.println("Insert a hotelID to get a count of available rooms");
                  userIn_1 = in.readLine();
                  if(esql.isEmpty(userIn_1))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn_1) == false)   {
                      throw new Exception(String.format("Error a hotelID number must not contain special characters or letters"));
                  } 
                  errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s", userIn_1);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! %s is not a vaild hotelID", userIn_1));
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a date for rooms in that week");
                  userIn_2 = in.readLine();
                  if(esql.isEmpty(userIn_2))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(userIn_2) == false)   {
                      throw new Exception("");
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }

          }while(true);


          String availableRoom = String.format("SELECT r.roomNo FROM Room r, (SELECT b.hotelID, b.roomNo FROM Booking b WHERE date_trunc('week', b.bookingDate) = date_trunc('week', '%s'::date) and b.hotelID = %s) as booked WHERE r.hotelID = booked.hotelID and r.roomNo != booked.roomNo;", userIn_2, userIn_1);
          int rowCount = esql.executeQuery(availableRoom);
          System.out.println("Total rows: " + rowCount);


      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      try {
          String userIn = "", startDate = "", endDate = "", errorCheck = "";
          int errorResult = 0;
          do   {
              try   {
                  System.out.println("Please give me a start date");
                  startDate = in.readLine();
                  if(esql.isEmpty(startDate))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(startDate) == false)   {
                      throw new Exception("");
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please give me a endDate date");
                  endDate = in.readLine();
                  if(esql.isEmpty(endDate))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(endDate) == false)   {
                      throw new Exception("");
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please give me a number of rooms to display");
                  userIn = in.readLine();
                  if(esql.isEmpty(userIn))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn) == false)   {
                      throw new Exception(String.format("Please use numbers instead of letters or special characters"));
                  } 
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);
          String query = String.format("SELECT r.roomType, r.roomNo, b.price, b.bookingDate FROM Room r, Booking b WHERE r.roomNo = b.roomNo and r.hotelID = b.hotelID and (b.bookingDate BETWEEN '%s'::date and '%s'::date) ORDER BY b.price DESC;", startDate, endDate);
          esql.executeQueryLimit(query,Integer.parseInt(userIn));

      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      try {
          String fName = "", lName = "", userIn = "", errorCheck = "";
          int errorResult = 0, textResult = 0;
          do   {
              try   {
                  System.out.println("Please enter a customer's first name");
                  fName = in.readLine();
                  if(esql.isEmpty(fName))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  textResult = esql.verifyText(fName);
                  if(textResult == 2)  {
                      throw new Exception("The length of the customer's first name must not exceed 30 characters");
                  }
                  else if(textResult == 1)   {
                      throw new Exception("Error! A customer's first name must not include digits or special characters");
                  }
                  errorCheck = String.format("SELECT c.fName FROM Customer c WHERE c.fName = '%s'", fName);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! The customer with first name of %s does not exist in the table of customer", fName));
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);
          
          do   {
              try   {
                  System.out.println("Please enter a customer's last name");
                  lName = in.readLine();
                  if(esql.isEmpty(lName))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  textResult = esql.verifyText(lName);
                  if(textResult == 2)  {
                      throw new Exception("The length of the customer's last name must not exceed 30 characters");
                  }
                  else if(textResult == 1)   {
                      throw new Exception("Error! A customer's last name must not include digits or special characters");
                  }
                  errorCheck = String.format("SELECT c.lName FROM Customer c WHERE c.lName = '%s'", lName);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! The customer with last name of %s does not exist in the table of customer", lName));
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please give me a number of rooms to display");
                  userIn = in.readLine();
                  if(esql.isEmpty(userIn))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(userIn) == false)   {
                      throw new Exception(String.format("Please use numbers instead of letters or special characters"));
                  } 
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          String query = String.format("SELECT c.fName, c.lName, b.price, b.bookingDate, b.hotelID FROM Customer c, Booking b WHERE c.customerID = b.customer and c.fName = '%s' and c.lName = '%s' ORDER BY b.price DESC;", fName, lName);
          esql.executeQueryLimit(query, Integer.parseInt(userIn));
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      try {
          String hotelID = "", fName = "", lName = "", startDate = "", endDate = "", errorCheck = "";
          int errorResult = 0, textResult = 0;
          do   {
              try   {
                  System.out.println("Insert a hotelID to get a count of booked rooms");
                  hotelID = in.readLine();
                  if(esql.isEmpty(hotelID))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyNumber(hotelID) == false)   {
                      throw new Exception(String.format("Error a hotelID number must not contain special characters or letters"));
                  } 
                  errorCheck = String.format("SELECT h.hotelID FROM Hotel h WHERE h.hotelID = %s", hotelID);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! %s is not a vaild hotelID", hotelID));
                  }
                  break;
              }
              catch (Exception e)   {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please enter a customer's first name");
                  fName = in.readLine();
                  if(esql.isEmpty(fName))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  textResult = esql.verifyText(fName);
                  if(textResult == 2)  {
                      throw new Exception("The length of the customer's first name must not exceed 30 characters");
                  }
                  else if(textResult == 1)   {
                      throw new Exception("Error! A customer's first name must not include digits or special characters");
                  }
                  errorCheck = String.format("SELECT c.fName FROM Customer c WHERE c.fName = '%s'", fName);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! The customer with first name of %s does not exist in the table of customer", fName));
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);
          
          do   {
              try   {
                  System.out.println("Please enter a customer's last name");
                  lName = in.readLine();
                  if(esql.isEmpty(lName))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  textResult = esql.verifyText(lName);
                  if(textResult == 2)  {
                      throw new Exception("The length of the customer's last name must not exceed 30 characters");
                  }
                  else if(textResult == 1)   {
                      throw new Exception("Error! A customer's last name must not include digits or special characters");
                  }
                  errorCheck = String.format("SELECT c.lName FROM Customer c WHERE c.lName = '%s'", lName);
                  errorResult = esql.errorChecker(errorCheck);
                  if(errorResult == 0)   {
                      throw new Exception(String.format("Error! The customer with last name of %s does not exist in the table of customer", lName));
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please give me a start date");
                  startDate = in.readLine();
                  if(esql.isEmpty(startDate))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(startDate) == false)   {
                      throw new Exception("");
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          do   {
              try   {
                  System.out.println("Please give me a endDate date");
                  endDate = in.readLine();
                  if(esql.isEmpty(endDate))   {
                      throw new Exception("Error! Empty input detected. Please enter input before hitting enter");
                  }
                  if(esql.verifyDate(endDate) == false)   {
                      throw new Exception("");
                  }
                  break;
              }
              catch (Exception e)    {
                  System.out.println(e.getMessage());
                  continue;
              }
          }while(true);

          String query = String.format("SELECT c.fName, c.lName, SUM(b.price) FROM Customer c, Booking b WHERE c.customerID = b.customer and b.hotelID = %s and  c.fName = '%s' and c.lName = '%s' and (b.bookingDate BETWEEN '%s'::date and '%s'::date) GROUP BY c.fName, c.lName;", hotelID, fName, lName, startDate, endDate);
          esql.executeQuery(query);

      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      try {
      }
      catch(Exception e)   {
        System.out.println(e.getMessage());
      }
   }//end listRepairsMade

}//end DBProject
