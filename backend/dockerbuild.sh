docker build -t arttab ./

docker ps -f name=arttab  -q | xargs --no-run-if-empty docker container stop

docker container ls -a -f name=arttab -q | xargs -r docker container rm

docker run -d -p 8080:8080 --name arttab arttab