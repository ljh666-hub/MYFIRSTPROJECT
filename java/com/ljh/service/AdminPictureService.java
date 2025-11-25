package com.ljh.service;

import java.util.List;

public interface AdminPictureService {
    boolean insert(String imageUrl);

    boolean deleteAll();

    List<String> selectAll();
}
