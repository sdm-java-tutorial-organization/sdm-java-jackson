
## Jackson

### #개념

- Jackson은 Java에서 Json 파일을 파싱할때 사용하는 라이브러리입니다.
  - Json뿐아니라, XML, YAML, CSV 등등 다양한 형식의 데이터를지원하는 Data-Processing 라이브러리입니다.
  - 내부는 스트림방식을 이용하여 속도가 빠르고 유연하며, 다양한 서드파티 데이터타입을 지원합니다.
  - Annotation 방식으로 메타데이터를 기술할 수 있어 JSON의 약점중 하나인 문서화와 데이터 Validatiaon 문제를 해결가능합니다.

-  Json 파싱에는 `GSON`, `SimpleJson` 등등 다양한 라이브러리가 있습니다.



### #구성

> core module

- [Streaming("jackson-core")](https://github.com/FasterXML/jackson-core) : defines low-level streaming API, and includes JSON-specific implementations
- [Annotations ("jackson-annotations")](https://github.com/FasterXML/jackson-annotations) : contains standard Jackson annotations
- [Databind  ("jackson-databind")](https://github.com/FasterXML/jackson-databind) :implements data-binding (and object serialization) support on streaming package; it depends both on streaming and annotations packages

> maven

- databind에 jackson-core, jackson-annotation의 의존성이 있어 databind 모듈만 기술해주면 됩니다.

```xml 
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>${jackson.version}</version>
</dependency>
```



### #장점

#### #MappingJacksonHttpMessageConverter

> `Jackson`은 `JSON`데이터를 출력하기위한 `MappingJacksonHttpMessageConverter`를 제공합니다.

Spring 3.0 이후 컨트롤러의 리턴방식이 `@RequestBody`형식이라면,  

Spring은 `MessageConverter` API를 통해 컨트롤러가 리턴하는 객체를 후킹합니다.

Spring의 `MessageConverter` 를 Jackson의 `MappingJacksonHttpMessageConverter`로 등록한다면, 

컨트롤러가 리턴하는 객체를 다시 뜯어 (자라리플랙션) Jackson의 ObjectMapper API로 JSON 객체를 만듭니다.

만약 Spirng `3.1` 버전 이후라면 클래스패스에 Jackson 라이브러리가 존재시에 MessageConverter가 자동으로 등록됩니다. 

```java
@RequestMapping("/json")
@ResponseBody()
public Object printJSON() {
    Person person = new Person("Mommoo", "Developer");
    return person;
}
```

> 즉, Jackson 덕분에 인스턴스를 리턴하더라도 JSON 포멧이 출력되게 됩니다.



### #API

#### #ObjectMapper

```java
ObjectMapper mapper = new ObjectMapper(); // create once, reuse
```

#### #JSON->POJO

```java
// File 에서 읽기 (readValue)
MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
//  URL 에서 읽기 (readValue)
value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
// String 으로 읽기 (readValue)
value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);
```

#### #POJO->JSON

```java
// File 로 저장 (writeValue)
mapper.writeValue(new File("result.json"), myResultObject);
// byte[] 로 저장 (writeValueAsBytes)
byte[] jsonBytes = mapper.writeValueAsBytes(myResultObject);
// string 으로 저장 (writeValueAsString)
String jsonString = mapper.writeValueAsString(myResultObject);
```

#### #개행처리

```java
// 개행처리하여 File 로 저장
mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), myResultObject);
```



### #Annotation

#### #@JsonIgnoreProperties

#### #@JsonIgnore

- Serializer/Deserialize 시 제외시킬 프로퍼티를 지정한다.
  - 전체지정 : @JsonIgnoreProperties 
  - 개별지정 : @JsonIgnore 

```java

@JsonIgnoreProperties({ "foo", "bar" })
public class MyBean
{
   //아래 두 개는 제외됨.
   public String foo;
   public String bar;
 
   // will not be written as JSON; nor assigned from JSON:
   @JsonIgnore
   public String internal;
 
   // no annotation, public field is read/written normally
   public String external;
    
   @JsonIgnore
   public void setCode(int c) { _code = c; }
 
   // note: will also be ignored because setter has annotation!
   public int getCode() { return _code; }
}
```



#### #@JsonProperty

- getter/setter 의 이름을 property 와 다른 이름을 사용할 수 있도록 설정한다.
- Database 를 자바 클래스로 매핑하는데 DB의 컬럼명이 알기 어려울 경우등에 유용하게 사용할 수 있다.

```sql
CREATE TABLE Users (
  u INT NOT NULL,
  a INT NOT NULL,
  e VARCHAR(80) NOT NULL
);
```

```java
public class User
{
    @JsonProperty("userId");
    public Integer u;
  
    @JsonProperty("age");
    public Integer a;
  
    @JsonProperty("email");
    public String e;
}
```

```json
{
    "userId": 1,
    "age": 13,
    "email": "user@host.com"
}
```



#### #JsonAutoDetect

- 맴버변수로만 Jackson을 구성하고 싶은 경우, `@JsonProperty`를 일일이 붙이는 것보다 편리합니다.

```java
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {
    private String myName = "Mommoo";
}
```

- getter / setter의 매핑정책을 설정할 수도 있습니다.
  다음의 경우는 맴버변수 뿐만 아니라, 기본적챙인 getter 역시 데이터 매핑이 진행됩니다.

```java
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY, 
    getterVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {
    private String myName = "Mommoo";
    
    public String getJob() {
        return "Developer";
    }
}
```





#### #@JsonInclude

- Serialize 시 동작을 지정
- 기본적으로 잭슨은 값의 유무와 상관없이 무조건 serialize 하게 되지만 
  다음과 같이 설정할 경우 not null 이거나 none empty 일 경우에만 serialize 된다.

```java
public class User
{
    public Integer u;
     
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer age;
 
 
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String email;
}
```



### #예외처리

#### #원리

> JSON 문자열은 작은 따옴표로 인용 할 수 없습니다. 
> spec의 여러 버전 (Douglas Crockford의 원문, ECMA 버전 및 IETF 버전)은 모두 문자열을 큰 따옴표로 묶어야한다고 말합니다. 
> 이것은 이론적 인 문제도 아니고 의견의 문제도 아닙니다. 
> 작은 따옴표로 묶은 문자열을 구문 분석해야하는 경우 실세계의 모든 JSON 파서가 오류가 발생합니다.
>
> [참조] https://stackoverflow.com/questions/19176024/how-to-escape-special-characters-in-building-a-json-string

![1554428051483](1554428051483.png)



- [CASE1] 따라서 문자열 사이에 추가적인 큰 따옴표가 들어가면 파싱오류를 발생시킵니다.
  - 위의 인용구대로 JSON은 큰 따옴표로만 구성이 가능합니다.

- [CASE2] 사진의 경우를 제외한 `\`사용은 JSON encode 과정에서 파싱오류를 발생시킵니다.
  - JSON encode 하는 과정에서 `\`는 지원하지 않으나 위의 사진과 같이 몇몇 `\ + char`경우에는 허용하고 있습니다.



#### #해결

- [CASE1] JSON 이용과정에서 큰 따옴표가 넘어오는 경우는 흔하기 때문에 `" -> \"` 변환처리를 진행합니다.

- [CASE2] `\`를 수용할 수 없기 때문에 `http status 204`로 처리하여 반환합니다.

  - > The 204 status code means that the request was received and understood, but that there is no need to send any data back.

> Case1

```java
public static String parseDoubleQuotes(String target) {
    return target
        .replace("\"", "\\\"")
        .replaceAll("\\\\{1,}", "\\\\");
}
```

> Case2 (AOP이용)

```java

```



[참조] <https://github.com/FasterXML/jackson>