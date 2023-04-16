package ma.enset;

import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRespository;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class Devoir4Application {

    public static void main(String[] args) {
        SpringApplication.run(Devoir4Application.class, args);
    }
    // l'exection de programme dans CommandLineRunner
        //@Bean=> execution de fonction CommandLineRunner
        CommandLineRunner commandLineRunner(PatientRespository patientRespository){
        return  args -> {
            //Insertition des patients
            patientRespository.save(new Patient(null,"Najwa",new Date(),false,212));
            patientRespository.save(new Patient(null,"Islam",new Date(),true,122));
            patientRespository.save(new Patient(null,"Khalid",new Date(),true,854));
            patientRespository.save(new Patient(null,"Hania",new Date(),false,300));

            //Affiche la liste des patients
            patientRespository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
        }
        @Bean
        PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        }

}
