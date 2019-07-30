package ru.knitu.app.service.authorize;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.knitu.app.helper.TempUserActor;
import ru.knitu.app.model.UserVk;
import ru.knitu.app.repository.UsersVkRepo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    File fileProperty = new File("C:\\Users\\arina\\AgregatorVk\\src\\main\\resources\\application.properties");
    Properties properties = new Properties();
    @Autowired
    UsersVkRepo usersVkRepo;
    private final String redirectUri = "http://localhost:8080/getcode";

    @Override
    public void authorize(String code) {
        try {
            properties.load(new FileReader(fileProperty));
        } catch (IOException e) {
            e.printStackTrace();
        }
         final Integer appId = Integer.valueOf(properties.getProperty("appId"));

         final String key = properties.getProperty("accsesToken");

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oauth().userAuthorizationCodeFlow(appId, key, redirectUri, code ).execute();
        } catch (ApiException e) {
            throw new UnableToAuthorizeException("Cannot get access token", e);
        } catch (ClientException e1) {
            throw new UnableToAuthorizeException("Cannot get access token", e1);
        }

        TempUserActor.userActor = new UserActor(authResponse.getUserId(),authResponse.getAccessToken());
        System.out.println("----------------------------------");
        System.out.println(TempUserActor.userActor.getId());

        ArrayList<UserVk> counterList = (ArrayList<UserVk>) usersVkRepo.findAll();
        int counter = counterList.size() + 1;
        String name = "User"+counter;
        UserVk user = new UserVk(authResponse.getUserId(),authResponse.getAccessToken(),name);
        usersVkRepo.save(user);
    }
}
