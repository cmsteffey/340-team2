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
##### 2.2.2.2 Log In

## 3. UML Class Diagram
![UML Class Diagram](https://github.com/cmsteffey/340-team2/blob/main/doc/Object-Oriented-Design/class-diagram.png)
## 4. Database Schema
![UML Class Diagram](https://github.com/cmsteffey/340-team2/blob/main/doc/Object-Oriented-Design/schema.png)