package com.mcakir.scanner;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

import static com.mcakir.scanner.Util.getSource;
import static com.mcakir.scanner.Util.sout;
import static com.mcakir.scanner.Util.getSource;
import static com.mcakir.scanner.Util.sout;

@RestController
public class Controller {

    @PostMapping("/")
    public List index(@RequestBody MultipartFile file) throws Exception {
        Mat source = Imgcodecs.imread(getSource("sheet-1.jpg"));
        Scanner scanner = new Scanner(source, 20);
        scanner.setLogging(true);
        scanner.scan();
        return new ArrayList(){{
            add("source");
        }};
    }
}
