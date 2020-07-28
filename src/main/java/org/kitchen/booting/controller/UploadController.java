package org.kitchen.booting.controller;

import lombok.extern.log4j.Log4j;
import org.kitchen.booting.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Log4j
public class UploadController {

    @Autowired
    private RecipeService recipeService;

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file)
    {
        try{
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
