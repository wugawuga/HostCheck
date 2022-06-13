# REST API
- Java 11
- Springboot 2.7.0
- MariaDB 10.0
- Jpa
- Gradle 7.4.1

# REST API

REST API ex)...

## Get 호스트 전체 조회

### Request

`GET /api/hosts`

    http://localhost:8085/api/hosts

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:27:55 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive
    
## Post 호스트 등록

### Request

`POST /api/hosts`

    http://localhost:8085/api/hosts

### Response

    HTTP/1.1 201
    Location: http://localhost:8085/api/hosts
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:46:12 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {id: 1, ip: "223.130.200.104", name: "naver.com", registrationTime: "2022/06/13 14:46",…}
 
## Get 특정 호스트 조회

### Request

`GET /api/hosts/ip`

    http://localhost:8085/api/hosts/ip

### Response

    HTTP/1.1 200
    Content-Disposition: inline;filename=f.txt
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:49:30 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {id: 1, ip: "223.130.200.104", name: "naver.com", registrationTime: "2022/06/13 14:46",…}
    
## Get 특정 호스트 없을 때

### Request

`GET /api/hosts/ip`

    http://localhost:8085/api/hosts/ip
    
### Response

    HTTP/1.1 404
    Content-Disposition: inline;filename=f.txt
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:50:43 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {msg: "존재하지 않는 호스트입니다", status: "NOT_FOUND"}
    
## Post 호스트 추가 등록 

### Request

`POST /api/hosts`

    http://localhost:8085/api/hosts

### Response

    HTTP/1.1 201
    Location: http://localhost:8085/api/hosts
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:53:41 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {id: 2, ip: "103.102.166.226", name: "wikipedia.com", registrationTime: "2022/06/13 14:53",…}
    
## Get 등록된 호스트 두개 이상일 때 전체 

### Request

`GET /api/hosts`

    http://localhost:8085/api/hosts

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 05:55:47 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    data: [{id: 1, ip: "223.130.200.104",…},{id: 2, ip: "103.102.166.226",…}]
    
## Put 호스트 수정

### Request

`PUT /api/hosts/ip`

    http://localhost:8085/api/hosts/ip

### Response

    HTTP/1.1 201
    Location: http://localhost:8085/api/hosts/103.102.166.226
    Content-Disposition: inline;filename=f.txt
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 13 Jun 2022 06:01:21 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {id: 1, ip: "103.102.166.226", name: 변경, registrationTime: "2022/06/13 15:00",…}

## Delete 호스트 삭제

### Request

`DELETE /api/hosts/ip`

    http://localhost:8085/api/hosts/ip

### Response

    HTTP/1.1 204
    Date: Mon, 13 Jun 2022 06:04:32 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive
