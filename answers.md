#Task5
1. Stack Trace
Startup failed with UnsatisfiedDependencyException for petController → petService → petRepository, rooted in PathElementException: Could not resolve attribute 'species'. Cause: the species field was removed from Pet while PetRepository.findAllSpecies() (SELECT DISTINCT p.species) still referenced it; Spring Data validates queries at bootstrap, so the repository bean could not be created and the whole dependency chain collapsed before any SQL ran. 
2. SQL Log 
I enabled SQL logging using:

spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG

3. Fix
I fixed the problem by:
    1. Adding the @GeneratedValue annotation with generation type, identity. In order for the database to automatically assign id to entities
    2. Changed the service so that microchipNumber receives a valid non-null value.
    3. Added the missing species field to the Pet entity.