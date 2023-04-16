package ma.enset.repositories;

import ma.enset.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRespository extends JpaRepository<Patient,Long> {
 Page<Patient> findByNomContains(String Recherche, Pageable pageable);






}
