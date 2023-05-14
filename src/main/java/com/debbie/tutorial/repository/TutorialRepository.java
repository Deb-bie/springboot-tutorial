package com.debbie.tutorial.repository;

import com.debbie.tutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// repository helps us to performs extra operations on the object.
// the repository layer helps us to access the database and gives us extra functionalities as well
// it inherits from the JpaRepository, thereby helping us to get access to the methods and functionalities available in the JpaRepository without implementing them
// it helps us to define our own custom finder methods.

//@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByIsPublished(boolean isPublished);
    List<Tutorial> findByTitleContaining(String title);
}
