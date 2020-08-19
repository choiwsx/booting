package org.kitchen.booting.controller;

import lombok.extern.log4j.Log4j;
import org.imgscalr.Scalr;
import org.kitchen.booting.domain.AttachFileDTO;
import org.kitchen.booting.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@Log4j
public class UploadController {


    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @Autowired
    private RecipeService recipeService;
    private ResourceLoader resourceLoader;

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
            logger.info("@@@@contentType"+contentType);
            return contentType.startsWith("image");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName, HttpServletRequest request)
    {
        String path = request.getSession().getServletContext().getRealPath("/");
//        String attach_path = "resoureces\\upload\\";
        String uploadFolder = path;
        File file = new File(uploadFolder+fileName);

        ResponseEntity<byte[]> result2 = null;
        logger.info("@@@@@file@@@@"+file);
        try{
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            ResponseEntity<byte[]> tmp  = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
//            logger.info("@@@@tmp@@@@"+tmp);
            if(tmp!=null)
            {
                result2 = tmp;
            }
        }catch(IOException e)
        {
//            e.printStackTrace();
        }
        return result2;
    }
    @PostMapping("deleteFile")
    @ResponseBody
    public ResponseEntity<String> delteFile(String fileName, String type, HttpServletRequest request)
    {
        File file;
        try {
            String path = request.getSession().getServletContext().getRealPath("/");
            String attach_path = "resources\\upload";
            String uploadFolder = path+attach_path;
//            file = new File(uploadFolder+"\\"+ URLDecoder.decode(fileName, "UTF-8"));
            file = new File(path+"\\"+ URLDecoder.decode(fileName, "UTF-8"));
            file.delete();

            if(type.equals("upload")) {
                String largeFileName = file.getAbsolutePath().replace("s_","");
                file = new File(largeFileName);
                file.delete();
            }
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @PostMapping(value= "upload/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile, HttpServletRequest request)
    {
        List<AttachFileDTO> list = new ArrayList<>();
//		String uploadFolder = "C:\\upload";
        String uploadFolderPath = getFolder();
        //make folder
        String path = request.getSession().getServletContext().getRealPath("/");
//        String path = getClass().getClassLoader().getResource(".")+"upload";
//        String path = "upload";
//        String attach_path = "resources\\upload";
        String uploadFolder = path;
        File uploadPath = new File(uploadFolder, uploadFolderPath);
//        Resource fileResource = resourceLoader.getResource("resources\\erd.png");
        ClassPathResource res = new ClassPathResource("resources");
        logger.info("@@@path"+uploadPath);
        if(uploadPath.exists() == false)
        {
            uploadPath.mkdirs();
        }

        for(MultipartFile multipartFile : uploadFile)
        {

            AttachFileDTO attachDTO = new AttachFileDTO();
            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
            attachDTO.setShowFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();
            String exeName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);

            uploadFileName = uuid.toString()+"."+exeName;
            //+"_"+uploadFileName;
            attachDTO.setFileName(uploadFileName);


            try {
                File saveFile = new File(uploadPath, uploadFileName);
                logger.info("@@@saveF"+saveFile);
                logger.info("왜안오는것같지"+attachDTO.toString());
                try {
                    multipartFile.transferTo(saveFile);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }

                attachDTO.setUuid(uuid.toString());
                attachDTO.setUploadPath(uploadFolderPath);
                if(checkImageType(saveFile))
                {
                    attachDTO.setImage(true);
                    logger.info("!!!"+attachDTO.toString());
                    logger.info("!!!2"+uploadPath);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
                    BufferedImage tmpImage = ImageIO.read(saveFile);
                    BufferedImage thumbImage = Scalr.resize( tmpImage , Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 1000, 1000, Scalr.OP_ANTIALIAS);
                    ImageIO.write(thumbImage, exeName , thumbnail);
                    thumbnail.close();

                }
                list.add(attachDTO);
            }
            catch(Exception e)
            {
                logger.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
