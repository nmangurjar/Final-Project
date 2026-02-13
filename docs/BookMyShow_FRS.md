# Functional Requirement Specification (FRS)
## BookMyShow Automation Testing Project

---

### Document Information
| **Field** | **Details** |
|-----------|-------------|
| Project Name | BookMyShow Test Automation |
| Document Version | 1.0 |
| Date | February 6, 2026 |
| Prepared By | Quality Engineering & Assurance Team |
| Application URL | https://in.bookmyshow.com |
| Technology Stack | Selenium WebDriver, Java, TestNG, Maven, Jenkins |

---

## 1. Introduction

### 1.1 Purpose
This document describes the functional requirements for the BookMyShow ticket booking platform to be automated using Selenium WebDriver. The automation focuses on key user journeys including event browsing, filtering, authentication, and ticket booking workflows.

### 1.2 Scope
The automation project covers three primary functional areas:
1. Sports Events Filtering and Display
2. Movie Language Extraction and Display
3. User Authentication (Sign In functionality)

### 1.3 Intended Audience
- QA Engineers
- Test Automation Engineers
- Development Team
- Project Managers
- Stakeholders

---

## 2. System Overview

### 2.1 Application Description
BookMyShow is an online ticket booking platform that allows users to:
- Browse and book movie tickets
- Explore sports events and activities
- Purchase tickets for plays and concerts
- Stream online entertainment events
- Access gift cards and special offers

### 2.2 Browser Compatibility
- Google Chrome (Primary)
- Mozilla Firefox
- Microsoft Edge
- Safari (Mac OS)

---

## 3. Functional Requirements

### 3.1 Sports Events Filtering System

**Requirement ID:** FR-001  
**Requirement Name:** Display Sports Events for Coming Weekend

**Description:**  
The system shall allow users to filter and view sports activities in Mumbai for the coming weekend, displaying events sorted by lowest ticket price on top.

**Preconditions:**
- User is on the BookMyShow homepage (https://in.bookmyshow.com)
- Location is set to "Mumbai"
- Sports events are available for the upcoming weekend

**Functional Details:**
1. **Navigation:** User clicks on "Sports" menu item in the main navigation bar
2. **Date Filtering:** User applies "This Weekend" filter from the available date options
3. **Event Display:** System displays sports events with the following information:
   - Event Name
   - Event Category (Cricket, Running, Cycling, Sailing, etc.)
   - Date and Time
   - Venue/Location
   - Ticket Price (Starting from ₹XXX onwards)
   
4. **Price Sorting:** Events are sorted by lowest ticket price in ascending order

**Acceptance Criteria:**
- Sports section accessible from main navigation
- "This Weekend" date filter is functional and clickable
- Events are displayed with complete information (name, category, date, venue, price)
- Price sorting displays lowest price events first
- If multiple events are there then minimum 10 sports events should be visible for the weekend

**Sample Data (Based on Exploration):**
- Mumbai Midnight Cycling Pedal & Go: ₹125 onwards (Lowest)
- Mumbai Midnight Cycling: ₹147 onwards
- MUMBAI ICC MEN'S T20 WC 2026: ₹150 onwards
- INDIA vs USA T20 WC Match: ₹750 onwards
- Private Sailing Mumbai: ₹4543 onwards (Highest observed)

**Categories Available:**
- Cricket
- Running
- Cycling
- Sailing
- Kabaddi
- Horse Race
- Boat Race
- Archery
- Tennis

---

### 3.2 Movie Language Extraction

**Requirement ID:** FR-002  
**Requirement Name:** Extract and Display Movie Languages

**Description:**  
The system shall display all available movie languages as filters in the Movies section, allowing users to extract and store these language options programmatically.

**Preconditions:**
- User is on the BookMyShow homepage
- Location is set to "Mumbai"
- Movies section is accessible

**Functional Details:**
1. **Navigation:** User clicks on "Movies" menu item in the main navigation bar
2. **Language Filters Display:** System displays language filters in the left sidebar under "Languages" section
3. **Language Options:** All available movie languages are displayed with checkboxes for filtering
4. **Data Extraction:** The automation script shall extract all language names and store them in a Java List data structure

**Available Movie Languages (As per exploration):**
1. Hindi
2. English
3. Marathi
4. Japanese
5. Malayalam
6. Korean
7. Gujarati
8. Telugu
9. Multi Language
10. Sindhi
11. Tamil

**Acceptance Criteria:**
- Movies section loads successfully with language filters visible
- All language filter options are displayed clearly
- Language names can be extracted using Selenium locators
- Extracted languages can be stored in a Java List<String> collection
- List maintains the display order from the website
- All languages should be extracted from the filter section

**Technical Implementation:**
- Use WebDriver to locate language filter elements
- Extract text from each language option using getText() method
- Store extracted values in ArrayList<String>
- Validate list size matches expected language count

---

### 3.3 User Authentication - Sign In with Google

**Requirement ID:** FR-003  
**Requirement Name:** Sign In with Invalid Google Credentials

**Description:**  
The system shall provide Google Sign-In functionality and display appropriate error messages when users attempt to sign in with invalid or incorrect Google account credentials.

**Preconditions:**
- User is on the BookMyShow homepage or any page
- User is not currently logged in
- "Sign in" button is visible in the header

**Functional Details:**
1. **Sign In Button:** User clicks on "Sign in" button in the top-right header area
2. **Authentication Modal:** System displays sign-in modal/popup with authentication options
3. **Google Sign-In Option:** Modal contains "Continue with Google" or similar option
4. **Invalid Credentials:** User enters invalid/incorrect Google account credentials
5. **Error Handling:** System displays appropriate error message indicating authentication failure

**Expected Behavior:**
- Sign-in button is clearly visible and clickable
- Click action opens authentication modal/dialog
- Google sign-in option is prominently displayed
- Error messages are clear and user-friendly
- Session remains unauthenticated after failed sign-in attempt

**Acceptance Criteria:**
- "Sign in" button is accessible from all pages
- Sign-in modal opens within 3 seconds of clicking the button
- Google authentication option is available in the modal
- Invalid credentials trigger error message display
- Error message is captured and validated by automation script
- User remains on the same page after error
- Modal can be closed without completing sign-in

**Known Issue (As per exploration):**
- During exploration (Feb 6, 2026), clicking "Sign in" button triggered an API error modal with message: "Sorry for bug-ging - Some issue from our side. Kindly refresh the page for more entertainment."
- This behavior should be captured as a defect in test execution
- Expected behavior: Sign-in modal should open with authentication options

**Error Message Capture:**
- Automation script should capture the error message text
- Error message should be logged for reporting
- Screenshot should be taken when error occurs
- Test should be marked as failed/blocked if sign-in modal doesn't appear

---

## 4. Non-Functional Requirements

### 4.1 Performance Requirements
- **Page Load Time:** All pages should load within 5 seconds on standard broadband connection
- **Filter Response Time:** Date and category filters should apply within 2 seconds
- **Search Results:** Event/movie search results should display within 3 seconds

### 4.2 Usability Requirements
- **Navigation:** Main navigation menu should be visible and accessible on all pages
- **Filter Clarity:** Filter options should be clearly labeled and easy to understand
- **Error Messages:** Error messages should be user-friendly and provide actionable guidance
- **Responsive Design:** Website should be responsive and functional across desktop resolutions (1366x768 minimum)

### 4.3 Compatibility Requirements
- **Browser Support:** Chrome 90+, Firefox 88+, Edge 90+, Safari 14+
- **Operating System:** Windows 10+, Mac OS 10.15+, Ubuntu 20.04+
- **Screen Resolution:** Minimum 1366x768, optimized for 1920x1080
- **Mobile Support:** Responsive design for mobile devices (not in automation scope)

---

## 5. User Interface Requirements

### 5.1 Navigation Structure
**Main Navigation Menu (Top Header):**
- Movies
- Stream
- Events
- Plays
- Sports
- Activities
- ListYourShow
- Corporates
- Offers
- Gift Cards

**User Account Area:**
- Search Box (Movies, Events, Plays, Sports and Activities)
- Location Selector (Default: Mumbai)
- Sign in Button (Top-right)

### 5.2 Sports Section UI
**Layout Components:**
- Page Title: "Sports In Mumbai" or similar
- Date Filter Bar:
  - Today
  - Tomorrow
  - This Weekend
  - Date Range (Custom selection)
- Category Filter (Sidebar):
  - Cricket
  - Running
  - Cycling
  - Sailing
  - Other sports categories
- Price Filter (if available)
- Sort Options (if available)
- Event Cards Display:
  - Event thumbnail image
  - Event name
  - Category badge
  - Date and day
  - Venue
  - Price (Starting from ₹XXX onwards)

### 5.3 Movies Section UI
**Layout Components:**
- Page Title: "Movies In Mumbai"
- Language Filter (Left Sidebar):
  - Filter heading: "Languages"
  - Clear button
  - Checkbox list of all languages
- Genre Filter (Left Sidebar)
- Format Filter (Left Sidebar)
- Browse by Cinemas option
- Movie Cards Display:
  - Movie poster
  - Movie title
  - Rating badge (UA16+, A, U, etc.)
  - Languages (comma-separated)
  - Likes/Votes count

### 5.4 Sign In Modal UI
**Expected Components:**
- Modal overlay (darkens background)
- Modal dialog box
- "Get Started" or "Sign In" heading
- "Continue with Google" button
- "Continue with Email" option (if available)
- "Continue with Apple" option (if available)
- Terms and Privacy Policy links
- Close button (X icon)

---

## 6. Data Requirements

### 6.1 Sports Events Data
**Data Fields:**
- Event ID (unique identifier)
- Event Name (String, max 200 characters)
- Category (String: Cricket, Running, Cycling, etc.)
- Date (Date format: "Day, DD MMM")
- Time (String, if specified)
- Venue (String, max 200 characters)
- City (String: Mumbai)
- Price Range (Integer: starting price in ₹)
- Image URL (String)
- Booking URL (String)

**Sample Data Structure:**
```
Event Name: "MUMBAI ICC MEN'S T20 WC 2026"
Category: "Cricket"
Date: "Saturday, 7 Feb"
Venue: "Wankhede Stadium"
Price: ₹150 onwards
```

### 6.2 Movie Language Data
**Data Structure:**
- Language Name (String)
- Display Order (Integer)
- Filter Status (Boolean: selected/unselected)

**Data Storage:**
```java
List<String> movieLanguages = new ArrayList<>();
// Contains: Hindi, English, Marathi, Japanese, Malayalam, 
// Korean, Gujarati, Telugu, Multi Language, Sindhi, Tamil
```

### 6.3 Authentication Error Data
**Error Message Structure:**
- Error Type (String)
- Error Message (String, max 500 characters)
- Error Code (if available)
- Timestamp (Date/Time of error occurrence)

---

## 8. Assumptions and Dependencies

### 8.1 Assumptions
1. Website is accessible and operational during test execution
2. Test data (sports events, movies) is available for the specified dates
3. Mumbai location has sufficient events for testing
4. Network connectivity is stable
5. Test environment has required browser drivers installed
6. Google authentication service is operational

### 8.2 Dependencies
1. **Selenium WebDriver:** Version 4.x or higher
2. **Java:** JDK 11 or higher
3. **TestNG:** Version 7.x or higher
4. **Maven:** Version 3.6 or higher
5. **Browser Drivers:** ChromeDriver, GeckoDriver for automation
6. **Jenkins:** For CI/CD integration (Phase 4)
7. **Apache POI:** For data-driven testing with Excel

---

## 9. Constraints and Limitations

### 9.1 Technical Constraints
- Test execution requires active internet connection
- Third-party OAuth flows (Google Sign-In) have automation limitations

### 9.2 Scope Limitations
- Mobile app testing is out of scope
- Performance and load testing not included
- Accessibility testing not included
- API testing not included in current scope

### 9.3 Environmental Limitations
- Tests run only in Mumbai region context
- Weekend event availability depends on actual event scheduling
- Sign-in testing limited to error validation (cannot use real Google credentials in automation)

---

## 10. Acceptance Criteria (Overall)

### 10.1 Functional Acceptance
✓ All three requirements (FR-001, FR-002, FR-003) are testable  
✓ Sports events are displayed and sortable by price  
✓ Movie languages can be extracted programmatically  
✓ Sign-in functionality behavior is documented (including errors)  
✓ Test scenarios cover positive and negative cases  
✓ Expected results are clearly defined for each scenario  

### 10.2 Automation Acceptance
✓ Page Object Model (POM) design pattern implemented  
✓ Reusable methods created for common actions  
✓ Test data managed through external files (Excel)  
✓ Comprehensive reporting with screenshots  
✓ Tests are independent and can run in any order  
✓ Minimum 20 test cases implemented

---

## 11. Glossary

| **Term** | **Definition** |
|----------|----------------|
| BookMyShow | Online entertainment ticket booking platform |
| POM | Page Object Model - Selenium design pattern |
| TestNG | Java testing framework for automation |
| Maven | Build automation tool for Java projects |
| WebDriver | Selenium API for browser automation |
| Hybrid Framework | Combination of data-driven and keyword-driven frameworks |
| CI/CD | Continuous Integration/Continuous Deployment |
| OAuth | Open Authorization protocol for secure authentication |
| API | Application Programming Interface |
| GUI | Graphical User Interface |

---

## 12. Approval

| **Role** | **Name** | **Signature** | **Date** |
|----------|----------|---------------|----------|
| QA Lead | _______________ | _______________ | ________ |
| Project Manager | _______________ | _______________ | ________ |
| Development Lead | _______________ | _______________ | ________ |
| Stakeholder | _______________ | _______________ | ________ |

---

**Document End**
