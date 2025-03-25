package com.example.crud_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud_spring.dto.CourseDTO;
import com.example.crud_spring.dto.CoursePageDTO;
import com.example.crud_spring.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

  private final CourseService courseService;

  public CoursesController(CourseService courseService){
    this.courseService = courseService;
  }

  @Operation(description = "Busca todos os cursos")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Retorna os cursos")
  })
  @GetMapping
  public CoursePageDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page, @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize){
    return courseService.list(page, pageSize);
  }

  @Operation(description = "Busca o curso pelo id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Retorna o curso"),
    @ApiResponse(responseCode = "400", description = "Não existe o curso informado")
  })
  @GetMapping("/{id}")
  public CourseDTO findById(@PathVariable @NotNull @Positive Long id){
    return courseService.findById(id);
  }

  @Operation(description = "Cria um novo curso")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Cria um novo curso"),
    @ApiResponse(responseCode = "400", description = "Não foi possível criar um novo curso informado")
  })
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public CourseDTO create(@RequestBody @Valid @NotNull CourseDTO course){
    return courseService.create(course);
  }

  @Operation(description = "Atualiza um curso pelo id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Atualiza o curso"),
    @ApiResponse(responseCode = "400", description = "Não existe o curso informado")
  })
  @PutMapping("/{id}")
  public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody CourseDTO course){
    return courseService.update(id, course);
  }

  @Operation(description = "Deleta um curso pelo id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Deleta o curso"),
    @ApiResponse(responseCode = "400", description = "Não existe o curso informado")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NotNull @Positive Long id){
    courseService.delete(id);
  }
}
