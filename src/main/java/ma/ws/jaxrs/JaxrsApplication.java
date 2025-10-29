package ma.ws.jaxrs;

import ma.ws.jaxrs.entities.Compte;
import ma.ws.jaxrs.entities.TypeCompte;
import ma.ws.jaxrs.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class JaxrsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JaxrsApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository repo){
        return args -> {
            repo.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
            repo.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.COURANT));
            repo.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
            repo.findAll().forEach(System.out::println);
        };
    }
}
