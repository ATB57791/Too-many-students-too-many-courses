package tikape.runko;

import spark.*;
import java.util.*;
import spark.template.thymeleaf.*;
import java.sql.*;
import static java.util.Optional.empty;
import static spark.Spark.port;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        Database db = new Database("jdbc:sqlite:Aseluvat.db");
        AseDao aseDao = new AseDao(db);
        VarusmiesDao varusmiesDao = new VarusmiesDao(db);
        KayttooikeusDao kayttooikeusDao = new KayttooikeusDao(db);

        Spark.get("/aseet/:id", (req, res) -> { // ase-sivu
            int a = Integer.parseInt(req.params(":id"));

            Ase ase = aseDao.findOne(a);

            List<Varusmies> oikeudelliset = kayttooikeusDao.AseeseenOikeutetut(a);

            HashMap map = new HashMap<>();
            map.put("ase", ase);
            map.put("oikeudelliset", oikeudelliset);

            return new ModelAndView(map, "ase");
        }, new ThymeleafTemplateEngine());

        Spark.post("/aseet/:id", (req, res) -> { // kayttooikeuden lisays tietylle aseelle!

            String varusmiehenHetu = req.queryParams("varusmiehenHetu");
            String aseenNumero = req.params(":id");
            Varusmies vm = varusmiesDao.findOne(varusmiehenHetu);
            Ase ase = aseDao.findOne(Integer.parseInt(aseenNumero));

            if (vm != null && ase != null) {
                Kayttooikeus ko = new Kayttooikeus(vm.getHetu(), ase.getNumero());
                kayttooikeusDao.saveOrUpdate(ko);
            }

            res.redirect("/aseet/" + aseenNumero);

            return "";
        });
        Spark.post("/aseet/:id", (req, res) -> { // kayttooikeuden lisays tietylle aseelle!

            String varusmiehenHetu = req.queryParams("varusmiehenHetu");
            String aseenNumero = req.params(":id");
            Varusmies vm = varusmiesDao.findOne(varusmiehenHetu);
            Ase ase = aseDao.findOne(Integer.parseInt(aseenNumero));

            if (vm != null && ase != null) {
                Kayttooikeus ko = new Kayttooikeus(vm.getHetu(), ase.getNumero());
                kayttooikeusDao.saveOrUpdate(ko);
            }

            res.redirect("/aseet/" + aseenNumero);

            return "";
        });

        Spark.get("/aseet", (req, res) -> {

            HashMap map = new HashMap<>();
            List<Ase> aseet = aseDao.findAll();
            map.put("aseet", aseet);
            return new ModelAndView(map, "aseet");
        }, new ThymeleafTemplateEngine());

        Spark.post("/varusmiehet/:id", (req, res) -> { // kayttooikeuden lisays tietylle aseelle!

            String varusmiehenHetu = req.params(":id");
            String aseenNumero = req.queryParams("aseenNumero");
            Ase ase = aseDao.findOne(Integer.parseInt(aseenNumero));
            Varusmies vm = varusmiesDao.findOne(varusmiehenHetu);

            if (vm != null && ase != null) {
                Kayttooikeus ko = new Kayttooikeus(vm.getHetu(), ase.getNumero());
                kayttooikeusDao.saveOrUpdate(ko);
            }

            res.redirect("/varusmiehet/" + varusmiehenHetu);

            return "";
        });

        Spark.get("/varusmiehet/:id", (req, res) -> {

            Varusmies varusmies = varusmiesDao.findOne(req.params(":id"));
            List<Ase> aseetJoihinOikeus = kayttooikeusDao.AseetJoihinLupa(varusmies.getHetu());

            HashMap map = new HashMap<>();
            map.put("varusmies", varusmies);
            map.put("aseet", aseetJoihinOikeus);
            return new ModelAndView(map, "varusmies");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmiehet", (req, res) -> {

            Map map = new HashMap();
            List<Varusmies> varusmiehet = varusmiesDao.findAll();
            map.put("varusmiehet", varusmiehet);
            return new ModelAndView(map, "varusmiehet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/paasivu", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "paasivu");
        }, new ThymeleafTemplateEngine());
        
                Spark.get("/", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "paasivu");
        }, new ThymeleafTemplateEngine());

        Spark.get("/aseenHaku", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "aseenHaku");
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmiehenHaku", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "varusmiehenHaku");
        }, new ThymeleafTemplateEngine());

        Spark.get("/lisaaAse", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "lisaaAse");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaaAse", (req, res) -> {

            String aseenNimi = req.queryParams("aseenNimi");
            String aseenNumero = req.queryParams("aseenNumero");
            System.out.println(aseenNimi + aseenNumero);
            Ase ase = new Ase(aseenNimi, Integer.parseInt(aseenNumero));
            aseDao.saveOrUpdate(new Ase(aseenNimi, Integer.parseInt(aseenNumero)));

            Map map = new HashMap();
            return new ModelAndView(map, "lisaaAse_ok");

        }, new ThymeleafTemplateEngine());

        Spark.post("/varusmiehenHaku", (Request req, Response res) -> {// hakuominaisuus aseelle tai varusmiehelle
            Map map = new HashMap();
            String varusmiehenHetu = req.queryParams("varusmiehenHetu");
            map.put("aseet", kayttooikeusDao.AseetJoihinLupa(varusmiehenHetu));

            Varusmies varusmies = varusmiesDao.findOne(varusmiehenHetu);
            map.put("varusmies", varusmies);

            if (varusmies == null) {
                return new ModelAndView(map, "eiloytynyt");

            } else {

                return new ModelAndView(map, "varusmies");

            }
        }, new ThymeleafTemplateEngine());

        Spark.post("/aseenHaku", (Request req, Response res) -> {// hakuominaisuus aseelle
            Map map = new HashMap();
            String aseenNumero = req.queryParams("aseenNumero");

            map.put("oikeudelliset", kayttooikeusDao.AseeseenOikeutetut(Integer.parseInt(aseenNumero)));
            Ase ase = aseDao.findOne(Integer.parseInt(aseenNumero));
            map.put("ase", ase);

            if (ase == null) {
                return new ModelAndView(map, "eiloytynyt");

            } else {

                return new ModelAndView(map, "ase");

            }
        }, new ThymeleafTemplateEngine());

        Spark.get("/varusmieslisays", (req, res) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "varusmieslisays");
        }, new ThymeleafTemplateEngine());

        Spark.post("/varusmieslisays", (req, res) -> { // kayttooikeuden lisays tietylle aseelle!
            String varusmiehenHetu = req.queryParams("varusmiehenHetu");
            Map map = new HashMap();

            Varusmies lisattava = new Varusmies(req.queryParams("varusmiehenNimi"), varusmiehenHetu);
            varusmiesDao.saveOrUpdate(lisattava);
            return new ModelAndView(map, "varusmieslisays_ok");

        }, new ThymeleafTemplateEngine());

        Spark.post("/aseenPoisto/:id", (req, res) -> { // Aseen poisto
            int aseenNumero = Integer.parseInt(req.params(":id"));
            aseDao.delete(aseenNumero);
            res.redirect("/aseet");
            return "";

        });
        Spark.post("/varusmiehenPoisto/:id", (req, res) -> { // Aseen poisto
            String hetu = req.params(":id");
            varusmiesDao.delete(hetu);
            res.redirect("/varusmiehet");
            return "";

        });
        Spark.post("/aluvanPoisto/:id/:jd", (req, res) -> { // Aseen poisto
            String hetu = req.params(":id");
            int aseenNumero = Integer.parseInt(req.params(":jd"));
            kayttooikeusDao.delete(hetu, aseenNumero);
            res.redirect("/aseet/" + aseenNumero);
            return "";

        });
        Spark.post("/vluvanPoisto/:id/:jd", (req, res) -> { // Aseen poisto
            String hetu = req.params(":id");
            int aseenNumero = Integer.parseInt(req.params(":jd"));
            kayttooikeusDao.delete(hetu, aseenNumero);
            res.redirect("/varusmiehet/" + hetu);
            return "";

        });

    }

}
