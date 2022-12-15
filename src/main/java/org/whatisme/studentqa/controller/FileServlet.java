package org.whatisme.studentqa.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/file")
@MultipartConfig
@Slf4j
public class FileServlet extends HttpServlet {
    private final static String filePath = "file";
    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("jpg", "image/jpeg");
        map.put("jpeg", "image/jpeg");
        map.put("gif", "image/gif");
        map.put("png", "image/png");
        map.put("bmp", "application/x-bmp");
        map.put("webp", "image/webp");
        map.put("pdf", "application/pdf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String file = req.getParameter("filename");
        if(file==null){
            log.error("图片未找到");
            return;
        }
        String path = filePath + "/" + file;
        resp.setCharacterEncoding("utf-8");
        String suffix = file.substring(file.lastIndexOf("."), file.length() - 1);
        if (map.containsKey(suffix)) {
            resp.setContentType(map.get(suffix));
        } else {
            resp.setHeader("Content-Disposition", "attachment; filename=" + file);
            resp.setContentType("application/octet-stream");
        }
        try (FileInputStream fis = new FileInputStream(path)) {
            ServletOutputStream out = resp.getOutputStream();
            byte[] bt = new byte[1024];
            int length = 0;
            while ((length = fis.read(bt)) != -1) {
                out.write(bt, 0, length);
            }
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Part> parts = req.getParts();
        HashMap<String, Object> ret = new HashMap<>();
        if (parts.isEmpty()) {
            ret.put("success", 0);
            ret.put("message", "上传文件为空");
            resp.getWriter().println(JSONObject.toJSONString(ret));
            return;
        }
        Part part = (Part) parts.toArray()[0];
        String disposition = part.getHeader("Content-Disposition");
        String suffix = disposition.substring(disposition.lastIndexOf("."), disposition.length() - 1);
        String filename = UUID.randomUUID() + suffix;
        InputStream is = part.getInputStream();
        FileOutputStream fos = new FileOutputStream(filePath + "/" + filename);
        byte[] bty = new byte[1024];
        int length = 0;
        while ((length = is.read(bty)) != -1) {
            fos.write(bty, 0, length);
        }
        fos.close();
        is.close();
        ret.put("success", 1);
        ret.put("url", "file?filename=" + filename);
        resp.getWriter().println(JSONObject.toJSONString(ret));
    }
}
