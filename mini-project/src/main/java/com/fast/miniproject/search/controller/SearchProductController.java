package com.fast.miniproject.search.controller;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.global.response.PageResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.search.service.SearchProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"검색 서비스"}, description = "검색 결과 반환 하는 서비스")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
@CrossOrigin(origins = "*")
public class SearchProductController {

    private final SearchProductService searchProductService;
    public static final int PAGE_SIZE = 10;

    @ApiOperation(value = "검색 결과 반환", notes = "검색어에 따른 상품 리스트 페이징과 함께 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchTarget", value = "검색 Target (name, price, brand) default = name"),
            @ApiImplicitParam(name = "searchKeyword", value = "검색어 default = \"\"(전체 검색)"),
            @ApiImplicitParam(name = "sortTarget", value = "정렬 Target (name, price, brand) default = name"),
            @ApiImplicitParam(name = "sortDirection", value = "정렬 방식 (ASC, DESC) default = ASC(오름차순)"),
            @ApiImplicitParam(name = "page", value = "페이지 번호 default = 1"),
            @ApiImplicitParam(name = "isChecked", value = "체크박스 체크 여부 default = false")
    })
    /*
    위처럼 스웨거 설정을 하면 파라미터 순서가 알파벳 타입으로 변경됨 (Apiparam도 동일)
    구글링 + 챗 gpt까지 동원해봤지만 방법을 못찾음
    그래도 설명이 없는것보단 있는게 나을거 같아서 추가
     */
    @GetMapping
    public ResponseDTO<PageResponseDTO> getProducts(
            @RequestParam(required = false, defaultValue = "name") String searchTarget,
            @RequestParam(required = false, defaultValue = "") String searchKeyword,
            @RequestParam(required = false, defaultValue = "name") String sortTarget,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "false") Boolean isChecked,
            @AuthenticationPrincipal LoginReqDTO loginReqDTO
    ) {
        PageRequest pageRequest = null;

        try {
            int intPage = Integer.parseInt(page);
            pageRequest = PageRequest.of(intPage - 1, PAGE_SIZE);
            //정상적인 범위 내의 페이지 번호면 해당 페이지로
        } catch (IllegalArgumentException e) {
            pageRequest = PageRequest.of(0, PAGE_SIZE);
            //음수나 오버플로 발생시키는 페이지 번호면 0번페이지로
            log.info(e.getMessage());
        }
        PageResponseDTO pageResponseDTO = null;
        if (isChecked) {
            pageResponseDTO = new PageResponseDTO(searchProductService.searchProducts(searchTarget, searchKeyword, sortTarget, sortDirection, pageRequest, loginReqDTO));
        } else {
            pageResponseDTO = new PageResponseDTO(searchProductService.searchProducts(searchTarget, searchKeyword, sortTarget, sortDirection, pageRequest));
        }
        return new ResponseDTO<PageResponseDTO>(pageResponseDTO);
    }
}