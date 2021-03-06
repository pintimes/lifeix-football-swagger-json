package com.football.api.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.venizeng.swagger.read.CombineJsons;

public class GenerateJSON {

    public static void main(String[] args) throws Exception {
        File rootDir = new File("src/main/resources/");
        File[] files = rootDir.listFiles();
        for (File temp : files) {
            String root = temp.getAbsolutePath();
            System.out.println(root);
            try {
                doSomething(root + File.separator, temp.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done,可以在http://192.168.1.18:8101校验json");
    }

    private static void doSomething(String root, String name) throws FileNotFoundException {
        if (name.equals("football")) {
            return;
        }
        File swaggerFile = new File(root + "app/swagger.json");
        File pathRoot = new File(root + "app/paths");
        File definationRoot = new File(root + "definitions");
        String json = new CombineJsons().toSwaggerJson(swaggerFile, definationRoot, pathRoot);
        
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        path = path.substring(0, path.indexOf("target"));
        File dir = new File(path + "/swagger/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir.getAbsolutePath() + File.separator + name + ".json");
        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        out.println(json);
        out.close();
    }

}
