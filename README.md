# Spring Boot with GraphQL Query Example

## Book Store
- `/v1/books` is the REST resource which can fetch Books information
- DataFetchers are Interfaces for RuntimeWiring of GraphQL with JpaRepository

## Sample GraphQL Scalar Queries
- Accessible under `http://localhost:8091/rest/books`
- Usage for `allBooks`
```
{
   allBooks {
     isn
     title
     authors
     publisher
   }
 }
```
- Usage for `book`
```
  {
   book(id: "1402728131") {
     title
     authors
     publisher
   }
```
- Combination of both `allBooks` and `book`
```
{
   allBooks {
     title
     authors
   }
   book(id: "1402728131") {
     title
     authors
     publisher
   }
 }
```
