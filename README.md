# Java data GraphQL

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

### Usage with spring and demo

Demo project with spring boot available here : [spring-data-graphql-demo](https://github.com/tcaselli/spring-data-graphql-demo)

### Generate GraphQL executor and schema

In order to generate a GraphQL executor with its related schema there is 1 entry point : the ```com.daikit.graphql.execution.GQLExecutor```.  
The constructor for this executor is waiting for a [schema configuration](#the-schema-configuration), a [meta model](#the-meta-model), an [error processor](#the-error-processor), some [data fetchers](#data-fetchers), and eventually an [executor callback](#the-executor-callback) to provide hooks before and/or after each execution.

```java
GQLExecutor executor = new GQLExecutor(
    createSchemaConfig(), // see "The schema configuration" section below
    createMetaModel(),  // see "The meta model" section below
    createErrorProcessor(), // See "The error processor" section below
    createGetByIdDataFetcher(), // see "Data Fetchers" section below
    createListDataFetcher(),
    createSaveDataFetchers(),
    createDeleteDataFetcher(),
    createCustomMethodsDataFetcher(),
    createPropertyDataFetchers()
);
```

### Execute operations on the schema
After building the GQLExecutor you can now request your schema.


```java
// Get query/mutation string somehow
String query = readGraphql("query.graphql");
// Then run the query/mutation (with one parameter here)
GQLExecutionResult result = executor.execute(
    ExecutionInput.newExecutionInput()
        .query(query)
        .variables(Collections.singletonMap("param", "value"))
        .build()
);
```

### The schema configuration

The schema configuration allows to choose prefixes, suffixes, attribute names,
 type names, method names... for generated schema items. Just use default class ```com.daikit.graphql.config.GQLSchemaConfig``` and eventually modify properties of your choice if default values do not suit your needs.

### The meta model

The meta model ```com.daikit.graphql.meta.GQLMetaModel``` **is the base of the schema generation**. It is in this meta model that you will define all your **entities** (top level or embedded, abstract or concrete), **enumerations**, **dynamic attributes** and **custom methods**.  
The meta model can be written by hand in java or automatically parsed from java domain model objects (for example from JPA entities).  

--> See next sections for details on how to define these meta datas, dynamic attributes and custom methods.  

### Creation of the meta model

You have **3 ways** of creating the meta model :

#### The automatic way

```java
/**
 * With this method, all meta datas will be
 * automatically generated and registered from given entity classes,
 * attributes and methods. Entities, enums, dynamic attributes and custom
 * method meta data will be automatically generated and registered.
 *
 * @param entityClasses
 *            the collection of entity classes
 * @param availableEmbeddedEntityClasses
 *            the collection of all available embedded entity classes. 
 *            It is the only way to provide allowed extending classes 
 *            for embedded entities. You can leave this null or
 *            empty if you don't need this advanced behavior.
 * @param dynamicAttributes
 *            the collection of IGQLAbstractDynamicAttribute to be
 *            automatically registered
 * @param customMethods
 *            the collection of GQLAbstractCustomMethod to be
 *            automatically registered
 * @return the created instance
 */
public static GQLMetaModel createFromEntityClasses(
    Collection<Class<?>> entityClasses,
    Collection<Class<?>> availableEmbeddedEntityClasses,
    Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes,
    Collection<IGQLAbstractCustomMethod<?>> customMethods) {...}
```

#### The semi-automatic way (manual generation of entities but automatic generation of dynamic attributes and custom methods)

```java
/**
 * With this method, entities and enums MetaData have been created manually
 * but dynamic attributes and custom method meta data will 
 * be automatically generated and registered.
 *
 * @param enumMetaDatas
 *            the collection of all registered GQLEnumMetaData
 * @param entityMetaDatas
 *            the collection of all registered GQLEntityMetaData
 * @param dynamicAttributes
 *            the collection of IGQLAbstractDynamicAttribute to be
 *            automatically registered (meta data will be created
 *            automatically)
 * @param customMethods
 *            the collection of GQLAbstractCustomMethod to be
 *            automatically registered (meta data will be created
 *            automatically)
 * @return the created instance
 */
public static GQLMetaModel createFromMetaDatas(
    Collection<GQLEnumMetaData> enumMetaDatas,
    Collection<GQLEntityMetaData> entityMetaDatas,
    Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes,
    Collection<IGQLAbstractCustomMethod<?>> customMethods) {...}
```

#### The manual way (manual generation of entities but automatic generation of dynamic attributes and custom methods)

```java
/**
 * With this method, dynamic attributes should  already be registered in entities
 * and custom methods should already have their MetaData automatically generated 
 * and registered.
 *
 * @param enumMetaDatas
 *            the collection of all registered  GQLEnumMetaData
 * @param entityMetaDatas
 *            the collection of all registered GQLEntityMetaData
 * @param methodMetaDatas
 *            the collection of all registered GQLAbstractMethodMetaData
 * @return the created instance
 */
public static GQLMetaModel createFromMetaDatas(
    Collection<GQLEnumMetaData> enumMetaDatas,
    Collection<GQLEntityMetaData> entityMetaDatas,
    Collection<GQLAbstractMethodMetaData> methodMetaDatas) {...}
```

**Be careful, all entities and enums referenced in dynamic attributes or custom methods arguments and returned types must have a corresponding registered meta data.**

### Serializable property types for building meta model

Your entities have properties. You can make them available in your schema if they are typed with one of the supported types (if you have other types of properties you can add custom scalar types or custom code in data fetcher to wrap these types to any of the supported types).

Supported types :

```java
// Scalar types
int, long, double, short, char, boolean, Integer, Long, Double, Short, Character, Boolean, String, 
BigInteger, BigDecimal, byte[], File, Date, LocalDate, LocalDateTime, Instant
// List of scalar. You can have other types of collection (like Set) in your entities, 
// but GraphQL will serialize them as JSON array anyway, if you want to handle specific
// collections deserialization you can do it in your data fetchers.
List<Integer>, List<Long> ...
// Enumerations
CustomEnum, List<CustomEnum>
// Relations 1To1, 1ToMany, ManyTo1, ManyToMany between entities
OtherEntity, List<OtherEntity>
// Relations 1To1, 1ToMany between an entity and an embedded entity 
// (embedded = persisted as part of the entity, embedded entities may have all 
// types of attributes including relations to other embedded entities but 
// no relation to entities)
EmbeddedEntity, List<EmbeddedEntity>
```

### Manual definition of entities in meta model

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
// Create meta data to be registered in meta model for schema generation
GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
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

### Manual definition of embedded entities in meta model

In order to define an entity you have to register a ```com.daikit.graphql.meta.entity.GQLEmbeddedEntityMetaData```  in the meta model. You would then do exactly the same than when you register an entity (see previous chapter) excepted that in an embedded entity you cannot register an attribute with a relation to a non embedded entity.

### Manual definition of custom methods in meta model

This library will generate CRUD method on entities and make them available in the schema. If you need to have other methods available in the schema you can define custom methods. A custom method can either be a query or a mutation.  
For each of these custom methods you will need to create a custom method object. This object will be of a different type depending on the number of arguments of your method.

-> For 0 argument methods, implement IGQLCustomMethod0Arg or extend default implementation GQLCustomMethod0Arg
```java
// Example of query method returning an Entity
GQLCustomMethod1Arg<Entity1, String> method = new GQLCustomMethod1Arg<Entity1, String>(
    "customMethodQuery", false) { 
    // "customMethodQuery" = method name
    // false = "this is a query not a mutation"
    @Override
    public Entity1 apply() {
        // Do something and return an Entity1
        return result;
    }
};
// The method meta data to be registered in meta model for schema generation
GQLAbstractMethodMetaData metaData = new GQLMethodEntityMetaData(method, Entity1.class);
```

-> For 1 argument methods, implement IGQLCustomMethod1Arg or extend default implementation GQLCustomMethod1Arg
```java
// Example of mutation method returning an Integer and taking 1 argument
GQLCustomMethod1Arg<Integer, String> method = new GQLCustomMethod1Arg<Integer, String>(
    "customMethodMutation", true, "arg1") { 
    // "customMethodMutation" = method name
    // true = "this is mutation"
    // "arg1" = argument name within the generated schema
    @Override
    public Integer apply(String arg1) {
        // Do something and return an Integer
        return result;
    }
};
// The method meta data to be registered in meta model for schema generation
GQLAbstractMethodMetaData metaData = new GQLMethodScalarMetaData(
    method, Entity1.class, GQLScalarTypeEnum.INT);
metaData.addArgument(new GQLMethodArgumentScalarMetaData(
    method.getArgName(0), GQLScalarTypeEnum.STRING));
```

-> For 2 argument methods, implement IGQLCustomMethod2Arg or extend default implementation GQLCustomMethod2Arg
```java
// Example of mutation method returning a Date and taking 2 arguments
GQLCustomMethod2Arg<Date, String, EmbeddedEntity1> method = 
    new GQLCustomMethod2Arg<Date, String, EmbeddedEntity1>(
    "customMethodMutation", true, "arg1", "arg2") { 
    // "customMethodMutation" = method name
    // true = "this is mutation"
    // "arg1" = argument 1 name within the generated schema
    // "arg2" = argument 2 name within the generated schema
    @Override
    public Date apply(String arg1, EmbeddedEntity1 arg2) {
        // Do something and return a Date
        return result;
    }
};
// The method meta data to be registered in meta model for schema generation
GQLAbstractMethodMetaData metaData = new GQLMethodScalarMetaData(
    method, Entity1.class, GQLScalarTypeEnum.DATE);
metaData.addArgument(new GQLMethodArgumentScalarMetaData(
    method.getArgName(0), GQLScalarTypeEnum.STRING));
metaData.addArgument(new GQLMethodArgumentEmbeddedEntityMetaData(
    method.getArgName(1), EmbeddedEntity1.class));
```

-> Etc... You can register methods with up to 5 arguments.

### Manual definition of dynamic attributes in meta model

A dynamic attribute is an entity virtual property available in the schema but not in the entity itself. This attribute can be read and/or written thanks to methods you have to implement.

-> For a **readable** dynamic attribute, implement IGQLDynamicAttributeGetter or extend default implementation GQLDynamicAttributeGetter
```java
// Example of dynamic attribute getter registered on Entity1 and returning a computed String
IGQLDynamicAttributeGetter<Entity1, String> dynamicAttributeGetter = 
    new GQLDynamicAttributeGetter<Entity1, String>("dynamicAttribute") { 
    // "dynamicAttribute" = attribute name within the generated schema
    @Override
    public String getValue(Entity1 source) {
        // Compute dynamic attribute String value and return it
        return result;
    }
};
// Then provide this attribute getter to the MetaModel constructor (see below)
```

-> For a **writable** dynamic attribute, implement IGQLDynamicAttributeSetter or extend default implementation GQLDynamicAttributeSetter
```java
IGQLDynamicAttributeSetter<Entity1, EmbeddedEntity1> dynamicAttributeSetter = 
    new GQLDynamicAttributeSetter<Entity1, EmbeddedEntity1>("dynamicAttribute") { 
    // "dynamicAttribute" = attribute name within the generated schema
    @Override
    public void setValue(Entity1 source, EmbeddedEntity1 valueToSet) {
        source.setStringAttr(valueToSet);
    }
};
// Then provide this attribute getter to the MetaModel constructor (see below)
```

-> For a **readable** AND **writable** dynamic attribute, implement both IGQLDynamicAttributeGetter and IGQLDynamicAttributeSetter or extend default implementation GQLDynamicAttributeGetterSetter

-> For a **readable** dynamic attribute that needs to be available as a filter in getList queries, set the attribute as filterable and implement IGQLDynamicAttributeGetter providing a ```filterQueryPath``` or overriding ```<QUERY_TYPE> void applyModificationsOnRequest(QUERY_TYPE query)``` method.

### Accessibility on entities & fields for CRUD operations

As stated before, this library is generating a schema with methods giving the possibility to execute CRUD operations on entities (getById, getAll, save, delete).  
By default all registered entities will have these 4 methods generated, but you can customize these methods generation in the meta model.

#### Customizing accessibility with automatic meta model generation

With automatic generation, meta data are created using reflection on entities classes.
You can configure this generation thanks to annotations.

Accessibility on entities using ```com.daikit.graphql.meta.GQLEntity``` annotation :

```java
// no save method will be generated in schema for this entity
@GQLEntity(save=false)
public class Entity1 {}

// no getById/getAll method will be generated in schema for this entity
@GQLEntity(read=false)
public class Entity1 {}

// no delete method will be generated in schema for this entity
@GQLEntity(delete=false)
public class Entity1 {}

// see GQLEntity annotation documentation for further details
// on possible configurations
[...]
```

Accessibility on attributes using ```com.daikit.graphql.meta.GQLAttribute``` annotation :

```java
public class Entity1 {
    // this attribute will not be redable, saveable and filterable
    @GQLAttribute(read = false, save=false, filter=false)
    private int intAttr;

    // see GQLAttribute annotation documentation for further details
    // on possible configurations
    [...]

    // Keep in mind that transient and static fields will be ignored
}
```

#### Customizing accessibility with manual meta model generation

Accessibility on entities :

```java
GQLEntityMetaData metaData = new GQLEntityMetaData("Entity1", Entity1.class, AbstractEntity.class);
// This will prevent "delete method" generation for this entity
metaData.setDeletable(false);
// This will prevent "getById method" and "getAll method" generation for this entity
metaData.setReadable(false);
// This will prevent "save method" generation for this entity
metaData.setSaveable(false);
```

Accessibility on attributes :

You can also configure the possibility to read, write, nullify and filter on entity attributes. By default all entity attributes are readable, writeable, nullable, and filterable.

```java
// Example with a scalar attribute
GQLAttributeScalarMetaData attribute = new GQLAttributeScalarMetaData(
    "attributeName", GQLScalarTypeEnum.STRING);
// This will prevent generation of this attribute in schema type for this entity
attribute.setReadable(false);
// This will prevent generation of this attribute in schema input type 
// for this entity save method
attribute.setSaveable(false);
// This will make this attribute "Not nullable" during a creation save operation.
// This configuration is ignored if this attribute is not saveable.
attribute.setNullableForCreate(false);
// This will make this attribute "Not nullable" during an update save operation.
// This configuration is ignored if this attribute is not saveable.
attribute.setNullableForUpdate(false);
// This will disable filtering feature on this attribute in "getAll" method
attribute.setFilterable(false);
```

### Data fetchers

Data fetchers ```graphql.schema.DataFetcher<?>``` are objects that will make the glue between graphQL and your persistence layer. You will have to provide a data fetcher for :
* 'getById' methods for retrieving a single entity by its ID
* 'getAll' methods for retrieving list of entities (with paging, filtering, sorting if needed)
* 'save' methods for creation & update of entities
* 'delete' methods dor deleting a single entity by its ID
* and 1 for each custom method that you want to define
  
You have an abstract class to extend for each of these data fetchers.  
See below examples explaining how to create these data fetchers , then you can use them for [generating the schema](#generate-the-schema)

-> Create the unique DataFetcher for all entities "getById" methods.

```java
private DataFetcher<?> createGetByIdDataFetcher() {
    return new GQLAbstractGetByIdDataFetcher() {
        @Override
        protected Object getById(Class<?> entityClass, String id) {
            // Get the entity by ID from your persistence layer here
            return entity;
        }
    };
}
```

-> Create the unique DataFetcher for all entities "getAll" methods.

```java
private DataFetcher<GQLListLoadResult> createListDataFetcher() {
    return new GQLAbstractGetListDataFetcher() {
        @Override
        protected GQLListLoadResult getAll(
            Class<?> entityClass, GQLListLoadConfig listLoadConfig) {
            // Build the GQLListLoadResult
            return result;
        }
        @Override
        protected Object getById(Class<?> entityClass, String id) {
            // Get the entity by ID from your persistence layer here
            // This is the same method than the one in getById DataFetcher
            // It is used to retrieve entity filters by ID
            return entity;
        }
    };
}
```

-> Create the unique DataFetcher for all entities "save" methods.

```java
private DataFetcher<?> createSaveDataFetchers() {
    return new GQLAbstractSaveDataFetcher() {
        @Override
        protected void save(Object entity) {
            // Run save here
        }
        @Override
        protected Object getOrCreateAndSetProperties(
            Class<?> entityClass,
            GQLDynamicAttributeRegistry dynamicAttributeRegistry,
            Map<String, Object> fieldValueMap) {
            // See below an example of code that should be changed 
            // to fit your persistence layer.
            // For simplicity sake, we do not handle embedded entities here.
            // ---------------
            // Get the class from entity name, 
            // it depends on how you registered your entity names.
            final Class<?> entityClass = getClassByEntityName(entityName);
            // Find or create entity
            final String id = (String) fieldValueMap.get(GQLSchemaConstants.FIELD_ID);
            final Optional<?> existing = StringUtils.isEmpty(id) ?
                Optional.empty() : persistence.getById(entityClass, id);
            Object entity = existing.isPresent() ? existing.get() : entityClass.newInstance();
            // Set properties
            fieldValueMap.entrySet().stream().forEach(entry -> {
                final IGQLDynamicAttributeSetter<Object, Object> 
                    dynamicAttributeSetter = dynamicAttributeRegistry.getSetter(entityClass, entry.getKey());
                if (dynamicAttributeSetter == null) {
                    GQLPropertyUtils.setPropertyValue(
                        entity, entry.getKey(), entry.getValue());
                } else {
                    dynamicAttributeSetter.setValue(
                        entity, entry.getValue());
                }
            });
            // ---------------
            return entity;
        }
    };
}
```

-> Create the unique DataFetcher for all entities "delete" methods.

```java
private DataFetcher<GQLDeleteResult> createDeleteDataFetcher() {
    return new GQLAbstractDeleteDataFetcher() {
        @Override
        protected void delete(Class<?> entityClass, String id) {
            // Delete your entity here
        }
    };
}
```

-> Create the unique DataFetcher for all "custom methods".

```java
private DataFetcher<?> createCustomMethodsDataFetcher() {
    // The default implementation should suit your needs.
    return new GQLCustomMethodDataFetcher();
}
```

-> If you want to specify custom DataFetchers for an entity attributes then for each of them you can register a ```com.daikit.graphql.datafetcher.GQLPropertyDataFetcher<?>```.  
This is an advanced topic, in most cases you should return an empty list here.

```java
private List<GQLPropertyDataFetcher<?>> createPropertyDataFetchers() {
    return Collections.emptyList();
}
```

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