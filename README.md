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
