# FUNCTIONAL REQUIREMENT SPECIFICATION (FRS)

---

## Document Information

| Field              | Details                                     |
|--------------------|---------------------------------------------|
| Project Name       | District (by Zomato) – Selenium Automation  |
| Organization       | Cognizant Academy                           |
| Document Version   | 1.0                                         |
| Date Prepared      | February 19, 2026                           |
| Prepared By        | QE&A Team                                   |
| Status             | Draft                                       |

---

## 1. Introduction

### 1.1 Purpose
This Functional Requirement Specification (FRS) document describes the functional requirements for the Selenium automation project targeting the District by Zomato web application (https://www.district.in/). It defines the scope, features, and expected behaviour of the system under test (SUT) as relevant to the automation project.

### 1.2 Scope
The scope of this automation project is limited to the following three functional areas of the District web application:

1. **Activities** – Navigation to the Activities section, applying date and sort filters, and extracting activity data.
2. **Movies** – Navigation to the Movies section and extracting language filter options.
3. **Login / User Profile** – Clicking on the User Avatar, entering an invalid mobile number, and capturing the error/warning message.

All other features of the District application (Dining, Events, Stores, Play, etc.) are **out of scope**.

### 1.3 Intended Audience
- QE&A Automation Engineers
- Project Coaches and Trainers
- BU Mentors and SME Reviewers

### 1.4 Definitions and Acronyms

| Term      | Definition                                        |
|-----------|---------------------------------------------------|
| SUT       | System Under Test                                 |
| FRS       | Functional Requirement Specification              |
| POM       | Page Object Model                                 |
| DOM       | Document Object Model                             |
| UI        | User Interface                                    |
| URL       | Uniform Resource Locator                          |
| OTP       | One-Time Password                                 |

---

## 2. System Overview

**District by Zomato** is a lifestyle and entertainment platform that allows users in Indian cities to discover and book tickets for:
- Movies (in theatres)
- Events (concerts, workshops, shows)
- Activities (adventure parks, art workshops, wellness)
- Dining experiences
- Stores and Play activities

The application defaults to the user's city (Mumbai by default) and provides filter/sort capabilities on each section.

### 2.1 Navigation Structure
| Menu Item  | URL                              | Description                              |
|------------|----------------------------------|------------------------------------------|
| For you    | https://www.district.in/         | Home page / personalised feed            |
| Dining     | https://www.district.in/dining/  | Dining experiences                       |
| Movies     | https://www.district.in/movies/  | Movie listings and ticket booking        |
| Events     | https://www.district.in/events/  | Events listing                           |
| Stores     | https://www.district.in/stores/  | Stores listing                           |
| Activities | https://www.district.in/activities/ | Activities and workshops listing      |
| Play       | https://www.district.in/play/    | Play section                             |

---

## 3. Functional Requirements

---

### FR-01: Activities – Filter and Sort

**Requirement ID:** FR-01  
**Module:** Activities  
**Priority:** High  

**Description:**  
The system shall allow the user to navigate to the Activities page, apply the "This Weekend" date filter, and sort the results by "Price: Low to High" using the Filters panel.

**Inputs:**  
- Click "Activities" in the navigation menu
- Click "This Weekend" quick filter button
- Click "Filters" button → select "Price: Low to High" → click "Apply Filters"

**Expected Behaviour:**  
- The Activities page (https://www.district.in/activities/) loads with activity listings.
- Applying "This Weekend" refreshes the list to show only weekend activities.
- Applying "Price: Low to High" sort reorders activities with the lowest price first.

**Pre-conditions:**  
- Browser is open, navigated to https://www.district.in/, city is set to Mumbai.

---

### FR-02: Activities – Data Extraction and Lowest Price

**Requirement ID:** FR-02  
**Module:** Activities – Data Extraction  
**Priority:** High  

**Description:**  
The automation script shall extract the name, venue, and price of all activities displayed after filtering and sorting, store the data in a Java Collection, and print the activity with the lowest price.

**Inputs:**  
- Activities page with "This Weekend" filter and "Price: Low to High" sort applied

**Expected Behaviour:**  
- All activity details (name, venue, price) are extracted from the page.
- Data is stored in a Java Collection (e.g., `List<Map<String, String>>`).
- The activity at index 0 (lowest price) is printed/logged to the console.

**Pre-conditions:**  
- FR-01 is completed (Activities page is filtered and sorted).

---

### FR-03: Movies – Language Filter Extraction

**Requirement ID:** FR-03  
**Module:** Movies  
**Priority:** High  

**Description:**  
The system shall allow the user to navigate to the Movies page, open the Filters panel, and extract all language options available in the Language filter section.

**Inputs:**  
- Click "Movies" in the navigation menu
- Click "Filters" button on the Movies page

**Expected Behaviour:**  
- The Movies page (https://www.district.in/movies/) loads with movie listings.
- The Filters panel opens, showing Genre, Language, and Format sections.
- All language options (e.g., Hindi, English, Marathi, Tamil, Telugu, Malayalam) are captured and printed.

**Pre-conditions:**  
- Browser is open, navigated to https://www.district.in/, city is set to Mumbai.

---

### FR-04: Login – Invalid Number Error Capture

**Requirement ID:** FR-04  
**Module:** Login  
**Priority:** High  

**Description:**  
The system shall display an error message when an invalid mobile number is submitted in the login modal. The automation shall capture the error text and save a screenshot.

**Inputs:**  
- Click "User Avatar" button in the navigation header
- Enter an invalid mobile number (e.g., fewer than 10 digits)
- Click the "Continue" button

**Expected Behaviour:**  
- Clicking User Avatar opens the login modal with a mobile number input field and "Continue" button.
- Submitting an invalid number shows an inline error/warning message.
- The automation captures the error message text and saves a screenshot.
- The user remains on the login modal (no navigation away).

**Pre-conditions:**  
- Any page of https://www.district.in/ is loaded and the user is not logged in.

---

## 4. Non-Functional Requirements

| ID     | Requirement                                                                                         |
|--------|-----------------------------------------------------------------------------------------------------|
| NFR-01 | Automation scripts shall run without manual intervention after initial trigger.                     |
| NFR-02 | The framework shall follow the Page Object Model (POM) design pattern.                              |
| NFR-03 | Explicit/Fluent Waits shall be used for synchronisation with dynamic content.                       |
| NFR-04 | All scripts shall include proper exception handling to prevent abrupt failures.                     |
| NFR-05 | Screenshots shall be captured on test failure and for FR-04 login error verification.               |
| NFR-06 | Test execution shall be managed via TestNG XML suites (Smoke and Regression).                       |
| NFR-07 | Maven shall be used for dependency management via pom.xml.                                          |

---

## 5. Automation Technology Stack

| Component           | Technology                          |
|---------------------|-------------------------------------|
| Programming Language| Java                                |
| Automation Tool     | Selenium WebDriver 4.x              |
| Test Framework      | TestNG                              |
| Design Pattern      | Page Object Model (POM)             |
| Build Tool          | Maven                               |
| Data Driver         | Apache POI / Properties file        |
| Browser             | Google Chrome (primary)             |
| Reporting           | TestNG built-in / Extent Reports    |
| IDE                 | Eclipse / IntelliJ IDEA             |
| Version Control     | Git                                 |

---

## 6. System Constraints

- The application is a dynamic, JavaScript-heavy Single Page Application (SPA).
- Login requires a valid OTP (not automatable end-to-end in production); only the error scenario for invalid mobile number is in scope.
- Activity listing data is live and changes daily; data extraction must be done at runtime.
- The application requires an internet connection.
- Location must be set to Mumbai for all test scenarios.

---

## 7. Assumptions

1. The website at https://www.district.in/ is accessible without any VPN or geo-restriction.
2. Mumbai is the pre-selected city (default) for all test cases.
3. The "This Weekend" filter always yields at least one activity result.
4. The Movies page always shows at least one language in the Language filter.
5. Login modal is accessible without prior account creation.
6. The automation scripts will not attempt to complete the OTP verification step.

---

## 8. Out of Scope

- Dining module automation
- Events module automation
- Stores module automation
- Play module automation
- OTP verification and successful login
- Payment gateway testing
- Mobile app testing
- API / Backend testing
- Performance testing

---

*End of Functional Requirement Specification Document*
