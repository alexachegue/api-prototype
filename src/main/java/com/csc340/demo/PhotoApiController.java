package com.csc340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PhotoApiController {
    Map<Integer, Photo> photoDatabase = new HashMap<>();

    /**
     * Welcome endpoint.
     */
    @GetMapping("/welcome")
    public String hello(){
        return "Welcome to the Photo Gallery";
    }

    /**
     * Get random photo
     */
    @GetMapping(value = "/photo/image", produces = "image/jpeg")
    public byte[] getPhotoImage() {
        try {
            String imageUrl = "https://picsum.photos/200/300";
            InputStream inputStream = new URL(imageUrl).openStream();
            return inputStream.readAllBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Get list of photo info
     * @param limit amount of photos
     */
    @GetMapping("/photo")
    public List<Photo> getPhoto(@RequestParam(defaultValue = "5") int limit){
        try{
            // Consuming a restful web service
            String url = "https://picsum.photos/v2/list?page=1&limit=" + limit;
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            List<Photo> photos = new ArrayList<>();

            // Extract relevant info from the response
            for(JsonNode photoNode : root){
                int id = photoNode.get("id").asInt();
                String author = photoNode.get("author").asText();
                String imageUrl = photoNode.get("url").asText();

                photos.add(new Photo(id, author, imageUrl));
            }

            return photos;
        } catch (JsonProcessingException ex){
            Logger.getLogger(PhotoApiController.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }
}
