# Simple repl program for sql written in java
Example after program starts:
```bash
Welcome to database tool
Available commands:
	init; - initialize database
	log <names of tables separated with space>; - generate log from specified tables
	exit; - stop program
	otherwise execute entered string as SQL query
Each command must end up with ';'
> init;
> SELECT * FROM customers;
customer_id: '1' first_name: 'First' last_name: 'User' 
customer_id: '2' first_name: 'Second' last_name: 'One' 
> SELECT * FROM sales;
sale_id: '1' product_id: '1' customer_id: '1' sale_date: '2020-01-01 14:00:01' 
sale_id: '2' product_id: '2' customer_id: '1' sale_date: '2020-02-01 17:10:01' 
sale_id: '3' product_id: '4' customer_id: '1' sale_date: '2020-04-01 11:00:01' 
sale_id: '4' product_id: '3' customer_id: '2' sale_date: '2020-04-01 12:00:01' 
sale_id: '5' product_id: '3' customer_id: '2' sale_date: '2020-04-01 10:00:01' 
sale_id: '6' product_id: '4' customer_id: '2' sale_date: '2020-05-01 12:00:01' 
sale_id: '7' product_id: '1' customer_id: '2' sale_date: '2020-05-01 11:00:01' 
> log customers;
Name of report file: customers_log.txt
> exit;

Process finished with exit code 0

```

resources/db.properties - properties for connections

test/resources/db.properties - properties for test connection (for tests H2 embedded database is used)


resources/ddl.sql - ddl for database initialization, will be executed after 'init;' command in repl

resources/dml.sql - dml for database initialization, will be executed after 'init;' command in repl