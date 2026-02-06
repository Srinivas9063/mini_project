

1. Application starts from MainOs.java
2. OS logo is displayed (MainOsLogo.java)
3. Installation check is performed (Installer.java)
4. User is prompted to login (Login.java)
5. Credentials are validated (LoginSecurity.java)
6. Security rules are applied (Security.java)
7. User profile is loaded (User1.java)
8. Desktop menu is shown (Desktop.java)
9. User selects services like Applications ,System Applications, Shutting down
10. Selected service executes and returns to Desktop

Overall SPARSH OS Flow 
.......................
Start
  ↓
Launch MainOs
  ↓
Check: New User ?
  ↓            ↓
Yes           No
 ↓             ↓
Installer     Login
 ↓             ↓
Validation Successful?
  ↓
Open Desktop
  ↓
Select Option
  ↓
Applications / Settings / Shutdown
  ↓
Exit
--------------------------------------------------
Login
......

Start
  ↓
Enter Username & Password
  ↓
Validate Inputs
  ↓
Valid ?
 ↓        ↓
Yes      No
 ↓        ↓
Login    Show Error
Success  Retry
  ↓
Open Desktop
---------------------------------------------------
Desktop Navigation Flow
.......................
Desktop Screen
  ↓
Display Menu
  ↓
1. Applications
2. System Applications
3. Shutdown
  ↓
User Choice
  ↓
Perform Action
  ↓
Return to Desktop / Exit
----------------------------------------------------
Applications Module Flow
........................

Applications Menu
  ↓
Select App
  ↓
Go Travel / Food / AddressBook 
  ↓
Run Selected App
  ↓
Task Completed
  ↓
Back to Applications Menu
----------------------------------------------------
Food App 
.........
Start
  ↓
Open Food App
  ↓
Display Food Platforms
(Zomato / Swiggy)
  ↓
User Selects Platform
  ↓
Display Restaurants
  ↓
Select Restaurant
  ↓
Display Menu Items
  ↓
Select Food Item(s)
  ↓
Add to Cart
  ↓
More Items?
 ↓        ↓
Yes      No
 ↓        ↓
Add More  Generate Bill
  ↓
Calculate Total Amount
  ↓
Confirm Order
  ↓
Order Placed Successfully
  ↓
Return to Applications Menu

----------------------------------------------------
Address book  App Flow

Address Book App
  ↓
Choose Option
  ↓
Add / Search / Delete
  ↓
Perform Operation
  ↓
Update 
  ↓
Show Result
  ↓
Return to Menu
-----------------------------------------------------
Go Travel Ride Booking Flow
...........................

Go Travel App
  ↓
Select Ride Type
(Bike / Car / Parcel)
  ↓
Enter Distance
  ↓
Calculate Fare
  ↓
Confirm Booking
  ↓
Ride Booked
------------------------------------------------------
All-In-One Swing App Flow
.........................

Launch All-In-One App
  ↓
Open Dashboard
  ↓
Select Utility / Game
  ↓
Open Swing Window
  ↓
Use Feature
  ↓
Close Window
  ↓
Return to Dashboard
------------------------------------------------------
Shutdown Flow
..............

User Selects Shutdown
  ↓
Confirm Exit
  ↓
Close Applications
  ↓
Terminate Program
  ↓
End

All Are Compailed Files So Use "java minios.MainOs" to run.