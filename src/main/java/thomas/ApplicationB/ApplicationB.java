package thomas.ApplicationB;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.math.*;

@SpringBootApplication
@RestController
public class ApplicationB {

    RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationB.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8082"));
        app.run(args);
    }

    @Operation(summary = "Hello world",description = "hello world",hidden = true)
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Goodbye %s!", name);
    }

    @Operation(summary = "va chercher la liste des heros dans la premiere api")
    @GetMapping("/hero")
    public List<LinkedHashMap> getHeroes(){
        String fooResourceUrl = "http://localhost:8081/hero";
        ResponseEntity<List> response = restTemplate.getForEntity(fooResourceUrl , List.class);
    return response.getBody();
    }

    @Operation(summary = "renvoie un nom au hasard")
    @GetMapping("/random-name")
    public String getRandomName(){
        ResponseEntity<String> response = restTemplate.getForEntity("https://random-word-api.herokuapp.com/word", String.class);
        return response.getBody().replace("[","").replace("]","");
    }

    @Operation(summary = "Renvoie un personnage existant au hasard")
    @GetMapping("/random")
    public LinkedHashMap getRandomHero(){
        List<LinkedHashMap> hl=getHeroes();
        int rid = (int) (Math.random()*hl.size())+0;
        return hl.get(rid);
    }

    @Operation(summary = "Créé un hero avec un nom random et le renvoie vers l'application A")
    @PostMapping("/random")
    public String createRandomHero(){
        ResponseEntity<Object> response = restTemplate.postForEntity("http://localhost:8081/hero", getRandomName(),Object.class);
        return response.getBody().toString();
    }


}
