package tikape.runko;

import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Database db = new Database("jdbc:sqlite:Aseluvat.db");
        
        
        AseDao aseDao = new AseDao(db);
        VarusmiesDao varusmiesDao = new VarusmiesDao(db);
        KayttooikeusDao kayttooikeusDao = new KayttooikeusDao(db);

        List<Varusmies> varusmiehet = varusmiesDao.findAll();
        List<Ase> aseet = aseDao.findAll();

        int a1 = 615667;
                    Map m1 = kayttooikeusDao.pala(a1);
            Map m2 = kayttooikeusDao.pala(a1);


        Spark.get("/aseet/:id", (req, res) -> { // ase-sivu
            int a = Integer.parseInt(req.params(":id"));

            Map m = kayttooikeusDao.pala(a);
            ArrayList<String> lista = (ArrayList) m.get("1");

            Ase ase = new Ase(lista.get(0), a);
            ArrayList<Varusmies> oikeudelliset = (ArrayList<Varusmies>) m.get("2");
            HashMap map = new HashMap<>();
            map.put("ase", ase);
            map.put("oikeudelliset", oikeudelliset);

            return new ModelAndView(map, "ase");
        }, new ThymeleafTemplateEngine());

        Spark.post("/aseet/:id", (req, res) -> { // käyttöoikeuden lisäys tietylle aseelle!
            HashMap map = new HashMap<>();
            Integer aseId = Integer.parseInt(req.params(":id"));
            map.put("ase", aseDao.findOne(aseId));
            map.put("varusmiehet", varusmiehet);

            return new ModelAndView(map, "ase");
        });
        Spark.get("/aseet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aseet", aseet);
            return new ModelAndView(map, "aseet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmies/:id", (req, res) -> {
            Varusmies vm = varusmiesDao.findOne(req.queryParams(":id"));
            List<Kayttooikeus> ko = new ArrayList();
            /*
            ko.add(new Kayttooikeus("1", vm, ase1));
            ko.add(new Kayttooikeus("2", vm, ase2));
             */
            System.out.println("Varusmiehen hetu: " + req.params(":id"));
            System.out.println(vm);
            HashMap map = new HashMap<>();
            map.put("aseet", aseet);
            map.put("nimi", vm.getNimi());
            map.put("numero", vm.getHetu());
            map.put("kayttooikeudet", ko);
            return new ModelAndView(map, "varusmies");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmiehet", (req, res) -> {
            Map map = new HashMap();
            map.put("varusmiehet", varusmiehet);
            return new ModelAndView(map, "varusmiehet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/paasivu", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "paasivu");
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
            Ase lisattava = new Ase(req.queryParams("nimi"), Integer.parseInt(req.queryParams("numero")));
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
    }
}
