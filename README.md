# Generics utils

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Documentation

Java-data-graphql is a layer above the great [graphql-java](https://github.com/graphql-java/graphql-java) library. It generates a graphql schema out of a given configuration which grants the possibility to :
* execute CRUD operations on entities (getById, getAll, save, delete).
* deal with persisted entities and embedded data objects.
* handle dynamic attributes on any entity.
* add custom methods (queries or mutations) to enrich your schema.
* deal with paging, sorting, filtering on queries.
* easily create "data fetchers" for linking this library to the persistence layer of your choice.

This library is well tested and used in several projects already. The real advantage of using this library over using graphql-java directly is that you do not define a graphql schema by yourself which can be a painful task, but you rather define a set of entities, custom methods etc and the graphQL schema is generated for you.  

### Getting started

In order to generate a schema there is 1 entry point : the ```com.daikit.graphql.builder.GQLSchemaBuilder``` class with its #buildSchema method.  
This method is waiting for a meta model and data fetchers.

### The meta model

The meta model ```com.daikit.graphql.meta.GQLMetaDataModel``` is the base of the schema generation. It is in the meta model that you can define all you entities (top level or embedded, abstract or concrete), enumerations and custom methods.  
The meta model can be written by hand in java or parsed from a json/yaml definition file or generated out of your code (for example using JPA meta model and spring components as a basis).  
See next sections for details on how to define these things and create this meta model.

### Data fetchers

Data fetchers ```graphql.schema.DataFetcher<?>``` are objects that will give provide the glue between graphQL and your persistence layer. you will have to provide a data fetcher for :
* 'getById' methods for retrieving a single entity by its ID
* 'getAll' methods for retrieving list of entities (with paging, filtering, sorting if needed)
* 'save' methods for creation & update of entities
* 'delete' methods dor deleting a single entity by its ID
* and 1 for each custom method that you define
  
You have an abstract class to extend for each of these data fetchers, this is making it quite straightforward you'll see.

### Defining entities

In order to define an entity you have to register a ```com.daikit.graphql.meta.data.entity.GQLEntityMetaData```  in the meta model.

```java
public class AbstractModel { private String id; }
public class Model1 extends AbstractModel {
    // Scalars
    private int intAttr;
    private long longAttr;
    private double doubleAttr;
    private String stringAttr;
    private boolean booleanAttr;
    private BigInteger bigIntAttr;
    private BigDecimal bigDecimalAttr;
    private byte[] bytesAttr;
    private short shortAttr;
    private char charAttr;
    private File fileAttr;
    private Date dateAttr;
    private LocalDate localDateAttr;
    private LocalDateTime localDateTimeAttr;
    // Scalar collections (here with String but works with all scalar type)
    private List<String> stringList = new ArrayList<>();
    private Set<String> stringSet = new HashSet<>();
    // Enumerations
    private Enum1 enumAttr;
    // Enumeration collections
    private List<Enum1> enumList = new ArrayList<>();
    private Set<Enum1> enumSet = new HashSet<>();
    // Relation to another entity (1to 1 or many to 1)
    private Model2 model2;
    // Relation to another entity (1 to many or many to many)
    private List<Model3> model3s = new ArrayList<>();
    // Embedded data (persisted as part of Model1)
    private EmbeddedData1 embeddedData1;
    // Embedded list of data (persisted as part of Model1)
    private List<EmbeddedData1> embeddedData1s = new ArrayList<>();
}
private GQLEntityMetaData buildEntity1() {
    final GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
    metaData.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID));
    metaData.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
    // ... same thing for all scalar types
    metaData.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
    metaData.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
    metaData.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
    metaData.addAttribute(new GQLAttributeEntityMetaData("entity2", Entity2.class));
    metaData.addAttribute(new GQLAttributeListEntityMetaData("entity3s", Entity3.class));
    metaData.addAttribute(new GQLAttributeEmbeddedEntityMetaData("embeddedData1", EmbeddedData1.class));
    metaData.addAttribute(new GQLAttributeListEmbeddedEntityMetaData("embeddedData1s", EmbeddedData1.class));
}
```

### Defining embedded entities

In order to define an entity you have to register a ```com.daikit.graphql.meta.data.entity.GQLEmbeddedEntityMetaData```  in the meta model.

```java
public class EmbeddedData1 {
	// Scalars
    private int intAttr;
    private long longAttr;
    // ... all other scalar types are accepted, @see Entity example
    // Scalar collections (here with String but works with all scalar type)
    private List<String> stringList = new ArrayList<>();
    // Enumerations
    private Enum1 enumAttr;
    // Enumeration collections
    private List<Enum1> enumList = new ArrayList<>();
    // Embedded data relations
    private EmbeddedData2 data2;
	private List<EmbeddedData3> data3s = new ArrayList<>();
}
private GQLEmbeddedEntityMetaData buildEmbeddedData1() {
    final GQLEmbeddedEntityMetaData metaData = new GQLEmbeddedEntityMetaData("EmbeddedData1", EmbeddedData1.class);
    metaData.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID));
    metaData.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
    // ... same thing for all scalar types
    metaData.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
    metaData.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
    metaData.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
    metaData.addAttribute(new GQLAttributeEmbeddedEntityMetaData("data2", EmbeddedData2.class));
    metaData.addAttribute(new GQLAttributeListEmbeddedEntityMetaData("data3s", EmbeddedData3.class));
}
```

### Defining custom methods
### Defining dynamic attributes
### Accessibility on entities & fields for CRUD operations

## Where can I get the latest release?

You can check latest version and pull it from the [central Maven repositories](https://mvnrepository.com/artifact/com.daikit/java-data-graphql):

With maven

```xml
<dependency>
    <groupId>com.daikit</groupId>
    <artifactId>java-data-graphql</artifactId>
    <version>x.x</version>
</dependency>
```

Or with gradle 

```gradle
compile group: 'com.daikit', name: 'java-data-graphql', version: 'x.x'
```

## Contributing

We accept Pull Requests via GitHub. There are some guidelines which will make applying PRs easier for us:
+ No spaces :) Please use tabs for indentation.
+ Respect the code style.
+ Create minimal diffs - disable on save actions like reformat source code or organize imports. If you feel the source code should be reformatted create a separate PR for this change.
+ Provide JUnit tests for your changes and make sure your changes don't break any existing tests by running ```mvn clean test```.

## License

This code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0).