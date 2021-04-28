package com.example.demo.service;

import com.example.demo.model.Painting;
import com.example.demo.repo.PaintingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PaintingRepoImpl implements PaintingRepoService {

    @Autowired
    private PaintingRepo paintingRepo;

    @Override
    public Painting getPainting(Long id) {
        Optional<Painting> p1 = paintingRepo.findById(id);
        Painting painting = p1.isPresent() ? p1.get() : new Painting();
        return painting;
    }



    @Override
    public void deletePainting(Long id) {
        paintingRepo.delete(getPainting(id));
    }

    @Override
    public List<Painting> selectAll() {
       Iterator<Painting> iterator = paintingRepo.findAll().iterator();
       List<Painting> paintings = new ArrayList<Painting>();
        while (iterator.hasNext()) {
            paintings.add(iterator.next());
        }
        return paintings;
    }

    @Override
    public void addPainting(String title,
                            String description,
                            String size,
                            String path,
                            String year,
                            Boolean in_stock) {
        Painting p = new Painting();
        p.setTitle(title);
        p.setDescription(description);
        p.setSize(size);
        p.setPath(path);
        p.setYear(year);
        p.setIn_stock(in_stock);
        paintingRepo.save(p);
    }

    @Override
    public void correctPainting(Long id, String title, String description, String size, String path, String year, Boolean in_stock) throws Exception {
        Painting painting = getPainting( id) ;
        if (painting == null)
            throw new Exception("Painting not found " + painting.getTitle());
        painting.setTitle(title);
        painting.setDescription(description);
        painting.setSize(size);
        painting.setPath(path);
        painting.setYear(year);
        painting.setIn_stock(in_stock);
        paintingRepo.save(painting);
    }
}
