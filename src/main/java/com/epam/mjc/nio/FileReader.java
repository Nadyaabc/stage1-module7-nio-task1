package com.epam.mjc.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file.getAbsoluteFile(), "r")) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) randomAccessFile.length());

            int bytesRead = fileChannel.read(buffer);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    stringBuilder.append((char) buffer.get());
                }
                buffer.clear();
                bytesRead = fileChannel.read(buffer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String[] array;
        Profile profile;
        profile = new Profile();
        array = stringBuilder.toString().split("\n");
        String[] arrayName = array[0].split(": ");
        profile.setName(arrayName[1].trim());
        String[] arrayAge = array[1].split(": ");
        profile.setAge(Integer.parseInt(arrayAge[1].trim()));
        String[] arrayMail = array[2].split(": ");
        profile.setEmail(arrayMail[1].trim());
        String[] arrayPhone = array[3].split(": ");
        profile.setPhone(Long.parseLong(arrayPhone[1].trim()));
        return profile;

    }
}
