package com.sniffer.UI;

import com.sniffer.UI.configWindow.ConfigWindow;
import com.sniffer.util.DialogHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;


/**
 * 主窗口类，继承Application，通过fmxl构造窗口，实例化controller
 * @author wxy
 * @version 1.0
 */
public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        ConfigWindow config = new ConfigWindow();
        Stage configStage = new Stage();
        config.start(configStage);
        configStage.showAndWait();


        URL location = getClass().getResource("main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();


        primaryStage.setTitle("Packet Sniffer");
        primaryStage.setScene(new Scene(root, 1600, 800));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(900);
        primaryStage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + File.separator + "icon.png"));
        primaryStage.show();

        controller = fxmlLoader.getController();
        controller.init();



        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(DialogHelper.popConfirmationDialog("确认","确认退出？")) {
                    if (DialogHelper.popConfirmationDialog("确认", "是否保存当前结果至txt文本中？"))
                        controller.saveToFile();
                    System.exit(0);
                }
                event.consume();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
