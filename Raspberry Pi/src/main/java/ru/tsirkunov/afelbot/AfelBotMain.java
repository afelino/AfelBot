package ru.tsirkunov.afelbot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Главный запускаемый файл.
 * 
 * В этом файле осуществляется обработка HTTP-запросов.
 *
 * @author Виталий Циркунов Андреевич
 */
@SpringBootApplication
public class AfelBotMain {

    // Драйвер двигателя (фейковый или настоящий)
    //private final MotorDriver md;
    
    /**
     * Точка входа в программу, с этой процедуры начинается выполнение программы.
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(AfelBotMain.class, args);

//        // Здесь определеяется настоящий драйвер двигателя, либо иммитация будет
//        // использована. Если передан параметр debug, то создается иммиация.
//        MotorDriver md;
//        if(args.length > 0 && "debug".equals(args[0])){
//            md = new NullMotorDriver();
//        } else {
//            md = new DaguMiniDriverComm();
//        }
//                
//        // Создается и запускается http-сервер.
//        AfelBotMain afelBotMain;
//        try {
//            afelBotMain = new AfelBotMain(md);
//            //afelBotMain.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
//        } catch (IOException ioe) {
//            System.err.println("Ошибка запуска сервера:\n" + ioe);
//        }
//        
//        // Мало ли в каком состоянии находится робот в момент остановки http-
//        // сервера, посылаемп команду на остановку.
//        md.stop();

    }
        
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Перечисление бинов.");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

   

//    /**
//     * Конструктур http-сервера.
//     * @param md драйвер двигателей.
//     * @throws IOException ошибка.
//     */
//    public AfelBotMain(MotorDriver md) throws IOException{
//        // сервер создается на порту 8081.
//        //super(8081);
//        
//        this.md = md;
//        System.out.println("Сервер запущен");
//    }
    
    /**
     * Обрабатывает http-запросы.
     * @param session собственно запрос.
     * @return ответ для клиента.
     */
//    @Override
//    public Response serve(IHTTPSession session) {
//        String uri = session.getUri();
//        
//        Response newFixedLengthResponse;
//        switch(uri){
//            case "/": 
//            case "/index.html":
//                
//                String button = session.getParms().get("button");
//                if(button != null){
//                    // Обработка нажатой кнопки
//                    switch (button) {
//                        case "forward":
//                            md.forward();
//                            break;
//                        case "turnleft":
//                            md.left();
//                            break;
//                        case "turnright":
//                            md.right();
//                            break;
//                        case "backward":
//                            md.backward();
//                            break;
//                        case "cameraup":
//                            md.cameraUp();
//                            break;
//                        case "cameraleft":
//                            md.cameraLeft();
//                            break;
//                        case "cameracenter":
//                            md.cameraCenter();
//                            break;
//                        case "cameraright":
//                            md.cameraRight();
//                            break;
//                        case "cameradown":
//                            md.cameraDown();
//                            break;
//                        case "stop":
//                        default:
//                            md.stop();
//                            break;
//                    }
//                }
//                        
//                // Возвращаю содержимое страницы
//                newFixedLengthResponse = newFixedLengthResponse(INDEX_HTML);
//                newFixedLengthResponse.setMimeType(MIME_HTML);
//                return newFixedLengthResponse;
//            
//            case "/style.css":
//                
//                // Здесь возвращается файл стилей.
//                newFixedLengthResponse = newFixedLengthResponse(STYLE_CSS);
//                newFixedLengthResponse.setMimeType("text/css");
//                return newFixedLengthResponse;
//            
//            default:
//                // Выдаст ошибку 404, файл не найден.
//                return super.serve(session);
//        }
//        
//    }    
    
}
