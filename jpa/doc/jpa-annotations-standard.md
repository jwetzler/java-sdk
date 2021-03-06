---

layout: doc
title: Standard JPA Annotations

---
# Standard JPA Annotations

JPA annotations enable you to define how the JPA provider persists your Java class.

**Note**: You can associate an annotation with each field or the getter method for the field. You must be consistent
within a class by using one approach or the other. If you add one annotation to a field and another to a getter method,
an error occurs. We recommend that you annotate the field instead of the getter method.

The [JPA 2.0 specification (JSR 317)](http://jcp.org/aboutJava/communityprocess/final/jsr317/index.html) lists all standard JPA annotations. [JPA 2.0 Specification Support](jpa-spec-support) lists the areas of the JPA 2.0 specification that are supported by the Database.com JPA provider and how Database.com differs from the standard JPA specification.

Some common standard JPA annotations are:

## @Entity
Marks a Java class as persistent. Each Java class must include an <code>@Entity</code> annotation.

When you start up your application, each class that's annotated with <code>@Entity</code> is loaded and synchronized with Database.com. For more details, see [schema creation](jpa-config-persistence#schemaProps) and [schema deletion](jpa-config-persistence#schemaDeleteProps) properties.

For sample syntax for standard and custom entities, see [@Table](#TableAnnotation).

The optional name attribute in <code>@Entity</code> specifies the entity name. If the name attribute is not specified, the entity
name defaults to the unqualified name of the entity class. If the name attribute is specified, you use the name to refer to the entity in [JPQL queries](jpa-queries).

**Note**: The value for the name attribute in <code>@Entity</code> doesn't have to match the value for the name attribute in <code>@Table</code>.

<a name="basicAnnotation"> </a>
## @Basic
Explicitly declares a field in a class as persistent. To make a field in a class persistent, its type must either be one that is persisted by default or you must explicitly declare it as persistent by using the <code>@Basic</code> annotation. The [DataNucleus
documentation](http://www.datanucleus.org/products/accessplatform/jpa/types.html) lists a table of common Java data types and indicates whether each is persisted by default.

## @GeneratedValue
Indicates that the identity value is generated by the database. This is mostly used for primary keys. Only the <code>GenerationType.IDENTITY</code> strategy is supported.

## @Id
Marks a field to use for the identity (primary key) of the class. Database.com entities always have a primary key. The Database.com JPA provider doesn't support all JPA standard primary types. For more information, see [Primary Keys](jpa-provider#primaryKeys).

<a name="TableAnnotation"> </a>
## @Table
Specifies an existing Database.com entity (object) name. The name attribute in <code>@Table</code> must match the underlying Database.com type; for example, User or MyCustomObject__c.

This is a sample for a standard User entity:

    @Entity
    @Table(name="User")
    public class User {
        ...
    }

**Note**: If you omit the @Table(name="User") annotation, the User class maps to a custom User__c entity instead of the standard User entity.

This is a sample for a custom MyCustomObject__c entity:

    @Entity
    @Table(name="MyCustomObject__c")
    public class MyCustomObject {
        ...    
    }

## @Version
Defines a field as holding the version for the class.

If you are using optimistic concurrency, you must include a lastModifiedDate field in your entity as follows:

    @Version
    private java.util.Calendar lastModifiedDate;

For information on usage of the <code>@ManyToOne</code> and <code>@OneToMany</code> annotations, see [Relationship Fields](database-com-datatypes#relFields).

## Required and Unique Fields

Use the <code>@Column</code> standard annotation to mark fields as required or unique.

### Required Fields
Use a <code>@Column(nullable = false)</code> annotation to mark a field as required.

### Unique Fields
Use a <code>@Column(unique = true)</code> annotation to mark a field as unique. We recommend that you mark unique fields
to be required too.

**Note**: The <code>@UniqueConstraint</code> annotation is not supported.

## Sample Class Using Standard Annotations
The following sample Java class uses standard JPA annotations.

    import java.util.Date;
    import javax.persistence.*;
    
    @Entity
    public class Student
    {
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        String id;
    
        @Basic
        // Explicitly marked as persistent. This is not necessary as String
        // fields are persisted by default
        private String firstName;
    
        // No annotation but String fields are persisted by default
        private String lastName;
    
        // No annotation but String fields are persisted by default
        private String email;
    
        // No annotation but Date fields are persisted by default
        private Date dateOfBirth;
    
        // getters and setters
        public String getId() {
            return id;
    
        public String getFirstName() {
            return firstName;
        } 
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        } 
    
        public String getLastName() {
            return lastName;
        } 
        public void setLastName(String lastName) {
            this.lastName = lastName;
        } 
    
        public String getEmail() {
            return email;
        } 
        public void setEmail(String email) {
            this.email = email;
        } 
    
        public Date getDateOfBirth() {
            return dateOfBirth;
        } 
        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        } 
    
    }
