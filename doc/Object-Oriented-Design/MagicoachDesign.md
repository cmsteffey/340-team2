# Magicoach - Software Design

Version 1  
Prepared by Sasha Steffey and Evan Spiering\
Magicoach\
Oct 18, 2025

Table of Contents
=================
* [Revision History](#revision-history)
* 1 [Product Overview](#1-product-overview)
* 2 [Use Cases](#2-use-cases)
    * 2.1 [Use Case Model](#21-use-case-model)
    * 2.2 [Use Case Descriptions](#22-use-case-descriptions)
        * 2.2.1 [Actor: Coach](#221-actor-coach)
        * 2.2.2 [Actor: Customer](#222-actor-customer)
* 3 [UML Class Diagram](#3-uml-class-diagram)
* 4 [Database Schema](#4-database-schema)

## Revision History
| Name  | Date  | Reason For Changes | Version |
|-------|-------|--------------------|---------|
| Sasha | 10/18 | Initial Template   | 1       |
| Sasha | 10/18 | Coach UCs          | 2       |
|       |       |                    |         |

## 1. Product Overview

## 2. Use Cases
### 2.1 Use Case Model
![Use Case Model](https://github.com/cmsteffey/340-team2/blob/main/doc/Object-Oriented-Design/use-case.png)

### 2.2 Use Case Descriptions

#### 2.2.1 Actor: Coach
##### 2.2.1.1 Sign Up
A coach can sign up to create their profile with their name, email, password, and phone number. Emails must be unique.
##### 2.2.1.2 Log In
A coach shall be able to sign in using their registered email and password. After logging in, the coach
will be redirected to their dashboard where they see an overview of their stats, appointments, reviews, and other information.
##### 2.2.1.3 Update Profile
A coach shall be to modify their profile by going to their profile page. They can change their email, password, and advertisement.
##### 2.2.1.4 Create Posts
From their profile page, coaches shall be able to make posts on their account with text which appear in customers' feeds
who subscribe.
##### 2.2.1.5 Import Decks
A coach shall be able to make a deck or view a deck on Scryfall.com and import it into their profile. This will later be able to be viewed
and shared with customers.
##### 2.2.1.6 Offer Appointments
A coach shall be able to create services that they offer with prices and descriptions. 
##### 2.2.1.7 Offer Events
A coach shall be able to create events with an address, time, entrance cost, and description.

#### 2.2.2 Actor: Customer
##### 2.2.2.1 Sign Up
A customer can create a profile with an email, username, password. Every customer has to have a unique email.
##### 2.2.2.2 Log In
Every customer can log into their account by providing their email and password provided during account sign up. Each user will be put on the dashboard/home page once they have signed in successfully.
##### 2.2.2.3 Update Profile
Customers will be able to access their profile page which will allow them to customize features about their profile like their display name, email, and password if needed. This is also where you would be able to add a payment method to your account in order to pay for coaching.
##### 2.2.2.4 Import Decks
Customers will have the ability to import decks from scryfall into the website through the export link from scryfall. This deck will be viewable through the user decks tab.
##### 2.2.2.5 Create Replies
Customers will be able to create replies from their dashboard on any posts made by coaches in order to provide feedback to the coach and share their opinions on the topic.
##### 2.2.2.6 Schedule Coaching Sessions
Customers will be able to pick a coach and sign up for a coaching session through the dashboard by clicking on the coach and selected a time slot.
##### 2.2.2.7 Subscribe to Coach
Customers will be able to subscribe to a coach so that any future posts from the coach will leave a notification on their website explorer (top bar) which they can click to visit the post made from the coach.
##### 2.2.2.8 Join Events
Customers will be able to RSVP to events notifying the coach that they will be attending the event.

## 3. UML Class Diagram
![UML Class Diagram](https://github.com/cmsteffey/340-team2/blob/main/doc/Object-Oriented-Design/class-diagram.png)
## 4. Database Schema
![DB Schema](https://github.com/cmsteffey/340-team2/blob/main/doc/Object-Oriented-Design/schema.png)