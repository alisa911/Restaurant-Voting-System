## **Restaurant Voting System**
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0e6f5a2939fa4d50b3052ebd2db55ec2)](https://www.codacy.com/app/alisa911/Restaurant-voting-system?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alisa911/Restaurant-voting-system&amp;utm_campaign=Badge_Grade)
-------
REST API using Hibernate/Spring/SpringMVC without frontend
----
#### Task

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users

- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)

- Menu changes each day (admins do the updates)

- Users can vote on which restaurant they want to have lunch at

- Only one vote counted per user

- If user votes again the same day:

- If it is before 11:00 we asume that he changed his mind.

- If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

---
#### Launch

- $ mvn package

- $ mvn cargo:run

- localhost:8080/votingsystem

---
#### Users

**Username** - qweqwe@gmail.com	 **Password** - admin **Role** - ADMIN
**Username** - asdasd@gmail.com	 **Password** - user	**Role** - USER

---

#### API documentation

| API        | Method | Description          | URL                                            | Body            | User           |
|------------|--------|----------------------|------------------------------------------------|-----------------|----------------|
| Admin      | GET    | get all profiles     | {URL}/admin/users                              | none            | Admin          |
|            | GET    | get profile          | {URL}/admin/users/{userID}                     | none            | Admin          |
|            | GET    | get profile by email | {URL}/admin/users/by?email={userEMAIL}         | none            | Admin          |
|            | POST   | Create Profile       | {URL}/admin/users                              | Create Body     | Admin          |
|            | DELETE | Delete Profile       | {URL}/admin/users/{userID}                     | none            | Admin          |
|            | PUT    | Update Profile       | {URL}/admin/users/{userID}                     | Update Body     | Admin          |
| Profile    | GET    | Get Profile          | {URL}/profile                                  | none            | Authorized     |
|            | PUT    | Update Profile       | {URL}/profile                                  | Update Body     | Authorized     |
|            | DELETE | Delete Profile       | {URL}/profile                                  | none            | Authorized     |
|            | POST   | Register Profile     | {URL}/profile/register                         | Register Body   | Not Authorized |
| Vote       | GET    | Get All              | {URL}/vote                                     | none            | Authorized     |
|            | GET    | Get                  | {URL}/vote/{voteID}                            | none            | Authorized     |
|            | POST   | Create               | {URL}/vote                                     | Create Body     | Authorized     |
|            | DELETE | Delete               | {URL}/vote/{voteID}                            | none            | Authorized     |
|            | PUT    | Update               | {URL}/vote/{voteID}                            | Update Body     | Authorized     |
| Restaurant | GET    | Get All              | {URL}/restaurant/all                           | none            | Everyone       |
|            | GET    | Get                  | {URL}/restaurant/{restaurantID}                | none            | Everyone       |
|            | POST   | Create               | {URL}/restaurant                               | Create Body     | Admin Only     |
|            | DELETE | Delete               | {URL}/restaurant/{restaurantID}                | none            | Admin Only     |
|            | PUT    | Update               | {URL}/restaurant/{restaurantID}                | Update Body     | Admin Only     |
| Meals      | GET    | Get All              | {URL}/meals/all/{restaurantID}                 | none            | Everyone       |
|            | GET    | Get All By Date      | {URL}/meals/all/{restaurantID}?date=2019-08-19 | none            | Everyone       |
|            | GET    | Get                  | {URL}/meals/{mealID}                           | none            | Everyone       |
|            | GET    | Get With Restaurant  | {URL}/meals/with/{mealID}                      | none            | Everyone       |
|            | POST   | Create               | {URL}/meals                                    | Create Body     | Admin Only     |
|            | POST   | Create All           | {URL}/meals/all                                | Create All Body | Admin Only     |
|            | DELETE | Delete               | {URL}/meals/{mealID}                           | none            | Admin Only     |
|            | PUT    | Update               | {URL}/meals/{mealID}                           | Update Body     | Admin Only     |

