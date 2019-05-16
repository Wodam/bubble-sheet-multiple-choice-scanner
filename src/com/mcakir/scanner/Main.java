package com.mcakir.scanner;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.Scanner;

import com.sun.net.httpserver.HttpServer;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import static com.mcakir.scanner.Util.getSource;
import static com.mcakir.scanner.Util.sout;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception, IOException {

        sout("Listening...");

        createServer();
    }

    public static void createServer() throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/", (exchange -> {
            String respText = "Worked!";
            createImg(exchange.getRequestBody());

            Mat source = Imgcodecs.imread(getSource("sheet-1.jpg"));

            Scanner scanner = new Scanner(source, 20);
            scanner.setLogging(true);
            scanner.scan();

//            Scanner scanner = new Scanner(exchange.getRequestBody());
//            System.out.println(scanner.useDelimiter("\\A").next());

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.setExecutor(null);
        server.start();
    }

    public static void createImg(InputStream data) throws IOException{
        File file = new File(getSource("sheet-1.jpg"));
        Image img = ImageIO.read(data);
        BufferedImage buff = new BufferedImage(3024, 4032, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buff.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(img, 0, 0, 3024, 4032, null);
        g.dispose();
        ImageIO.write(buff, "jpeg", file);
    }
}
