package com.example.demo.service;

import com.example.demo.model.Painting;

import java.util.List;


public interface PaintingRepoService {
    public Painting getPainting(Long id);
    public void deletePainting(Long id);
    public List<Painting> selectAll();
    public void addPainting(String title, String description, String size, String path,String year, Boolean in_stock);

 }
