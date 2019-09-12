## **Restaurant Voting System**
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0e6f5a2939fa4d50b3052ebd2db55ec2)](https://www.codacy.com/app/alisa911/Restaurant-voting-system?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alisa911/Restaurant-voting-system&amp;utm_campaign=Badge_Grade)
-------
### REST API using Hibernate/Spring/SpringMVC without frontend
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

- **Username** - qweqwe@gmail.com	 **Password** - admin **Role** - ADMIN

- **Username** - asdasd@gmail.com	 **Password** - user	**Role** - USER

---

#### API documentation

| API        | Method | Description          | URL                                            | User           |
|------------|--------|----------------------|------------------------------------------------|----------------|
| Admin      | GET    | get all profiles     | {URL}/admin/users                              | Admin          |
|            | GET    | get profile          | {URL}/admin/users/{userID}                     | Admin          |
|            | GET    | get profile by email | {URL}/admin/users/by?email={userEMAIL}         | Admin          |
|            | POST   | Create Profile       | {URL}/admin/users                              | Admin          |
|            | DELETE | Delete Profile       | {URL}/admin/users/{userID}                     | Admin          |
|            | PUT    | Update Profile       | {URL}/admin/users/{userID}                     | Admin          |
| Profile    | GET    | Get Profile          | {URL}/profile                                  | Authorized     |
|            | PUT    | Update Profile       | {URL}/profile                                  | Authorized     |
|            | DELETE | Delete Profile       | {URL}/profile                                  | Authorized     |
|            | POST   | Register Profile     | {URL}/profile/register                         | Not Authorized |
| Vote       | GET    | Get All              | {URL}/vote                                     | Authorized     |
|            | GET    | Get                  | {URL}/vote/{voteID}                            | Authorized     |
|            | POST   | Create               | {URL}/vote                                     | Authorized     |
|            | DELETE | Delete               | {URL}/vote/{voteID}                            | Authorized     |
|            | PUT    | Update               | {URL}/vote/{voteID}                            | Authorized     |
| Restaurant | GET    | Get All              | {URL}/restaurant/all                           | Everyone       |
|            | GET    | Get                  | {URL}/restaurant/{restaurantID}                | Everyone       |
|            | POST   | Create               | {URL}/restaurant                               | Admin Only     |
|            | DELETE | Delete               | {URL}/restaurant/{restaurantID}                | Admin Only     |
|            | PUT    | Update               | {URL}/restaurant/{restaurantID}                | Admin Only     |
| Meals      | GET    | Get All              | {URL}/meals/all/{restaurantID}                 | Everyone       |
|            | GET    | Get All By Date      | {URL}/meals/all/{restaurantID}?date=2019-08-19 | Everyone       |
|            | GET    | Get                  | {URL}/meals/{mealID}                           | Everyone       |
|            | GET    | Get With Restaurant  | {URL}/meals/with/{mealID}                      | Everyone       |
|            | POST   | Create               | {URL}/meals                                    | Admin Only     |
|            | POST   | Create All           | {URL}/meals/all                                | Admin Only     |
|            | DELETE | Delete               | {URL}/meals/{mealID}                           | Admin Only     |
|            | PUT    | Update               | {URL}/meals/{mealID}                           | Admin Only     |

---
### CURL

### Admin

| Description          | CURL                                                                                                                                                                                                                                                                                     |
|----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|get all profiles      |`curl -s {URL}/admin/users --user qweqwe@gmail.com:admin`                                                                                                                                                                                                                                 |
|get profile           | `curl -s {URL}/admin/users/101 --user qweqwe@gmail.com:admin`                                                                                                                                                                                                                            |
| get profile by email | `curl -s {URL}/admin/users/by?email=qweqwe@gmail.com --user qweqwe@gmail.com:admin`                                                                                                                                                                                                      |
| Create Profile       | `curl -s -X POST -d '{"id": null,"name": "New User","email": "newemail2@gmail.com","password": "newPass","registered": "2019-09-12T12:00:00","enabled": true,"roles": ["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' {URL}/admin/users --user qweqwe@gmail.com:admin` |
| Update Profile       | `curl -s -X PUT -d '{"id": 1001,"name": "UpdatingName","email": "updatingemail@gmail.com"}' -H 'Content-Type: application/json' {URL}/admin/users/1001 --user qweqwe@gmail.com:admin`  
                                                                                                  
### Profile

| Description      | CURL                                                                                                                                                                                                                                                                                     |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Get Profile      | `curl -s {URL}/profile --user qweqwe@gmail.com:admin`                                                                                                                         |
| Update Profile   | `curl -s -X PUT -d '{"name": "newName","email": "newemail@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json' {URL}/profile --user qweqwe@gmail.com:admin` |
| Delete Profile   | `curl -s -X DELETE {URL}/profile --user qweqwe@gmail.com:admin`                                                                                                               |
| Register Profile | `curl -s -X POST -d '{"name": "newName","email": "newemail@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json;charset=UTF-8' {URL}/profile/register`       |

### Vote

| Description      | CURL                                                                                                                                                                            
|---------|--------------------------------------------------------------------------------------------------------------------------------------------|
| Get All | `curl -s {URL}/vote --user qweqwe@gmail.com:admin`                                                                                         |
| Get     | `curl -s {URL}/vote/1013 --user qweqwe@gmail.com:admin`                                                                                    |
| Create  | `curl -s -X POST -d '{"restaurantId": 1002}' -H 'Content-Type: application/json;charset=UTF-8' {URL}/vote --user qweqwe@gmail.com:admin`   |
| Delete  | `curl -s -X DELETE {URL}/vote/1011 --user qweqwe@gmail.com:admin`                                                                          |
| Update  | `curl -s -X PUT -d '{"id": 1013, "restaurantId": 1002}' -H 'Content-Type: application/json' {URL}/vote/1013 --user qweqwe@gmail.com:admin` |

### Restaurant

| Description      | CURL                                                                                                                                                                            
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| Get All | `curl -s {URL}/restaurant/all`                                                                                                                     |
| Get     | `curl -s {URL}/restaurant/1003`                                                                                                                    |
| Create  | `curl -s -X POST -d '{"name": "New Restaurant"}' -H 'Content-Type: application/json;charset=UTF-8' {URL}/restaurant --user qweqwe@gmail.com:admin` |
| Delete  | `curl -s -X DELETE {URL}/restaurant/1003 --user qweqwe@gmail.com:admin`                                                                            |
| Update  | `curl -s -X PUT -d '{"id": 1002, "name": "Updated Name"}' -H 'Content-Type: application/json' {URL}/restaurant/1002 --user qweqwe@gmail.com:admin` |

### Meals

| Description         | CURL                                                                                                                                                                            
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Get All             | `curl -s {URL}/meals/all/1004`                                                                                                                                             |
| Get All By Date     | `curl -s {URL}/meals/all/1004?date=2019-08-19`                                                                                                                             |
| Get                 | `curl -s {URL}/meals/1005`                                                                                                                                                 |
| Get With Restaurant | `curl -s {URL}/meals/with/1005`                                                                                                                                            |
| Create              | `curl -s -X POST -d '{"name": "New Meal 1","price": 30, "restaurantId": 103}' -H 'Content-Type: application/json;charset=UTF-8' {URL}/meals --user admin@gmail.com:admin`  |
| Delete              | `curl -s -X DELETE {URL}/meals/1005 --user qweqwe@gmail.com:admin`                                                                                                         |
| Update              | `curl -s -X PUT -d '{"id": 1005,"name": "New meal","price": 10, "restaurantId": 1002}' -H 'Content-Type: application/json' {URL}/meals/1005 --user qweqwe@gmail.com:admin` |

                                                                                                  |