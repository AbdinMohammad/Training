<!-- ChallengeOne -->
## ChallengeOne

I used postgres to write these queries 

#### Validate the duplication
```sql
SELECT first_name ,last_name ,address ,city ,state ,zip_code , email ,birthdate, COUNT(*) FROM customer_related GROUP BY first_name ,last_name ,address ,city ,state ,zip_code , email ,birthdate HAVING COUNT(*) > 1;
```
|first_name|last_name|address      |city   |state|zip_code|email                |birthdate  |count|
|----------|---------|-------------|-------|-----|--------|---------------------|----------|-----|

#### Validate the Null Values
```sql
SELECT * FROM customer_related WHERE First_Name IS NULL OR Last_Name IS NULL OR Address IS NULL OR City IS NULL OR State IS NULL OR Zip_Code IS NULL OR Phone_Number IS NULL OR Email IS NULL OR birthdate IS NULL;
```
|first_name|last_name|address      |city   |state|zip_code|phone|email                |birthdate  |
|----------|---------|-------------|-------|-----|--------|---------------------|----------|-----|
|Sarah     |Lee      |567 Fifth St |Thirdtown|FL   |   45678|555-555-1212|sarah.lee@invalidemail|NULL|
|Jane      |Doe      |456 Second St|Anytown  |CA   |   12345|555-555-1212|jane.doe@invalidemail |02-02-1990|

#### Validate the Values
```sql
SELECT *,CAST(Zip_Code AS VARCHAR) FROM customer_related WHERE CAST(Zip_Code AS VARCHAR) !~ '^\d{5}$' OR Phone_Number !~ '^\d{3}-\d{3}-\d{4}$' OR Email NOT LIKE '%@%.%';
```
|first_name|last_name|address      |city   |state|zip_code|phone|email                |birthdate  |
|----------|---------|-------------|-------|-----|--------|---------------------|----------|-----|
|Jane      |Doe      |456 Second St|Anytown  |CA   |   12345|NULL|jane.doe@email.com     |1990-02-02|
|John      |Smith    |789 Third St |Othertown|NY   |   67890|555-555-1212|NULL|03-12-1987|
|Sarah     |Lee      |567 Fifth St |Thirdtown|FL   |   45678|555-555-1212|sarah.lee@invalidemail |NULL|
|John      |Smith    |123 Main St  |Anytown  |CA   |NULL|555-555-1212|john.smith@example.com |1980-01-01|
|John      |Smith    |789 Third St |Othertown|NULL|   67890|555-555-1212|john.smith@email.com   |1975-03-03|
|Bob       |Johnson  |234 Fourth St|Othertown|NY   |NULL|555-555-1212|bob.johnson@example.com|1985-04-04|
### Installation

Regarding whether the coulmns can be shared with client, it depends on the requirements and standards of organization. If the data has been validated and meets the necessary quality standards, it can be shared with client. However, if there are any issues or inconsistencies in the data, they should be addressed before sharing the table with client, and we can't share any data that can expose individuales to huge risks.
