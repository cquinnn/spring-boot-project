package com.webapp.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
	@Builder.Default
	int code = 1000;
	String message;
	T result;
	/*
	 * public int getCode() { return code; } public void setCode(int code) {
	 * this.code = code; } public String getMessage() { return message; } public
	 * void setMessage(String message) { this.message = message; } public T
	 * getResult() { return result; } public void setResult(T result) { this.result
	 * = result; }
	 */
}