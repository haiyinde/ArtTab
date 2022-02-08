sudo docker build -t arttab ./

sudo docker ps -f name=arttab  -q | xargs --no-run-if-empty docker container stop

sudo docker container ls -a -f name=arttab -q | xargs -r docker container rm

sudo docker run -d -p 8080:8080 --name arttab arttab