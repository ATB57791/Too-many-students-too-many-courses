package tikape.runko;

import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection yhteys = DriverManager.getConnection("jdbc:sqlite:Aseluvat.db");
        Statement komento = yhteys.createStatement();
        ResultSet tulos = komento.executeQuery("SELECT 1");
        if (tulos.next()) {
            System.out.println("Tietokanta toimii!");
        } else {
            System.out.println("Tietokanta ei ole käytössä!");
        }

        List<Ase> aseet = new ArrayList();
        Map<String, Ase> aseetNumeroittain = new HashMap();
        Ase ase1 = new Ase("aseennimi1", "1");
        aseet.add(ase1);
        aseetNumeroittain.put(ase1.getNumero(), ase1);
        Ase ase2 = new Ase("aseennimi2", "2");
        aseet.add(ase2);
        aseetNumeroittain.put(ase2.getNumero(), ase2);
        System.out.println(aseet.size());
        Map<String, Varusmies> varusmiehetNumeroittain = new HashMap();
        List<Varusmies> varusmiehet = new ArrayList();
        Varusmies vm1 = new Varusmies("varusmies1", "1");
        varusmiehet.add(vm1);
        Varusmies vm2 = new Varusmies("varusmies2", "2");
        varusmiehet.add(vm2);
        Varusmies vm3 = new Varusmies("varusmies3", "3");
        varusmiehet.add(vm3);
        Varusmies vm4 = new Varusmies("varusmies4", "4");
        varusmiehet.add(vm4);
        System.out.println(varusmiehet.size());
        varusmiehetNumeroittain.put(vm1.getHetu(), vm1);
        varusmiehetNumeroittain.put(vm2.getHetu(), vm2);
        varusmiehetNumeroittain.put(vm3.getHetu(), vm3);
        varusmiehetNumeroittain.put(vm4.getHetu(), vm4);
        System.out.println(varusmiehetNumeroittain.keySet().size());

        Map<Ase, List<Varusmies>> kayttooikeutetut = new HashMap();
        List<Varusmies> ko1 = new ArrayList();
        ko1.add(varusmiehet.get(0));
        ko1.add(varusmiehet.get(2));

        kayttooikeutetut.put(ase1, varusmiehet);
        kayttooikeutetut.put(ase2, ko1);
        Spark.get("/ase/:id", (req, res) -> { // ase-sivu
            Ase ase = aseetNumeroittain.get(req.params(":id"));
            List<Varusmies> varusmieslista = kayttooikeutetut.get(ase);
            List<Kayttooikeus> kayttooikeudet = new ArrayList();
            for (Varusmies varusmies : varusmieslista) {
                kayttooikeudet.add(new Kayttooikeus("", varusmies, ase));
            }
            HashMap map = new HashMap<>();
            map.put("nimi", ase.getNimi());
            map.put("numero", ase.getNumero());
            map.put("kayttooikeudet", kayttooikeudet);
            return new ModelAndView(map, "ase");
        }, new ThymeleafTemplateEngine());

        Spark.post("/ase/:id", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            // lisättävä varusmies
            Varusmies varusmies = new Varusmies(req.queryParams("nimi"), req.queryParams("hetu"));
            // korvaa seuraava koodi
            kayttooikeutetut.get(aseetNumeroittain.get(req.params(":id"))).add(varusmies);
            res.redirect("/ase/" + aseetNumeroittain.get(req.params(":id")).getNumero());
            // ---
            return "";
        });
        Spark.get("/aseet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aseet", aseet);
            return new ModelAndView(map, "aseet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmies/:id", (req, res) -> {
            Varusmies vm = varusmiehetNumeroittain.get(req.params(":id"));
            List<Kayttooikeus> ko = new ArrayList();
            ko.add(new Kayttooikeus("1", vm, ase1));
            ko.add(new Kayttooikeus("2", vm, ase2));
            System.out.println("Varusmiehen hetu: " + req.params(":id"));
            System.out.println(vm);
            HashMap map = new HashMap<>();
            map.put("aseet", aseet);
            map.put("nimi", vm.getNimi());
            map.put("numero", vm.getHetu());
            map.put("kayttooikeudet", ko);
            return new ModelAndView(map, "varusmies");
        }, new ThymeleafTemplateEngine());
        Spark.get("/kayttooikeus/:id", (req, res) -> {
            Ase ase = aseetNumeroittain.get(req.params(":id_ase"));
            Varusmies vm = varusmiehetNumeroittain.get(req.params(":id_varusmies"));
            kayttooikeutetut.get(ase).remove(vm);
            res.redirect("/ase/" + ase.getNumero());
            return "";
        });
        Spark.get("/paasivu", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "paasivu");
        }, new ThymeleafTemplateEngine());
        Spark.get("/varusmiehet", (req, res) -> {
            Map map = new HashMap();
            map.put("varusmiehet", varusmiehet);
            return new ModelAndView(map, "varusmiehet");
        }, new ThymeleafTemplateEngine());
        Spark.get("/haku", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "etsinta");
        }, new ThymeleafTemplateEngine());
        Spark.get("/aselisays", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "lisaaase");
        }, new ThymeleafTemplateEngine());
        Spark.post("/aselisays", (req, res) -> { 
            Ase lisattava = new Ase(req.queryParams("nimi"), req.queryParams("numero"));
            Map map = new HashMap();
            return new ModelAndView(map, "lisaaase_ok");
        }, new ThymeleafTemplateEngine());
        Spark.post("/haku", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            String hakutermi = req.queryParams("hakusana");
            Map map = new HashMap();
            map.put("tulokset", varusmiehet);
            return new ModelAndView(map, "etsinta");
        }, new ThymeleafTemplateEngine());
        Spark.post("/varusmieslisays", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            Varusmies lisattava = new Varusmies(req.queryParams("nimi"), req.queryParams("hetu"));
            Map map = new HashMap();
            return new ModelAndView(map, "lisaavarusmies_ok");
        }, new ThymeleafTemplateEngine());
        Spark.get("/varusmieslisays", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "lisaavarusmies");
        }, new ThymeleafTemplateEngine());
//
//        Spark.get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        Spark.get("/aseet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aseet",aseet);
//            System.out.println("Map aseet ok");
//            return new ModelAndView(map, "aseet");
//        }, new ThymeleafTemplateEngine());
//        Spark.get("/virhe", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aseet",aseet);
//            System.out.println("Map aseet ok");
//            return new ModelAndView(map, "virhe");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.post("/aseet", (req, res) -> {
//            String numero = req.queryParams("numero");
//            if (numero.equals("david")) {
//            res.redirect("/virhe");
//            }
//            String tyyppi = req.queryParams("tyyppi");
//            aseet.add(new Ase(numero).toString());
//            System.out.println(aseet.get(0));
//            System.out.println("Lisäys ok!");
//            res.redirect("/aseet");
//            System.out.println("Uudelleenohjaus ok!");
//            return "";
//        });
//        
//        Spark.get("/index", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/haeOpiskelija", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "haeOpiskelija");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/lisaaOpiskelija", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaOpiskelija");
//        }, new ThymeleafTemplateEngine());
//        
//        Spark.get("/lisaaKurssisuoritus", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaKurssisuoritus");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/lisaaKurssi", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaKurssi");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/listaaKurssinOpiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "listaaKurssinOpiskelijat");
//        }, new ThymeleafTemplateEngine());
//        
//        
//        Spark.get("/lisaaOpiskelijaKurssille", (req, res) -> {
//            HashMap map = new HashMap<>();
//            return new ModelAndView(map, "lisaaOpiskelijaKurssille");
//        }, new ThymeleafTemplateEngine());
//        
    }
}
