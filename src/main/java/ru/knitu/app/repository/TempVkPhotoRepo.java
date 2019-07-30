package ru.knitu.app.repository;

import org.springframework.stereotype.Component;
import ru.knitu.app.helper.TempVkPhoto;
import ru.knitu.app.model.UserVk;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TempVkPhotoRepo {
    public static List<String> listOfURL = new ArrayList<String>();
    public static List<String> listOfDate = new ArrayList<String>();
    public List<String> listOfImage = new ArrayList<String>();
    public List<String> listOfUsualDate = new ArrayList<String>();
    public List<TempVkPhoto> listOfTempPhoto = new ArrayList<TempVkPhoto>();
    public void saveListOfUrl(List<String> inputList){
        listOfURL = inputList;
    }
    public void saveListOfDate(List<String> inputList){
        listOfDate = inputList;
    }
    public  void setImage() throws IOException {
        listOfImage = listOfURL;
    }
    public  void setDate() throws IOException {
        for(String s : listOfDate){
            long unixSeconds = Integer.parseInt(s);
            Date date = new Date(unixSeconds*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            String formattedDate = sdf.format(date);
            listOfUsualDate.add(formattedDate);
        }
    }

    public  void setTempVkPhoto(){
        for(int i=0;i<listOfUsualDate.size();i++){
            TempVkPhoto tempVkPhoto = new TempVkPhoto();
            tempVkPhoto.setDate(listOfUsualDate.get(i));
            tempVkPhoto.setUrl(listOfImage.get(i));
            listOfTempPhoto.add(tempVkPhoto);
        }
    }
}
