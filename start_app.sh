export DOVIT_API_BASE_DOMAIN=http://`curl https://ipinfo.io/ip`:8080/api
docker-compose up --build --remove-orphans