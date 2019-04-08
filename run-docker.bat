title coffee_shop
call mvn clean -Pdocker install -DskipTests
echo on
docker-compose -f docker-compose.yml up
pause