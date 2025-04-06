package com.example.backend_scaffold.application.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * API错误类
 * <p>
 * 用于封装API错误信息的数据传输对象
 * </p>
 *
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    /**
     * 错误状态码
     */
    private int status;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误发生时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 错误详情列表
     */
    @Builder.Default
    private List<String> errors = new ArrayList<>();

    /**
     * 错误路径
     */
    private String path;

    /**
     * 添加错误详情
     *
     * @param error 错误详情
     */
    public void addError(String error) {
        this.errors.add(error);
    }

    /**
     * 添加多个错误详情
     *
     * @param errors 错误详情列表
     */
    public void addErrors(List<String> errors) {
        this.errors.addAll(errors);
    }
}