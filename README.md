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

### Serializable property types

Your entities have properties. You can make them available in your schema if they typed with one of the supported types (if you have other types of properties you can add custom scalar types or custom code in data fetcher to wrap these types to any of the supported types).

Supported types :

```java
// Scalar types
int, long, double, short, char, boolean, Integer, Long, Double, Short, Character, Boolean, String, 
BigInteger, BigDecimal, byte[], File, Date, LocalDate, LocalDateTime
// List of scalar. You can have other types of collection (like Set) in your entities, 
// but GraphQL will serialize them as JSON array anyway, if you want to handle specific
// collections deserialization you can do it in your data fetchers.
List<Integer>, List<Long> ...
// Enumerations
CustomEnum, List<CustomEnum>
// Relations 1To1, 1ToMany, ManyTo1, ManyToMany between entities
OtherEntity, List<OtherEntity>
// Relations 1To1, 1ToMany between an entity and an embedded entity (embedded = persisted as part of the entity, embedded entities may have all types of attributes including relations to other embedded entities but no relation to entities)
EmbeddedEntity, List<EmbeddedEntity>
```

### Defining entities

In order to define an entity you have to register a ```com.daikit.graphql.meta.entity.GQLEntityMetaData```  in the meta model.

```java
// Your domain entities
public class AbstractEntity { private String id; }
public class Entity1 extends AbstractEntity {
    // Scalars
    private int intAttr;
    private String stringAttr;
    // ...
    // Scalar collections (here with String but works with all scalar type)
    private List<String> stringList = new ArrayList<>();
    // Enumerations
    private Enum1 enumAttr;
    // Enumeration collections
    private List<Enum1> enumList = new ArrayList<>();
    // Relation to another entity (1to 1 or many to 1)
    private Entity2 entity2;
    // Relation to another entity (1 to many or many to many)
    private List<Entity3> entity3s = new ArrayList<>();
    // Embedded entity (persisted as part of Entity1)
    private EmbeddedEntity embeddedEntity1;
    // List of embedded entities (persisted as part of Entity1)
    private List<EmbeddedEntity> embeddedEntity1s = new ArrayList<>();
}
// Create meta data to be registered in meta model for schema building
final GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
metaData.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID));
metaData.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));
// ...
metaData.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
metaData.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
metaData.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
metaData.addAttribute(new GQLAttributeEntityMetaData("entity2", Entity2.class));
metaData.addAttribute(new GQLAttributeListEntityMetaData("entity3s", Entity3.class));
metaData.addAttribute(new GQLAttributeEmbeddedEntityMetaData("embeddedEntity1", embeddedEntity1.class));
metaData.addAttribute(new GQLAttributeListEmbeddedEntityMetaData("embeddedEntity1s", embeddedEntity1.class));
```

### Defining embedded entities

In order to define an entity you have to register a ```com.daikit.graphql.meta.entity.GQLEmbeddedEntityMetaData```  in the meta model. You would then do exactly the same than when you register an entity (see previous chapter) excepted that you cannot register an attribute with a relation to a non embedded entity.

### Defining custom methods

This library will generate CRUD method on entities and make them available in the schema. If you need to have other methods available in the schema you can define custom methods. A custom method can either be a query or a mutation.  
For each of these custom methods you will need to create a custom method object. This object will be a different type depending on the number of arguments of your method.

-> For 0 argument methods, implement IGQLCustomMethod0Arg or extend default implementation GQLCustomMethod0Arg
```java
// Example of query method returning an Entity
final GQLCustomMethod1Arg<Entity1, String> method = new GQLCustomMethod1Arg<Entity1, String>(
    "customMethodQuery", false) { 
    // "customMethodQuery" = method name
    // false = "this is a query not a mutation"
    @Override
    public Entity1 apply() {
        // Do something and return an Entity1
        return result;
    }
};
// The method meta data to be registered in meta model for schema building
final GQLAbstractMethodMetaData metaData = new GQLMethodEntityMetaData(method, Entity1.class);
```

-> For 1 argument methods, implement IGQLCustomMethod1Arg or extend default implementation GQLCustomMethod1Arg
```java
// Example of mutation method returning an Integer and taking 1 argument
final GQLCustomMethod1Arg<Integer, String> method = new GQLCustomMethod1Arg<Integer, String>(
    "customMethodMutation", true, "arg1") { 
    // "customMethodMutation" = method name
    // true = "this is mutation"
    // "arg1" = argument name within the schema
    @Override
    public Integer apply(String arg1) {
        // Do something and return an Integer
        return result;
    }
};
// The method meta data to be registered in meta model for schema building
final GQLAbstractMethodMetaData metaData = new GQLMethodScalarMetaData(method, Entity1.class, GQLScalarTypeEnum.INT);
metaData.addArgument(new GQLMethodArgumentScalarMetaData(method.getArgName(0), GQLScalarTypeEnum.STRING));
```

-> For 2 argument methods, implement IGQLCustomMethod2Arg or extend default implementation GQLCustomMethod2Arg
```java
// Example of mutation method returning a Date and taking 2 arguments
final GQLCustomMethod2Arg<Date, String, EmbeddedEntity1> method = 
    new GQLCustomMethod2Arg<Date, String, EmbeddedEntity1>(
    "customMethodMutation", true, "arg1", "arg2") { 
        // "customMethodMutation" = method name
        // true = "this is mutation"
        // "arg1" = argument 1 name within the schema
        // "arg2" = argument 2 name within the schema
    @Override
    public Date apply(String arg1, EmbeddedEntity1 arg2) {
        // Do something and return a Date
        return result;
    }
};
// The method meta data to be registered in meta model for schema building
final GQLAbstractMethodMetaData metaData = new GQLMethodScalarMetaData(
    method, Entity1.class, GQLScalarTypeEnum.DATE);
metaData.addArgument(new GQLMethodArgumentScalarMetaData(method.getArgName(0), GQLScalarTypeEnum.STRING));
metaData.addArgument(new GQLMethodArgumentEmbeddedEntityMetaData(method.getArgName(1), EmbeddedEntity1.class));
```

-> Etc... You can register methods with up to 5 arguments.

### Defining dynamic attributes

A dynamic attribute is an entity virtual property available in the schema but not in the entity itself. This attribute can be read and/or written thanks to methods you have to implement.

-> For a **readable** dynamic attribute, implement IGQLDynamicAttributeGetter or extend default implementation GQLDynamicAttributeGetter
```java
// Example of dynamic attribute getter registered on Entity1 and returning a computed String
final IGQLDynamicAttributeGetter<Entity1, String> dynamicAttributeGetter = 
    new GQLDynamicAttributeGetter<Entity1, String>("dynamicAttribute") { 
    // "dynamicAttribute" = attribute name within the schema
    @Override
    public String getValue(Entity1 source) {
        // Compute dynamic attribute String value and return it
        return result;
    }
};
// Register the attribute in the entity meta data
final GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
final GQLAttributeScalarMetaData attribute = new GQLAttributeScalarMetaData(
    dynamicAttributeGetter.getName(), GQLScalarTypeEnum.STRING);
attribute.setDynamicAttributeGetter(dynamicAttributeGetter);
metaData.addAttribute(attribute);
```

-> For a **writable** dynamic attribute, implement IGQLDynamicAttributeSetter or extend default implementation GQLDynamicAttributeSetter
```java
final IGQLDynamicAttributeSetter<Entity1, EmbeddedEntity1> dynamicAttributeSetter = 
    new GQLDynamicAttributeSetter<Entity1, EmbeddedEntity1>("dynamicAttribute") { 
    // "dynamicAttribute" = attribute name within the schema
    @Override
    public void setValue(Entity1 source, EmbeddedEntity1 valueToSet) {
        source.setStringAttr(valueToSet);
    }
};
// Register the attribute in the entity meta data
final GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
final GQLAttributeEmbeddedEntityMetaData attribute = new GQLAttributeEmbeddedEntityMetaData(
    dynamicAttributeSetter.getName(), EmbeddedEntity1.class);
attribute.setDynamicAttributeSetter(dynamicAttributeSetter);
metaData.addAttribute(attribute);
```

-> For a **readable** an **writable** attribute, implement IGQLDynamicAttributeGetter and IGQLDynamicAttributeSetter or extend default implementation GQLDynamicAttributeGetterSetter

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