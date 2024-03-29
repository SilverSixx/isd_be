connectDb:
	docker exec -it isd_be-db-1  mysql -uroot -p2311 isd
showTables:
	docker exec -it isd_be-db-1  mysql -uroot -p2311 isd "SHOW TABLES;"
showAdminTable:
	docker exec -it isd_be-db-1  mysql "select * from admin"
showTeacherTable:
	docker exec -it isd_be-db-1  mysql "select * from teacher"
showKidTable:
	docker exec -it isd_be-db-1  mysql "select * from kid"
showClassTable:
	docker exec -it isd_be-db-1  mysql "select * from class"
dropContainer:
	docker rm isd_be-db-1
runContainer:
	docker-compose up -d
stopContainer:
	docker-compose down

.PHONEY : connectDb showTables showAdminTable showTeacherTable showKidTable showClassTable dropContainer runContainer stopContainer