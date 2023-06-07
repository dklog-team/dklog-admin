package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.entity.Image;
import kr.dklog.admin.dklogadmin.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {
    @Value("${image.root-url}")
    private String imageRootUrl;

    private final ImageRepository imageRepository;

    public List<String> getImages(int pageNum){
        PageRequest of = PageRequest.of(pageNum, 6);
        List<Image> imageNameList = imageRepository.findAll(of).getContent();
        List<String> imageList = imageNameList.stream().map(imageName -> imageRootUrl + "\\" + imageName.getStoreName() + imageName.getFileType()).collect(Collectors.toList());
        imageList.forEach(System.out::println);
        return imageList;
    }
}
