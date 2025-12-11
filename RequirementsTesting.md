# MagiCoach Requirements Testing
## Actors
- Coach (C)
- Player (P)

### Use Cases
#### 1. Provider: Create/Update provider profile use case:
1. Provider P logs in for the first time and creates a profile.
2. P creates new events E1 and E2 with values for searchable criteria C1, C2, C3 (C1=Duration, C2=Cost, C3=Date).  
P1 exits the app.

#### 2. Customer: Create customer profile:
1. Customer C1 logs in for the first time and creates a profile.

#### 3. Customer:  View and subscribe to services:
1. Customer C2 logs in for the first time and creates a new profile.
2. C2 views available Coaches Coach1 and Coach2.
3. C2 subscribes to Coach1.

#### 4. Customer: Write review
1. C2 log in and views their subscribed coaches.
2. C2 writes a positive review of coach C1. C2 exits.

#### 5. Customer: Attend Events
1. C1 logs in and views upcoming events.
2. C1 joins an event occuring next week.
3. C1 attends the event.

#### 6. Provider:  View Customer Statistics
1. Coach1 logs into their dashboard.
2. Coach1 gathers stastistics about C1.
3. Coach1 offers material tailored to their biggest fan C1.

#### 7. Provider: Create posts
1. Coach1 logs into their dashboard.
2. Coach1 types up a new announcement as a post.
3. Coach1 posts to their subscribers.

#### 8. Customer comments on a post
1. Customer signs into their dashboard.
2. Customers subscribes to a coach of their choosing.
3. Customer writes a comment on a post made by one of their subscribed coaches.

#### 9. Customer imports a deck
1. Customer signs into their dashboard and navigates to the decks tab.
2. Customer finds a deck they like on moxfield
3. Customer exports the deck as plain text
4. Customer pasts into import field and names
5. Customer has a shiny new deck ready to go.
6. Customer shows coach and they discuss during their appointment.

#### 10. Provider: Updates profile description
1. Provider logs in and visits their profile page.
2. Privder enters in a new description.
3. Provider confirms change and description is switched.
