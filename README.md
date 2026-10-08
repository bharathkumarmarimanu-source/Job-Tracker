# Job Application Tracker

A menu-driven Java console application to log and track job applications, built as part of my Java Full Stack training.

## Features

- Add an application (company, role, date applied) as either **Online** (with the portal used) or **Referral** (with who referred you)
- View all applications
- Update an application's status: `APPLIED`, `INTERVIEW`, `OFFER` or `REJECTED`
- Delete an application by ID
- Search by company name (case-insensitive, partial match)
- Summary: total applications, unique companies and a count per status
- Input validation: non-numeric IDs, empty fields, wrong date formats and invalid statuses are handled without crashing

## Java concepts used

- **OOP:** a `JobApplication` base class with `ReferralApplication` and `OnlineApplication` subclasses (encapsulation, inheritance, method overriding)
- **Collections:** `HashMap` keyed by application ID, `HashSet` for unique companies, and a `HashMap` for status counts
- **Exception handling:** `try/catch` for `NumberFormatException` on numeric input
- **Core Java:** `Scanner`, loops, `switch`, `String` methods and static helper methods

## How to run (Eclipse)

1. Create a Java project and a package named `bharath`.
2. Add `JobTracker.java` to that package.
3. Run `JobTracker.java` as a Java Application and use the menu in the Console.

## Example session

```
===== JOB TRACKER =====
1. Add application
2. View all
3. Update status
4. Delete
5. Search by company
6. Summary
7. Exit
Choose: 1
Company: infosys
Role: software engineer
Date applied (dd-mm-yyyy): 09-10-2026
Type (1=Online, 2=Referral): 1
Portal (LinkedIn/Naukri/Company site): linkedin
Added successfully!

Choose: 2
#1 | Infosys | Software Engineer | APPLIED | Applied on: 09-10-2026 | Type: Online (Linkedin)
```

## Possible improvements

- Save applications to a file so they are kept after the program closes
- Edit the details of an existing application
- Validate that the date is a real calendar date

## Author

Bharath Kumar Marrimanu
