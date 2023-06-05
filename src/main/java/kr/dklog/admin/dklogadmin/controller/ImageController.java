package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping()
    public ResponseEntity ImageAllList(int pageNum){
        System.out.println(pageNum);
        List<String> images = imageService.getImages(pageNum);
        System.out.println(images.get(0));
        return ResponseEntity.ok(images);
    }
}
