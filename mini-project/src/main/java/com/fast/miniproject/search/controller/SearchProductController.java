package com.fast.miniproject.search.controller;

import com.fast.miniproject.global.response.PageResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.search.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SearchProductController {

    private final SearchProductService searchProductService;
    public static final int PAGE_SIZE = 5;

    @GetMapping
    public ResponseDTO<PageResponseDTO> getProductByName(@RequestParam String name, @RequestParam(required = false, defaultValue = "0") String page) {
        PageRequest pageRequest = null;
        try {
            int intPage = Integer.parseInt(page);
            pageRequest = PageRequest.of(intPage, PAGE_SIZE);
            //정상적인 범위 내의 페이지 번호면 해당 페이지로
        } catch (IllegalArgumentException e) {
            pageRequest = PageRequest.of(0, PAGE_SIZE);
            //음수나 오버플로 발생시키는 페이지 번호면 0번페이지로
            log.info(e.getMessage());
        }

        PageResponseDTO pageResponseDTO = new PageResponseDTO(searchProductService.searchProductsByName(name, pageRequest));
        return new ResponseDTO<PageResponseDTO>(pageResponseDTO);
    }
}