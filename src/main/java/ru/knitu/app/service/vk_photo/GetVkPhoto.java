package ru.knitu.app.service.vk_photo;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.knitu.app.model.UserVk;
import ru.knitu.app.model.VkPhoto;
import ru.knitu.app.repository.TempVkPhotoRepo;
import ru.knitu.app.repository.UsersVkRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetVkPhoto {
    @Autowired
    UsersVkRepo usersVkRepo;
    @Autowired
    TempVkPhotoRepo tempVkPhotoRepo;
    UserVk userVk;
    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient);
    GetResponse getResponse;

    public void getVkphoto(int id) throws ClientException, ApiException {
        VkPhoto vkPhoto = new VkPhoto();
        userVk = usersVkRepo.findUserVkById(id);
        UserActor userActor = new UserActor(userVk.getUserId(),userVk.getAccessToken());
        List<Photo> list = new ArrayList<Photo>();
        list = vk.photos().get(userActor)
                .ownerId(userVk.getUserId())
                .albumId("wall")
                .execute().getItems();
        List<String> dateList = new ArrayList<String>();
        List<String> urlList = new ArrayList<String>();
        for(Photo photo : list){
            String json = photo.toString().replaceAll("Photo","").replace('=',':');
            JSONObject jsonObject = new JSONObject(json);
            vkPhoto.setUrl((String) jsonObject.get("photo604"));
            vkPhoto.setDate((Integer) jsonObject.get("date"));
            dateList.add(String.valueOf(vkPhoto.getDate()));
            urlList.add(String.valueOf(vkPhoto.getUrl()));
        }
        tempVkPhotoRepo.saveListOfUrl(urlList);
        tempVkPhotoRepo.saveListOfDate(dateList);
    }
}
