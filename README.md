# Android-Contact-Manager-App
A miniature contact manager implemented as an Android App that enables Adding, Editing and Deleting a contact.

Each contact has the following details:
A.	First Name
B.	Last Name
C.	Phone Number
D.	E-mail address
E.	Birth date

Requirements ans pecifications of the application are as follows:
When the program comes up, a list of contacts must be seen.  This list shows the first and last name combined and the phone number, and is sorted by first name. You will be able to scroll through the list.  There will be nothing else visible on the screen except the action bar.  If there are no contacts, the list will be empty.
An add button on the action bar will bring up a separate screen (Android activity) to add a new contact.  When you finish adding and save, the new contact will be in the list. The Save button can be either on the entry screen or on the action bar, your choice. This screen disappears when you save and the list shows again.
Touching a contact brings up the details.  With the fields visible, you can either modify or delete the contact.  You can do this either with buttons on the details screen or with action bar buttons.
There must be only one activity for showing the details.  This is used for both adding a new contact and for modification and deletion.
The Save button described above saves contact information, either by updating a contact you selected from the list or creating a new one, depending upon program mode.  Once you save information, the detail screen disappears and the screen with the list of contacts shows.  You can also to return to the main (list) screen without saving.
All fields except the first name are optional.  There is no validity checking on anything.  There is no duplicate checking.
Contacts are stored in a text file, not in a SQLite database.  Each contact is a single line, with fields separated by tabs, not commas.
