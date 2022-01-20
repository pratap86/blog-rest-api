# Blog-Rest-API

### Spring Boot REST Api Validation
1. Add maven dependency

``` ruby
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

2. Apply validation annotation to a RequestModel bean, ex @NotNull, @NotBlank and @Size
3. Enable validation on Spring Rest controller by adding @Valid annotation in addition to @RequestBody
4. To customize response validation we need to extend ResponseEntityExceptionHandler class and override <br>handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) method.

### Spring Boot Securing REST Api
1. Add maven dependency

``` ruby
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2. update application.yml

``` ruby
logging.level.org.springframework.security = DEBUG
```

### JWT

- Best way to communicate securely between client and server
- Follows stateless authentication mechanism
- We should go with JWT when;
  - Authorization
  - Information Exchange

#### JWT Structure

- JWT consist of three parts separated by dot, eg: xxxx.yyyy.zzzz
  - Header
  - Payload
  - Signature

### Versioning REST APIs

- API versioning is the practice of transparently managing changes to your API, there are four ways of versioning a REST API through;
    - URI path
    - query parameter
    - custom headers
    - content negotiation


