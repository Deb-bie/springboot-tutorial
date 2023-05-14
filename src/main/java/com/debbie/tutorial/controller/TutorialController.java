package com.debbie.tutorial.controller;


//the controller class contains the business logic, that is, what we are trying to achieve in the current application we are building.
// dor this application, one of our business logic is being able to retrieve all published tutorials. another is the ability ti retrieve all tutorials


import com.debbie.tutorial.model.Tutorial;
import com.debbie.tutorial.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;





//the main idea of CORS (Cross Origin Resource Sharing) is to allow a webpage to request additional resources into the browser from other domains.
// that is, the browser may request for additional resources such as an api data using ajax, font files , style sheets and others.
// we need to allow our webpages access data from other sites, so we will have to set up cors
@CrossOrigin(origins = "http://localhost:8081") //for permitting cross-origin requests on specific handler classes
// it marks the class as a controller where every method returns a domain object instead of a view
//it is used to create controllers for rest apis
// it is used to define a controller and to indicate that the return value of the methods should be bound to the web response body
@RestController //it is an annotation which is equals to @Controller + @ResponseBody
//it maps http request with a path to a controller method
//it is used to map web requests onto specific handler classes
// it is used to map the request to the specific method in spring.
// for instance, looking at our what we are trying to achieve here. if a client requests for all tutorials, the request mapping
// maps this request, (/getTutorials) to the specific method that is to handle this request.
// this method is run every time an end-user makes an HTTP request. The method can also be done at the method level as well
@RequestMapping("api/v1") // it declares that all apis url in the controller will  start with /api/v1
public class TutorialController {


    //auto wiring enables you to inject the object dependency implicitly. It cannot be used to inject primitive and string values
    //it is used for automatic dependency injection. that is, when you use the @autowired annotation, you are not required to create a constructor
    // and inject the dependency into it
    //we use @autowired to inject tutorialRepository bean to local variable
    //
    @Autowired
    TutorialRepository tutorialRepository;


    //this annotation is for mapping HTTP GET requests onto specific handler methods. it is a composed annotation that acts
    //as a shortcut for @request mapping annotation
    //it is used to handle get requests.
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(
            //the @requestparam annotation is used to bind a web request parameter to a method parameter
            //they are used to capture query parameters from the url. In our case, the query parameter is title,
            // and we have set the required to false, so even if the request parameter is not available, it should still work
            @RequestParam(required = false)
            String title
    ) {
        try {
            List<Tutorial> tutorials = new ArrayList<>();

            if (title == null) {
//                tutorialRepository.findAll().forEach(tutorial -> tutorials.add(tutorial));
                tutorialRepository.findAll().forEach(tutorials::add);
            }
            else {
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @GetMapping("/tutorials/{id}")
    //the @pathvariable annotation - if the name of the method parameter matches the name of the path variable exactly.
    //it is used to capture values from the url. So with our example, the actual id of the tutorial we are trying to get will be in the request url
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return  new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tutorials/isPublished")
    public ResponseEntity<List<Tutorial>> findByIsPublished(){
        try{
            List<Tutorial> tutorialData = tutorialRepository.findByIsPublished(true);

            if (tutorialData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorialData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/tutorials")
    //the @request  body annotation is used to bind the HTTP request/ response body with a domain object in the method parameter or return type
    //it is an annotation that indicates that a method parameter should be bound to the body of the web request
    //in our  case, we are saying that the Tutorial object should be created from the json in the body of the request. The resulting tutorial
    //can be used to create a new tutorial
    //so in simpler terms , it is used to tell the server that it should look for data in the request body and convert it to a java objec that the application can work with.
    //it is used in post requests, eg. creating a form and others
    public ResponseEntity<Tutorial> createTutorial (@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialRepository
                    .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        }catch (Exception e) {
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    //the @pathvariable tells spring to extract the value of the tutorial id from the request url and use that id in the code below
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("tutorials")
    public ResponseEntity<Tutorial> deleteAllTutorials (){
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
