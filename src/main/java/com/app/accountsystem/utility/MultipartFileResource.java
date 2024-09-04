package com.app.accountsystem.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileResource implements MultipartFile {

    private final Resource resource;

    public MultipartFileResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getFilename();
    }

    @Override
    public String getOriginalFilename() {
        return resource.getFilename();
    }

    @Override
    public String getContentType() {
        return "application/octet-stream"; // You can specify content type if needed
    }

    @Override
    public boolean isEmpty() {
    	boolean content = false;
        try {
			 if(resource.contentLength() == 0) content = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return content;
    }

    @Override
    public long getSize() {
        try {
            return resource.contentLength();
        } catch (IOException e) {
            throw new RuntimeException("Error getting file size", e);
        }
    }

    @Override
    public byte[] getBytes() throws IOException {
        return resource.getInputStream().readAllBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(resource.getInputStream(), dest.toPath());
    }
}

