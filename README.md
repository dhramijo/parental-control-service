Parental Control Service
===

Scenario
---

**Prevent access to movies based on parental control level**
*As a customer I don’t want my account to be able to access movies that have a higher parental control level than my 
current preference setting.*

**Parental Control Levels:**
U, PG, 12, 15, 18 (where U is the least restrictive and 18 is the most restrictive)

Instructions
---
Implementation of a *ParentalControlService* using the provided project shell. 

The service should accept as input the customer’s *parental control level 
preference* and a *movie id*. If the customer is able to watch the movie the *ParentalControlService* should indicate 
this to the calling client.

Movie Service
---
The Movie Meta Data team is currently developing the MovieService getParentalControlLevel call that accepts the *movie 
id* as an input and returns the parental control level for that movie as a string. 

This is a simple diagram of the interaction between the services:

```
Client                      ParentalControlService                      MovieService
------                      ----------------------                      ------------
  |                                   |                                       |
  | customer parental control level,  |                                       |
  |          movie id                 |                                       |
  |---------------------------------->|                                       |
  |                                   |      getParentalControlLevel(...)     |
  |                                   |-------------------------------------->|
  |                                   |                                       |
  |                                   |      movie parental control level     |
  |                                   |<--------------------------------------|
  |            boolean                |                                       |
  |<----------------------------------|                                       |
  |                                   |                                       |
```

MovieService Interface
---
```java
package com.thirdparty.movie;

public interface MovieService {
    String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
```

This service throws checked exceptions.

Acceptance Criteria
---

The following table describes the expected ParentalControlService result based on a given MovieService 
*getParentalControlLevel* response.

| MovieService getParentalControlLevel response| Description                                     | ParentalControlService result  
|----------------------------------------------|------------                                     |------------------------------
| Parental Control level                       |A string e.g. "PG"                               |If the parental control level of the movie is equal to or less than the customer’s preference indicate to the caller that the customer can watch the movie
| TitleNotFoundException                       |The movie service could not find the given movie |Indicate the error to the calling client.
| TechnicalFailureException                    |System error                                     |Indicate that the customer cannot watch this movie
