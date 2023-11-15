# boot3-configurable-mdc

A Spring Boot 3 utility library to automatically append those application
properties from `logging.map.*` to your Mapped Diagnostic Context.

## Dependency

In your gradle file

_Follow [this guide](https://github.com/testersen/no.ghpkg) on how to set up
your environment for GitHub packages._

<!-- @formatter:off -->
```kt
plugins {
  id("no.ghpkg") version "0.3.3"
}

repositories {
  git.hub("telenornorway")
}

dependencies {
  implementation("no.telenor.kt:boot3-configurable-mdc:<VERSION HERE>")
  // implementation("org.springframework.boot:spring-boot-starter-web")
}
```
<!-- @formatter:on -->

## Usage

<!-- @formatter:off -->
```yaml
logging.map:
  request.userAgent: '#request.getHeader("user-agent")'
  request.correlationId: '#request.getHeader("x-correlation-id") ?: T(java.util.UUID).randomUUID()'
```
<!-- @formatter:on -->

Other libraries can also provide default mappers that can either be enabled by
default, or manually enabled in configuration:

<!-- @formatter:off -->
```java
@Configuration
public class MyDefaultMdcMappers {
  MyDefaultMdcMappers(KeyRegistry registry) {
    // Add request.userAgent, which is enabled by default.
    registry.define(
      "request.userAgent",
      (context) -> ((HttpServletRequest) context.get("request")).getHeader("user-agent")
    );
  }
}
```
<!-- @formatter:on -->

This will now append `request.userAgent` to the MDC by default, this can be
disabled in the application properties by using a falsy boolean on the key:

<!-- @formatter:off -->
```yaml
logging.map:
  request.userAgent: off # false
```
<!-- @formatter:on -->

Similarly, we can create a default key that must be manually enabled in the
application properties.

<!-- @formatter:off -->
<!-- @noinspection -->
```java
registry.define(
  "request.id",
  false, // enabled = false | true
  (context) -> Optional.ofNullable(((HttpServletRequest) context.get("request")).getHeader("x-request-id"))
    .orElse(UUID.randomUUID())
)
```
<!-- @formatter:on -->

The above default value will be registered, but it will not be included by
default, but can be activated using the application properties.

<!-- @formatter:off -->
```yaml
logging.map:
  request.id: on # true
```
<!-- @formatter:on -->
