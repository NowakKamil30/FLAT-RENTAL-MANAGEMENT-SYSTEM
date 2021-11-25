package pl.kamilnowak.flatrentalmanagementsystem.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableHelper {
    public Pageable countPageable(int page) {
        if(page <= 0){
            page = 1;
        }
        return PageRequest.of((page - 1) , 10);
    }
}
