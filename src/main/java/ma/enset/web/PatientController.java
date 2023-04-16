package ma.enset.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRespository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//Pour une application nous avons besoin d'un Controller
@Controller
//Injection des dependances via Constructor
@AllArgsConstructor
public class PatientController {
    private PatientRespository patientRespository;

//acces à la methode patient
    @GetMapping(path="/user/index")
    // la methode patient return un vue
    //Module de spring MVC
    public String patient(Model model,
                          //pagination
                           @RequestParam(name="page",defaultValue = "0") int page,
                          //size de page
                          @RequestParam(name="size",defaultValue = "5") int size,
                          @RequestParam(name="Recherche",defaultValue = "") String Recherche

    ){
        Page<Patient> pagePatients=patientRespository.findByNomContains(Recherche,PageRequest.of(page, size));
        // stocker dans le modul
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("Recherche",Recherche);
        return "patients";
   }
@GetMapping("/admin/delete")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public  String delete(long id,String Recherche,int page ){
        patientRespository.deleteById(id);
        return "redirect:/user/index?page="+page+"&Recherche="+Recherche;
}
@GetMapping("/")
    public  String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> patientList(){
        return  patientRespository.findAll();
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping(path="/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "")String Recherche){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRespository.save(patient);
        return "redirect:/user/index?page="+page+"&Recherche="+Recherche;
    }
    @GetMapping("/admin/editPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatients(Model model,Long id,String Recherche,int page){
        Patient patient=patientRespository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("Recherche",Recherche);

        return "editPatients";
    }
}
