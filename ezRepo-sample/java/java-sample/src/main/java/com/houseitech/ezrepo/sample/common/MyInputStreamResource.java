package com.houseitech.ezrepo.sample.common;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public class MyInputStreamResource extends InputStreamResource {
    private long length;
    private String fileName;

    public MyInputStreamResource(InputStream inputStream, long length, String fileName) {
        super(inputStream);
        this.length = length;
        this.fileName = fileName;
    }

    @Override
    public String getFilename() {
        return fileName;
    }

    @Override
    public long contentLength() {
        long estimate = length;
        return estimate == 0 ? 1 : estimate;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
