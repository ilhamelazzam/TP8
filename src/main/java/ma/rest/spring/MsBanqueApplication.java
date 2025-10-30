package ma.rest.spring;

import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.TypeCompte;
import ma.rest.spring.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MsBanqueApplication {
    public static void main(String[] args) { SpringApplication.run(MsBanqueApplication.class, args); }

    @Bean CommandLineRunner seed(CompteRepository repo) {
        return args -> {
            if (repo.count()==0) {
                repo.save(new Compte(null, 9000,  new Date(), TypeCompte.COURANT));
                repo.save(new Compte(null,12000,  new Date(), TypeCompte.EPARGNE));
                repo.save(new Compte(null, 4500,  new Date(), TypeCompte.COURANT));
            }
        };
    }
}
