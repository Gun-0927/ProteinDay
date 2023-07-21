package com.blue.bluearchive.admin.service;

import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.repository.CategoryRepository;
import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.service.BoardService;
import com.blue.bluearchive.report.repository.ReportBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final AdminBoardService boardService;
    private final BoardRepository boardRepository;


    //승훈이꺼 추가
    public String normalizeCategoryName(String categoryName) {
        // 공백 및 띄어쓰기 제거
        String trimmedName = categoryName.replaceAll("\\s", "");
        // 소문자로 변환
        String lowercaseName = trimmedName.toLowerCase();
        return lowercaseName;
    }


    public void save(CategoryDto categoryDto) {
        String normalizedCategoryName = normalizeCategoryName(categoryDto.getCategoryName());
        if (isCategoryNameExists(normalizedCategoryName)) {
            throw new IllegalArgumentException("중복된 카테고리 이름입니다. 다른 이름을 입력해주세요.");
        }
        Category category = Category.toCategoryEntity(categoryDto);
        category.setCategoryName(normalizedCategoryName);
        categoryRepository.save(category);
    }


    public boolean isCategoryNameExists(String categoryName) {
        return categoryRepository.existsByCategoryNameIgnoreCase(categoryName);
    }

    public void delete(int id) {
        Category category = getCategoryById(id);
        List<BoardDto> boardList = boardService.getBoardsByCategoryId(category.getCategoryId());
        for (BoardDto board : boardList) {
            boardService.delete(board.getBoardId());
        }
        categoryRepository.deleteById(id);
    }
    public CategoryDto update(CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));

        String newCategoryName = normalizeCategoryName(categoryDto.getCategoryName());
        if (!existingCategory.getCategoryName().equals(newCategoryName) && isCategoryNameExists(newCategoryName)) {
            throw new IllegalArgumentException("중복된 카테고리 이름입니다. 다른 이름을 입력해주세요.");
        }

        existingCategory.setCategoryName(newCategoryName);
        categoryRepository.save(existingCategory);
        return categoryDto;
    }


    //승훈이 추가 끝
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수가 없습니다."));
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categoryEntities = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categoryEntities) {
            categoryDtos.add(modelMapper.map(category, CategoryDto.class));
        }
        return categoryDtos;
    }

    public List<CategoryDto> getTotal() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categoryList) {
            List<Board> boardList = boardRepository.findByCategory(category);
            int boardCount = boardList.size();
            int totalViews = boardList.stream().mapToInt(Board::getBoardCount).sum();
            int totalReports = boardList.stream().mapToInt(Board::getBoardReportsCount).sum();

            // 카테고리의 값을 업데이트
            category.setBoardCount(boardCount);
            category.setTotalViews(totalViews);
            category.setTotalReports(totalReports);
            categoryRepository.save(category);

            // 업데이트된 값을 사용하여 CategoryDto 객체 생성
            CategoryDto categoryDto = CategoryDto.toCategoryDto(category);
            categoryDto.setBoardCount(boardCount);
            categoryDto.setTotalViews(totalViews);
            categoryDto.setTotalReports(totalReports);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
}