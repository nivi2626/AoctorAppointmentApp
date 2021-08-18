DocTor - an appointment system for doctors.
The user can register as a doctor or as a patient, and then log in to the app as the same type of user.
- As a patient, the user can see a list of doctors, make appointments, cancel appointments and view
the waiting list for each doctor.
- As a doctor, the user can see his current patient and the next patients waiting. The doctor can click on
a button that indicates he has finished with the current patient and gets the next patient.

-----------------
implementation details:
- The registration system for the application is done with Authentication fireBase.
- The DB of the patients and doctors is implemented and updated with FireStore.
- The list of optional doctors that the patient sees is implemented with recycleView.