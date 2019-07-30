package ru.knitu.app.controller;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.knitu.app.helper.TempUserActor;
import ru.knitu.app.model.UserVk;
import ru.knitu.app.repository.TempVkPhotoRepo;
import ru.knitu.app.repository.UsersVkRepo;
import ru.knitu.app.service.authorize.AuthorizationServiceImpl;
import ru.knitu.app.service.vk_photo.GetVkPhoto;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


@Controller
public class HomeController {
    File fileProperty = new File("C:\\Users\\arina\\AgregatorVk\\src\\main\\resources\\application.properties");
    Properties properties = new Properties();

    private static Integer appId;

    private final String redirectUri = "http://localhost:8080/getcode";


    @Autowired
    UsersVkRepo usersVkRepo;
    @Autowired
    AuthorizationServiceImpl authorizationService;
    @Autowired
    GetVkPhoto getVkPhoto;
    @Autowired
    TempVkPhotoRepo tempVkPhotoRepo;
    @Autowired
    TempUserActor tempUserActor;

    @GetMapping("/home")
    public String getHomePage( ModelMap model) throws ClientException, ApiException, IOException {
        UserVk userVk = usersVkRepo.findFirstByUserId(tempUserActor.userActor.getId());
        getVkPhoto.getVkphoto(userVk.getId());
        tempVkPhotoRepo.setImage();
        tempVkPhotoRepo.setDate();
        tempVkPhotoRepo.setTempVkPhoto();
        model.addAttribute("image",tempVkPhotoRepo.listOfTempPhoto);
        return "home";
    }
    @GetMapping("/main")
    public String getHomePage() {
        return "main";
    }
    @GetMapping (value = "/authorize")
    public String authorize(){
        try {
            properties.load(new FileReader(fileProperty));
        } catch (IOException e) {
            e.printStackTrace();
        }
        appId  = Integer.valueOf(properties.getProperty("appId"));
        System.out.println(appId);
        String authorize = "https://oauth.vk.com/authorize?client_id="+appId+"&display=page&redirect_uri="+redirectUri+"&scope=friends,offline,photos&response_type=code&v=5.101";
        return "redirect:"+authorize;
    }
    @GetMapping (value = "/getcode")
    public String createUserActor(@RequestParam String code) throws ApiException, ClientException {
        authorizationService.authorize(code);
        return "redirect:/main";
    }
}
