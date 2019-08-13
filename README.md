[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0e6f5a2939fa4d50b3052ebd2db55ec2)](https://www.codacy.com/app/alisa911/Restaurant-voting-system?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=alisa911/Restaurant-voting-system&amp;utm_campaign=Badge_Grade)

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