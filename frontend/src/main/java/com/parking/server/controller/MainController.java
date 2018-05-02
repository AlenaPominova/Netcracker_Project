package com.parking.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String reg() {
        return "login";
    }

    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    public String index1() {
        return "index1";
    }

    @RequestMapping(value = "/staticPage", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/pages/final.htm";
    }



    /*@Autowired
    private UserService userService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private PojoService pojoService;*/




   /* @RequestMapping(value = "/cache_parking/9", method = RequestMethod.GET)
    public String getCacheParking() throws IOException {

        String url = "http://localhost:8086/parking/objects_json/9";// + id;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        //String s = String.valueOf(parkingService.loadAllTest(result));
        //return parkingService.loadAllTest(result);
        return null;

        //return result;
        //return result.get("name").asText();
        // ObjectMapper objectMapper = new ObjectMapper();
        //JsonNode obj = objectMapper.readValue(new URL(url), JsonNode.class);
        //return obj.asText();
        //userService.loadAll(obj);
    }*/


    /*@RequestMapping(value = "/cache_users/26", method = RequestMethod.GET)
    public String getCacheUsers() throws IOException {

        String url = "http://localhost:8086/parking/objects_json/26";// + id;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        //String s = String.valueOf(userService.loadAllTest(result));
        //return s;

        return null;
        //return result;
        //return result.get("name").asText();
        // ObjectMapper objectMapper = new ObjectMapper();
        //JsonNode obj = objectMapper.readValue(new URL(url), JsonNode.class);
        //return obj.asText();
        //userService.loadAll(obj);
    }*/


    /*@RequestMapping(value = "/objects_json/{id}", method = RequestMethod.GET)
    public String getObjectById(@PathVariable("id") long id) throws IOException {

        String url = "http://localhost:8086/parking/objects_json/" + id;
        RestTemplate restTemplate = new RestTemplate();
        JsonNode result = restTemplate.getForObject(url, JsonNode.class);

        return result.get("name").asText();
    }*/
}
