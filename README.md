# DB Manager

A desktop Java application for managing company IT assets and related data in an Oracle database.  
Built as a school project using Java Swing and JDBC.

**Author:** T1n3core

---

## Description

DB Manager provides a simple graphical interface to perform CRUD operations on the following entities:

| Entity              | Description                                      |
|---------------------|--------------------------------------------------|
| **Zaposleni**       | Employees (name, surname, email, department)     |
| **StrojnaOprema**   | Hardware (name, type, serial number, purchase date) |
| **ProgramskaOprema**| Software (name, version, manufacturer)           |
| **Servis**          | Service records for hardware                     |
| **Namestitev**      | Software installations on hardware               |
| **Licenca**         | Software licenses (type, expiry, max installations) |
| **Dodelitev**       | Hardware assignments to employees                |

The application uses a classic DAO pattern with a generic interface, reflection-based UI generation, and foreign-key aware forms.

---

## Features

- Login screen with Oracle database authentication
- Table view with dynamic entity switching
- Insert / Update / Delete operations
- Automatic form generation based on entity fields
- Foreign key selection via combo boxes
- Dark / Light theme switching (Darklaf)
- Help and about dialogs

---

## Technologies

- **Language:** Java
- **GUI:** Java Swing + [Darklaf](https://github.com/weisJ/darklaf)
- **Database:** Oracle (JDBC)
- **Date picker:** LGoodDatePicker
- **Architecture:** DAO + Model + Swing UI

---

## Database Connection

The application connects to a fixed Oracle instance:jdbc:oracle:thin:@193.2.139.22:1521/erstextCredentials are entered at login time and stored only in memory for the session.

---

## Project Structure

```text
io.github.T1n3core.DB_Manager
├── dao/                  # Data Access Objects
│   ├── GenericDAO.java
│   ├── DAOFactory.java
│   ├── IdGenerator.java
│   └── *DAO.java         # Concrete DAOs for each entity
├── db/                   # Database connection & config
│   ├── DatabaseConfig.java
│   └── DatabaseConnection.java
├── model/                # Entity classes
│   ├── Zaposleni.java
│   ├── StrojnaOprema.java
│   ├── ProgramskaOprema.java
│   ├── Servis.java
│   ├── Namestitev.java
│   ├── Licenca.java
│   └── Dodelitev.java
├── data/                 # Utility structures (Trie, AutocompleteItem)
├── ui/                   # Swing components
│   ├── LoginPanel.java
│   ├── TablePanel.java
│   ├── InsertDialog.java
│   ├── ReflectionTableModel.java
│   ├── ForeignKeyComboBox.java
│   └── ...
└── main/
├── Main.java
└── MainFrame.javatext---
```

## How to Run

### Requirements

- Java 17+ (or compatible version)
- Oracle JDBC driver (`ojdbc*.jar`)
- Darklaf and LGoodDatePicker libraries on the classpath

### Steps

1. Clone / download the project
2. Add the required dependencies (Oracle JDBC, Darklaf, LGoodDatePicker)
3. Compile and run the `Main` class:

```bash
java -cp ".:lib/*" io.github.T1n3core.DB_Manager.main.Main
```
Enter valid database credentials on the login screen
Select a table and use the new / modify / delete buttons
Usage
Log in with your Oracle username and password
Choose an entity from the dropdown on the left
Use:
new → insert a new record
modify → edit the selected row
delete → remove the selected row

Switch theme with the tema button
Open help with pomoč or credits with vizitka
Notes
Primary keys are auto-generated using MAX(id) + 1
Foreign key relationships are handled by loading related entities into combo boxes
Some autocomplete components exist in the codebase but are marked as deprecated / unfinished
The UI contains a mix of Slovenian and English labels
